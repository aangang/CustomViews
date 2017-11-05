package com.aaangang.customviews;

/**
 * Created by Administrator on 2017/11/1.
 */
public class COMMENT4_ViewGroup {
    //参考自http://blog.csdn.net/harvic880925/article/details/50995268   自定义控件三部曲
    //
/*
http://blog.csdn.net/harvic880925/article/details/47029169
一、ViewGroup绘制流程
注意，View及ViewGroup基本相同，只是在ViewGroup中不仅要绘制自己还是绘制其中的子控件，而View则只需要绘制自己就可以了，
所以我们这里就以ViewGroup为例来讲述整个绘制流程。
绘制流程分为三步：测量、布局、绘制
分别对应：onMeasure()、onLayout()、onDraw()
其中，他们三个的作用分别如下：
onMeasure()：测量自己的大小，为正式布局提供建议。（注意，只是建议，至于用不用，要看onLayout）;
onLayout():使用layout()函数对所有子控件布局；
onDraw():根据布局的位置绘图；
二、onMeasure与MeasureSpec

布局绘画涉及两个过程：测量过程和布局过程。测量过程通过measure方法实现，是View树自顶向下的遍历，每个View在循环
过程中将尺寸细节往下传递，当测量过程完成之后，所有的View都存储了自己的尺寸。第二个过程则是通过方法layout来实现的，
也是自顶向下的。在这个过程中，每个父View负责通过计算好的尺寸放置它的子View。
前面讲过，onMeasure()是用来测量当前控件大小的，给onLayout（）提供数值参考，需要特别注意的是：测量完成以后通过
setMeasuredDimension(int,int)设置给系统。
1、onMeasure
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
widthMeasureSpec和heightMeasureSpec转化成二进制数字表示，他们都是32位的。前两位代表mode(测量模式)，
后面30位才是他们的实际数值（size）。
（1）模式分类
它有三种模式：
①、UNSPECIFIED(未指定)，父元素不对子元素施加任何束缚，子元素可以得到任意想要的大小；
②、EXACTLY(完全)，父元素决定子元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
③、AT_MOST(至多)，子元素至多达到指定大小的值。
他们对应的二进制值分别是：
UNSPECIFIED=00000000000000000000000000000000
EXACTLY =01000000000000000000000000000000
AT_MOST =10000000000000000000000000000000
由于最前面两位代表模式，所以他们分别对应十进制的0，1，2；
简单来说，XML布局和模式有如下对应关系：
wrap_content-> MeasureSpec.AT_MOST
match_parent -> MeasureSpec.EXACTLY
具体值 -> MeasureSpec.EXACTLY
例如，下面这个XML
[java] view plain copy
<com.example.harvic.myapplication.FlowLayout
     android:layout_width="match_parent"
     android:layout_height="wrap_content">
</com.example.harvic.myapplication.FlowLayout>
一定要注意是，当模式是MeasureSpec.EXACTLY时，我们就不必要设定我们计算的大小了，因为这个大小是用户指定的，我们不应更改
但当模式是MeasureSpec.AT_MOST时，也就是说用户将布局设置成了wrap_content，我们就需要将大小设定为我们计算的数值，因为
用户根本没有设置具体值是多少，需要我们自己计算
即，假如width和height是我们经过计算的控件所占的宽度和高度。那在onMeasure()中使用setMeasuredDimension（）
最后设置时，代码应该是这样的:
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
    int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
    int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
    int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
    //经过计算，控件所占的宽和高分别对应width和height
    //计算过程，我们会在下篇细讲
    …………
    setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth: width,
                                    (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight: height);
}

三、onLayout()
1、概述

上面说了，onLayout()是实现所有子控件布局的函数。注意，是所有子控件！！！那它自己的布局怎么办？后面我们再讲，
先讲讲在onLayout()中我们应该做什么。
我们先看看ViewGroup的onLayout()函数的默认行为是什么
在ViewGroup.java中
[java] view plain copy
@Override
protected abstract void onLayout(boolean changed, int l, int t, int r, int b);
是一个抽象方法，说明凡是派生自ViewGroup的类都必须自己去实现这个方法。像LinearLayout、RelativeLayout等布局，
都是重写了这个方法，然后在内部按照各自的规则对子视图进行布局的。

简单实例：
1、三个TextView竖直排列
2、背景的Layout宽度是match_parent，高度是wrap_content.
下面我们就看一下，代码上如何实现：
（1）、XML布局
首先我们看一下XML布局：（activity_main.xml）
[html] view plain copy
<com.harvic.simplelayout.MyLinLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#ff00ff"
    tools:context=".MainActivity">
    <TextView android:text="第一个VIEW"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
    <TextView android:text="第二个VIEW"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
    <TextView android:text="第三个VIEW"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</com.harvic.simplelayout.MyLinLayout>
可见里面有三个TextView,然后自定义的MyLinLayout布局，宽度设为了match_parent,高度设为了wrap_content.
(2)、MyLinLayout实现：重写onMeasure()函数
我们前面讲过，onMeasure()的作用就是根据container内部的子控件计算自己的宽和高，最后通过setMeasuredDimension（int width,int height设置进去）；
下面看看onMeasure()的完整代码,然后再逐步讲解：
[java] view plain copy
@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
    int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
    int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
    int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
    int height = 0;
    int width = 0;
    int count = getChildCount();
    for (int i=0;i<count;i++) {
        //测量子控件
        View child = getChildAt(i);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        //获得子控件的高度和宽度
        int childHeight = child.getMeasuredHeight();
        int childWidth = child.getMeasuredWidth();
        //得到最大宽度，并且累加高度
        height += childHeight;
        width = Math.max(childWidth, width);
    }
    setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth: width,
                            (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight: height);
}
首先，是从父类传过来的建议宽度和高度值：widthMeasureSpec、heightMeasureSpec
从他们里面利用MeasureSpec提取宽高值和对应的模式：
首先，是从父类传过来的建议宽度和高度值：widthMeasureSpec、heightMeasureSpec
从他们里面利用MeasureSpec提取宽高值和对应的模式：
[java] view plain copy
int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
接下来就是通过测量它所有的子控件来决定它所占位置的大小：
[java] view plain copy
int height = 0;
int width = 0;
int count = getChildCount();
for (int i=0;i<count;i++) {
    //测量子控件
    View child = getChildAt(i);
    measureChild(child, widthMeasureSpec, heightMeasureSpec);
    //获得子控件的高度和宽度
    int childHeight = child.getMeasuredHeight();
    int childWidth = child.getMeasuredWidth();
    //得到最大宽度，并且累加高度
    height += childHeight;
    width = Math.max(childWidth, width);
}
我们这里要计算的是整个VIEW当被设置成layout_width="wrap_content",layout_height="wrap_content"所占用的大小，
因为我们是垂直排列其内部所有的VIEW，所以container所占宽度应该是各个TextVIew中的最大宽度，所占高度应该是所有控件的高度和。
前面我们讲过，模式与XML布局的对应关系：
* wrap_content-> MeasureSpec.AT_MOST
* match_parent -> MeasureSpec.EXACTLY
* 具体值 -> MeasureSpec.EXACTLY
再看我们前面XML中针对MyLinLayout的设置：
[html] view plain copy
<com.harvic.simplelayout.MyLinLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#ff00ff"
    tools:context=".MainActivity">
所以这里的measureWidthMode应该是MeasureSpec.EXACTLY,measureHeightMode应该是MeasureSpec.AT_MOST；所以在最后利用
setMeasuredDimension(width,height)来最终设置时，width使用的是从父类传过来的measureWidth，而高度则是我们自己计算的height.
即实际的运算结果是这样的：
[java] view plain copy
setMeasuredDimension(measureWidth,height);
总体来讲，onMeasure()中计算出的width和height，就是当XML布局设置为layout_width="wrap_content"、
layout_height="wrap_content"时所占的宽和高；即整个container所占的最小矩形


(3)、MyLinLayout实现：重写onLayout()函数
在这部分，就是根据自己的意愿把内部的各个控件排列起来。我们要完成的是将所有的控件垂直排列；
先看完整的代码，然后再细讲：
[java] view plain copy
protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int top = 0;
    int count = getChildCount();
    for (int i=0;i<count;i++) {

        View child = getChildAt(i);

        int childHeight = child.getMeasuredHeight();
        int childWidth = child.getMeasuredWidth();

        child.layout(0, top, childWidth, top + childHeight);
        top += childHeight;
    }
}
最核心的代码，就是调用layout()函数设置子控件所在的位置：
[java] view plain copy
int childHeight = child.getMeasuredHeight();
int childWidth = child.getMeasuredWidth();

child.layout(0, top, childWidth, top + childHeight);
top += childHeight;
在这里top指的是控件的顶点，那bottom的坐标就是top+childHeight,我们从最左边开始布局，那么right的坐标就肯定
是子控件的宽度值了childWidth.
(4)、getMeasuredWidth()与getWidth()
趁热打铁，就这个例子，我们讲一个很容易出错的问题：getMeasuredWidth()与getWidth()的区别。他们的值大部分时间都是相同的，
但意义确是根本不一样的，我们就来简单分析一下。
区别主要体现在下面几点：
- 首先getMeasureWidth()方法在measure()过程结束后就可以获取到了，而getWidth()方法要在layout()过程结束后才能获取到。
- getMeasureWidth()方法中的值是通过setMeasuredDimension()方法来进行设置的，而getWidth()方法中的值则是通过layout
(left,top,right,bottom)方法设置的。
还记得吗，我们前面讲过，setMeasuredDimension()提供的测量结果只是为布局提供建议，最终的取用与否要看layout()函数。
大家再看看我们上面重写的MyLinLayout，是不是我们自己使用child.layout(left,top,right,bottom)来定义了各个子控件所应在的位置：
[java] view plain copy
int childHeight = child.getMeasuredHeight();
int childWidth = child.getMeasuredWidth();
child.layout(0, top, childWidth, top + childHeight);
一定要注意的一点是：getMeasureWidth()方法在measure()过程结束后就可以获取到了，而getWidth()方法要在layout()过程结束后才能获
取到。再重申一遍！！！！！






















 */


























}
