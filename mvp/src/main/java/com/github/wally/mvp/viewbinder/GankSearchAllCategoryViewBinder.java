package com.github.wally.mvp.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.wally.mvp.R;
import com.github.wally.mvp.bean.gank.GankSearchBean;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Package: com.github.wally.mvp.viewbinder
 * FileName: GankSearchAllCategoryViewBinder
 * Date: on 2018/6/24  上午12:19
 * Auther: zihe
 * Descirbe:所有类型的条目
 * Email: hezihao@linghit.com
 */
public class GankSearchAllCategoryViewBinder extends ItemViewBinder<GankSearchBean.Msg, GankSearchAllCategoryViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_gank_search_all_category, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GankSearchBean.Msg gankSearchAllCategory) {
        String desc = gankSearchAllCategory.getDesc();
        String who = gankSearchAllCategory.getWho();
        holder.mDescTv.setText(desc);
        holder.mWhoTv.setText(who);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mDescTv;
        private final TextView mWhoTv;

        ViewHolder(View itemView) {
            super(itemView);
            mDescTv = itemView.findViewById(R.id.desc_tv);
            mWhoTv = itemView.findViewById(R.id.who_tv);
        }
    }
}
