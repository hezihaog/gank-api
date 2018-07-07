package com.github.wally.mvp.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.github.wally.mvp.R
import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean
import com.github.wally.mvp.bean.gank.GankRandomListBean
import com.github.wally.mvp.util.UIHelper
import com.github.wally.mvp.widget.RatioImageView
import me.drakeet.multitype.ItemViewBinder
import me.yokeyword.fragmentation.SupportActivity

/**
 * Package: com.github.wally.mvp.viewbinder
 * FileName: GankRandomContentViewBinder
 * Date: on 2018/6/18  上午11:09
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
internal class GankRandomMeiZiViewBinder : ItemViewBinder<GankRandomListBean.MeiZi, GankRandomMeiZiViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_gank_random_mei_zi_content, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, meiZi: GankRandomListBean.MeiZi) {
        val position = getPosition(holder)
        if (position % 2 == 0) {
            holder.mImageView.setOriginalSize(50, 70)
        } else {
            holder.mImageView.setOriginalSize(50, 80)
        }
        holder.mImageView.requestLayout()
        Glide.with(holder.itemView.context)
                .load(meiZi.url)
                .placeholder(R.drawable.ic_default_image)
                .crossFade()
                .into(holder.mImageView)
        holder.itemView.setOnClickListener {
            val bean = DisplayMeiZiImageBean()
            try {
                bean.id = meiZi.id
                bean.createdAt = meiZi.createdAt
                bean.desc = meiZi.desc
                bean.publishedAt = meiZi.publishedAt
                bean.source = meiZi.source
                bean.type = meiZi.type
                bean.url = meiZi.url
                bean.used = meiZi.used
                bean.who = meiZi.who
            } catch (e: Exception) {
                e.printStackTrace()
            }

            UIHelper.showMeiZiDetail(holder.itemView.context as SupportActivity, bean)
        }
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val mImageView: RatioImageView

        init {
            mImageView = itemView.findViewById(R.id.image_iv)
            mImageView.setOriginalSize(50, 70)
        }
    }
}
