package com.conan.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.conan.myapp.R;
import com.conan.myapp.activity.CollapsingActivity;
import com.conan.myapp.bean.Stars;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author        JY
 * PublishDate   2017-03-25
 * Description   功能描述
 * Version       1.0
 * Updated       JY
 */
public class StarsAdapter extends RecyclerView.Adapter<StarsAdapter.ViewHolder> {

    private Context mContext;
    private List<Stars> list;

    public StarsAdapter(List<Stars> list) {
        this.list = list;
    }

    @Override
    public StarsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_resycler_stars, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Stars star = list.get(position);
                Intent intent = new Intent(mContext, CollapsingActivity.class);
                intent.putExtra(CollapsingActivity.STAR_NAME, star.getName());
                intent.putExtra(CollapsingActivity.STAR_IMG_URL, star.getImageUrl());
                mContext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StarsAdapter.ViewHolder holder, int position) {
        Stars star = list.get(position);
        holder.tvName.setText(star.getName());
        Glide.with(mContext).load(star.getImageUrl()).into(holder.imgStar);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_star)
        ImageView imgStar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.card_view)
        CardView cardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
