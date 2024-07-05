package com.dhprac.myapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dhprac.myapp2.databinding.ActivityShortsBinding;

public class ShortsActivity extends AppCompatActivity {

    ActivityShortsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShortsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}