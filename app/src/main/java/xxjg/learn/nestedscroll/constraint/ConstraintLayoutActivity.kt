package xxjg.learn.nestedscroll.constraint

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import xxjg.learn.nestedscroll.databinding.ActivityConstraintLayoutBinding

class ConstraintLayoutActivity : AppCompatActivity() {

    companion object {
        fun startMe(context: Context) {
            val intent = Intent(context, ConstraintLayoutActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityConstraintLayoutBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstraintLayoutBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setOnclickListener()
    }


    private fun setOnclickListener() {
        binding.tvBaseline.setOnClickListener {
            FirstConActivity.startMe(this)
        }
        binding.tvRatio.setOnClickListener {
            SecondConActivity.startMe(this)
        }

        binding.tvFlow.setOnClickListener {
            ThirdActivity.startMe(this)
        }
    }
}