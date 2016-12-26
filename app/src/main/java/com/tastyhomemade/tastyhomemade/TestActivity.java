package com.tastyhomemade.tastyhomemade;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Others.FileUploader;
import com.tastyhomemade.tastyhomemade.Others.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Raed on 12/25/2016.
 */

public class TestActivity extends AppCompatActivity {
    private int PICKPHOTO_RESULT = 4;
    byte[] ObjImageArray = null;
    InputStream ObjInputStream = null;
    String sLastFile = null;
    ImageView imgPreview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button btnLoadImage = (Button) findViewById(R.id.btnLoadImage);
        Button btnRetrieveImage = (Button) findViewById(R.id.btnRetrieveImage);
        imgPreview = (ImageView)findViewById(R.id.imgPreview);

        btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ObjIntent = new Intent(Intent.ACTION_PICK);
                ObjIntent.setType("image/*");
                startActivityForResult(ObjIntent, PICKPHOTO_RESULT);
            }
        });

        btnRetrieveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FileUploader Obj = new FileUploader();
                        try {
                          final Bitmap ObjBitMap =  Obj.LoadImage(sLastFile);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                            imgPreview.setImageBitmap(ObjBitMap );
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICKPHOTO_RESULT && resultCode == RESULT_OK) {
            try {
                ObjInputStream = getBaseContext().getContentResolver().openInputStream(data.getData());
//                int iLength = ObjInputStream.available();
//               ObjImageArray = new byte[iLength];
//                ObjInputStream.read(ObjImageArray,0,iLength);
//                ObjInputStream.close();


//               (ObjImageArray);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {


                        try {
                            FileUploader Obj = new FileUploader();
                            sLastFile = Obj.SaveImage(ObjInputStream);
                            Toast.makeText(getBaseContext(), sLastFile, Toast.LENGTH_LONG);
                        } catch (Exception ex) {

                        }
                    }
                });
                t.start();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
}
