package com.example.doubtnutprojectapp.network;

import android.content.Context;

import com.example.doubtnutprojectapp.models.NewsModel;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {

    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static final String API_KEY = "4e02fcfa306546ba9556850eb654e723";

    private FetchNews apiService;

    public ApiHelper(Context context) {
        Cache cache = new Cache(context.getApplicationContext().getCacheDir(), 1024 * 10 * 10);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(FetchNews.class);
    }

    public Call<NewsModel> getArticles( int page) {
        return apiService.getNews("in", 10, page, API_KEY);
    }


}