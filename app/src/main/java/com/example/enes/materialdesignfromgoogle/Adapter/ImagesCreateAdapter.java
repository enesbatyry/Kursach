package com.example.enes.materialdesignfromgoogle.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.enes.materialdesignfromgoogle.R;

import java.util.List;

/**
 * Created by Enes on 02.03.2018.
 */

public class ImagesCreateAdapter extends RecyclerView.Adapter<ImagesCreateAdapter.ViewHolder> {

    private List<Bitmap> images;
    private Context context;

    public ImagesCreateAdapter(List<Bitmap> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row_images,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageBitmap(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
        }
    }
}
