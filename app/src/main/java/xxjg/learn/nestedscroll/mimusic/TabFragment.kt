package xxjg.learn.nestedscroll.mimusic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import xxjg.learn.nestedscroll.R
import xxjg.learn.nestedscroll.databinding.FragmentTextLayoutBinding

/**
 * @author xugang
 * @date 2021/4/27
 */
class TabFragment : Fragment() {

    companion object {
        fun newInstance(name: String): TabFragment {
            val args = Bundle()
            args.putString("data", name)
            val fragment = TabFragment()
            fragment.arguments = args
            return fragment
        }
    }

    /*可空类型*/
    private var name: String? = ""
    private lateinit var binding: FragmentTextLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString("data")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_text_layout, container, false)
        binding = FragmentTextLayoutBinding.bind(view)
        binding.tvFragmentTabText.text = name
        return view
    }

}