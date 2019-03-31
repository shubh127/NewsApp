package com.example.doubtnutprojectapp.newsList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.doubtnutprojectapp.R;
import com.example.doubtnutprojectapp.constants.IntentConstants;
import com.example.doubtnutprojectapp.models.ArticleModel;
import com.example.doubtnutprojectapp.newsDetail.NewsDetailActivity;
import com.example.doubtnutprojectapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListActivity extends AppCompatActivity implements NewsAdapter.OnItemClickListner, View.OnClickListener {
    private RecyclerView rvArticlesList;
    private NewsAdapter newsAdapter;
    private boolean isLoading = false;
    private List<ArticleModel> articleModelList;
    private NewsListViewModel newsListViewModel;
    private ProgressDialog progressDialog;
    private LinearLayout llNoDataLayout;
    private Button btnRetry;
    private Context context;
    private NewsListActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        context = this;
        mActivity = this;

        initViews();
        fetchNews();
    }

    private void initViews() {

        rvArticlesList = findViewById(R.id.rv_news_list);
        llNoDataLayout = findViewById(R.id.ll_no_data_layout);
        btnRetry = findViewById(R.id.btn_retry);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        rvArticlesList.setLayoutManager(mLayoutManager);
        rvArticlesList.setHasFixedSize(true);
        newsListViewModel = ViewModelProviders.of(mActivity).get(NewsListViewModel.class);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(getString(R.string.please_wait_fetching_data));


        articleModelList = new ArrayList<>();

        newsAdapter = new NewsAdapter();
        rvArticlesList.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListner(this);
        btnRetry.setOnClickListener(this);
    }

    private void fetchNews() {
        showHideProgressDialog(true);
        newsListViewModel.getArticles().observe(this, new Observer<List<ArticleModel>>() {
            @Override
            public void onChanged(List<ArticleModel> articleModel) {
                showHideProgressDialog(false);
                if (articleModel != null && articleModel.size() > 0) {
                    rvArticlesList.setVisibility(View.VISIBLE);
                    llNoDataLayout.setVisibility(View.GONE);
                    articleModelList.clear();
                    articleModelList.addAll(articleModel);
                    newsAdapter.setArticle(articleModel, context);
                    if (!Utils.isNetworkAvailable()) {
                        Utils.showToast(context, getString(R.string.connect_to_internet_for_latest_news));
                    }
                } else {
                    rvArticlesList.setVisibility(View.GONE);
                    llNoDataLayout.setVisibility(View.VISIBLE);
                    Utils.showToast(context, getString(R.string.connext_to_internet_first_time));
                }
            }
        });

        initScrollListener();

    }

    private void showHideProgressDialog(boolean show) {
        if (show) {
            progressDialog.show();
            isLoading = true;
        } else {
            isLoading = false;
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    private void initScrollListener() {
        rvArticlesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == articleModelList.size() - 1) {
                        //bottom of list!
                        fetchNews();
                    }
                }
            }
        });
    }


    @Override
    public void onItemClick(ArticleModel articleModel) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(IntentConstants.NEWS_TITLE, articleModel.getTitle());
        intent.putExtra(IntentConstants.AUTHOR, articleModel.getAuthor());
        intent.putExtra(IntentConstants.NEWS_CONTENT, articleModel.getContent());
        intent.putExtra(IntentConstants.NEWS_DESCRIPTION, articleModel.getDescription());
        intent.putExtra(IntentConstants.PUBLISHED_AT, articleModel.getPublishedAt());
        intent.putExtra(IntentConstants.SOURCE_URL, articleModel.getUrl());
        intent.putExtra(IntentConstants.IMAGE_URL, articleModel.getUrlToImage());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_retry:
                fetchNews();
                break;
        }
    }
}
