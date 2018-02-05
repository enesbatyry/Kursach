package com.example.enes.materialdesignfromgoogle.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enes.materialdesignfromgoogle.Activities.ContentActivity;
import com.example.enes.materialdesignfromgoogle.Model.InfoContent;
import com.example.enes.materialdesignfromgoogle.R;
import com.example.enes.materialdesignfromgoogle.Util.Constants;
import com.example.enes.materialdesignfromgoogle.Util.ImageUtils;
import com.example.enes.materialdesignfromgoogle.fragments.ContentFragment;

import java.util.List;

/**
 * Created by Enes on 16/12/2017.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder>{

    private Activity context;
    private final int REQUEST_CONTENT = 2;
    private static ContentAdapter contentAdapter;

    public List<InfoContent> getInfoContentList() {
        return infoContentList;
    }

    public static List<InfoContent> infoContentList;

    public ContentAdapter(Context context, List<InfoContent> infoContentList) {
        this.context = (Activity) context;
        this.infoContentList = infoContentList;
        contentAdapter = this;
    }

    public ContentAdapter(Context context) {
        this.context = (Activity) context;
    }

    public void setInfoContentList(List<InfoContent> infoContentList) {
        this.infoContentList = infoContentList;
    }

    public static ContentAdapter getAdapter(){
        return contentAdapter;
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row_content, parent,false);

        return new ContentHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        String title = infoContentList.get(position).getTitle();

        int message_length = infoContentList.get(position).getMessage().length();
        String message = infoContentList.get(position).getMessage();
        if (message_length > 20){
            message = infoContentList.get(position).getMessage().substring(0,20).concat("...");
        }

        Bitmap image = infoContentList.get(position).getImage();

        holder.mTitle.setText(title);
        holder.mMessage.setText(message);
        holder.imageView.setImageBitmap(image);
    }

    @Override
    public int getItemCount() {
        return infoContentList.size();
    }


    public class ContentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTitle;
        public TextView mMessage;
        public ImageView imageView;
        private Intent intent;


        public ContentHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mMessage = itemView.findViewById(R.id.message);
            imageView = itemView.findViewById(R.id.imageView);
            intent = new Intent(context, ContentActivity.class);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.v(Constants.MyLog,"Position = " + position);

            intent.putExtra("id",position);
            context.startActivityForResult(intent, REQUEST_CONTENT);

        }


    }

}
