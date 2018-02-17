package com.example.enes.materialdesignfromgoogle.Model;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * Created by Enes on 16/12/2017.
 */

public class InfoContent {
    private UUID id;
    private String title;
    private String message;
    private Bitmap image;
    private String imageFileName;
    private byte shipped;


    public InfoContent() {
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

    public String getImageFileName() {
        return "IMG_" + getId().toString() + ".jpg";
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
