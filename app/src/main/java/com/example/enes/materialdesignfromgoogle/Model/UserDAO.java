package com.example.enes.materialdesignfromgoogle.Model;

import android.content.Context;

import com.example.enes.materialdesignfromgoogle.Data.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enes on 03.02.2018.
 */

public class UserDAO {
    private static UserDAO instance;
    public static User user = new User();
    private List<User> userList;
    private DatabaseHandler db;

    private UserDAO(Context context){
        userList = new ArrayList<>();
        db = new DatabaseHandler(context);
    }

    public static UserDAO getInstance(Context context){
        if (instance == null){
            instance = new UserDAO(context);
        }
        return instance;
    }

    public void addUser(User user){
        if (userList.contains(user)) return;
        db.addUser(user);
        userList.add(user);
    }

    public List<User> getAllUser(){
        userList = db.getAllUser();
        return userList;
    }

    public void deleteUser(User user){
        db.deleteUser(user);
        userList.remove(user);
    }
}
