package com.example.enes.materialdesignfromgoogle.Model;

import android.content.Context;

import com.example.enes.materialdesignfromgoogle.Data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Enes on 26/12/2017.
 */

public class InfoContentDAO {

    private static InfoContentDAO instance;
    private List<InfoContent> contentList;
    private DatabaseHandler db;

    private InfoContentDAO(Context context){
        contentList = new ArrayList<>();
        db = new DatabaseHandler(context);
    }

    public static InfoContentDAO getInstance(Context context){

        if(instance == null){
            instance = new InfoContentDAO(context);
        }

        return instance;
    }


    public List<InfoContent> getContentList() {
        contentList = db.getAllContent();
        return contentList;
    }

    public void deleteContent(InfoContent content){
        db.deleteContent(content.getId());
        contentList.remove(content);
    }

    public void addContent(InfoContent content){
        db.addContent(content);
        contentList.add(0,content);
    }
    public void setContentList(List<InfoContent> contentList) {
        this.contentList = contentList;
    }

}
