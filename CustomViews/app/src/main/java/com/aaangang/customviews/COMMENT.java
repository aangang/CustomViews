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
}
