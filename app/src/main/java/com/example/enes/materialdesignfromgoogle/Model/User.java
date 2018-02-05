package com.example.enes.materialdesignfromgoogle.Model;

import android.graphics.Bitmap;

/**
 * Created by Enes on 03.02.2018.
 */

public class User {
    private int id;
    private String nic;
    private Bitmap avatar;
    private Service service;

    public User() {
    }

    public User(String nic, Bitmap avatar, Service service) {
        this.nic = nic;
        this.avatar = avatar;
        this.service = service;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
