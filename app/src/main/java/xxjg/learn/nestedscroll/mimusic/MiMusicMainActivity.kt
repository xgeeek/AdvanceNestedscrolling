package xxjg.learn.nestedscroll.mimusic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.jaeger.library.StatusBarUtil
import xxjg.learn.nestedscroll.databinding.ActivityMainMiBinding

/**
 * @author xugang
 * @date 2021/4/27
 */
class MiMusicMainActivity : AppCompatActivity() {

    companion object {
        fun startMe(context: Context) {
            context.startActivity(Intent(context, MiMusicMainActivity::class.java))
        }
    }

    private lateinit var binding: ActivityMainMiBinding
    private val titlesTab: Array<String> = arrayOf("热门", "专辑", "视屏", "资讯")
    private var mFragments: ArrayList<Fragment> = ArrayList()
    private lateinit var mAdapter: MyFragmentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMiBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        StatusBarUtil.setTranslucentForImageView(this, 0, null)
        initData()
        initView()
    }


    private fun initData() {
        mFragments.add(SongFragment.newInstance())
        mFragments.add(TabFragment.newInstance("我是专辑页面"))
        mFragments.add(TabFragment.newInstance("我是视屏页面"))
        mFragments.add(TabFragment.newInstance("我是资讯页面"))

        mAdapter = MyFragmentAdapter(supportFragmentManager)
    }

    private fun initView() {
        binding.vp.adapter = mAdapter
        binding.stl.setViewPager(binding.vp, titlesTab)
        // 反射修改最少滑动距离
        try {
            val touchSlp = ViewPager::class.java.getDeclaredField("mTouchSlop")
            touchSlp.isAccessible = true
            touchSlp.setInt(binding.vp, dp2px(50f))
        } catch (e: Exception) {
            Log.d("touchSlp", e.message.toString())
        }
        binding.vp.offscreenPageLimit = mFragments.size
    }

    private fun dp2px(dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, resources.displayMetrics).toInt()
    }


    /**
     * 内部类 inner 可以访问外部类的成员
     * 嵌套类 去除inner即可,相当于java中的static class
     */
    inner class MyFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }

    }
}