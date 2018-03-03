package com.example.enes.materialdesignfromgoogle.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.enes.materialdesignfromgoogle.Activities.MainActivity;
import com.example.enes.materialdesignfromgoogle.R;

/**
 * Created by Enes on 25.02.2018.
 */

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public int[] list_images = {
            R.drawable.prepod,
            R.drawable.student,
            R.drawable.famicon,
    };

    public String[] list_title = {
            "АЛИЕВ М.В",
            "БАТЫРЫ Э.О",
            "ФАМИКОН",
    };

    public String[] list_description = {
            "Научный руководитель",
            "Студент",
            "Группа 4ИС",
    };

    public  int[] list_backgroudColor = {
            Color.rgb(55,55,55),
            Color.rgb(239,85,85),
            Color.rgb(110,49,89),
    };

    @Override
    public int getCount() {
        return list_title.length;
    }

    public SlideAdapter(Context context){
        this.context = context;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide, container, false);
        LinearLayout linearLayout = view.findViewById(R.id.slidelinearlatyout);
        ImageView imageView = view.findViewById(R.id.slideimg);
        TextView textTitle = view.findViewById(R.id.txtTitle);
        TextView textDesc = view.findViewById(R.id.txtdescription);
        Button button = view.findViewById(R.id.skipButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });

        linearLayout.setBackgroundColor(list_backgroudColor[position]);
        imageView.setImageResource(list_images[position]);
        textTitle.setText(list_title[position]);
        textDesc.setText(list_description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
