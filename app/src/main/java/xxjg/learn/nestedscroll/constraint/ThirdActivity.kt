package xxjg.learn.nestedscroll.constraint

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import xxjg.learn.nestedscroll.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    companion object {
        fun startMe(context: Context) {
            context.startActivity(Intent(context, ThirdActivity::class.java))
        }
    }

    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.C.setOnClickListener() {
            binding.flow.setWrapMode(1)
        }

        binding.D.setOnClickListener() {
            binding.flow.setWrapMode(2)
        }

        binding.E.setOnClickListener() {
            binding.flow.setWrapMode(0)
        }

    }
}