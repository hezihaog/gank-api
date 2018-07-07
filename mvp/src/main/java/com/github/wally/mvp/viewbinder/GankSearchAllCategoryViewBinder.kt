package com.github.wally.mvp.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.github.wally.mvp.R
import com.github.wally.mvp.bean.gank.GankSearchBean

import me.drakeet.multitype.ItemViewBinder

/**
 * Package: com.github.wally.mvp.viewbinder
 * FileName: GankSearchAllCategoryViewBinder
 * Date: on 2018/6/24  上午12:19
 * Auther: zihe
 * Descirbe:所有类型的条目
 * Email: hezihao@linghit.com
 */
internal class GankSearchAllCategoryViewBinder : ItemViewBinder<GankSearchBean.Msg, GankSearchAllCategoryViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_gank_search_all_category, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, gankSearchAllCategory: GankSearchBean.Msg) {
        val desc = gankSearchAllCategory.desc
        val who = gankSearchAllCategory.who
        holder.mDescTv.text = desc
        holder.mWhoTv.text = who
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val mDescTv: TextView
        internal val mWhoTv: TextView

        init {
            mDescTv = itemView.findViewById(R.id.desc_tv)
            mWhoTv = itemView.findViewById(R.id.who_tv)
        }
    }
}
