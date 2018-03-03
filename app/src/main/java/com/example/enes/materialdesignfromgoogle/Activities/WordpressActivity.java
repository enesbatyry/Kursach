package com.example.enes.materialdesignfromgoogle.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.enes.materialdesignfromgoogle.Data.RunnableClient;
import com.example.enes.materialdesignfromgoogle.Model.InfoContent;
import com.example.enes.materialdesignfromgoogle.Model.InfoContentDAO;
import com.example.enes.materialdesignfromgoogle.R;
import com.example.enes.materialdesignfromgoogle.Util.Constants;
import com.example.enes.materialdesignfromgoogle.Util.ImageUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordpressActivity extends AppCompatActivity {

    private TextView txt_sys, txt_response;
    private EditText edit_msg, edit_ip, edit_port;
    private Button btn_send, btn_clear;
    private Context context;

    public Context getContext() {
        return WordpressActivity.this;
    }

    private InfoContent infoContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordpress);

        infoContent = InfoContentDAO.getInstance(this).getContentList().get(1);
        final ImageUtils utils = new ImageUtils(this);

        txt_sys = findViewById(R.id.txt_sys);
        txt_sys.setText(
                System.getProperty("os.arch") + "/"
                        + System.getProperty("os.name"));

        edit_msg = findViewById(R.id.edit_msg);
        edit_ip = findViewById(R.id.edit_ip);
        edit_port = findViewById(R.id.edit_port);

        txt_response = findViewById(R.id.txt_response);


        btn_send = findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String msg = String.valueOf(edit_msg.getText());
               // String msg = infoContent.getImageFileName();
                String title = infoContent.getTitle();
                String message = infoContent.getMessage();
//                if (msg.equals("")){
//                    msg = null;
//                }
//
//                RunnableClient runnableClient
//                        = new RunnableClient(edit_ip.getText().toString(),
//                        Integer.parseInt(edit_port.getText().toString()),
//                        msg, infoContent);
//
//                new Thread(runnableClient).start();
            }
        });

    }
}
