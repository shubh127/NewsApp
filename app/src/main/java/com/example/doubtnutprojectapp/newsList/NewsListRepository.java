package com.example.doubtnutprojectapp.newsList;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.doubtnutprojectapp.database.NewsDatabase;
import com.example.doubtnutprojectapp.models.ArticleModel;
import com.example.doubtnutprojectapp.models.NewsModel;
import com.example.doubtnutprojectapp.network.ApiHelper;
import com.example.doubtnutprojectapp.utils.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class NewsListRepository {

    private static NewsListRepository instance;
    private NewsDatabase database;
    private ApiHelper apiHelper;
    private int pageCount = 1;


    private NewsListRepository(ApiHelper apiHelper, NewsDatabase database) {
        this.apiHelper = apiHelper;
        this.database = database;
    }

    static NewsListRepository getInstance(Context context) {
        if (instance == null) {
            instance = new NewsListRepository(new ApiHelper(context),
                    NewsDatabase.getInstance(context));
        }
        return instance;
    }

    MutableLiveData<List<ArticleModel>> getArticles() {
        final MutableLiveData<List<ArticleModel>> mutableLiveData = new MutableLiveData<>();
        if (Utils.isNetworkAvailable()) {
            apiHelper.getArticles( pageCount).enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(@NonNull Call<NewsModel> call,
                                       @NonNull final Response<NewsModel> response) {

                    if (response.body() != null) {
                        pageCount++;
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                if (pageCount == 0) {
                                    database.newsDao().deleteAllNews();
                                }
                                if (response.body().getArticle()!= null) {
                                    for (ArticleModel articleModel : response.body().getArticle()) {
                                        database.newsDao().insert(articleModel);
                                    }
                                }
                                mutableLiveData.postValue(database.newsDao().getAllNews());
                            }
                        };
                        thread.start();

                    }

                }

                @Override
                public void onFailure(@NonNull Call<NewsModel> call,
                                      @NonNull Throwable t) {
                }
            });
        } else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    final List<ArticleModel> templist = database.newsDao().getAllNews();

                    Handler handler = new Handler(Looper.getMainLooper());
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            mutableLiveData.setValue(templist);
                        }
                    };
                    handler.post(runnable);
                }
            };
            thread.start();
        }
        return mutableLiveData;
    }

}
