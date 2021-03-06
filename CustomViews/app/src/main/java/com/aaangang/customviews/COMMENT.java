package com.aaangang.customviews;

/**
 * Created by Administrator on 2017/11/1.
 */
public class COMMENT {
    //参考自https://github.com/GcsSloop/AndroidNote/tree/master/CustomView
    //感谢GcsSloop，其blog地址：http://www.gcssloop.com/#blog





    //坐标系 ####################################################################
    /*View的坐标系统是相对于父控件而言的.
    getTop();       //获取子View左上角距父View顶部的距离
    getLeft();      //获取子View左上角距父View左侧的距离
    getBottom();    //获取子View右下角距父View顶部的距离
    getRight();     //获取子View右下角距父View左侧的距离

    MotionEvent中 get 和 getRaw 的区别
    event.getX();       //触摸点相对于其所在组件坐标系的坐标
    event.getY();
    event.getRawX();    //触摸点相对于屏幕默认坐标系的坐标
    event.getRawY();*/

    //弧度角度  ####################################################################
    /*
    为什么对角的描述存在角度与弧度两种单位？
    简单来说就是为了方便，为了精确描述一个角的大小引入了角度与弧度的概念。
    由于两者进制是不同的(角度是60进制，弧度是10进制),在合适的地方使用合适的单位来描述会更加方便。
    角度	两条射线从圆心向圆周射出，形成一个夹角和夹角正对的一段弧。当这段弧长正好等于圆周长的360分之一时，两条射线的夹角的大小为1度.
    弧度	两条射线从圆心向圆周射出，形成一个夹角和夹角正对的一段弧。当这段弧长正好等于圆的半径时，两条射线的夹角大小为1弧度.

    圆一周对应的角度为360度(角度)，对应的弧度为2π弧度。
    故得等价关系:360(角度) = 2π(弧度) ==> 180(角度) = π(弧度)
    rad = deg x π / 180	2π ＝ 360 x π / 180
    deg = rad x 180 / π	360 ＝ 2π x 180 / π
    在常见的数学坐标系中角度增大方向为逆时针，
    在默认的屏幕坐标系中角度增大方向为顺时针。
    */

    //颜色  ####################################################################
    /*
    java中定义颜色
    int color = Color.GRAY;     //灰色
    由于Color类提供的颜色仅为有限的几个，通常还是用ARGB值进行表示。
    int color = Color.argb(127, 255, 0, 0);   //半透明红色
    int color = 0xaaff0000;                   //带有透明度的红色

    在xml文件中定义颜色
    在/res/values/color.xml 文件中如下定义：
    <?xml version="1.0" encoding="utf-8"?>
    <resources>
        <color name="red">#ff0000</color>
        <color name="green">#00ff00</color>
    </resources>

    在java文件中引用xml中定义的颜色：
    int color = getResources().getColor(R.color.mycolor);
    int color = getColor(R.color.myColor);    //API 23 及以上支持该方法

    屏幕取色工具
    取色调色工具，可以从屏幕取色或者使用调色板调制颜色，非常小而精简。
    */

    //自定义View分类与流程  ####################################################################
    /*
    开始——构造函数——onMeasure——onSizeChanged——onLayout——onDraw——视图状态改变——结束
                                                                    A   invaladate  |
                                                                    |_______________|

1.自定义ViewGroup
自定义ViewGroup一般是利用现有的组件根据特定的布局方式来组成新的组件，大多继承自ViewGroup或各种Layout，包含有子View。

2.自定义View
在没有现成的View，需要自己实现的时候，就使用自定义View，一般继承自View，SurfaceView或其他的View，不包含子View。

二.几个重要的函数
1构造函数
构造函数是View的入口，可以用于初始化一些的内容，和获取自定义属性。
View的构造函数有四种重载分别如下:
  public void SloopView(Context context) {}  一般在直接New一个View的时候调用。
  public void SloopView(Context context, AttributeSet attrs) {}  一般在layout文件中使用的时候会调用，关于它的所有属性(包括自定义属性)都会包含在attrs中传递进来
  public void SloopView(Context context, AttributeSet attrs, int defStyleAttr) {}
  public void SloopView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {}
由于三个参数的构造函数第三个参数一般不用，暂不考虑，两个参数的构造函数最常用，如下，通过attrs传递xml中的参数
//在layout文件中 - 格式为： 包名.View名
  <com.sloop.study.SloopView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>

2测量View大小(onMeasure)
    Q: 为什么要测量View大小？
    A: View的大小不仅由自身所决定，同时也会受到父控件的影响，为了我们的控件能更好的适应各种情况，一般会自己进行测量。
    测量View大小使用的是onMeasure函数，我们可以从onMeasure的两个参数中取出宽高的相关数据：
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);      //取出宽度的确切数值
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);      //取出宽度的测量模式
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);    //取出高度的确切数值
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);    //取出高度的测量模式
    }
    在int类型的32位二进制位中，31-30这两位表示测量模式,29~0这三十位表示宽和高的实际值
    测量模式一共有三种， 被定义在 Android 中的 View 类的一个内部类View.MeasureSpec中：
UNSPECIFIED	 00	默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
EXACTLY	     01	表示父控件已经确切的指定了子View的大小。
AT_MOST	     10	表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
如果对View的宽高进行修改了，不要调用super.onMeasure(widthMeasureSpec,heightMeasureSpec);
要调用setMeasuredDimension(widthsize,heightsize); 这个函数。

3.确定View大小(onSizeChanged)
Q: 在测量完View并使用setMeasuredDimension函数之后View的大小基本上已经确定了，那么为什么还要再次确定View的大小呢？
A: 这是因为View的大小不仅由View本身控制，而且受父控件的影响，所以我们在确定View大小的时候最好使用系统提供的onSizeChanged回调函数。
onSizeChanged如下：
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
可以看出，它又四个参数，分别为 宽度，高度，上一次宽度，上一次高度。
这个函数比较简单，我们只需关注 宽度(w), 高度(h) 即可，这两个参数就是View最终的大小。

4.确定子View布局位置(onLayout)
确定布局的函数是onLayout，它用于确定子View的位置，在自定义ViewGroup中会用到，他调用的是子View的layout函数。
在自定义ViewGroup中，onLayout一般是循环取出子View，然后经过计算得出各个子View位置的坐标值，然后用以下函数设置子View位置。
  child.layout(l, t, r, b);
l	View左侧距父View左侧的距离	getLeft();
t	View顶部距父View顶部的距离	getTop();
r	View右侧距父View左侧的距离	getRight();
b	View底部距父View顶部的距离	getBottom();

5.绘制内容(onDraw)
onDraw是实际绘制的部分，也就是我们真正关心的部分，使用的是Canvas绘图。
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

6.对外提供操作方法和监听回调
自定义完View之后，一般会对外暴露一些接口，用于控制View的状态等，或者监听View的变化.

自定义View流程：
步骤	关键字	作用
1	构造函数	View初始化
2	onMeasure	测量View大小
3	onSizeChanged	确定View大小
4	onLayout	确定子View布局(自定义View包含子View时有用)
5	onDraw	实际绘制内容
6	提供接口	控制View或监听View某些状态。


Canvas之绘制基本形状  ####################################################################
Canvas我们可以称之为画布，能够在上面绘制各种东西
绘制颜色	drawColor, drawRGB, drawARGB	使用单一颜色填充整个画布
绘制基本形状	drawPoint, drawPoints, drawLine, drawLines, drawRect, drawRoundRect, drawOval, drawCircle, drawArc	依次为 点、线、矩形、圆角矩形、椭圆、圆、圆弧
绘制图片	drawBitmap, drawPicture	绘制位图和图片
绘制文本	drawText, drawPosText, drawTextOnPath	依次为 绘制文字、绘制文字时指定每个文字位置、根据路径绘制文字
绘制路径	drawPath	绘制路径，绘制贝塞尔曲线时也需要用到该函数
顶点操作	drawVertices, drawBitmapMesh	通过对顶点操作可以使图像形变，drawVertices直接对画布作用、 drawBitmapMesh只对绘制的Bitmap作用
画布剪裁	clipPath, clipRect	设置画布的显示区域
画布快照	save, restore, saveLayerXxx, restoreToCount, getSaveCount	依次为 保存当前状态、 回滚到上一次保存的状态、 保存图层状态、 回滚到指定状态、 获取保存次数
画布变换	translate, scale, rotate, skew	依次为 位移、缩放、 旋转、错切
Matrix(矩阵)	getMatrix, setMatrix, concat	实际画布的位移，缩放等操作的都是图像矩阵Matrix，只不过Matrix比较难以理解和使用，故封装了一些常用的方法。

创建画笔：

要想绘制内容，首先需要先创建一个画笔，如下：
  // 1.创建一个画笔
  private Paint mPaint = new Paint();
  // 2.初始化画笔
    private void initPaint() {
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
    }
  // 3.在构造函数中初始化
    public SloopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }
在创建完画笔之后，就可以在Canvas中绘制各种内容了。
绘制点：
以绘制一个点，也可以绘制一组点，如下：
        canvas.drawPoint(200, 200, mPaint);     //在坐标(200,200)位置绘制一个点
        canvas.drawPoints(new float[]{          //绘制一组点，坐标位置由float数组指定
                500,500,
                500,600,
                500,700
        },mPaint);
绘制直线：
绘制直线需要两个点，初始点和结束点，同样绘制直线也可以绘制一条或者绘制一组：
        canvas.drawLine(300,300,500,600,mPaint);    // 在坐标(300,300)(500,600)之间绘制一条直线
        canvas.drawLines(new float[]{               // 绘制一组线 每四数字(两个点的坐标)确定一条线
                100,200,200,200,
                100,300,200,300
        },mPaint);

绘制矩形：
确定一个矩形最少需要四个数据，就是对角线的两个点的坐标值，这里一般采用左上角和右下角的两个点的坐标。
关于绘制矩形，Canvas提供了三种重载方法，第一种就是提供四个数值(矩形左上角和右下角两个点的坐标)来确定一个矩形进行绘制。
其余两种是先将矩形封装为Rect或RectF(实际上仍然是用两个坐标点来确定的矩形)，然后传递给Canvas绘制，如下：
        // 第一种
        canvas.drawRect(100,100,800,400,mPaint);
        // 第二种
        Rect rect = new Rect(100,100,800,400);
        canvas.drawRect(rect,mPaint);
        // 第三种
        RectF rectF = new RectF(100,100,800,400);
        canvas.drawRect(rectF,mPaint);
以上三种方法所绘制出来的结果是完全一样的。
为什么会有Rect和RectF两种？两者有什么区别吗？
答案当然是存在区别的，两者最大的区别就是精度不同，Rect是int(整形)的，而RectF是float(单精度浮点型)的。除了精度不同，两种提供的方法也稍微存在差别

绘制圆角矩形：
绘制圆角矩形也提供了两种重载方式，如下：
        // 第一种
        RectF rectF = new RectF(100,100,800,400);
        canvas.drawRoundRect(rectF,30,30,mPaint);
        // 第二种
        canvas.drawRoundRect(100,100,800,400,30,30,mPaint);
上面两种方法绘制效果也是一样的，但鉴于第二种方法在API21的时候才添加上，所以我们一般使用的都是第一种。
既然是圆角矩形，他的角肯定是圆弧(圆形的一部分)，我们一般用什么确定一个圆形呢？
答案是圆心 和 半径，其中圆心用于确定位置，而半径用于确定大小。
由于矩形位置已经确定，所以其边角位置也是确定的，那么确定位置的参数就可以省略，只需要用半径就能描述一个圆弧了。
但是，半径只需要一个参数，但这里怎么会有两个呢？
好吧，让你发现了，这里圆角矩形的角实际上不是一个正圆的圆弧，而是椭圆的圆弧，这里的两个参数实际上是椭圆的两个半径

绘制椭圆：
相对于绘制圆角矩形，绘制椭圆就简单的多了，因为他只需要一个矩形矩形作为参数:
        // 第一种
        RectF rectF = new RectF(100,100,800,400);
        canvas.drawOval(rectF,mPaint);
        // 第二种
        canvas.drawOval(100,100,800,400,mPaint);
同样，以上两种方法效果完全一样，但一般使用第一种。
绘制椭圆实际上就是绘制一个矩形的内切图形

绘制圆：
绘制圆形也比较简单, 如下：
    canvas.drawCircle(500,500,400,mPaint);  // 绘制一个圆心坐标在(500,500)，半径为400 的圆。
绘制圆形有四个参数，前两个是圆心坐标，第三个是半径，最后一个是画笔。

绘制圆弧：       ******************
绘制圆弧就比较神奇一点了，为了理解这个比较神奇的东西，我们先看一下它需要的几个参数：
// 第一种
public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint){}
// 第二种
public void drawArc(float left, float top, float right, float bottom, float startAngle,
            float sweepAngle, boolean useCenter, @NonNull Paint paint) {}
从上面可以看出，相比于绘制椭圆，绘制圆弧还多了三个参数：
startAngle  // 开始角度
sweepAngle  // 扫过角度
useCenter   // 是否使用中心，是否从RECT中心点绘制一个折角圆弧，还是直接绘制弓形的圆弧

简要介绍Paint
实际上画笔有三种模式，如下：
STROKE                //描边
FILL                  //填充
FILL_AND_STROKE       //描边加填充

简要介绍画布的操作:
相关操作	简要介绍
save	    保存当前画布状态
restore	    回滚到上一次保存的状态
translate	相对于当前位置位移
rotate	    旋转

    */


//Canvas之画布操作 ####################################################################
/*
    1.画布操作
    为什么要有画布操作？
    画布操作可以帮助我们用更加容易理解的方式制作图形。
    例如： 从坐标原点为起点，绘制一个长度为20dp，与水平线夹角为30度的线段怎么做？
    按照我们通常的想法(被常年训练出来的数学思维)，就是先使用三角函数计算出线段结束点的坐标，然后调用drawLine即可。
    然而这是否是被固有思维禁锢了？
    假设我们先绘制一个长度为20dp的水平线，然后将这条水平线旋转30度，则最终看起来效果是相同的，而且不用进行三角函数计算，这样是否更加简单了一点呢？

合理的使用画布操作可以帮助你用更容易理解的方式创作你想要的效果，这也是画布操作存在的原因。
PS: 所有的画布操作都只影响后续的绘制，对之前已经绘制过的内容没有影响。
⑴位移(translate)
translate是坐标系的移动，可以为图形绘制选择一个合适的坐标系。 请注意，位移是基于当前位置移动，而不是每次基于屏幕左上角的(0,0)点移动，如下：
        // 省略了创建画笔的代码
        // 在坐标原点绘制一个黑色圆形
        mPaint.setColor(Color.BLACK);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);
        // 在坐标原点绘制一个蓝色圆形
        mPaint.setColor(Color.BLUE);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);
我们首先将坐标系移动一段距离绘制一个圆形，之后再移动一段距离绘制一个圆形，两次移动是可叠加的。

⑵缩放(scale)
缩放提供了两个方法，如下：
 public void scale (float sx, float sy)
 public final void scale (float sx, float sy, float px, float py)
这两个方法中前两个参数是相同的分别为x轴和y轴的缩放比例。而第二种方法比前一种多了两个参数，用来控制缩放中心位置的。
缩放比例(sx,sy)取值范围详解：
取值范围(n)	说明
    [-∞, -1)	  先根据缩放中心放大n倍，再根据中心轴进行翻转
    -1	          根据缩放中心轴进行翻转
    (-1, 0)	      先根据缩放中心缩小到n，再根据中心轴进行翻转
    0	          不会显示，若sx为0，则宽度为0，不会显示，sy同理
    (0, 1)	      根据缩放中心缩小到n
    1	          没有变化
    (1, +∞)	  根据缩放中心放大n倍
缩放的中心默认为坐标原点,而缩放中心轴就是坐标轴
// 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect = new RectF(0,-400,400,0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.scale(0.5f,0.5f);                // 画布缩放
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);

接下来我们使用第二种方法让缩放中心位置稍微改变一下，如下：
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect = new RectF(0,-400,400,0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.scale(0.5f,0.5f,200,0);          // 画布缩放  <-- 缩放中心向右偏移了200个单位
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);

和位移(translate)一样，缩放也是可以叠加的。
   canvas.scale(0.5f,0.5f);
   canvas.scale(0.5f,0.1f);
调用两次缩放则 x轴实际缩放为0.5x0.5=0.25 y轴实际缩放为0.5x0.1=0.05

⑶旋转(rotate)
旋转提供了两种方法：
  public void rotate (float degrees)
  public final void rotate (float degrees, float px, float py)
和缩放一样，第二种方法多出来的两个参数依旧是控制旋转中心点的。
默认的旋转中心依旧是坐标原点：
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect = new RectF(0,-400,400,0);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.rotate(180);                     // 旋转180度 <-- 默认旋转中心为原点
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);

旋转也是可叠加的
     canvas.rotate(180);
     canvas.rotate(20);
调用两次旋转，则实际的旋转角度为180+20=200度。
为了演示这一个效果，我做了一个不明觉厉的东西：
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(0,0,400,mPaint);          // 绘制两个圆形
        canvas.drawCircle(0,0,380,mPaint);
        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
            canvas.drawLine(0,380,0,400,mPaint);
            canvas.rotate(10);
        }

⑷错切(skew)
skew这里翻译为错切，错切是特殊类型的线性变换。
错切只提供了一种方法：
  public void skew (float sx, float sy)
参数含义：
float sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值，
float sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值.
变换后:
X = x + sx * y
Y = sy * x + y
// 将坐标系原点移动到画布正中心
        canvas.translate(mWidth / 2, mHeight / 2);
        RectF rect = new RectF(0,0,200,200);   // 矩形区域
        mPaint.setColor(Color.BLACK);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
        canvas.skew(1,0);                       // 水平错切 <- 45度
        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
        canvas.drawRect(rect,mPaint);

⑸快照(save)和回滚(restore)
Q: 为什存在快照与回滚
A：画布的操作是不可逆的，而且很多画布操作会影响后续的步骤，例如第一个例子，两个圆形都是在坐标原点绘制的，而因为坐标系的移动绘制出来的实际位置不同。所以会对画布的一些状态进行保存和回滚。
与之相关的API:
相关API	简介
save	把当前的画布的状态进行保存，然后放入特定的栈中
saveLayerXxx	新建一个图层，并放入特定的栈中
restore	把栈中最顶层的画布状态取出来，并按照这个状态恢复当前的画布
restoreToCount	弹出指定位置及其以上所有的状态，并按照指定位置的状态进行恢复
getSaveCount	获取栈中内容的数量(即保存次数)

这个栈可以存储画布状态和图层状态。
Q：什么是画布和图层？
A：实际上我们看到的画布是由多个图层构成的
实际上我们之前讲解的绘制操作和画布操作都是在默认图层上进行的。
在通常情况下，使用默认图层就可满足需求，但是如果需要绘制比较复杂的内容，如地图(地图可以有多个地图层叠加而成，比如：政区层，道路层，兴趣点层)等，则分图层绘制比较好一些。
你可以把这些图层看做是一层一层的玻璃板，你在每层的玻璃板上绘制内容，然后把这些玻璃板叠在一起看就是最终效果。

save 有两种方法：
  // 保存全部状态
  public int save ()
  // 根据saveFlags参数保存一部分状态
  public int save (int saveFlags)
可以看到第二种方法比第一种多了一个saveFlags参数，使用这个参数可以只保存一部分状态，更加灵活，这个saveFlags参数具体可参考上面表格中的内容。
每调用一次save方法，都会在栈顶添加一条状态信息，以上面状态栈图片为例，再调用一次save则会在第5次上面载添加一条状态。
restore
状态回滚，就是从栈顶取出一个状态然后根据内容进行恢复。
同样以上面状态栈图片为例，调用一次restore方法则将状态栈中第5次取出，根据里面保存的状态进行状态恢复。

restoreToCount
弹出指定位置以及以上所有状态，并根据指定位置状态进行恢复。
以上面状态栈图片为例，如果调用restoreToCount(2) 则会弹出 2 3 4 5 的状态，并根据第2次保存的状态进行恢复。
getSaveCount
获取保存的次数，即状态栈中保存状态的数量，以上面状态栈图片为例，使用该函数的返回值为5。
不过请注意，该函数的最小返回值为1，即使弹出了所有的状态，返回值依旧为1，代表默认状态。
常用格式
虽然关于状态的保存和回滚啰嗦了不少，不过大多数情况下只需要记住下面的步骤就可以了：
   save();      //保存状态
   ...          //具体操作
   restore();   //回滚到之前的状态
这种方式也是最简单和最容易理解的使用方法。

*/

//Canvas之图片文字
    /*
Canvas基本操作详解
1.绘制图片
绘制有两种方法，drawPicture(矢量图) 和 drawBitmap(位图),接下来我们一一了解。
(1)drawPicture
使用Picture前请关闭硬件加速，以免引起不必要的问题！
在AndroidMenifest文件中application节点下添上 android:hardwareAccelerated="false"以关闭整个应用的硬件加速。
Picture和上文中的录像功能是类似的，只不过我们Picture录的是Canvas中绘制的内容。
我们把Canvas绘制点，线，矩形等诸多操作用Picture录制下来，下次需要的时候拿来就能用，使用Picture相比于再次
调用绘图API，开销是比较小的，也就是说对于重复的操作可以更加省时省力
PS：你可以把Picture看作是一个录制Canvas操作的录像机。
了解一下Picture的相关方法。
相关方法	简介
public int getWidth ()	获取宽度
public int getHeight ()	获取高度
public Canvas beginRecording (int width, int height)开始录制 (返回一个Canvas，在Canvas中所有的绘制都会存储在Picture中)
public void endRecording ()	结束录制
public void draw (Canvas canvas)	将Picture中内容绘制到Canvas中
很明显，beginRecording 和 endRecording 是成对使用的，一个开始录制，一个是结束录制，两者之间的操作将会存储在Picture中。
录制内容，即将一些Canvas操作用Picture存储起来，录制的内容是不会直接显示在屏幕上的，只是存储起来了而已。
Picture虽然方法就那么几个，但是具体使用起来还是分很多情况的，由于录制的内容不会直接显示，就像存储的视频不点击播放不会自动播放一样，
同样，想要将Picture中的内容显示出来就需要手动调用播放(绘制)，将Picture中的内容绘制出来可以有以下几种方法：
1	使用Picture提供的draw方法绘制。
2	使用Canvas提供的drawPicture方法绘制。
3	将Picture包装成为PictureDrawable，使用PictureDrawable的draw方法绘制。
以上几种方法主要区别：
主要区别	分类	简介
是否对Canvas有影响	1有影响
2,3不影响	此处指绘制完成后是否会影响Canvas的状态(Matrix clip等)
可操作性强弱	1可操作性较弱
2,3可操作性较强	此处的可操作性可以简单理解为对绘制结果可控程度。


(2)drawBitmap
既然要绘制Bitmap，就要先获取一个Bitmap，那么如何获取呢？
获取Bitmap方式:
序号	获取方式	备注
1	通过Bitmap创建	复制一个已有的Bitmap(新Bitmap状态和原有的一致) 或者 创建一个空白的Bitmap(内容可改变)
2	通过BitmapDrawable获取	从资源文件 内存卡 网络等地方获取一张图片并转换为内容不可变的Bitmap
3	通过BitmapFactory获取	从资源文件 内存卡 网络等地方获取一张图片并转换为内容不可变的Bitmap
常来说，我们绘制Bitmap都是读取已有的图片转换为Bitmap绘制到Canvas上。
很明显，第1种方式不能满足我们的要求，暂时排除。
第2种方式虽然也可满足我们的要求，但是我不推荐使用这种方式，至于为什么在后续详细讲解Drawable的时候会说明,暂时排除。
第3种方法我们会比较详细的说明一下如何从各个位置获取图片。
通过BitmapFactory从不同位置获取Bitmap:
资源文件(drawable/mipmap/raw):
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.raw.bitmap);
资源文件(assets):
        Bitmap bitmap=null;
        try {
            InputStream is = mContext.getAssets().open("bitmap.png");
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
内存卡文件:
    Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/bitmap.png");
网络文件:
        // 此处省略了获取网络输入流的代码
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        is.close();
既然已经获得到了Bitmap，那么就开始本文的重点了，将Bitmap绘制到画布上。
绘制Bitmap：
依照惯例先预览一下drawBitmap的常用方法：
    // 第一种
    public void drawBitmap (Bitmap bitmap, Matrix matrix, Paint paint)
    // 第二种
    public void drawBitmap (Bitmap bitmap, float left, float top, Paint paint)
    // 第三种
    public void drawBitmap (Bitmap bitmap, Rect src, Rect dst, Paint paint)
    public void drawBitmap (Bitmap bitmap, Rect src, RectF dst, Paint paint)
第一种方法中后两个参数(matrix, paint)是在绘制的时候对图片进行一些改变，如果只是
需要将图片内容绘制出来只需要如下操作就可以了：
PS:图片左上角位置默认为坐标原点。
    canvas.drawBitmap(bitmap,new Matrix(),new Paint());
第二种方法就是在绘制时指定了图片左上角的坐标(距离坐标原点的距离)：
注意：此处指定的是与坐标原点的距离，并非是与屏幕顶部和左侧的距离, 虽然默认状态下两者是重合的，但是也请注意分别两者的不同。
    canvas.drawBitmap(bitmap,200,500,new Paint());
第三种方法比较有意思，上面多了两个矩形区域(src,dst),这两个矩形选区是干什么用的？
名称	作用
Rect src	指定绘制图片的区域
Rect dst 或RectF dst	指定图片在屏幕上显示(绘制)的区域
示例：
        // 将画布坐标系移动到画布中央
        canvas.translate(mWidth/2,mHeight/2);
        // 指定图片绘制区域(左上角的四分之一)
        Rect src = new Rect(0,0,bitmap.getWidth()/2,bitmap.getHeight()/2);
        // 指定图片在屏幕上显示的区域
        Rect dst = new Rect(0,0,200,400);
        // 绘制图片
        canvas.drawBitmap(bitmap,src,dst,null);
图片宽高会根据指定的区域自动进行缩放

2.绘制文字
依旧预览一下相关常用方法：
    // 第一类
    public void drawText (String text, float x, float y, Paint paint)
    public void drawText (String text, int start, int end, float x, float y, Paint paint)
    public void drawText (CharSequence text, int start, int end, float x, float y, Paint paint)
    public void drawText (char[] text, int index, int count, float x, float y, Paint paint)
    // 第二类
    public void drawPosText (String text, float[] pos, Paint paint)
    public void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)
    // 第三类
    public void drawTextOnPath (String text, Path path, float hOffset, float vOffset, Paint paint)
    public void drawTextOnPath (char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint)
绘制文字部分大致可以分为三类：
第一类只能指定文本基线位置(基线x默认在字符串左侧，基线y默认在字符串下方)。
第二类可以分别指定每个文字的位置。
第三类是指定一个路径，根据路径绘制文字。
Paint文本相关常用方法表
标题	相关方法	备注
色彩	setColor setARGB setAlpha	设置颜色，透明度
大小	setTextSize	设置文本字体大小
字体	setTypeface	设置或清除字体样式
样式	setStyle	填充(FILL),描边(STROKE),填充加描边(FILL_AND_STROKE)
对齐	setTextAlign	左对齐(LEFT),居中对齐(CENTER),右对齐(RIGHT)
测量	measureText	测量文本大小(注意，请在设置完文本各项参数后调用)
为了绘制文本，我们先创建一个文本画笔：
        Paint textPaint = new Paint();          // 创建画笔
        textPaint.setColor(Color.BLACK);        // 设置颜色
        textPaint.setStyle(Paint.Style.FILL);   // 设置样式
        textPaint.setTextSize(50);              // 设置字体大小
第一类(drawText)
第一类可以指定文本开始的位置，可以截取文本中部分内容进行绘制。
其中x，y两个参数是指定文本绘制两个基线,示例：
        // 文本(要绘制的内容)
        String str = "ABCDEFGHIJK";
        // 参数分别为 (文本 基线x 基线y 画笔)
        canvas.drawText(str,200,500,textPaint);
// 文本(要绘制的内容)
        String str = "ABCDEFGHIJK";
        // 参数分别为 (字符串 开始截取位置 结束截取位置 基线x 基线y 画笔)
        canvas.drawText(str,1,3,200,500,textPaint);
第二类(drawPosText)
通过和第一类比较，我们可以发现，第二类中没有指定x，y坐标的参数，而是出现了这样一个参数float[] pos。
好吧，这个名为pos的浮点型数组就是指定坐标的，至于为啥要用数组嘛，因为这家伙野心比较大
，想给每个字符都指定一个位置。
示例：
        String str = "SLOOP";
        canvas.drawPosText(str,new float[]{
                100,100,    // 第一个字符位置
                200,200,    // 第二个字符位置
                300,300,    // ...
                400,400,
                500,500
        },textPaint);
不过嘛，虽然虽然这个方法也比较容易理解，但是关于这个方法我个人是不推荐使用的，因为坑比较多，主要有一下几点：
序号	反对理由
1	必须指定所有字符位置，否则直接crash掉，反人类设计
2	性能不佳，在大量使用的时候可能导致卡顿
3	不支持emoji等特殊字符，不支持字形组合与分解

     */


//Path之基本操作
    /*
一.Path常用方法表

为了兼容性(偷懒) 本表格中去除了部分API21(即安卓版本5.0)以上才添加的方法。
作用	相关方法	备注
移动起点	moveTo	移动下一次操作的起点位置
设置终点	setLastPoint	重置当前path中最后一个点位置，如果在绘制之前调用，效果和moveTo相同
连接直线	lineTo	添加上一个点到当前点之间的直线到Path
闭合路径	close	连接第一个点连接到最后一个点，形成一个闭合区域
添加内容	addRect, addRoundRect, addOval, addCircle, addPath, addArc, arcTo	添加(矩形， 圆角矩形， 椭圆， 圆， 路径， 圆弧) 到当前Path (注意addArc和arcTo的区别)
是否为空	isEmpty	判断Path是否为空
是否为矩形	isRect	判断path是否是一个矩形
替换路径	set	用新的路径替换到当前路径所有内容
偏移路径	offset	对当前路径之前的操作进行偏移(不会影响之后的操作)
贝塞尔曲线	quadTo, cubicTo	分别为二次和三次贝塞尔曲线的方法
rXxx方法	rMoveTo, rLineTo, rQuadTo, rCubicTo	不带r的方法是基于原点的坐标系(偏移量)， rXxx方法是基于当前点坐标系(偏移量)
填充模式	setFillType, getFillType, isInverseFillType, toggleInverseFillType	设置,获取,判断和切换填充模式
提示方法	incReserve	提示Path还有多少个点等待加入**(这个方法貌似会让Path优化存储结构)**
布尔操作(API19)	op	对两个Path进行布尔运算(即取交集、并集等操作)
计算边界	computeBounds	计算Path的边界
重置路径	reset, rewind	清除Path中的内容
reset不保留内部数据结构，但会保留FillType.
rewind会保留内部的数据结构，但不保留FillType
矩阵操作	transform	矩阵变换

二.Path详解
请关闭硬件加速，以免引起不必要的问题！
Path作用
本次特地开了一篇详细讲解Path，为什么要单独摘出来呢，这是因为Path在2D绘图中是一个很重要的东西。
在前面我们讲解的所有绘制都是简单图形(如 矩形 圆 圆弧等)，而对于那些复杂一点的图形则没法去绘制(如绘制一个
心形 正多边形 五角星等)，而使用Path不仅能够绘制简单图形，也可以绘制这些比较复杂的图形。另外，
根据路径绘制文本和剪裁画布都会用到Path。
Path含义
Path封装了由直线和曲线(二次，三次贝塞尔曲线)构成的几何路径。你能用Canvas中的drawPath来把这条路径画出来
(同样支持Paint的不同绘制模式)，也可以用于剪裁画布和根据路径绘制文字。我们有时会用Path来描述一个图像的轮
廓，所以也会称为轮廓线(轮廓线仅是Path的一种使用方法，两者并不等价)
另外路径有开放和封闭的区别。
Path使用方法详解
第1组: moveTo、 setLastPoint、 lineTo 和 close
由于Path的有些知识点无法单独来讲，所以本次采取了一次讲一组方法。
按照惯例，先创建画笔：
        Paint mPaint = new Paint();             // 创建画笔
        mPaint.setColor(Color.BLACK);           // 画笔颜色 - 黑色
        mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
        mPaint.setStrokeWidth(10);              // 边框宽度 - 10
lineTo：
方法预览：
public void lineTo (float x, float y)
首先讲解的的LineTo，为啥先讲解这个呢？
是因为moveTo、 setLastPoint、 close都无法直接看到效果，借助有具现化效果的lineTo才能让这些方法现出原形。
lineTo很简单，只有一个方法，作用也很容易理解，line嘛，顾名思义就是一条线。
俗话(数学书上)说，两点确定一条直线，但是看参数明显只给了一个点的坐标吧(这不按常理出牌啊)。
再仔细一看，这个lineTo除了line外还有一个to呢，to翻译过来就是“至”，到某个地方的意思，lineTo难道是指从某个点到参数坐标点之间连一条线？
没错，你猜对了，但是这某个点又是哪里呢？
前面我们提到过Path可以用来描述一个图像的轮廓，图像的轮廓通常都是用一条线构成的，所以这里的某个点就
是上次操作结束的点，如果没有进行过操作则默认点为坐标原点。

moveTo 和 setLastPoint：
方法预览：
        // moveTo
        public void moveTo (float x, float y)
        // setLastPoint
        public void setLastPoint (float dx, float dy)
这两个方法虽然在作用上有相似之处，但实际上却是完全不同的两个东东，具体参照下表：
方法名	简介	是否影响之前的操作	是否影响之后操作
moveTo	移动下一次操作的起点位置	否	是
setLastPoint	设置之前操作的最后一个点位置	是	是
废话不多说，直接上代码：
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        Path path = new Path();                     // 创建Path
        path.lineTo(200, 200);                      // lineTo
        path.moveTo(200,100);                       // moveTo
        path.lineTo(200,0);                         // lineTo
        canvas.drawPath(path, mPaint);              // 绘制Path
moveTo只改变下次操作的起点，在执行完第一次LineTo的时候，本来的默认点位置是A(200,200),
但是moveTo将其改变成为了C(200,100),所以在第二次调用lineTo的时候就是连接C(200,100) 到 B(200,0) 之间的直线

下面是setLastPoint的示例：
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        Path path = new Path();                     // 创建Path
        path.lineTo(200, 200);                      // lineTo
        path.setLastPoint(200,100);                 // setLastPoint
        path.lineTo(200,0);                         // lineTo
        canvas.drawPath(path, mPaint);              // 绘制Path
setLastPoint是重置上一次操作的最后一个点，在执行完第一次的lineTo的时候，最后一个点是A(200,200),而setLastPoint更改最后一个点
为C(200,100),所以在实际执行的时候，第一次的lineTo就不是从原点O到A(200,200)的连线了，而变成了从原点O到C(200,100)之间的连线了。
close
方法预览：
        public void close ()
close方法用于连接当前最后一个点和最初的一个点(如果两个点不重合的话)，最终形成一个封闭的图形。

第2组: addXxx与arcTo
这次内容主要是在Path中添加基本图形，重点区分addArc与arcTo。
第一类(基本形状)
方法预览：
// 第一类(基本形状)
    // 圆形
    public void addCircle (float x, float y, float radius, Path.Direction dir)
    // 椭圆
    public void addOval (RectF oval, Path.Direction dir)
    // 矩形
    public void addRect (float left, float top, float right, float bottom, Path.Direction dir)
    public void addRect (RectF rect, Path.Direction dir)
    // 圆角矩形
    public void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
    public void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
这一类就是在path中添加一个基本形状，基本形状部分和前面所讲的绘制基本形状并无太大差别
仔细观察一下第一类的方法，无一例外，在最后都有一个_Path.Direction_，这是一个什么神奇的东东？
Direction的意思是 方向，趋势。 点进去看一下会发现Direction是一个枚举(Enum)类型，里面只有两个枚举常量，如下：
类型	解释	翻译
CW	clockwise	顺时针
CCW	counter-clockwise	逆时针
顺时针和逆时针的作用。
序号	作用
1	在添加图形时确定闭合顺序(各个点的记录顺序)
2	对图形的渲染结果有影响(是判断图形渲染的重要条件)
添加一个矩形试试看：
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        Path path = new Path();
        path.addRect(-200,-200,200,200, Path.Direction.CW);
        canvas.drawPath(path,mPaint);
将上面代码的CW改为CCW再运行一次。接下来就是见证奇迹的时刻，两次运行结果一模一样
这个东东是自带隐身技能的，想要让它现出原形，就要用到咱们刚刚学到的setLastPoint(重置当前最后一个点的位置)。
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        Path path = new Path();
        path.addRect(-200,-200,200,200, Path.Direction.CW);
        path.setLastPoint(-300,300);                // <-- 重置最后一个点的位置
        canvas.drawPath(path,mPaint);
Path是封装了由直线和曲线(二次，三次贝塞尔曲线)构成的几何路径。其中曲线部分用的是贝塞尔曲线，稍后再讲。 然而除了
曲线部分就只剩下直线了，对于直线的存储最简单的就是记录坐标点，然后直接连接各个点就行了。虽然记录矩形只需要两个点，
但是如果只用两个点来记录一个矩形的话，就要额外增加一个标志位来记录这是一个矩形，显然对于存储和解析都是很不划算的事情，
将矩形转换为直线，为的就是存储记录方便。
图形在实际记录中就是记录各个的点，对于一个图形来说肯定有多个点，既然有这么多的点，肯定就需要一个先后顺序，这里顺时针和
逆时针就是用来确定记录这些点的顺序的。
对于上面这个矩形来说，我们采用的是顺时针(CW)，所以记录的点的顺序就是 A -> B -> C -> D. 最后一个点就是D，我们这里使用setLastPoint
改变最后一个点的位置实际上是改变了D的位置。
理解了上面的原理之后，设想如果我们将顺时针改为逆时针(CCW)，则记录点的顺序应该就是 A -> D -> C -> B, 再使用setLastPoint则改变的是B的位置

我们用两个点的坐标确定了一个矩形，矩形起始点(A)就是我们指定的第一个点的坐标。
需要注意的是，交换坐标点的顺序可能就会影响到某些绘制内容哦，例如上面的例子，你可以尝试交换两个坐标点，或者指定另外两个点来作为参数，
虽然指定的是同一个矩形，但实际绘制出来是不同的哦。
参数中点的顺序很重要！

第二类(Path)
方法预览：
// 第二类(Path)
    // path
    public void addPath (Path src)
    public void addPath (Path src, float dx, float dy)
    public void addPath (Path src, Matrix matrix)
这个相对比较简单，也很容易理解，就是将两个Path合并成为一个。
这个相对比较简单，也很容易理解，就是将两个Path合并成为一个。
第三个方法是将src添加到当前path之前先使用Matrix进行变换。
第二个方法比第一个方法多出来的两个参数是将src进行了位移之后再添加进当前path中。
示例：
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴
        Path path = new Path();
        Path src = new Path();
        path.addRect(-200,-200,200,200, Path.Direction.CW);
        src.addCircle(0,0,100, Path.Direction.CW);
        path.addPath(src,0,200);
        mPaint.setColor(Color.BLACK);           // 绘制合并后的路径
        canvas.drawPath(path,mPaint);
第三类(addArc与arcTo)
方法预览：
// 第三类(addArc与arcTo)
    // addArc
    public void addArc (RectF oval, float startAngle, float sweepAngle)
    // arcTo
    public void arcTo (RectF oval, float startAngle, float sweepAngle)
    public void arcTo (RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)
从名字就可以看出，这两个方法都是与圆弧相关的，作用都是添加一个圆弧到path中，但既然存在两个方法，两者之间肯定是有区别的：
名称	作用	区别
addArc	添加一个圆弧到path	直接添加一个圆弧到path中
arcTo	添加一个圆弧到path	添加一个圆弧到path，如果圆弧的起点和上次最后一个坐标点不相同，就连接两个点
forceMoveTo是什么作用呢？
这个变量意思为“是否强制使用moveTo”，也就是说，是否使用moveTo将变量移动到圆弧的起点位移，也就意味着：
forceMoveTo	含义	等价方法
true	将最后一个点移动到圆弧起点，即不连接最后一个点与圆弧起点	public void addArc (RectF oval, float startAngle, float sweepAngle)
false	不移动，而是连接最后一个点与圆弧起点	public void arcTo (RectF oval, float startAngle, float sweepAngle)

示例(addArc)：
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴
        Path path = new Path();
        path.lineTo(100,100);
        RectF oval = new RectF(0,0,300,300);
        path.addArc(oval,0,270);
        // path.arcTo(oval,0,270,true);             // <-- 和上面一句作用等价
        canvas.drawPath(path,mPaint);
示例(arcTo)：
        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
        canvas.scale(1,-1);                         // <-- 注意 翻转y坐标轴
        Path path = new Path();
        path.lineTo(100,100);
        RectF oval = new RectF(0,0,300,300);
        path.arcTo(oval,0,270);
        // path.arcTo(oval,0,270,false);             // <-- 和上面一句作用等价
        canvas.drawPath(path,mPaint);


    */

//Path之贝塞尔曲线
    /*
贝塞尔曲线	quadTo, cubicTo	分别为二次和三次贝塞尔曲线的方法
本次需要了解其中的曲线部分,说到曲线，就不得不提大名鼎鼎的贝塞尔曲线。它的发明者是下面这个人(法国数学家PierreBézier)。
贝塞尔曲线能干什么？
贝塞尔曲线的运用是十分广泛的，可以说贝塞尔曲线奠定了计算机绘图的基础(因为它可以将任何复杂的图形用精确的数学语言进行描述)，
在你不经意间就已经使用过它了。

第一步.理解贝塞尔曲线的原理
此处理解贝塞尔曲线并非是学会公式的推导过程(不是推倒(ﾉ*･ω･)ﾉ)，而是要了解贝塞尔曲线是如何生成的。 贝塞尔曲线是用一系列点
来控制曲线状态的，我将这些点简单分为两类：
类型	作用
数据点	确定曲线的起始和结束位置
控制点	确定曲线的弯曲程度

一阶曲线原理：
一阶曲线是没有控制点的，仅有两个数据点(A 和 B)，最终效果一个线段。
二阶曲线原理：
二阶曲线由两个数据点(A 和 C)，一个控制点(B)来描述曲线状态
三阶曲线原理：
三阶曲线由两个数据点(A 和 D)，两个控制点(B 和 C)来描述曲线状态
第二步.了解贝塞尔曲线相关函数使用方法
二阶曲线：
通过上面对二阶曲线的简单了解，我们知道二阶曲线是由两个数据点，一个控制点构成，接下来我们就用一个实例来演示二阶曲线是如何运用的。
首先，两个数据点是控制贝塞尔曲线开始和结束的位置，比较容易理解，而控制点则是控制贝塞尔的弯曲状态，相对来说比较难以理解，所以本示
例重点在于理解贝塞尔曲线弯曲状态与控制点的关系

三阶曲线相比于二阶曲线可以制作更加复杂的形状，但是对于高阶的曲线，用低阶的曲线组合也可达到相同的效果，就是传说中的降阶。因此我们对贝塞尔曲线的封装方法一般最高只到三阶曲线。
降阶与升阶
类型	释义	变化
降阶	在保持曲线形状与方向不变的情况下，减少控制点数量，即降低曲线阶数	方法变得简单，数据点变多，控制点可能减少，灵活性变弱
升阶	在保持曲线形状与方向不变的情况下，增加控制点数量，即升高曲线阶数	方法更加复杂，数据点不变，控制点增加，灵活性变强

第三步.贝塞尔曲线使用实例
在制作这个实例之前，首先要明确一个内容，就是在什么情况下需要使用贝塞尔曲线？
需要绘制不规则图形时？ 当然不是！目前来说，我觉得使用贝塞尔曲线主要有以下几个方面(仅个人拙见，可能存在错误，欢迎指正)
序号	内容	用例
1	事先不知道曲线状态，需要实时计算时	天气预报气温变化的平滑折线图
2	显示状态会根据用户操作改变时	QQ小红点，仿真翻书效果
3	一些比较复杂的运动状态(配合PathMeasure使用)	复杂运动状态的动画效果
至于只需要一个静态的曲线图形的情况，用图片岂不是更好，大量的计算会很不划算。
贝塞尔曲线的主要优点是可以实时控制曲线状态，并可以通过改变控制点的状态实时让曲线进行平滑的状态变化。

接下来我们就用一个简单的示例让一个圆渐变成为心形：
思路分析：
我们最终的需要的效果是将一个圆转变成一个心形，通过分析可知，圆可以由四段三阶贝塞尔曲线组合而成
心形也可以由四段的三阶的贝塞尔曲线组成
两者的差别仅仅在于数据点和控制点位置不同，因此只需要调整数据点和控制点的位置，就能将圆形变为心形。
核心难点：
1.如何得到数据点和控制点的位置？
关于使用绘制圆形的数据点与控制点早就已经有人详细的计算好了，可以参考stackoverflow的一个回答
How to create circle with Bézier curves?其中的数据只需要拿来用即可。
而对于心形的数据点和控制点，可以由圆形的部分数据点和控制点平移后得到，具体参数可以自己慢慢调整到一个满意的效果。
2.如何达到渐变效果？
渐变其实就是每次对数据点和控制点稍微移动一点，然后重绘界面，在短时间多次的调整数据点与控制点，使其逐渐接近目标值，
通过不断的重绘界面达到一种渐变的效果。






     */

//Path之玩出花样(PathMeasure)
    /*
Path & PathMeasure
顾名思义，PathMeasure是一个用来测量Path的类，主要有以下方法:
构造方法
方法名	释义
PathMeasure()	创建一个空的PathMeasure
PathMeasure(Path path, boolean forceClosed)	创建 PathMeasure 并关联一个指定的Path(Path需要已经创建完成)。
公共方法
返回值	方法名	释义
void	setPath(Path path, boolean forceClosed)	关联一个Path
boolean	isClosed()	是否闭合
float	getLength()	获取Path的长度
boolean	nextContour()	跳转到下一个轮廓
boolean	getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo)	截取片段
boolean	getPosTan(float distance, float[] pos, float[] tan)	获取指定长度的位置坐标及该点切线值
boolean	getMatrix(float distance, Matrix matrix, int flags)	获取指定长度的位置坐标及该点Matrix
PathMeasure的方法也不多，接下来我们就逐一的讲解一下。

1.构造函数
构造函数有两个。
无参构造函数：
  PathMeasure ()
用这个构造函数可创建一个空的 PathMeasure，但是使用之前需要先调用 setPath 方法来与 Path 进行关联。
被关联的 Path 必须是已经创建好的，如果关联之后 Path 内容进行了更改，则需要使用 setPath 方法重新关联。
有参构造函数：
  PathMeasure (Path path, boolean forceClosed)
用这个构造函数是创建一个 PathMeasure 并关联一个 Path， 其实和创建一个空的 PathMeasure 后调用 setPath 进行关联效果是一样的，
同样，被关联的 Path 也必须是已经创建好的，如果关联之后 Path 内容进行了更改，则需要使用 setPath 方法重新关联。
该方法有两个参数，第一个参数自然就是被关联的 Path 了，第二个参数是用来确保 Path 闭合，如果设置为 true， 则不论之
前Path是否闭合，都会自动闭合该 Path(如果Path可以闭合的话)。
在这里有两点需要明确:
    <1>不论 forceClosed 设置为何种状态(true 或者 false)， 都不会影响原有Path的状态，即 Path 与 PathMeasure 关联之后，
之前的的 Path 不会有任何改变。
    <2>forceClosed 的设置状态可能会影响测量结果，如果 Path 未闭合但在与 PathMeasure 关联的时候设置 forceClosed 为 true 时，
测量结果可能会比 Path 实际长度稍长一点，获取到到是该 Path 闭合时的状态。
下面我们用一个例子来验证一下：
canvas.translate(mViewWidth/2,mViewHeight/2);
Path path = new Path();
path.lineTo(0,200);
path.lineTo(200,200);
path.lineTo(200,0);
PathMeasure measure1 = new PathMeasure(path,false);
PathMeasure measure2 = new PathMeasure(path,true);
Log.e("TAG", "forceClosed=false---->"+measure1.getLength());
Log.e("TAG", "forceClosed=true----->"+measure2.getLength());
canvas.drawPath(path,mDeafultPaint);
我们所创建的 Path 实际上是一个边长为 200 的正方形的三条边，通过上面的示例就能验证以上两个问题。
1).我们将 Path 与两个的 PathMeasure 进行关联，并给 forceClosed 设置了不同的状态，之后绘制再绘制出来的 Path 没有
任何变化，所以与 Path 与 PathMeasure进行关联并不会影响 Path 状态。
2).我们可以看到，设置 forceClosed 为 true 的方法比设置为 false 的方法测量出来的长度要长一点，这是由于 Path 没有闭合
的缘故，多出来的距离正是 Path 最后一个点与最开始一个点之间点距离。forceClosed 为 false 测量的是当前 Path 状态的长度，
 forceClosed 为 true，则不论Path是否闭合测量的都是 Path 的闭合长度。

2.setPath、 isClosed 和 getLength
这三个方法都如字面意思一样，非常简单，这里就简单是叙述一下，不再过多讲解。
setPath 是 PathMeasure 与 Path 关联的重要方法，效果和 构造函数 中两个参数的作用是一样的。
isClosed 用于判断 Path 是否闭合，但是如果你在关联 Path 的时候设置 forceClosed 为 true 的话，这个方法的返回值则一定为true。
getLength 用于获取 Path 的总长度，在之前的测试中已经用过了。

3.getSegment
getSegment 用于获取Path的一个片段，方法如下：
boolean getSegment (float startD, float stopD, Path dst, boolean startWithMoveTo)
方法各个参数释义：
参数	作用	备注
返回值(boolean)	判断截取是否成功	true 表示截取成功，结果存入dst中，false 截取失败，不会改变dst中内容
startD	开始截取位置距离 Path 起点的长度	取值范围: 0 <= startD < stopD <= Path总长度
stopD	结束截取位置距离 Path 起点的长度	取值范围: 0 <= startD < stopD <= Path总长度
dst	截取的 Path 将会添加到 dst 中	注意: 是添加，而不是替换
startWithMoveTo	起始点是否使用 moveTo	用于保证截取的 Path 第一个点位置不变
如果 startD、stopD 的数值不在取值范围 [0, getLength] 内，或者 startD == stopD 则返回值为 false，不会改变 dst 内容。
如果在安卓4.4或者之前的版本，在默认开启硬件加速的情况下，更改 dst 的内容后可能绘制会出现问题，请关闭硬件加速或者给 dst 添加
一个单个操作，例如: dst.rLineTo(0, 0)
当 dst 中有内容时,被截取的 Path 片段会添加到 dst 中，而不是替换 dst 中到内容。
如果 startWithMoveTo 为 true, 则被截取出来到Path片段保持原状，如果 startWithMoveTo 为 false，则会将截取出来的 Path 片段的起始
点移动到 dst 的最后一个点，以保证 dst 的连续性。
从而我们可以用以下规则来判断 startWithMoveTo 的取值：
取值	主要功用
true	保证截取得到的 Path 片段不会发生形变
false	保证存储截取片段的 Path(dst) 的连续性

4.nextContour
我们知道 Path 可以由多条曲线构成，但不论是 getLength , getSegment 或者是其它方法，都只会在其中第一条线段上运行，而这
个 nextContour 就是用于跳转到下一条曲线到方法，如果跳转成功，则返回 true， 如果跳转失败，则返回 false。
如下，我们创建了一个 Path 并使其中包含了两个闭合的曲线，内部的边长是200，外面的边长是400，现在我们使用 PathMeasure
分别测量两条曲线的总长度。
通过测试，我们可以得到以下内容：
1>.曲线的顺序与 Path 中添加的顺序有关。
2>.getLength 获取到到是当前一条曲线分长度，而不是整个 Path 的长度。
3>.getLength 等方法是针对当前的曲线(其它方法请自行验证)。

5.getPosTan
这个方法是用于得到路径上某一长度的位置以及该位置的正切值：
boolean getPosTan (float distance, float[] pos, float[] tan)
方法各个参数释义：
参数	作用	备注
返回值(boolean)	判断获取是否成功	true表示成功，数据会存入 pos 和 tan 中，
false 表示失败，pos 和 tan 不会改变
distance	距离 Path 起点的长度	取值范围: 0 <= distance <= getLength
pos	该点的坐标值	当前点在画布上的位置，有两个数值，分别为x，y坐标。
tan	该点的正切值	当前点在曲线上的方向，使用 Math.atan2(tan[1], tan[0]) 获取到正切角的弧度值。

核心要点:
1.通过 tan 得值计算出图片旋转的角度，tan 是 tangent 的缩写，即中学中常见的正切， 其中tan[0]是邻边边长，tan[1]是对边边长，而Math中 atan2 方法是根据正切是数值计算出该角度的大小,得到的单位是弧度(取值范围是 -pi 到 pi)，所以上面又将弧度转为了角度。
2.通过 Matrix 来设置图片对旋转角度和位移，这里使用的方法与前面讲解过对 canvas操作 有些类似，对于 Matrix 会在后面专一进行讲解，敬请期待。
3.页面刷新，页面刷新此处是在 onDraw 里面调用了 invalidate 方法来保持界面不断刷新，但并不提倡这么做，正确对做法应该是使用 线程 或者 ValueAnimator 来控制界面的刷新，关于控制页面刷新这一部分会在后续的 动画部分 详细讲解，同样敬请期待。
关于tan这个参数有很多魔法师不理解，特此拉出来详述一下，tan 在数学中被称为正切，在直角三角形中，一个锐角的正切定义为它的对边(Opposite side)与邻边(Adjacent side)的比值(来自维基百科)：
我们此处用 tan 来描述 Path 上某一点的切线方向，主要用了两个数值 tan[0] 和 tan[1] 来描述这个切线的方向(切线方向与x轴夹角) ，看上面公式可知 tan 既可以用 对边／邻边 来表述，也可以用 sin／cos 来表述，此处用两种理解方式均可以(注意下面等价关系):
tan[0] = cos = 邻边(单位圆x坐标)
tan[1] = sin = 对边(单位圆y坐标)

6.getMatrix
这个方法是用于得到路径上某一长度的位置以及该位置的正切值的矩阵：
boolean getMatrix (float distance, Matrix matrix, int flags)
方法各个参数释义：
参数	作用	备注
返回值(boolean)	判断获取是否成功	true表示成功，数据会存入matrix中，false 失败，matrix内容不会改变
distance	距离 Path 起点的长度	取值范围: 0 <= distance <= getLength
matrix	根据 falgs 封装好的matrix	会根据 flags 的设置而存入不同的内容
flags	规定哪些内容会存入到matrix中	可选择
POSITION_MATRIX_FLAG(位置)
ANGENT_MATRIX_FLAG(正切)
其实这个方法就相当于我们在前一个例子中封装 matrix 的过程由 getMatrix 替我们做了，我们可以直接得到一个封装好到 matrix，岂不快哉。
但是我们看到最后到 flags 选项可以选择 位置 或者 正切 ,如果我们两个选项都想选择怎么办？
如果两个选项都想选择，可以将两个选项之间用 | 连接起来，如下：
measure.getMatrix(distance, matrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
可以看到使用 getMatrix 方法的确可以节省一些代码，不过这里依旧需要注意一些内容:
1.对 matrix 的操作必须要在 getMatrix 之后进行，否则会被 getMatrix 重置而导致无效。
2.矩阵对旋转角度默认为图片的左上角，我们此处需要使用 preTranslate 调整为图片中心。
3.pre(矩阵前乘) 与 post(矩阵后乘) 的区别，此处请等待后续的文章或者自行搜索。

Path使用技巧
话说本篇文章的名字不是叫 玩出花样么？怎么只见前面啰啰嗦嗦的扯了一大堆不明所以的东西，花样在哪里？
前面的内容虽然啰嗦繁杂，但却是重中之重的基础，如果在修仙界，这叫根基，而下面讲述的内容的是招式，
有了根基才能演化出千变万化的招式，而没有根基只学招式则是徒有其表，只能学一样会一样，很难适应千变万化的需求。

这是一个搜索的动效图，通过分析可以得到它应该有四种状态，分别如下:
状态	概述
初始状态	初始状态，没有任何动效，只显示一个搜索标志 :mag:
准备搜索	放大镜图标逐渐变化为一个点
正在搜索	围绕这一个圆环运动，并且线段长度会周期性变化
准备结束	从一个点逐渐变化成为放大镜图标
这些状态是有序转换的，转换流程以及转换条件如下：
其中 正在搜索 这个状态持续时间长度是不确定的，在没有搜索完成前，应该一直处于搜索状态。
Path 划分

为了制作对方便，此处整个动效用了两个 Path， 一个是中间对放大镜， 另一个则是外侧的圆环,将两者全部画出来是这样子的。
其中 Path 的走向要把握好，如下(只是一个放大镜，并不是♂):
其中圆形上面的点可以用 PathMeasure 测量，无需计算。
动画状态与时间关联
此处使用的是 ValueAnimator，它可以将一段时间映射到一段数值上，随着时间变化不断的更新数值，
并且可以使用插值器开控制数值变化规律(此处使用的是默认插值器)。
具体绘制
绘制部分是根据 当前状态以及从 ValueAnimator 获得的数值来截取 Path 中合适的部分绘制出来。







     */

























}
