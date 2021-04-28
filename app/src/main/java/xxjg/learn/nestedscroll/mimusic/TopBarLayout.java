package xxjg.learn.nestedscroll.mimusic;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @author xugang
 * @date 2021/4/27
 */
public class TopBarLayout extends ConstraintLayout {

    public TopBarLayout(@NonNull Context context) {
        super(context);
    }

    public TopBarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TopBarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() + getStatusBarHeight(), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }

    /**
     * 获取状态栏高度
     */
    private int getStatusBarHeight() {
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getContext().getResources().getDimensionPixelSize(resourceId);
    }
}
