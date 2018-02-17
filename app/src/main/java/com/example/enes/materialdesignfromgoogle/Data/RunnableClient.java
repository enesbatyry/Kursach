package com.example.enes.materialdesignfromgoogle.Data;

import android.os.Environment;
import android.util.Log;

import com.example.enes.materialdesignfromgoogle.Model.InfoContent;
import com.example.enes.materialdesignfromgoogle.Util.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Enes on 17.02.2018.
 */

public class RunnableClient implements Runnable {
    String dstAddress;
    int dstPort;
    String response = "";
    InfoContent infoContent;
    byte[] imageToServer;

    public RunnableClient(String addr, int port, InfoContent content) {
        dstAddress = addr;
        dstPort = port;
        infoContent = content;
    }

    public RunnableClient(String dstAddress, int dstPort,String msgTo, byte[] imageToServer) {
        this.dstAddress = dstAddress;
        this.dstPort = dstPort;
        this.imageToServer = imageToServer;
    }

    @Override
    public void run() {
        Socket socket = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;

        try {
            socket = new Socket(dstAddress, dstPort);
            dataOutputStream = new DataOutputStream(
                    socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            dataOutputStream.writeUTF(infoContent.getImageFileName());
            dataOutputStream.writeUTF(infoContent.getTitle());
            dataOutputStream.writeUTF(infoContent.getMessage());
            FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory().toString() +"/saved_images/"+infoContent.getImageFileName());
            byte[] buffer = new byte[16*1024];
            int read;
            while((read=fis.read(buffer)) >= 0) {

                Log.d(Constants.MyLog, "read: " + read);
                dataOutputStream.write(buffer, 0, read);
                Log.d(Constants.MyLog, "read: " + read);
            }


            dataOutputStream.flush();
            fis.close();
            dataOutputStream.close();
            Log.d(Constants.MyLog, "sended image ");

            response = dataInputStream.readUTF();


        } catch (IOException ex) {

        } finally {
            /*txt_response.post(new Runnable() {
                @Override
                public void run() {
                    txt_response.setText(response);
                }
            });*/

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {

                }
            }

            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException ex) {

                }
            }

            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException ex) {

                }
            }

        }
    }
}
