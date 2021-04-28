package xxjg.learn.nestedscroll.mimusic

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xxjg.learn.nestedscroll.R

class SongAdater(data: List<Int?>?) : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_song_laypout, data) {
    override fun convert(helper: BaseViewHolder, item: Int) {
        helper.setText(R.id.tv_item_muisc_no, (item + 1).toString())
    }
}