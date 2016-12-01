package com.tastyhomemade.tastyhomemade.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.EnvironmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Business.Categories;
import com.tastyhomemade.tastyhomemade.Business.CategoriesDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Raed on 11/28/2016.
 */

public class AddFoodsAndDrinksFragment extends Fragment implements View.OnClickListener {

    List<Categories> ObjCategoriesList = new ArrayList<Categories>();
    private int CAMERA_RESULT = 1;
    private int PICKPHOTO_RESULT = 2;

    EditText txtAddFoodName;
    View Include_ddlAddCategory;
    EditText txtAddFoodDescription;
    EditText txtAddFoodOrderPrice;
    Button btnAddFoodCameraPhoto;
    Button btnAddFoodFromStoragePhoto;
    ImageView imgAddFoodPhoto;
    EditText txtAddGradient;
    EditText txtAddGradientPrice;
    Button btnAddGradientCamera;
    Button btnAddGradientFromStorage;
    ImageView imgGradientPhoto;
    Button btnAddGradientAdd;
    Button btnAddFoodSave;
    View Include_ddlShowToCustomer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_foods_and_drinks, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtAddFoodName = (EditText) view.findViewById(R.id.txtAddFoodName);
        View Include_ddlAddCategory = (View) view.findViewById(R.id.Include_ddlAddCategory);
        txtAddFoodDescription = (EditText) view.findViewById(R.id.txtAddFoodDescription);
        txtAddFoodOrderPrice = (EditText) view.findViewById(R.id.txtAddFoodOrderPrice);
        btnAddFoodCameraPhoto = (Button) view.findViewById(R.id.btnAddFoodCameraPhoto);
        btnAddFoodCameraPhoto.setOnClickListener(this);
        btnAddFoodFromStoragePhoto = (Button) view.findViewById(R.id.btnAddFoodFromStoragePhoto);
        btnAddFoodFromStoragePhoto.setOnClickListener(this);
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
        btnAddFoodSave = (Button) view.findViewById(R.id.btnAddFoodSave);
        btnAddFoodSave.setOnClickListener(this);
        Include_ddlShowToCustomer = (View)view.findViewById(R.id.Include_ddlShowToCustomer);

        ObjCategoriesList = FillCategories();
        FillDropDowns();



    }

    @Override
    public void onClick(View view) {
        if (view == btnAddFoodCameraPhoto) {
            Intent IntentFoodCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(IntentFoodCamera, CAMERA_RESULT);
        }
        if (view == btnAddFoodFromStoragePhoto) {

            Intent IntentFoodPhotoChooser = new Intent(Intent.ACTION_PICK);
            IntentFoodPhotoChooser.setType("image/*");
            startActivityForResult(IntentFoodPhotoChooser ,PICKPHOTO_RESULT);

        }
        if (view == btnAddGradientCamera) {

        }
        if (view == btnAddGradientFromStorage) {

        }
        if (view == btnAddGradientAdd) {

        }
        if (view == btnAddFoodSave) {
            if (txtAddFoodName.getText().toString().trim().length() == 0)
            {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(),R.string.Error_PleaseEnterFoodName,new Settings(getContext()).getCurrentLanguageId()),Toast.LENGTH_LONG).show();
                return;
            }

            Spinner ddlAddCategory = (Spinner) Include_ddlAddCategory.findViewById(R.id.ddlSpinner);
            if (ddlAddCategory.getSelectedItemPosition() == 0)
            {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(),R.string.Error_PleaseSelectFoodCategory,new Settings(getContext()).getCurrentLanguageId()),Toast.LENGTH_LONG).show();
                return;
            }

            if (txtAddFoodDescription.getText().toString().length() == 0)
            {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(),R.string.Error_PleaseEnterFooDescription,new Settings(getContext()).getCurrentLanguageId()),Toast.LENGTH_LONG).show();
                return;
            }

            if(((BitmapDrawable)imgAddFoodPhoto.getDrawable()).getBitmap() == null)
            {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(),R.string.Error_PleaseAddPhotoForFood,new Settings(getContext()).getCurrentLanguageId()),Toast.LENGTH_LONG).show();
                return;
            }

            Spinner ddlShowToCustomer = (Spinner)  Include_ddlShowToCustomer.findViewById(R.id.ddlSpinner);
            if (ddlShowToCustomer .getSelectedItemPosition() == 0 )
            {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(),R.string.Error_PleaseSelectIsFoodVisibleToCustomerOrNot,new Settings(getContext()).getCurrentLanguageId()),Toast.LENGTH_LONG).show();
                return;
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {
          try {

              //Bundle extras = data.getExtras();
              //Bitmap imageBitmap = (Bitmap) extras.get("data");
              Bitmap ObjBitmap = (Bitmap )data.getExtras().get("data");

              int iWidth = ObjBitmap.getWidth();
              int iHeight =  ObjBitmap.getHeight();
              int inewHeight = 290;
              int inewWidth =  (int)(((float)(iWidth * inewHeight)) /((float)iHeight));
              imgAddFoodPhoto.setImageBitmap(Bitmap.createScaledBitmap(ObjBitmap,inewWidth,inewHeight,false) );

          }
          catch (Exception ex)
          {
              ex.printStackTrace();
          }
        }
        else if (requestCode == PICKPHOTO_RESULT && resultCode==RESULT_OK)
        {
            if ( data == null)
                return;
            try
            {
               InputStream ObjInputStream= getContext().getContentResolver().openInputStream(data.getData());
               Bitmap imageBitmap = BitmapFactory.decodeStream(ObjInputStream);
               int iWidth = imageBitmap.getWidth();
               int iHeight =  imageBitmap.getHeight();
                int inewHeight = 290;
                int inewWidth =  (int)(((float)(iWidth * inewHeight)) /((float)iHeight));

                imgAddFoodPhoto.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap,inewWidth,inewHeight,false));
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
         Spinner  ddlAddCategory = (Spinner) Include_ddlAddCategory.findViewById(R.id.ddlSpinner);
        ddlAddCategory.setAdapter(CategoriesAdaptor);

        return ObjCategoriesListTemp;


    }

    private void FillDropDowns ()
    {
        Spinner RequestFrom_ddlSpinnerMinutes = (Spinner) getActivity().findViewById(R.id.Include_RequestFromTime).findViewById(R.id.Include_Spinner_Minutes).findViewById(R.id.Spinner_Item);
        Spinner RequestFrom_ddlSpinnerHours = (Spinner) getActivity().findViewById(R.id.Include_RequestFromTime).findViewById(R.id.Include_Spinner_Minutes).findViewById(R.id.Spinner_Item);
        Spinner RequestFrom_ddlSpinnerDayNight = (Spinner) getActivity().findViewById(R.id.Include_RequestFromTime).findViewById(R.id.Include_Spinner_Minutes).findViewById(R.id.Spinner_Item);

        Spinner RequestTo_ddlSpinnerMinutes = (Spinner) getActivity().findViewById(R.id.Include_RequestTimeTo).findViewById(R.id.Include_Spinner_Minutes).findViewById(R.id.Spinner_Item);
        Spinner RequestTo_ddlSpinnerHours = (Spinner) getActivity().findViewById(R.id.Include_RequestTimeTo).findViewById(R.id.Include_Spinner_Minutes).findViewById(R.id.Spinner_Item);
        Spinner RequestTo_ddlSpinnerDayNight = (Spinner) getActivity().findViewById(R.id.Include_RequestTimeTo).findViewById(R.id.Include_Spinner_Minutes).findViewById(R.id.Spinner_Item);

        ArrayAdapter<CharSequence> ObjAdapterMinutes_From = ArrayAdapter.createFromResource(getContext(), R.array.minutes,R.layout.spinner_item);
        ArrayAdapter<CharSequence> ObjAdapterHours_From = ArrayAdapter.createFromResource(getContext(), R.array.hours,R.layout.spinner_item);
        ArrayAdapter<String> ObjAdapterDayNight_From   = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,Utils.GetResourceArrayName(getContext(),R.array.DayNight,new Settings(getContext()).getCurrentLanguageId()));

        ArrayAdapter<CharSequence> ObjAdapterMinutes_To = ArrayAdapter.createFromResource(getContext(), R.array.minutes,R.layout.spinner_item);
        ArrayAdapter<CharSequence> ObjAdapterHours_To = ArrayAdapter.createFromResource(getContext(), R.array.hours,R.layout.spinner_item);
        ArrayAdapter<String> ObjAdapterDayNight_To   = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,Utils.GetResourceArrayName(getContext(),R.array.DayNight,new Settings(getContext()).getCurrentLanguageId()));

        RequestFrom_ddlSpinnerMinutes.setAdapter(ObjAdapterMinutes_From);
        RequestFrom_ddlSpinnerHours.setAdapter(ObjAdapterHours_From);
        RequestFrom_ddlSpinnerDayNight.setAdapter(ObjAdapterDayNight_From);

        RequestTo_ddlSpinnerMinutes.setAdapter(ObjAdapterMinutes_To);
        RequestTo_ddlSpinnerHours.setAdapter(ObjAdapterHours_To);
        RequestTo_ddlSpinnerDayNight.setAdapter(ObjAdapterDayNight_To);
    }

}


