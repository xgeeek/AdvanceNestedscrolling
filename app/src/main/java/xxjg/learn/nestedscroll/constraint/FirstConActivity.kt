package xxjg.learn.nestedscroll.constraint

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import xxjg.learn.nestedscroll.databinding.ActivityBaselineCircleBiasBinding

/**
 * Baseline   基准线对齐
 * Circle     角度对齐
 * Bias       百分比便宜
 * goneMargin 消失时生效
 * 控件尺寸    spread---充满 percent---比例 wrap---自适应
 */
class FirstConActivity : AppCompatActivity() {

    companion object {
        fun startMe(context: Context) {
            val intent = Intent(context, FirstConActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityBaselineCircleBiasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaselineCircleBiasBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setOnClickListener()
    }


    private fun setOnClickListener() {
        binding.A.setOnClickListener {
            binding.A.visibility = View.GONE
        }
    }

}