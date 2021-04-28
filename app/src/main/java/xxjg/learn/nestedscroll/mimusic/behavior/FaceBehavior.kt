package xxjg.learn.nestedscroll.mimusic.behavior

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.palette.graphics.Palette
import xxjg.learn.nestedscroll.R

/**
 * @function: face部分的Behavior
 */
class FaceBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<View>(context, attrs) {
    private val topBarHeight //topBar内容高度
            : Int
    private val contentTransY //滑动内容初始化TransY
            : Float
    private val downEndY //下滑时终点值
            : Float
    private val faceTransY //图片往上位移值
            : Float
    private val drawable //蒙层的背景
            : GradientDrawable

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        //依赖Content View
        return dependency.id == R.id.ll_content
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        //设置蒙层背景
        child.findViewById<View>(R.id.v_mask).background = drawable
        return false
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        //计算Content的上滑百分比、下滑百分比
        val upPro = (contentTransY - MathUtils.clamp(dependency.translationY, topBarHeight.toFloat(), contentTransY)) / (contentTransY - topBarHeight)
        val downPro = (downEndY - MathUtils.clamp(dependency.translationY, contentTransY, downEndY)) / (downEndY - contentTransY)
        val iamgeview = child.findViewById<ImageView>(R.id.iv_face)
        val maskView = child.findViewById<View>(R.id.v_mask)
        if (dependency.translationY >= contentTransY) {
            //根据Content上滑百分比位移图片TransitionY
            iamgeview.translationY = downPro * faceTransY
        } else {
            //根据Content下滑百分比位移图片TransitionY
            iamgeview.translationY = faceTransY + 4 * upPro * faceTransY
        }
        //根据Content上滑百分比设置图片和蒙层的透明度
        iamgeview.alpha = 1 - upPro
        maskView.alpha = upPro
        //因为改变了child的位置，所以返回true
        return true
    }

    private fun getTranslucentColor(percent: Float, rgb: Int): Int {
        val blue = Color.blue(rgb)
        val green = Color.green(rgb)
        val red = Color.red(rgb)
        var alpha = Color.alpha(rgb)
        alpha = Math.round(alpha * percent)
        return Color.argb(alpha, red, green, blue)
    }

    init {
        //引入尺寸值
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        topBarHeight = context.resources.getDimension(R.dimen.top_bar_height).toInt() + statusBarHeight
        contentTransY = context.resources.getDimension(R.dimen.content_trans_y)
        downEndY = context.resources.getDimension(R.dimen.content_trans_down_end_y)
        faceTransY = context.resources.getDimension(R.dimen.face_trans_y)

        //抽取图片资源的亮色或者暗色作为蒙层的背景渐变色
        val palette = Palette.from(BitmapFactory.decodeResource(context.resources, R.mipmap.jj))
                .generate()
        val vibrantSwatch = palette.vibrantSwatch
        val mutedSwatch = palette.mutedSwatch
        val colors = IntArray(2)
        if (mutedSwatch != null) {
            colors[0] = mutedSwatch.rgb
            colors[1] = getTranslucentColor(0.6f, mutedSwatch.rgb)
        } else if (vibrantSwatch != null) {
            colors[0] = vibrantSwatch.rgb
            colors[1] = getTranslucentColor(0.6f, vibrantSwatch.rgb)
        } else {
            colors[0] = Color.parseColor("#4D000000")
            colors[1] = getTranslucentColor(0.6f, Color.parseColor("#4D000000"))
        }
        drawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
    }
}