package com.example.doubtnutprojectapp.database;

import com.example.doubtnutprojectapp.models.ArticleModel;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArticleModel news);

    @Query("DELETE FROM news_table")
    void deleteAllNews();

    @Query("SELECT * FROM news_table")
    List<ArticleModel>getAllNews();
}
