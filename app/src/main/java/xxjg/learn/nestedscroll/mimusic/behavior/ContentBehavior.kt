package xxjg.learn.nestedscroll.mimusic.behavior

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import xxjg.learn.nestedscroll.R
import java.lang.reflect.Field

/**
 * @author xugang
 * @date 2021/4/27
 */
class ContentBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context, attrs) {

    companion object {
        /*编译时常量 编译时就确定了*/
        private const val ANIM_DURATION_FRACTION = 200L
    }

    private val topBarHeight: Int //topBar内容高度
    private val contentTransY: Float  //滑动内容初始化TransY
    private val downEndY: Float //下滑时终点值
    private var restoreAnimator: ValueAnimator?  //收起内容时执行的动画
    private var flingFromCollaps = false //fling是否从折叠状态发生的

    private var mLlContent: View? = null //Content部分

    init {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)

        // toInt的使用
        topBarHeight = context.resources.getDimension(R.dimen.top_bar_height).toInt() + statusBarHeight
        contentTransY = context.resources.getDimension(R.dimen.content_trans_y)
        downEndY = context.resources.getDimension(R.dimen.content_trans_down_end_y)
        restoreAnimator = ValueAnimator()
        // todo  匿名函数的使用
//        restoreAnimator.addUpdateListener(new ValueAnimator. AnimatorUpdateListener () {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                translation(mLlContent, (float) animation . getAnimatedValue ());
//            }
//        });
        restoreAnimator!!.addUpdateListener { animation ->
            translation(mLlContent, animation.animatedValue as Float)
        }
    }

    override fun onMeasureChild(parent: CoordinatorLayout, child: View, parentWidthMeasureSpec: Int, widthUsed: Int,
                                parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        val childLpHeight = child.layoutParams.height
        if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT || childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //先获取CoordinatorLayout的测量规格信息，若不指定具体高度则使用CoordinatorLayout的高度
            var availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec)
            if (availableHeight == 0) {
                availableHeight = parent.height
            }

            // todo 条件语句的使用
            //设置Content部分高度
            val height = availableHeight - topBarHeight
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height,
                    if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT)
                        View.MeasureSpec.EXACTLY
                    else
                        View.MeasureSpec.AT_MOST)

            //执行指定高度的测量，并返回true表示使用Behavior来代理测量子View
            parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed)
            return true
        }
        return false
    }

    private fun translation(view: View?, translationY: Float) {
        // 确定不为空的使用
        view!!.translationY = translationY
    }


    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        val handleLayout = super.onLayoutChild(parent, child, layoutDirection)
        // 绑定content view
        mLlContent = child
        return handleLayout
    }


    //--- NestedScrollingParent ---//
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int): Boolean {
        return onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, ViewCompat.TYPE_TOUCH);
    }

    override fun onNestedScrollAccepted(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int) {
        onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, axes, ViewCompat.TYPE_TOUCH)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray) {
        onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, ViewCompat.TYPE_TOUCH)
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View) {
        onStopNestedScroll(coordinatorLayout, child, target, ViewCompat.TYPE_TOUCH)
    }


    //--- NestedScrollingParent2 ---//
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        //只接受内容View的垂直滑动
        return (directTargetChild.id == R.id.ll_content
                && axes == ViewCompat.SCROLL_AXIS_VERTICAL)
    }

    override fun onNestedScrollAccepted(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int) {
        if (restoreAnimator!!.isStarted) {
            restoreAnimator!!.cancel()
        }
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, type: Int) {
        //如果是从初始状态转换到展开状态过程触发收起动画
        if (child.translationY > contentTransY) {
            restore()
        }
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        var transY = child.translationY - dy

        //处理上滑
        if (dy > 0) {
            if (transY >= topBarHeight) {
                translationByConsume(child, transY, consumed, dy.toFloat())
            } else {
                translationByConsume(child, topBarHeight.toFloat(), consumed, (child.translationY - topBarHeight))
            }
        }

        if (dy < 0 && !target.canScrollVertically(-1)) {
            //下滑时处理Fling,折叠时下滑Recycler(或NestedScrollView) Fling滚动到contentTransY停止Fling
            if (type == ViewCompat.TYPE_NON_TOUCH && transY >= contentTransY && flingFromCollaps) {
                flingFromCollaps = false
                translationByConsume(child, contentTransY, consumed, dy.toFloat())
                stopViewScroll(target)
                return
            }

            //处理下滑
            if (transY >= topBarHeight && transY <= downEndY) {
                translationByConsume(child, transY, consumed, dy.toFloat())
            } else {
                translationByConsume(child, downEndY, consumed, downEndY - child.translationY)
                stopViewScroll(target)
            }
        }
    }

    private fun stopViewScroll(target: View) {
        if (target is RecyclerView) {
            target.stopScroll()
        }
        if (target is NestedScrollView) {
            try {
                val clazz: Class<out NestedScrollView?> = target.javaClass
                val mScroller: Field = clazz.getDeclaredField("mScroller")
                mScroller.isAccessible = true
                val overScroller: OverScroller = mScroller.get(target) as OverScroller
                overScroller.abortAnimation()
            } catch (e: Exception) {
            }
        }
    }

    private fun translationByConsume(view: View, translationY: Float, consumed: IntArray, consumedDy: Float) {
        consumed[1] = consumedDy.toInt()
        view.translationY = translationY
    }

    private fun restore() {
        if (restoreAnimator!!.isStarted) {
            restoreAnimator!!.cancel()
            restoreAnimator!!.removeAllListeners()
        }
        restoreAnimator!!.setFloatValues(mLlContent!!.translationY, contentTransY)
        restoreAnimator!!.duration = ANIM_DURATION_FRACTION
        restoreAnimator!!.start()
    }

    override fun onDetachedFromLayoutParams() {
        if (restoreAnimator!!.isStarted) {
            restoreAnimator!!.cancel()
            restoreAnimator!!.removeAllUpdateListeners()
            restoreAnimator!!.removeAllListeners()
            restoreAnimator = null
        }
        super.onDetachedFromLayoutParams()
    }

}