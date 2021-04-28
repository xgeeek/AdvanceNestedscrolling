package xxjg.learn.nestedscroll.mimusic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xxjg.learn.nestedscroll.R
import java.util.*

class SongFragment : Fragment() {

    companion object {
        fun newInstance(): SongFragment {
            return SongFragment()
        }
    }

    private var mRecyclerView: RecyclerView? = null
    private var mAdater: SongAdater? = null
    private val mDatas = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_song_layout, container, false)
        initView(view)
        initEvent()
        return view
    }

    private fun initView(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
    }

    private fun initEvent() {
        mRecyclerView!!.adapter = mAdater
    }

    private fun initData() {
        for (i in 0..31) {
            mDatas.add(i)
        }
        mAdater = SongAdater(mDatas)
    }
}