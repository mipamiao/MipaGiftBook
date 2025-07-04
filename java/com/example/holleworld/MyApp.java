package com.example.holleworld;

import android.app.Application;

import androidx.room.Room;


import com.example.holleworld.DATA.Repository.AppDatabase;
import com.facebook.stetho.Stetho;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    private AppDatabase database;
    private ExecutorService executor;
    private ThreadPoolExecutor threadPoolExecutor;
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化全局对象
        database = Room.databaseBuilder(this, AppDatabase.class, "my-database")
                .fallbackToDestructiveMigration()
                .build();

        instance  = this;
        Stetho.initializeWithDefaults(this);
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
