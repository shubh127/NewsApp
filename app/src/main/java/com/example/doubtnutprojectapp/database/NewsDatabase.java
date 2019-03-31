package com.example.doubtnutprojectapp.database;

import android.content.Context;

import com.example.doubtnutprojectapp.models.ArticleModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ArticleModel.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    private static NewsDatabase instance;

    public abstract NewsDao newsDao();


    public static synchronized NewsDatabase getInstance(Context contxet) {
        if (instance == null) {
            instance = Room.databaseBuilder(contxet.getApplicationContext(),
                    NewsDatabase.class, "news_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
