package com.example.doubtnutprojectapp.models;

import java.util.List;

public class NewsModel {

    private String status;

    private int totalResults;

    private List<ArticleModel> articles;




    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResult() {
        return totalResults;
    }

    public void setTotalResult(int totalResult) {
        this.totalResults = totalResult;
    }

    public List<ArticleModel> getArticle() {
        return articles;
    }

    public void setArticle(List<ArticleModel> article) {
        this.articles = article;
    }


}
