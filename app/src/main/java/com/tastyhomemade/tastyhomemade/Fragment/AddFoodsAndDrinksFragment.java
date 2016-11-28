package com.tastyhomemade.tastyhomemade.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.tastyhomemade.tastyhomemade.Business.Categories;
import com.tastyhomemade.tastyhomemade.Business.CategoriesDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Raed on 11/28/2016.
 */

public class AddFoodsAndDrinksFragment extends Fragment implements View.OnClickListener {

    List<Categories> ObjCategoriesList = new ArrayList<Categories>();
    private int CAMERA_RESULT = 1;

    EditText txtAddName;
    Spinner ddlAddCategory;
    EditText txtAddDescription;
    EditText txtAddOrderPrice;
    Button btnAddFoodCamera;
    Button btnAddFoodFromStorage;
    ImageView imgAddFoodPhoto;
    EditText txtAddGradient;
    EditText txtAddGradientPrice;
    Button btnAddGradientCamera;
    Button btnAddGradientFromStorage;
    ImageView imgGradientPhoto;
    Button btnAddGradientAdd;
    Button btnAddSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_foods_and_drinks, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtAddName = (EditText) view.findViewById(R.id.txtAddName);
        ddlAddCategory = (Spinner) view.findViewById(R.id.ddlAddCategory);
        txtAddDescription = (EditText) view.findViewById(R.id.txtAddDescription);
        txtAddOrderPrice = (EditText) view.findViewById(R.id.txtAddOrderPrice);
        btnAddFoodCamera = (Button) view.findViewById(R.id.btnAddFoodCamera);
        btnAddFoodCamera.setOnClickListener(this);
        btnAddFoodFromStorage = (Button) view.findViewById(R.id.btnAddFoodFromStorage);
        btnAddFoodFromStorage.setOnClickListener(this);
        imgAddFoodPhoto = (ImageView) view.findViewById(R.id.imgAddFoodPhoto);
        txtAddGradient = (EditText) view.findViewById(R.id.txtAddGradient);
        txtAddGradientPrice = (EditText) view.findViewById(R.id.txtAddGradientPrice);
        btnAddGradientCamera = (Button) view.findViewById(R.id.btnAddGradientCamera);
        btnAddGradientCamera.setOnClickListener(this);
        btnAddGradientFromStorage = (Button) view.findViewById(R.id.btnAddGradientFromStorage);
        btnAddGradientFromStorage.setOnClickListener(this);
        imgGradientPhoto = (ImageView) view.findViewById(R.id.imgGradientPhoto);
        btnAddGradientAdd = (Button) view.findViewById(R.id.btnAddGradientAdd);
        btnAddGradientAdd.setOnClickListener(this);
        btnAddSave = (Button) view.findViewById(R.id.btnAddSave);
        btnAddSave.setOnClickListener(this);

        ObjCategoriesList = FillCategories();
    }

    @Override
    public void onClick(View view) {
        if (view == btnAddFoodCamera) {
            Intent IntentFoodCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(IntentFoodCamera, CAMERA_RESULT);
        }
        if (view == btnAddFoodFromStorage) {

        }
        if (view == btnAddGradientCamera) {

        }
        if (view == btnAddGradientFromStorage) {

        }
        if (view == btnAddGradientAdd) {

        }
        if (view == btnAddSave) {

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {
          try {
              Bundle extras = data.getExtras();
              Bitmap imageBitmap = (Bitmap) extras.get("data");

              imgAddFoodPhoto.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, 120, 120, false));
              imgAddFoodPhoto.post(new Runnable() {
                  @Override
                  public void run() {
                      imgAddFoodPhoto.setVisibility(View.GONE);
                      imgAddFoodPhoto.setVisibility(View.VISIBLE);
                  }
              });
          }
          catch (Exception ex)
          {
              ex.printStackTrace();
          }
        }

    }

    private List<Categories> FillCategories() {
        Settings ObjSettings = new Settings(getActivity());
        List<Categories> ObjCategoriesListTemp = new CategoriesDB().SelectAll(ObjSettings.getCurrentLanguageId());
        List<String> ObjCategoreisListTemp2 = new ArrayList<String>();
        for (Categories ObjTemp : ObjCategoriesList) {
            ObjCategoreisListTemp2.add(ObjTemp.getName());
        }

        ArrayAdapter<String> CategoriesAdaptor = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Arrays.copyOf(ObjCategoreisListTemp2.toArray(), ObjCategoreisListTemp2.size(), String[].class));
        ddlAddCategory.setAdapter(CategoriesAdaptor);

        return ObjCategoriesListTemp;


    }

}


