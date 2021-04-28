package xxjg.learn.nestedscroll.mimusic.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import xxjg.learn.nestedscroll.R

/**
 * @function: TopBar部分的Behavior
 */
class TopBarBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context, attrs) {

    private val contentTransY: Float//滑动内容初始化TransY
    private val topBarHeight: Int //topBar内容高度


    init {
        //引入尺寸值
        contentTransY = context.resources.getDimension(R.dimen.content_trans_y).toFloat()
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        topBarHeight = context.resources.getDimension(R.dimen.top_bar_height).toInt() + statusBarHeight
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        //依赖Content
        return dependency.id == R.id.ll_content
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        //计算Content上滑的百分比，设置子view的透明度
        val upPro: Float = (contentTransY - MathUtils.clamp(dependency.translationY, topBarHeight.toFloat(), contentTransY)) / (contentTransY - topBarHeight)
        val tvName = child.findViewById<View>(R.id.tv_top_bar_name)
        val tvColl = child.findViewById<View>(R.id.tv_top_bar_coll)
        tvName.alpha = upPro
        tvColl.alpha = upPro
        return true
    }

}