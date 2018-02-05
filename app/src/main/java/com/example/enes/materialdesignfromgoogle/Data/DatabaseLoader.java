package com.example.enes.materialdesignfromgoogle.Data;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.enes.materialdesignfromgoogle.Adapter.ContentAdapter;
import com.example.enes.materialdesignfromgoogle.Model.InfoContent;
import com.example.enes.materialdesignfromgoogle.Model.InfoContentDAO;
import com.example.enes.materialdesignfromgoogle.Util.Constants;

import java.util.List;

/**
 * Created by Enes on 19.01.2018.
 */

public class DatabaseLoader extends AsyncTaskLoader<ContentAdapter> {


    private DatabaseHandler db;
    public static final String ARG_CONTENTS = "contents";
    private List<InfoContent> infoContentList;
    private ContentAdapter adapter;
    private String mContents;
    private InfoContentDAO contentLab;
    private Context ctx;

    public DatabaseLoader(Context context, Bundle args) {
        super(context);
        ctx = context;
        if (args != null)
            mContents = args.getString(ARG_CONTENTS);
    }


    @Override
    public ContentAdapter loadInBackground() {
        Log.d(Constants.MyLog, "loadInBackground: ");
        contentLab = InfoContentDAO.getInstance(ctx);
        infoContentList = contentLab.getContentList();
        adapter = new ContentAdapter(ctx,infoContentList);
        return adapter;
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    public void deliverResult(ContentAdapter data) {
        super.deliverResult(data);
    }
}
