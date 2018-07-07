package com.github.wally.mvp.viewbinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.wally.mvp.R;
import com.github.wally.mvp.base.BaseRxActivity;
import com.github.wally.mvp.bean.gank.DisplayMeiZiImageBean;
import com.github.wally.mvp.bean.gank.GankRandomListBean;
import com.github.wally.mvp.util.UIHelper;
import com.github.wally.mvp.widget.RatioImageView;

import me.drakeet.multitype.ItemViewBinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Package: com.github.wally.mvp.viewbinder
 * FileName: GankRandomContentViewBinder
 * Date: on 2018/6/18  上午11:09
 * Auther: zihe
 * Descirbe:
 * Email: hezihao@linghit.com
 */
public class GankRandomMeiZiViewBinder extends ItemViewBinder<GankRandomListBean.MeiZi, GankRandomMeiZiViewBinder.ViewHolder> {
    private BaseRxActivity mActivity;

    public GankRandomMeiZiViewBinder(BaseRxActivity activity) {
        this.mActivity = activity;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_gank_random_mei_zi_content, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final GankRandomListBean.MeiZi meiZi) {
        int position = getPosition(holder);
        if (position % 2 == 0) {
            holder.mImageView.setOriginalSize(50, 70);
        } else {
            holder.mImageView.setOriginalSize(50, 80);
        }
        holder.mImageView.requestLayout();
        Glide.with(holder.itemView.getContext())
                .load(meiZi.getUrl())
                .placeholder(R.drawable.ic_default_image)
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
                UIHelper.INSTANCE.showMeiZiDetail((SupportActivity) (holder.itemView.getContext()), bean);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final RatioImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_iv);
            mImageView.setOriginalSize(50, 70);
        }
    }
}
