package com.aaangang.customviews.views;

/**
 * Created by Administrator on 2017/11/1.
 */
public class COMMENT2 {
    //参考自https://github.com/GcsSloop/AndroidNote/tree/master/CustomView
    //感谢GcsSloop，其blog地址：http://www.gcssloop.com/#blog


    //Matrix ####################################################################
/*
本篇的主角Matrix，是一个一直在后台默默工作的劳动模范，虽然我们所有看到View背后都有着Matrix的功劳，但我们却很少
见到它，本篇我们就看看它是何方神圣吧。
Matrix简介
Matrix是一个矩阵，主要功能是坐标映射，数值转换。
[MSCALE_X、MSKEW_X、MTRANS_X]
[MSKEW_Y、MSCALE_Y、MTRANS_Y]
[MPERSP_0、MPERSP_1、MPERSP_2]
Matrix作用就是坐标映射，那么为什么需要Matrix呢? 举一个简单的例子:
我的的手机屏幕作为物理设备，其物理坐标系是从左上角开始的，但我们在开发的时候通常不会使用这一坐标系，而是使用内容区的坐标系。
以下图为例，我们的内容区和屏幕坐标系还相差一个通知栏加一个标题栏的距离，所以两者是不重合的，我们在内容区的坐标系中的内容
最终绘制的时候肯定要转换为实际的物理坐标系来绘制，Matrix在此处的作用就是转换这些数值。
假设通知栏高度为20像素，导航栏高度为40像素,那么我们在内容区的(0，0)位置绘制一个点，最终就要转化为在实际坐标系
中的(0，60)位置绘制一个点。
Matrix特点
作用范围更广，Matrix在View，图片，动画效果等各个方面均有运用，相比与之前讲解等画布操作应用范围更广。
更加灵活，画布操作是对Matrix的封装，Matrix作为更接近底层的东西，必然要比画布操作更加灵活。
封装很好，Matrix本身对各个方法就做了很好的封装，让开发者可以很方便的操作Matrix。
难以深入理解，很难理解中各个数值的意义，以及操作规律，如果不了解矩阵，也很难理解前乘，后乘。
常见误解
1.认为Matrix最下面的一行的三个参数(MPERSP_0、MPERSP_1、MPERSP_2)没有什么太大的作用，在这里只是为了凑数。
实际上最后一行参数在3D变换中有着至关重要的作用，这一点会在后面中Camera一文中详细介绍。
2.最后一个参数MPERSP_2被解释为scale
的确，更改MPERSP_2的值能够达到类似缩放的效果，但这是因为齐次坐标的缘故，并非这个参数的实际功能。

Matrix基本原理
Matrix 是一个矩阵，最根本的作用就是坐标转换，下面我们就看看几种常见变换的原理:
基本变换有4种: 平移(translate)、缩放(scale)、旋转(rotate) 和 错切(skew)。
下面我们看一下四种变换都是由哪些参数控制的。
从上图可以看到最后三个参数是控制透视的，这三个参数主要在3D效果中运用，通常为(0, 0, 1)，不在本篇讨论范围内，
暂不过多叙述，会在之后对文章中详述其作用。

矩阵注意事项
矩阵相乘： C = AB
当矩阵A的列数等于矩阵B的行数时，A与B可以相乘。
矩阵C的行数等于矩阵A的行数，C的列数等于B的列数。
乘积C的第m行第n列的元素等于矩阵A的第m行的元素与矩阵B的第n列对应元素乘积之和。

1.缩放(Scale)
x = m*x0
y = n*y0
[x]    [m 0 0][x0]
[y] =  [0 n 0][y0]
[1]    [0 0 0][ 1]
你可能注意到了，我们坐标多了一个1，这是使用了齐次坐标系的缘故，在数学中我们的点和向量都是这样表示的(x, y)，两者看起来一样，计算
机无法区分，为此让计算机也可以区分它们，增加了一个标志位，增加之后看起来是这样:
(x, y, 1) - 点
(x, y, 0) - 向量
另外，齐次坐标具有等比的性质，(2,3,1)、(4,6,2)...(2N,3N,N)表示的均是(2,3)这一个点。(将MPERSP_2解释为scale这一误解就源于此)。

2.错切(Skew)
错切存在两种特殊错切，水平错切(平行X轴)和垂直错切(平行Y轴)。
水平错切
x = x0 + k*y0
y = y0
[x]    [1 k 0][x0]
[y] =  [0 1 0][y0]
[1]    [0 0 1][ 1]
垂直错切
x = x0
y = k*x0 + y0
[x]    [1 0 0][x0]
[y] =  [k 1 0][y0]
[1]    [0 0 1][ 1]
复合错切
水平错切和垂直错切的复合。
x = x0 + m*y0
y = n*x0 + y0
[x]    [1 m 0][x0]
[y] =  [n 1 0][y0]
[1]    [0 0 1][ 1]

3.旋转(Rotate)
假定一个点 A(x0, y0) ,距离原点距离为 r, 与水平轴夹角为 a 度, 绕原点旋转 b 度, 旋转后为点 B(x, y) 如下:
x0 = r*cos(a)
y0 = r*sin(a)
x = r*cos(a+b) = r*cos(a)*cos(b) - r*sin(a)*sin(b) = x0*cos(b)-y0*sin(b)
y = r*sin(a+b) = r*sin(a)*cos(b) + r*cos(a)*sin(b) = y0*con(b)+x0*sin(b)
[x]    [cos(b) -sin(b) 0][x0]
[y] =  [sin(b)  cos(b) 0][y0]
[1]    [0       0      1][ 1]

4.平移(Translate)
此处也是使用齐次坐标的优点体现之一，实际上前面的三个操作使用 2x2 的矩阵也能满足需求，但是使用 2x2 的矩阵，无法将平移操作加入
其中，而将坐标扩展为齐次坐标后，将矩阵扩展为 3x3 就可以将算法统一，四种算法均可以使用矩阵乘法完成。
x = x0 + m
y = y0 + n
[x]    [1 0 m][x0]
[y] =  [0 1 n][y0]
[1]    [0 0 1][ 1]

Matrix复合原理
其实Matrix的多种复合操作都是使用矩阵乘法实现的，从原理上理解很简单，但是，使用矩阵乘法也有其弱点，后面的操作可能会影响到前面到操作，
所以在构造Matrix时顺序很重要。
我们常用的四大变换操作，每一种操作在Matrix均有三类,前乘(pre)，后乘(post)和设置(set)，可以参见文末对Matrix方法表，由于矩阵乘法不
满足交换律，所以前乘(pre)，后乘(post)和设置(set)的区别还是很大的。
前乘(pre)
前乘相当于矩阵的右乘：
M = M0*S
这表示一个矩阵与一个特殊矩阵前乘后构造出结果矩阵。
后乘(post)
前乘相当于矩阵的左乘：
M = S*M0
这表示一个矩阵与一个特殊矩阵后乘后构造出结果矩阵。
设置(set)
设置使用的不是矩阵乘法，而是直接覆盖掉原来的数值，所以，使用设置可能会导致之前的操作失效。

如何理解和使用 pre 和 post ？
不要去管什么先后论，顺序论，就按照最基本的矩阵乘法理解。
pre  : 右乘， M‘ = M*A
post : 左乘， M’ = A*M

那么如何使用？
正确使用方式就是先构造正常的 Matrix 乘法顺序，之后根据情况使用 pre 和 post 来把这个顺序实现。
还是用一个最简单的例子理解，假设需要围绕某一点旋转。
可以用这个方法 xxxRotate(angle, pivotX, pivotY) ,由于我们这里需要组合构造一个 Matrix，所以不直接使用这个方法。
首先，有两条基本定理：
所有的操作(旋转、平移、缩放、错切)默认都是以坐标原点为基准点的。
之前操作的坐标系状态会保留，并且影响到后续状态。
基于这两条基本定理，我们可以推算出要基于某一个点进行旋转需要如下步骤：
1. 先将坐标系原点移动到指定位置，使用平移 T
2. 对坐标系进行旋转，使用旋转 S (围绕原点旋转)
3. 再将坐标系平移回原来位置，使用平移 -T
具体公式如下：
M 为原始矩阵，是一个单位矩阵， M‘ 为结果矩阵， T 为平移， R为旋转
M' = M*T*R*-T = T*R*-T
按照公式写出来的伪代码如下：
Matrix matrix = new Matrix();
matrix.preTranslate(pivotX,pivotY);
matrix.preRotate(angle);
matrix.preTranslate(-pivotX, -pivotY);
围绕某一点操作可以拓展为通用情况，即：
Matrix matrix = new Matrix();
matrix.preTranslate(pivotX,pivotY);
// 各种操作，旋转，缩放，错切等，可以执行多次。
matrix.preTranslate(-pivotX, -pivotY);
公式为：
M' = M*T* ... *-T = T* ... *-T
但是这种方式，两个调整中心的平移函数就拉的太开了，所以通常采用这种写法：
Matrix matrix = new Matrix();
// 各种操作，旋转，缩放，错切等，可以执行多次。
matrix.postTranslate(pivotX,pivotY);
matrix.preTranslate(-pivotX, -pivotY);
这样公式为：
M' = T*M* ... *-T = T* ... *-T
可以看到最终化简结果是相同的。

所以说，pre 和 post 就是用来调整乘法顺序的，正常情况下应当正向进行构建出乘法顺序公式，之后根据实际情况调整书写即可。

在构造 Matrix 时，个人建议尽量使用一种乘法，前乘或者后乘，这样操作顺序容易确定，出现问题也比较容易排查。当然，由于矩阵乘法
不满足交换律，前乘和后乘的结果是不同的，使用时应结合具体情景分析使用。

Matrix方法表
这个方法表，暂时放到这里让大家看看，方法的使用讲解放在下一篇文章中。
方法类别	相关API	摘要
基本方法	equals hashCode toString toShortString	比较、 获取哈希值、 转换为字符串
数值操作	set reset setValues getValues	设置、 重置、 设置数值、 获取数值
数值计算	mapPoints mapRadius mapRect mapVectors	计算变换后的数值
设置(set)	setConcat setRotate setScale setSkew setTranslate	设置变换
前乘(pre)	preConcat preRotate preScale preSkew preTranslate	前乘变换
后乘(post)	postConcat postRotate postScale postSkew postTranslate	后乘变换
特殊方法	setPolyToPoly setRectToRect rectStaysRect setSinCos	一些特殊操作
矩阵相关	invert isAffine isIdentity	求逆矩阵、 是否为仿射矩阵、 是否为单位矩阵
总结
对于Matrix重在理解，理解了其中的原理之后用起来将会更加得心应手。

Matrix方法详解
构造方法
构造方法没有在上面表格中列出。
无参构造
Matrix ()
创建一个全新的Matrix，使用格式如下：
Matrix matrix = new Matrix();
通过这种方式创建出来的并不是一个数值全部为空的矩阵，而是一个单位矩阵,如下:
[1、0、0]
[0、1、0]
[0、0、1]
有参构造
Matrix (Matrix src)
这种方法则需要一个已经存在的矩阵作为参数，使用格式如下:
Matrix matrix = new Matrix(src);
创建一个Matrix，并对src深拷贝(理解为新的matrix和src是两个对象，但内部数值相同即可)。

基本方法
基本方法内容比较简单，在此处简要介绍一下。
1.equals
比较两个Matrix的数值是否相同。
2.hashCode
获取Matrix的哈希值。
3.toString
将Matrix转换为字符串: Matrix{[1.0, 0.0, 0.0][0.0, 1.0, 0.0][0.0, 0.0, 1.0]}
4.toShortString
将Matrix转换为短字符串: [1.0, 0.0, 0.0][0.0, 1.0, 0.0][0.0, 0.0, 1.0]

数值操作
数值操作这一组方法可以帮助我们直接控制Matrix里面的数值。
1.set
void set (Matrix src)
没有返回值，有一个参数，作用是将参数Matrix的数值复制到当前Matrix中。如果参数为空，则重置当前Matrix，相当于reset()。
2.reset
void reset ()
重置当前Matrix(将当前Matrix重置为单位矩阵)。
3.setValues
void setValues (float[] values)
setValues的参数是浮点型的一维数组，长度需要大于9，拷贝数组中的前9位数值赋值给当前Matrix。
4.getValues
void getValues (float[] values)
很显然，getValues和setValues是一对方法，参数也是浮点型的一维数组，长度需要大于9，将Matrix中的数值拷贝进参数的前9位中。

数值计算
1.mapPoints
void mapPoints (float[] pts)
void mapPoints (float[] dst, float[] src)
void mapPoints (float[] dst, int dstIndex,float[] src, int srcIndex, int pointCount)
计算一组点基于当前Matrix变换后的位置，(由于是计算点，所以参数中的float数组长度一般都是偶数的,若为奇数，则最后一个数值不参与计算)。
它有三个重载方法:
(1) void mapPoints (float[] pts) 方法仅有一个参数，pts数组作为参数传递原始数值，计算结果仍存放在pts中。
示例:
// 初始数据为三个点 (0, 0) (80, 100) (400, 300)
float[] pts = new float[]{0, 0, 80, 100, 400, 300};
// 构造一个matrix，x坐标缩放0.5
Matrix matrix = new Matrix();
matrix.setScale(0.5f, 1f);
// 输出pts计算之前数据
Log.i(TAG, "before: "+ Arrays.toString(pts));
// 调用map方法计算
matrix.mapPoints(pts);
// 输出pts计算之后数据
Log.i(TAG, "after : "+ Arrays.toString(pts));
结果:
before: [0.0, 0.0, 80.0, 100.0, 400.0, 300.0]
after : [0.0, 0.0, 40.0, 100.0, 200.0, 300.0]

(2) void mapPoints (float[] dst, float[] src) ，src作为参数传递原始数值，计算结果存放在dst中，src不变。
如果原始数据需要保留则一般使用这种方法。
示例:
// 初始数据为三个点 (0, 0) (80, 100) (400, 300)
float[] src = new float[]{0, 0, 80, 100, 400, 300};
float[] dst = new float[6];
// 构造一个matrix，x坐标缩放0.5
Matrix matrix = new Matrix();
matrix.setScale(0.5f, 1f);
// 输出计算之前数据
Log.i(TAG, "before: src="+ Arrays.toString(src));
Log.i(TAG, "before: dst="+ Arrays.toString(dst));
// 调用map方法计算
matrix.mapPoints(dst,src);
// 输出计算之后数据
Log.i(TAG, "after : src="+ Arrays.toString(src));
Log.i(TAG, "after : dst="+ Arrays.toString(dst));
结果:
before: src=[0.0, 0.0, 80.0, 100.0, 400.0, 300.0]
before: dst=[0.0, 0.0, 0.0, 0.0, 0.0, 0.0]
after : src=[0.0, 0.0, 80.0, 100.0, 400.0, 300.0]
after : dst=[0.0, 0.0, 40.0, 100.0, 200.0, 300.0]

2.mapRadius
float mapRadius (float radius)
测量半径，由于圆可能会因为画布变换变成椭圆，所以此处测量的是平均半径。
示例:
float radius = 100;
float result = 0;
// 构造一个matrix，x坐标缩放0.5
Matrix matrix = new Matrix();
matrix.setScale(0.5f, 1f);
Log.i(TAG, "mapRadius: "+radius);
result = matrix.mapRadius(radius);
Log.i(TAG, "mapRadius: "+result);
结果:
mapRadius: 100.0
mapRadius: 70.71068

3.mapRect
boolean mapRect (RectF rect)
boolean mapRect (RectF dst, RectF src)
测量矩形变换后位置。
(1) boolean mapRect (RectF rect) 测量rect并将测量结果放入rect中，返回值是判断矩形经过变换后是否仍为矩形。
示例：
RectF rect = new RectF(400, 400, 1000, 800);
// 构造一个matrix
Matrix matrix = new Matrix();
matrix.setScale(0.5f, 1f);
matrix.postSkew(1,0);
Log.i(TAG, "mapRadius: "+rect.toString());
boolean result = matrix.mapRect(rect);
Log.i(TAG, "mapRadius: "+rect.toString());
Log.e(TAG, "isRect: "+ result);
结果：
mapRadius: RectF(400.0, 400.0, 1000.0, 800.0)
mapRadius: RectF(600.0, 400.0, 1300.0, 800.0)
isRect: false
由于使用了错切，所以返回结果为false。
(2) boolean mapRect (RectF dst, RectF src) 测量src并将测量结果放入dst中，返回值是判断矩形经过变换后是否仍为矩形,和之前没
有什么太大区别，此处就不啰嗦了。

4.mapVectors

测量向量。
void mapVectors (float[] vecs)
void mapVectors (float[] dst, float[] src)
void mapVectors (float[] dst, int dstIndex, float[] src, int srcIndex, int vectorCount)
mapVectors 与 mapPoints 基本上是相同的，可以直接参照上面的mapPoints使用方法。
而两者唯一的区别就是mapVectors不会受到位移的影响，这符合向量的定律，如果你不了解的话，请找到以前教过你的老师然后把学费要回来。
区别:
float[] src = new float[]{1000, 800};
float[] dst = new float[2];
// 构造一个matrix
Matrix matrix = new Matrix();
matrix.setScale(0.5f, 1f);
matrix.postTranslate(100,100);
// 计算向量, 不受位移影响
matrix.mapVectors(dst, src);
Log.i(TAG, "mapVectors: "+Arrays.toString(dst));
// 计算点
matrix.mapPoints(dst, src);
Log.i(TAG, "mapPoints: "+Arrays.toString(dst));
结果:
mapVectors: [500.0, 800.0]
mapPoints: [600.0, 900.0]

set、pre 与 post
对于四种基本变换 平移(translate)、缩放(scale)、旋转(rotate)、 错切(skew) 它们每一种都三种操作方法，分别为 设置(set)、
前乘(pre) 和 后乘 (post)。而它们的基础是Concat，
通过先构造出特殊矩阵然后用原始矩阵Concat特殊矩阵，达到变换的结果。
关于四种基本变换的知识和三种对应操作的区别，详细可以参考 Canvas之画布操作 和 Matrix原理 这两篇文章的内容。
由于之前的文章已经详细的讲解过了它们的原理与用法，所以此处就简要的介绍一下:
方法	简介
set	设置，会覆盖掉之前的数值，导致之前的操作失效。
pre	前乘，相当于矩阵的右乘， M' = M * S (S指为特殊矩阵)
post	后乘，相当于矩阵的左乘，M' = S * M （S指为特殊矩阵）
Matrix 相关的重要知识：
1.一开始从Canvas中获取到到Matrix并不是初始矩阵，而是经过偏移后到矩阵，且偏移距离就是距离屏幕左上角的位置。
这个可以用于判定View在屏幕上的绝对位置，View可以根据所处位置做出调整。
2.构造Matrix时使用的是矩阵乘法，前乘(pre)与后乘(post)结果差别很大。
这个直接参见上一篇文章 Matrix原理 即可。
3.受矩阵乘法影响，后面的执行的操作可能会影响到之前的操作。
使用时需要注意构造顺序。

特殊方法
这一类方法看似不起眼，但拿来稍微加工一下就可能制作意想不到的效果。
1.setPolyToPoly
boolean setPolyToPoly (
        float[] src, 	// 原始数组 src [x,y]，存储内容为一组点
        int srcIndex, 	// 原始数组开始位置
        float[] dst, 	// 目标数组 dst [x,y]，存储内容为一组点
        int dstIndex, 	// 目标数组开始位置
        int pointCount)	// 测控点的数量 取值范围是: 0到4
Poly全称是Polygon，多边形的意思，了解了意思大致就能知道这个方法是做什么用的了，应该与PS中自由变换中的扭曲有点类似。
我们知道pointCount支持点的个数为0到4个，四个一般指图形的四个角，属于最常用的一种情形，但前面几种是什么情况呢？
发布此文的时候之所以没有讲解0到3的情况，是因为前面的几种情况在实际开发中很少会出现，	才不是因为偷懒呢，哼。
pointCount	摘要
0	相当于reset
1	相当于translate
2	可以进行 缩放、旋转、平移 变换
3	可以进行 缩放、旋转、平移、错切 变换
4	可以进行 缩放、旋转、平移、错切以及任何形变
为什么说前面几种情况在实际开发中很少出现?
作为开发人员，写出来的代码出了要让机器"看懂"，没有歧义之外，最重要的还是让人看懂，以方便后期的维护修改，从上边的表格中
可以看出，前面的几种种情况都可以有更直观的替代方法，只有四个参数的情况下的特殊形变是没有替代方法的。
测控点选取位置?
测控点可以选择任何你认为方便的位置，只要src与dst一一对应即可。不过为了方便，通常会选择一些特殊的点： 图形的四个角，边线的
中心点以及图形的中心点等。不过有一点需要注意，测控点选取都应当是不重复的(src与dst均是如此)，如果选取了重复的点会直接导致测量
失效，这也意味着，你不允许将一个方形(四个点)映射为三角形(四个点，但其中两个位置重叠)，但可以接近于三角形。

作用范围?
作用范围当然是设置了Matrix的全部区域，如果你将这个Matrix赋值给了Canvas，它的作用范围就是整个画布，如果你赋值给了Bitmap，它的作
用范围就是整张图片。


























 */


























}
