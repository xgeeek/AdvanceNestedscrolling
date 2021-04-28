package xxjg.learn.nestedscroll.constraint

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import xxjg.learn.nestedscroll.databinding.ActivitySecondConBinding

/**
 * ratio  宽高比例
 * Chains 占用方式：spread---均分 spread_inside---两侧贴边  packed---紧贴居中
 *                 Chains(链)还支持weight（权重）的配置，使用layout_constraintHorizontal_weight和layout_constraintVertical_weight进行设置链元素的权重
 * Guideline 参考线
 */
class SecondConActivity : AppCompatActivity() {

    companion object {
        fun startMe(context: Context) {
            val intent = Intent(context, SecondConActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivitySecondConBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondConBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.A.setOnClickListener {
            var chainStyleParams: ConstraintLayout.LayoutParams = binding.A.layoutParams as ConstraintLayout.LayoutParams
            chainStyleParams.horizontalChainStyle = 0
            binding.A.post {
                binding.A.layoutParams = chainStyleParams
            }
        }

        binding.B.setOnClickListener {
            var chainStyleParams: ConstraintLayout.LayoutParams = binding.A.layoutParams as ConstraintLayout.LayoutParams
            chainStyleParams.horizontalChainStyle = 1
            binding.A.post {
                binding.A.layoutParams = chainStyleParams
            }
        }

        binding.C.setOnClickListener {
            var chainStyleParams: ConstraintLayout.LayoutParams = binding.A.layoutParams as ConstraintLayout.LayoutParams
            chainStyleParams.horizontalChainStyle = 2
            binding.A.post {
                binding.A.layoutParams = chainStyleParams
            }
        }

        binding.tvGl.setOnClickListener {
            var layoutParams: ConstraintLayout.LayoutParams = binding.tvGl.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.width = 300
            binding.tvGl.post {
                binding.tvGl.layoutParams = layoutParams
            }
        }

        binding.tvContent.setOnClickListener {
            binding.placeHolder.setContentId(binding.tvContent.id)
        }
    }

}