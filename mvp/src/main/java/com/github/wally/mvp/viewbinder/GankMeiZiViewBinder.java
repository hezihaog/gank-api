package com.github.wally.mvp.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.wally.mvp.R;
import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean;
import com.github.wally.mvp.bean.gank.GankMeiZiListBean;
import com.github.wally.mvp.util.UIHelper;
import com.github.wally.mvp.widget.RatioImageView;

import me.drakeet.multitype.ItemViewBinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Package: com.github.wally.mvp.viewbinder
 * FileName: GankMeiZiViewBinder
 * Date: on 2018/6/16  上午10:55
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankMeiZiViewBinder extends ItemViewBinder<GankMeiZiListBean.MeiZi, GankMeiZiViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_gank_mei_zi, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final GankMeiZiListBean.MeiZi meiZi) {
        Glide.with(holder.itemView.getContext())
                .load(meiZi.getUrl())
                .placeholder(R.drawable.shape_item_decoration)
                .crossFade()
                .into(holder.mImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayMeiZiImageBean bean = new DisplayMeiZiImageBean();
                try {
                    bean.setId(meiZi.getId());
                    bean.setCreatedAt(meiZi.getCreatedAt());
                    bean.setDesc(meiZi.getDesc());
                    bean.setPublishedAt(meiZi.getPublishedAt());
                    bean.setSource(meiZi.getSource());
                    bean.setType(meiZi.getType());
                    bean.setUrl(meiZi.getUrl());
                    bean.setUsed(meiZi.getUsed());
                    bean.setWho(meiZi.getWho());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                UIHelper.showMeiZiDetail((SupportActivity) (holder.itemView.getContext()), bean);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final RatioImageView mImageView;

        ViewHolder(final View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_iv);
            mImageView.setOriginalSize(50, 70);
        }
    }
}
