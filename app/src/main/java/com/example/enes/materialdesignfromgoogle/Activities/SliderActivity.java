package com.example.enes.materialdesignfromgoogle.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.enes.materialdesignfromgoogle.Adapter.SlideAdapter;
import com.example.enes.materialdesignfromgoogle.R;

public class SliderActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SlideAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        viewPager = findViewById(R.id.viewPager);
        myAdapter = new SlideAdapter(this);
        viewPager.setAdapter(myAdapter);
    }
}
