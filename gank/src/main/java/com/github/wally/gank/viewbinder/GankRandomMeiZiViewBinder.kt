package com.github.wally.gank.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.wally.gank.R
import com.github.wally.gank.bean.gank.DisplayMeiZiImageBean
import com.github.wally.gank.bean.gank.GankRandomListBean
import com.github.wally.gank.ext.loadUrlImage
import com.github.wally.gank.util.UIHelper
import com.github.wally.gank.widget.RatioImageView
import me.yokeyword.fragmentation.SupportActivity

/**
 * Package: com.github.wally.gank.viewbinder
 * FileName: GankRandomContentViewBinder
 * Date: on 2018/6/18  上午11:09
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
internal class GankRandomMeiZiViewBinder : BaseItemViewBinder<GankRandomListBean.MeiZi, GankRandomMeiZiViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(root: View): ViewHolder {
        return ViewHolder(root)
    }

    override fun onLayoutId(): Int {
        return R.layout.item_gank_random_mei_zi_content
    }

    override fun onBindViewHolder(holder: ViewHolder, meiZi: GankRandomListBean.MeiZi) {
        val position = getPosition(holder)
        if (position % 2 == 0) {
            holder.mImageView.setOriginalSize(50, 70)
        } else {
            holder.mImageView.setOriginalSize(50, 80)
        }
        holder.mImageView.requestLayout()
        meiZi.url.let {
            loadUrlImage(it, holder.mImageView, R.drawable.ic_default_image)
        }
        holder.itemView.setOnClickListener {
            val bean = DisplayMeiZiImageBean().apply {
                id = meiZi.id
                createdAt = meiZi.createdAt
                desc = meiZi.desc
                publishedAt = meiZi.publishedAt
                source = meiZi.source
                type = meiZi.type
                url = meiZi.url
                used = meiZi.used
                who = meiZi.who
            }
            UIHelper.showMeiZiDetail(holder.itemView.context as SupportActivity, bean)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView: RatioImageView

        init {
            mImageView = itemView.findViewById(R.id.image_iv)
            mImageView.setOriginalSize(50, 70)
        }
    }
}
