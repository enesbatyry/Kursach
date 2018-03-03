package com.example.enes.materialdesignfromgoogle.Model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Enes on 16/12/2017.
 */

public class InfoContent {
    private UUID id;
    private String title;
    private String message;
    private Bitmap image;
    private List<String> imageFileNames;
    private byte shipped;


    public InfoContent() {
    }


    public InfoContent(String title, String message, List<String> imageFileNames) {
        this.title = title;
        this.message = message;
        this.imageFileNames = imageFileNames;
    }

    public InfoContent(String title, String message, Bitmap image) {
        this.title = title;
        this.message = message;
        this.image = image;
        id = UUID.randomUUID();
    }

    public InfoContent(String title, String message) {
        this.title = title;
        this.message = message;
        id = UUID.randomUUID();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getShipped() {
        return shipped;
    }

    public void setShipped(byte shipped) {
        this.shipped = shipped;
    }

    public UUID getId() {
        return id;
    }

    public List<String> getImageFileNames(int count)
    {
        imageFileNames = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            imageFileNames.add("IMG_" + getId().toString()+ "(" + i + ").jpg");
        }
        return imageFileNames;
    }

    public List<String> getImageFileNames(){
        return imageFileNames;
    }

    public void setImageFileNames(List<String> imageFileNames) {
        this.imageFileNames = imageFileNames;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
