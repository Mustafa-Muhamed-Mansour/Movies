package com.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.movies.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    
    private ActivityMainBinding activityMainBinding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        
        
    }
}