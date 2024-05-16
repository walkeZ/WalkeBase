package com.walker.mvvmdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.walker.mvvmdemo.ui.main.Mvvm2Fragment;

public class Mvvm2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm2);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Mvvm2Fragment.newInstance())
                    .commitNow();
        }
    }
}