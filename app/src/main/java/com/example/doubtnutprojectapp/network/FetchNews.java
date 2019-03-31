package com.example.doubtnutprojectapp.network;

import com.example.doubtnutprojectapp.models.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FetchNews {

    @GET("top-headlines")
    Call<NewsModel> getNews(
            @Query("country") String country ,
            @Query("pageSize") int pageSize ,
            @Query("page") int page ,
            @Query("apiKey") String apiKey
    );
}
