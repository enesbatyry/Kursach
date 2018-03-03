package com.example.enes.materialdesignfromgoogle.fragments;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.enes.materialdesignfromgoogle.Adapter.ImagesCreateAdapter;
import com.example.enes.materialdesignfromgoogle.Data.DatabaseHandler;
import com.example.enes.materialdesignfromgoogle.Model.InfoContent;
import com.example.enes.materialdesignfromgoogle.Model.InfoContentDAO;
import com.example.enes.materialdesignfromgoogle.R;
import com.example.enes.materialdesignfromgoogle.Util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class EditFragment extends Fragment implements View.OnClickListener{
    private EditText textInfo;
    private EditText textTitle;
    private ImageButton insertButton;
    private ImageButton photoButton;
    private ImageButton selectButton;
    private ImageButton deleteButton;
    //private ImageView image;
    private Button saveButton;
    private List<Bitmap> bitmaps;
    private String image_path;


    private DatabaseHandler db;
    private InfoContent infoContent;
    private InfoContentDAO contentLab;
    private List<InfoContent> contentList;
    private ImagesCreateAdapter adapter;


    private static final int GALLERY_REQUEST = 1;
    private static final int GALLERY_MULTI_REQUEST = 2;
    private static final int PHOTO_REQUEST = 0;

    private String imageEncoded;
    private List<String> imagesPathList;
    private  ArrayList<Uri> mArrayUri;
    private  RecyclerView recyclerView;

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

        bitmaps = new ArrayList<>();

        textInfo = view.findViewById(R.id.text);
        photoButton = view.findViewById(R.id.photoButton);
        insertButton = view.findViewById(R.id.insertButton);
        saveButton = view.findViewById(R.id.saveButton);
        selectButton = view.findViewById(R.id.selectButton);
        deleteButton = view.findViewById(R.id.deleteButton);
        //image = view.findViewById(R.id.image);
        textTitle = view.findViewById(R.id.textTitle);
        recyclerView = view.findViewById(R.id.imageRecyclerViewEdit);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        contentLab = InfoContentDAO.getInstance(getContext());
        contentList = contentLab.getContentList();

        insertButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        photoButton.setOnClickListener(this);
        selectButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
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
            case R.id.selectButton:{
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_MULTI_REQUEST);
                break;
            }
            case R.id.deleteButton:{
                bitmaps.clear();
                adapter = new ImagesCreateAdapter(bitmaps, getContext());
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                break;
            }

            case R.id.saveButton:{
                Log.v(Constants.MyLog,"saveButton");
                String title = textTitle.getText().toString();
                String message = textInfo.getText().toString();
                infoContent = new InfoContent(title,message);
                contentLab.saveImage(bitmaps,infoContent.getImageFileNames(bitmaps.size()));
                contentLab.addContent(infoContent);
                Toast.makeText(getContext(), "Saved to Database", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case GALLERY_REQUEST: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        image_path = selectedImage.getPath();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        bitmaps.add(bitmap);
                        adapter = new ImagesCreateAdapter(bitmaps, getContext());
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                       // image.setImageBitmap(bitmap);
                       // image.setImageURI(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case PHOTO_REQUEST:{
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    bitmaps.add(bitmap);
                    adapter = new ImagesCreateAdapter(bitmaps, getContext());
                    recyclerView.setAdapter(adapter);
                    // image.setImageBitmap(bitmap);
                }
                break;
            }
            case GALLERY_MULTI_REQUEST:{
                if (resultCode == RESULT_OK && data != null) {
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    imagesPathList = new ArrayList<String>();
                    if(data.getData()!=null){

                        Uri mImageUri=data.getData();

                        // Get the cursor
                        Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageEncoded  = cursor.getString(columnIndex);
                        cursor.close();

                    } else {
                        if (data.getClipData() != null) {
                            ClipData mClipData = data.getClipData();
                            Log.v("LOG_TAG", "Count " + mClipData.getItemCount());

                            mArrayUri = new ArrayList<Uri>();
                            for (int i = 0; i < mClipData.getItemCount(); i++) {

                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri uri = item.getUri();
                                mArrayUri.add(uri);
                                Bitmap bitmap = null;
                                Log.v("LOG_TAG", "Bitmap1 = " + bitmap);

                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                                    Log.v("LOG_TAG", "Bitmap2 = " + bitmap);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                bitmaps.add(bitmap);
                                // Get the cursor
                                Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                                // Move to first row
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                imageEncoded = cursor.getString(columnIndex);
                                imagesPathList.add(imageEncoded);
                                cursor.close();

                            }
                            adapter = new ImagesCreateAdapter(bitmaps, getContext());
                            recyclerView.setAdapter(adapter);
                            //recyclerView.setAdapter(new ImagesAdapter(NewActivity.this, mArrayUri));
                            Log.v("LOG_TAG", "Selected Images " + mArrayUri.size());
                        }
                    }
                }
                break;
            }
        }
    }
}
