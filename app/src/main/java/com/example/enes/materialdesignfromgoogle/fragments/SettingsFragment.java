package com.example.enes.materialdesignfromgoogle.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.enes.materialdesignfromgoogle.Activities.FacebookActivity;
import com.example.enes.materialdesignfromgoogle.R;
import com.example.enes.materialdesignfromgoogle.Activities.VkActivity;

public class SettingsFragment extends Fragment {
    private Button btn_showActivity_vk;
    private Button btn__showActivity_facebook;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        btn_showActivity_vk =view.findViewById(R.id.btn_showActivity_vk);
        btn_showActivity_vk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VkActivity.class);
                startActivity(intent);
            }
        });

        btn__showActivity_facebook = view.findViewById(R.id.btn_showActivity_facebook);
        btn__showActivity_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FacebookActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}