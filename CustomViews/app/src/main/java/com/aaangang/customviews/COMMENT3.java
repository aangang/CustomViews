package com.aaangang.customviews;

/**
 * Created by Administrator on 2017/11/1.
 */
public class COMMENT3 {
    //参考自https://github.com/GcsSloop/AndroidNote/tree/master/CustomView
    //感谢GcsSloop，其blog地址：http://www.gcssloop.com/#blog
/*
    事件分发
事件分发机制原理
为什么要有事件分发机制？

安卓上面的View是树形结构的，View可能会重叠在一起，当我们点击的地方有多个View都可以响应的时候，
这个点击事件应该给谁呢？为了解决这一个问题，就有了事件分发机制。
如下图，View是一层一层嵌套的，当手指点击 View1 的时候，下面的ViewGroupA、 RootView 等也是能够响应的，为了
确定到底应该是哪个View处理这次点击事件，就需要事件分发机制来帮忙。

可以看到在上面的View结构中莫名多出来的两个东西，PhoneWindow 和 DecorView ，这两个我们并没有在Layout文件中
定义过，但是为什么会存在呢？
仔细观察上面的 layout 文件，你会发现一个问题，我在 layout 文件中的最顶层 View(Group) 的大小并不是填满父窗体的，
留下了大量的空白区域，由于我们的手机屏幕不能透明，所以这些空白区域肯定要显示一些东西，那么应该显示什么呢？
有过安卓开发经验的都知道，屏幕上没有View遮挡的部分会显示主题的颜色。不仅如此，最上面的一个标题栏也没有在 layout
文件中，这个标题栏又是显示在哪里的呢？
你没有猜错，这个主题颜色和标题栏等内容就是显示在DecorView中的。

现在知道 DecorView 是干什么的了，那么PhoneWindow 又有什么作用？
要了解 PhoneWindow 是干啥的，首先要了解啥是 Window ，看官方说明：
简单来说，Window是一个抽象类，是所有视图的最顶层容器，视图的外观和行为都归他管，不论是背景显示，标题栏还是事件处理都是
他管理的范畴，它其实就像是View界的太上皇(虽然能管的事情看似很多，但是没实权，因为抽象类不能直接使用)。
而 PhoneWindow 作为 Window 的唯一亲儿子(唯一实现类)，自然就是 View 界的皇帝了，PhoneWindow 的权利可是非常大大，
不过对于我们来说用处并不大，因为皇帝平时都是躲在深宫里面的，虽然偶尔用特殊方法能见上一面，但想要完全指挥
PhoneWindow 为你工作是很困难的。
而上面说的 DecorView 是 PhoneWindow 的一个内部类，其职位相当于小太监，就是跟在 PhoneWindow 身边专业为 PhoneWindow 服务
的，除了自己要干活之外，也负责消息的传递，PhoneWindow 的指示通过 DecorView 传递给下面的 View，而下面 View 的信息也
通过 DecorView 回传给 PhoneWindow。

事件分发、拦截与消费
下表省略了 PhoneWidow 和 DecorView。
√ 表示有该方法。
X 表示没有该方法。
类型	    相关方法	         Activity	ViewGroup	View
事件分发	dispatchTouchEvent	    √	      √	   √
事件拦截	onInterceptTouchEvent	 X	      √	    X
事件消费	onTouchEvent	        √	      √	   √
这个三个方法均有一个 boolean(布尔) 类型的返回值，通过返回 true 和 false 来控制事件传递的流程。
PS: 从上表可以看到 Activity 和 View 都是没有事件拦截的，这是因为：
Activity 作为原始的事件分发者，如果 Activity 拦截了事件会导致整个屏幕都无法响应事件，这肯定不是我们想要的效果。
View最为事件传递的最末端，要么消费掉事件，要么不处理进行回传，根本没必要进行事件拦截。
事件收集之后最先传递给 Activity， 然后依次向下传递，大致如下：
Activity －> PhoneWindow －> DecorView －> ViewGroup －> ... －> View
这样的事件分发机制逻辑非常清晰，可是，你是否注意到一个问题？如果最后分发到View，如果这个View也没有处理事件怎么办，
就这样让事件浪费掉？
当然不会啦，如果没有任何View消费掉事件，那么这个事件会按照反方向回传，最终传回给Activity，如果最后 Activity
也没有处理，本次事件才会被抛弃:
Activity <－ PhoneWindow <－ DecorView <－ ViewGroup <－ ... <－ View

看到这里，我不禁微微一皱眉，这个东西咋看起来那么熟悉呢？再仔细一看，这不就是一个非常经典的责任链模式吗， 如果我能处
理就拦截下来自己干，如果自己不能处理或者不确定就交给责任链中下一个对象。
这种设计是非常精巧的，上层View既可以直接拦截该事件，自己处理，也可以先询问(分发给)子View，如果子View需要就交给子View处
理，如果子View不需要还能继续交给上层View处理。既保证了事件的有序性，又非常的灵活

先确定几个角色：
Activity － 公司大老板
RootView － 项目经理
ViewGroupA － 技术小组长
View1 － 码农小王(公司里唯一的码农)
View2 － 跑龙套的路人甲，无视即可
1.点击 View1 区域但没有任何 View 消费事件
当手指在 View1 区域点击了一下之后，如果所有View都不消耗事件，你就能看到一个完整的事件分发流程，大致如下：

上面的流程中存在部分不合理内容，请大家选择性接受。
事件返回时 dispatchTouchEvent 直接指向了父View的 onTouchEvent 这一部分是不合理的，实际上它仅仅是给了父View的 dispatchTouchEvent 一个 false 返回值，父View根据返回值来调用自身的 onTouchEvent。
ViewGroup 是根据 onInterceptTouchEvent 的返回值来确定是调用子View的 dispatchTouchEvent 还是自身的 onTouchEvent， 并没有将调用交给 onInterceptTouchEvent。
ViewGroup 的事件分发机制伪代码如下，可以看出调用的顺序。
public boolean dispatchTouchEvent(MotionEvent ev) {
    boolean result = false;             // 默认状态为没有消费过
    if (!onInterceptTouchEvent(ev)) {   // 如果没有拦截交给子View
        result = child.dispatchTouchEvent(ev);
    }
    if (!result) {                      // 如果事件没有被消费,询问自身onTouchEvent
        result = onTouchEvent(ev);
    }
    return result;
}
测试:
情景：老板: 我看公司最近业务不咋地，准备发展一下电商业务，下周之前做个淘宝出来试试怎么样。
事件顺序，老板(MainActivity)要做淘宝，这个事件通过各个部门(ViewGroup)一层一层的往下传，传到最底层的时候，
码农小王(View1)发现做不了，于是消息又一层一层的回传到老板那里。
可以看到整个事件传递路线非常有序。从Activity开始，最后回传给Activity结束(由于我们无法操作Phone Window和DecorView，
所以没有它们的信息)。
MainActivity [老板]: dispatchTouchEvent     经理,我准备发展一下电商业务,下周之前做一个淘宝出来.
RootView     [经理]: dispatchTouchEvent     呼叫技术部,老板要做淘宝,下周上线.
RootView     [经理]: onInterceptTouchEvent  (老板可能疯了,但又不是我做.)
ViewGroupA   [组长]: dispatchTouchEvent     老板要做淘宝,下周上线?
ViewGroupA   [组长]: onInterceptTouchEvent  (看着不太靠谱,先问问小王怎么看)
View1        [码农]: dispatchTouchEvent     做淘宝???
View1        [码农]: onTouchEvent           这个真心做不了啊.
ViewGroupA   [组长]: onTouchEvent           小王说做不了.
RootView     [经理]: onTouchEvent           报告老板, 技术部说做不了.
MainActivity [老板]: onTouchEvent           这么简单都做不了,你们都是干啥的(愤怒).


2.点击 View1 区域且事件被 View1 消费
如果事件被View1消费掉了则事件会回传告诉上层View这个事件已经被我解决了，上层View就无需再响应了。
注意：这张图中的事件回传路径才是正确的路径。
测试:
情景：老板: 我觉得咱们这个app按钮不好看，做的有光泽一点，要让人有一种想点的欲望。
事件顺序，老板(MainActivity)要做改界面，这个事件通过各个部门(ViewGroup)一层一层的往下传，传到最底层的时候，
码农小王(View1)就在按钮上添加了一道光(为啥是小王呢？因为公司没有设计师)。
可以看出，事件一旦被消费就意味着消息传递的结束，上层View知道了事件已经被消费掉，就不再处理了。
MainActivity [老板]: dispatchTouchEvent     把按钮做的好看一点,要有光泽,给人一种点击的欲望.
RootView     [经理]: dispatchTouchEvent     技术部,老板说按钮不好看,要加一道光.
RootView     [经理]: onInterceptTouchEvent
ViewGroupA   [组长]: dispatchTouchEvent     给按钮加上一道光.
ViewGroupA   [组长]: onInterceptTouchEvent
View1        [码农]: dispatchTouchEvent     加一道光.
View1        [码农]: onTouchEvent           做好了.

3.点击 View1 区域但事件被 ViewGroupA 拦截
上层的View有权拦截事件，不传递给下层View，例如 ListView 滑动的时候，就不会将事件传递给下层的子 View。
注意：可以看到，如果上层拦截了事件，下层View将接收不到事件信息。
测试：
情景：老板: 报告一下项目进度。
事件顺序，老板(MainActivity)要知道项目进度，这个事件通过各个部门(ViewGroup)一层一层的往下传，传到技术组
组长(ViewGroupA)的时候，组长(ViewGroupA)上报任务即可。无需告知码农小王(View1)。
MainActivity [老板]: dispatchTouchEvent     现在项目做到什么程度了?
RootView     [经理]: dispatchTouchEvent     技术部,你们的app快做完了么?
RootView     [经理]: onInterceptTouchEvent
ViewGroupA   [组长]: dispatchTouchEvent     项目进度?
ViewGroupA   [组长]: onInterceptTouchEvent
ViewGroupA   [组长]: onTouchEvent           正在测试,明天就测试完了

其他情况
事件分发机制设计到到情形非常多，这里就不一一列举了，记住以下几条原则就行了。
1.如果事件被消费，就意味着事件信息传递终止。
2.如果事件一直没有被消费，最后会传给Activity，如果Activity也不需要就被抛弃。
3.判断事件是否被消费是根据返回值，而不是根据你是否使用了事件。










 */


























}
