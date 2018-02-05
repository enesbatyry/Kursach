package com.example.enes.materialdesignfromgoogle.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enes.materialdesignfromgoogle.Adapter.ContentAdapter;
import com.example.enes.materialdesignfromgoogle.Data.DatabaseLoader;
import com.example.enes.materialdesignfromgoogle.Model.InfoContent;
import com.example.enes.materialdesignfromgoogle.Model.InfoContentDAO;
import com.example.enes.materialdesignfromgoogle.R;
import com.example.enes.materialdesignfromgoogle.Util.Constants;

import java.util.List;

public class ContentFragment extends Fragment{

    private RecyclerView recyclerView;

    public static final int LOADER_ID = 1;
    private Loader<ContentAdapter> mLoader;
    private List<InfoContent> infoContentList;
    private InfoContentDAO contentLab;

    private ContentAdapter adapter;

    public ContentFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Constants.MyLog, "onCreate: ");

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_content, container, false);

        recyclerView = view.findViewById(R.id.contentRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        Bundle bundle = new Bundle();
        bundle.putString(DatabaseLoader.ARG_CONTENTS,"test");
       // mLoader = getActivity().getSupportLoaderManager().initLoader(LOADER_ID,bundle,this);

        Log.d(Constants.MyLog, "onCreateView: ");

        return view;
    }

    public void updateUI(){
        contentLab = InfoContentDAO.getInstance(getActivity());
        infoContentList = contentLab.getContentList();
        if (adapter == null){
            adapter = new ContentAdapter(getContext(),infoContentList);
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

}
