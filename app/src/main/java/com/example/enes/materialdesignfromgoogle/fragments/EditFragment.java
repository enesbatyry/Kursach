package com.example.enes.materialdesignfromgoogle.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.enes.materialdesignfromgoogle.Data.DatabaseHandler;
import com.example.enes.materialdesignfromgoogle.Model.InfoContent;
import com.example.enes.materialdesignfromgoogle.Model.InfoContentDAO;
import com.example.enes.materialdesignfromgoogle.R;
import com.example.enes.materialdesignfromgoogle.Util.Constants;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class EditFragment extends Fragment implements View.OnClickListener{
    private EditText textInfo;
    private EditText textTitle;
    private ImageButton insertButton;
    private ImageButton photoButton;
    private ImageView image;
    private Button saveButton;
    private Bitmap bitmap;
    private String image_path;


    private DatabaseHandler db;
    private InfoContent infoContent;
    private InfoContentDAO contentLab;
    private List<InfoContent> contentList;


    private static final int GALLERY_REQUEST = 1;
    private static final int PHOTO_REQUEST = 0;

    public EditFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        textInfo = view.findViewById(R.id.text);
        photoButton = view.findViewById(R.id.photoButton);
        insertButton = view.findViewById(R.id.insertButton);
        saveButton = view.findViewById(R.id.saveButton);
        image = view.findViewById(R.id.image);
        textTitle = view.findViewById(R.id.textTitle);

        bitmap = Bitmap.createBitmap(200,200, Bitmap.Config.ARGB_4444);


        contentLab = InfoContentDAO.getInstance(getContext());
        contentList = contentLab.getContentList();

        insertButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        photoButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.photoButton:{
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PHOTO_REQUEST);
                break;
            }
            case R.id.insertButton:{
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                break;
            }

            case R.id.saveButton:{
                Log.v(Constants.MyLog,"saveButton");
                String title = textTitle.getText().toString();
                String message = textInfo.getText().toString();
                infoContent = new InfoContent(title,message, bitmap);
                contentLab.saveImage(bitmap,infoContent.getImageFileName());
                contentLab.addContent(infoContent);
                Toast.makeText(getContext(), "Saved to Database", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case GALLERY_REQUEST: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        image_path = selectedImage.getPath();
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                       // image.setImageBitmap(bitmap);
                        image.setImageURI(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case PHOTO_REQUEST:{
                if (resultCode == RESULT_OK) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(bitmap);
                }
                break;
            }
        }
    }
}
