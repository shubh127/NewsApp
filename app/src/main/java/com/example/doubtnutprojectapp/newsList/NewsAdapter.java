package com.example.doubtnutprojectapp.newsList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doubtnutprojectapp.R;
import com.example.doubtnutprojectapp.models.ArticleModel;
import com.example.doubtnutprojectapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    private List<ArticleModel> articleModelList = new ArrayList<>();
    private Context context;
    private OnItemClickListner listner;


    class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNewsImage;
        TextView tvNewsTitle, tvNewsDescription;

        NewsViewHolder(View v) {
            super(v);
            ivNewsImage = v.findViewById(R.id.iv_news_image);
            tvNewsTitle = v.findViewById(R.id.tv_news_title);
            tvNewsDescription = v.findViewById(R.id.tv_news_description);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onItemClick(articleModelList.get(position));
                    }
                }
            });
        }
    }

    void setArticle(List<ArticleModel> articleModelList, Context context) {
        if (getItemCount() == articleModelList.size()) {
            Utils.showToast(context, context.getString(R.string.no_new_news));
            return;
        }
        this.articleModelList = articleModelList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_child, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        Picasso.with(context).load(articleModelList.get(position).getUrlToImage()).placeholder(R.drawable.placeholder).into(holder.ivNewsImage);
        holder.tvNewsTitle.setText(articleModelList.get(position).getTitle());
        holder.tvNewsDescription.setText(articleModelList.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return articleModelList.size();
    }

    public interface OnItemClickListner {
        void onItemClick(ArticleModel articleModel);
    }

    void setOnItemClickListner(OnItemClickListner listner) {
        this.listner = listner;
    }

}