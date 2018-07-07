package com.github.wally.mvp.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.github.wally.mvp.R
import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean
import com.github.wally.mvp.bean.gank.GankMeiZiListBean
import com.github.wally.mvp.util.UIHelper
import com.github.wally.mvp.widget.RatioImageView

import me.drakeet.multitype.ItemViewBinder
import me.yokeyword.fragmentation.SupportActivity

/**
 * Package: com.github.wally.mvp.viewbinder
 * FileName: GankMeiZiViewBinder
 * Date: on 2018/6/16  上午10:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
internal class GankMeiZiViewBinder : ItemViewBinder<GankMeiZiListBean.MeiZi, GankMeiZiViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_gank_mei_zi, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, meiZi: GankMeiZiListBean.MeiZi) {
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

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val mImageView: RatioImageView

        init {
            mImageView = itemView.findViewById(R.id.image_iv)
            mImageView.setOriginalSize(50, 70)
        }
    }
}