package com.example.holleworld.Utils;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActi extends AppCompatActivity {
    String TAG = "787877887";
    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }

    @Override
    protected void onResume() {
        super.onResume();Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();Log.e(TAG, "onDestroy: ");
    }
}
