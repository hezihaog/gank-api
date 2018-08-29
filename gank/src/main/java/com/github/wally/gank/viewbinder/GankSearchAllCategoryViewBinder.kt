package com.github.wally.gank.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.github.wally.gank.R
import com.github.wally.gank.bean.gank.GankSearchBean
import com.github.wally.gank.ext.setViewText

/**
 * Package: com.github.wally.gank.viewbinder
 * FileName: GankSearchAllCategoryViewBinder
 * Date: on 2018/6/24  上午12:19
 * Auther: zihe
 * Descirbe:所有类型的条目
 * Email: hezihao@linghit.com
 */
class GankSearchAllCategoryViewBinder : BaseItemViewBinder<GankSearchBean.Msg, GankSearchAllCategoryViewBinder.ViewHolder>() {
    override fun onLayoutId(): Int {
        return R.layout.item_gank_search_all_category
    }

    override fun onCreateViewHolder(root: View): ViewHolder {
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, gankSearchAllCategory: GankSearchBean.Msg) {
        gankSearchAllCategory.desc.let {
            setViewText(holder.mDescTv, it)
        }
        gankSearchAllCategory.who.let {
            setViewText(holder.mWhoTv, it)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mDescTv: TextView
        val mWhoTv: TextView

        init {
            mDescTv = itemView.findViewById(R.id.desc_tv)
            mWhoTv = itemView.findViewById(R.id.who_tv)
        }
    }
}
