package com.example.enes.materialdesignfromgoogle.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.enes.materialdesignfromgoogle.R;

/**
 * Created by Enes on 18/12/2017.
 */

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsHolder> {

    private String[] menu;
    private Context context;

    public SettingsAdapter(Context context, String[] menu) {
        this.context = context;
        this.menu = menu;
    }

    @Override
    public SettingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row_settings, parent, false);


        return new SettingsHolder(view);
    }

    @Override
    public void onBindViewHolder(SettingsHolder holder, int position) {
        holder.textView.setText(menu[position]);
    }

    @Override
    public int getItemCount() {
        return menu.length;
    }

    public class SettingsHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public SettingsHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id .title_settings_menu);
        }
    }
}
