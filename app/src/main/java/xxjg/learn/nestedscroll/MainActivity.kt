package xxjg.learn.nestedscroll

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import xxjg.learn.nestedscroll.constraint.ConstraintLayoutActivity
import xxjg.learn.nestedscroll.databinding.ActivityMainBinding
import xxjg.learn.nestedscroll.mimusic.MiMusicMainActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        setOnClickListener()
    }


    private fun setOnClickListener() {
        binding.tvConstraint.setOnClickListener {
            ConstraintLayoutActivity.startMe(this)
        }

        binding.tvNestedMusic.setOnClickListener {
            MiMusicMainActivity.startMe(this)
        }
    }
}