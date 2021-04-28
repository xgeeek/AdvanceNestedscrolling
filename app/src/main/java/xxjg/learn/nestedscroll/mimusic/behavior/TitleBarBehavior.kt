package xxjg.learn.nestedscroll.mimusic.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import xxjg.learn.nestedscroll.R

/**
 * @author xugang
 * @date 2021/4/28
 */
class TitleBarBehavior(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private var topBarHeight: Int
    private var contentTransY: Float

    init {
        contentTransY = context.resources.getDimension(R.dimen.content_trans_y)
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        topBarHeight = context.resources.getDimension(R.dimen.top_bar_height).toInt() + statusBarHeight
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency.id == R.id.ll_content
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        //调整TitleBar位置要紧贴Content顶部上面
        adjustPosition(parent, child, dependency)
        //这里只计算Content上滑范围一半的百分比
        val start = (contentTransY + topBarHeight) / 2
        val upPro: Float = (contentTransY - MathUtils.clamp(dependency.translationY, start, contentTransY)) / (contentTransY - start)
        child.alpha = 1 - upPro
        return true
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        //找到Content的依赖引用
        val dependencies = parent.getDependencies(child)
        var dependency: View? = null
        for (view in dependencies) {
            if (view.id == R.id.ll_content) {
                dependency = view
                break
            }
        }
        return if (dependency != null) {
            //调整TitleBar位置要紧贴Content顶部上面
            adjustPosition(parent, child, dependency)
            true
        } else {
            false
        }
    }

    private fun adjustPosition(@NonNull parent: CoordinatorLayout, @NonNull child: View, dependency: View) {
        val lp: CoordinatorLayout.LayoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
        var left = parent.paddingLeft + lp.leftMargin
        var top: Int = (dependency.y - child.measuredHeight + lp.topMargin).toInt()
        val right = child.measuredWidth + left - parent.paddingRight - lp.rightMargin
        val bottom = (dependency.y - lp.bottomMargin).toInt()
        child.layout(left, top, right, bottom)
    }


}