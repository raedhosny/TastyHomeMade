package com.tastyhomemade.tastyhomemade.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Adapter.GradientAdapter;
import com.tastyhomemade.tastyhomemade.Business.Additions;
import com.tastyhomemade.tastyhomemade.Business.AdditionsDB;
import com.tastyhomemade.tastyhomemade.Business.Categories;
import com.tastyhomemade.tastyhomemade.Business.CategoriesDB;
import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.Business.Foods_Additions;
import com.tastyhomemade.tastyhomemade.Business.Foods_AdditionsDB;
import com.tastyhomemade.tastyhomemade.MainActivity;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Time;
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

    Spinner ddlRequestTimeFromMinutes;
    Spinner ddlRequestTimeFromHours;
    Spinner dllRequestTimeFromDayNight;

    Spinner ddlRequestTimeToMinutes;
    Spinner ddlRequestTimeToHours;
    Spinner ddlRequestTimeToDayNight;
    Spinner ddlShowToCustomer;
    ListView lvAddFoodGradient;
    List<Foods_Additions> Obj_Foods_Additions_List;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_foods_and_drinks, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtAddFoodName = (EditText) view.findViewById(R.id.txtAddFoodName);
        Include_ddlAddCategory = (View) view.findViewById(R.id.Include_ddlAddCategory);
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
        ddlShowToCustomer = (Spinner) view.findViewById(R.id.Include_ddlShowToCustomer).findViewById(R.id.ddlSpinner);

        ddlRequestTimeFromMinutes = (Spinner)view.findViewById(R.id.Include_RequestFromTime).findViewById(R.id.Include_Spinner_Minutes)
                                    .findViewById(R.id.Spinner_Item);
        ddlRequestTimeFromHours = (Spinner)view.findViewById(R.id.Include_RequestFromTime).findViewById(R.id.Include_Spinner_Hours)
                .findViewById(R.id.Spinner_Item);
        dllRequestTimeFromDayNight = (Spinner)view.findViewById(R.id.Include_RequestFromTime).findViewById(R.id.Include_Spinner_DayNight)
                .findViewById(R.id.Spinner_Item);

        ddlRequestTimeToMinutes = (Spinner)view.findViewById(R.id.Include_RequestTimeTo).findViewById(R.id.Include_Spinner_Minutes)
                .findViewById(R.id.Spinner_Item);
        ddlRequestTimeToHours = (Spinner)view.findViewById(R.id.Include_RequestTimeTo).findViewById(R.id.Include_Spinner_Hours)
                .findViewById(R.id.Spinner_Item);
        ddlRequestTimeToDayNight = (Spinner)view.findViewById(R.id.Include_RequestTimeTo).findViewById(R.id.Include_Spinner_DayNight)
                .findViewById(R.id.Spinner_Item);

        lvAddFoodGradient = (ListView)view.findViewById(R.id.lvAddFoodGradient);

        Obj_Foods_Additions_List = new ArrayList<Foods_Additions>();

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
            startActivityForResult(IntentFoodPhotoChooser, PICKPHOTO_RESULT);

        }
        if (view == btnAddGradientCamera) {

        }
        if (view == btnAddGradientFromStorage) {

        }
        if (view == btnAddGradientAdd) {

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Additions ObjAddition = new Additions();
                    ObjAddition.setLanguageId(new Settings(getActivity()).getCurrentLanguageId());
                    ObjAddition.setName(txtAddGradient.getText().toString().trim());
                    ObjAddition.setPrice(Float.parseFloat(txtAddGradientPrice.getText().toString().trim()));
                    imgGradientPhoto.setDrawingCacheEnabled(true);
                    imgGradientPhoto.buildDrawingCache(true);
                    Bitmap ObjBitmapTemp = Bitmap.createBitmap(imgGradientPhoto.getDrawingCache());
                    imgGradientPhoto.setDrawingCacheEnabled(false);
                    ByteArrayOutputStream ObjArrayTemp = new ByteArrayOutputStream();
                    ObjBitmapTemp.compress(Bitmap.CompressFormat.PNG,100,ObjArrayTemp);
                    ObjAddition.setPhoto(Base64.encode(ObjArrayTemp.toByteArray(),Base64.DEFAULT));
                    int iAdditionId = new AdditionsDB().InsertUpdate(ObjAddition);
                    Foods_Additions ObjFoods_Additions = new Foods_Additions(-1,-1,iAdditionId);
                    Obj_Foods_Additions_List.add(ObjFoods_Additions);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GradientAdapter ObjAdapter = new GradientAdapter(getActivity(),Obj_Foods_Additions_List);

                            lvAddFoodGradient.setAdapter(ObjAdapter);



                        }
                    });


                }
            });
            t.start();


        }
        if (view == btnAddFoodSave) {


            if (txtAddFoodName.getText().toString().trim().length() == 0) {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseEnterFoodName, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                return;
            }

            Spinner ddlAddCategory = (Spinner) Include_ddlAddCategory.findViewById(R.id.ddlSpinner);
            if (ddlAddCategory.getSelectedItemPosition() == 0) {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseSelectFoodCategory, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                return;
            }

            if (txtAddFoodDescription.getText().toString().length() == 0) {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseEnterFooDescription, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                return;
            }

            imgAddFoodPhoto.setDrawingCacheEnabled(true);
            imgAddFoodPhoto.buildDrawingCache(true);
            Bitmap  ObjBitmapTemp = Bitmap.createBitmap(imgAddFoodPhoto.getDrawingCache());
            imgAddFoodPhoto.setDrawingCacheEnabled(false);


            if (ObjBitmapTemp  == null) {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseAddPhotoForFood, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                return;
            }


            if (ddlShowToCustomer.getSelectedItemPosition() == 0) {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseSelectIsFoodVisibleToCustomerOrNot, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                return;
            }


            if (txtAddGradient.getText().toString().trim().length() == 0)
            {

            }

             //Insert New Food
            final Foods ObjFood = new Foods();
            ObjFood.setCategoryId(ObjCategoriesList.get(ddlAddCategory.getSelectedItemPosition()).getId());
            ObjFood.setUserId(new Settings(getActivity()).getUserId());
            ObjFood.setLanguageId(new Settings(getActivity()).getCurrentLanguageId());
            int iRequestTimeFromMinutes = Integer.parseInt(ddlRequestTimeFromMinutes.getSelectedItem().toString());
            int iRequestTimeFromHours = Integer.parseInt(ddlRequestTimeFromHours.getSelectedItem().toString());
            if (dllRequestTimeFromDayNight.getSelectedItem().toString() .equals("مساءا")
                    ||
                    dllRequestTimeFromDayNight.getSelectedItem().toString() .equals("PM")
                    )
            iRequestTimeFromHours = (iRequestTimeFromHours + 12) % 24;
            ObjFood.setRequestTimeFrom(new Time(iRequestTimeFromHours,iRequestTimeFromMinutes,0));

            int iRequestTimeToMinutes = Integer.parseInt(ddlRequestTimeToMinutes.getSelectedItem().toString());
            int iRequestTimeToHours = Integer.parseInt(ddlRequestTimeToHours.getSelectedItem().toString());
            if (ddlRequestTimeToDayNight.getSelectedItem().toString() .equals("مساءا")
                    ||
                    ddlRequestTimeToDayNight.getSelectedItem().toString() .equals("PM")
                    )
                iRequestTimeToHours = (iRequestTimeFromHours + 12) % 24;
            ObjFood.setRequestTimeTo(new Time(iRequestTimeToHours,iRequestTimeToMinutes,0));
            //Bitmap ObjBitmapTemp = ((BitmapDrawable)imgAddFoodPhoto.getDrawable()).getBitmap();
            ByteArrayOutputStream ObjByteArrayTemp = new ByteArrayOutputStream();
            ObjBitmapTemp.compress(Bitmap.CompressFormat.JPEG,100,ObjByteArrayTemp );
            ObjFood.setPhoto(ObjByteArrayTemp.toByteArray() );
            ObjFood.setPrice(Float.parseFloat(txtAddFoodOrderPrice.getText().toString()) );
            ObjFood.setName(txtAddFoodName.getText().toString().trim());
            ObjFood.setDescription(txtAddFoodDescription.getText().toString().trim());
            boolean bIsVisible =  false;
            if (ddlShowToCustomer.getSelectedItemPosition() == 1)
                bIsVisible = true;
            else if (ddlShowToCustomer.getSelectedItemPosition() == 2)
                bIsVisible = false;
            ObjFood.setVisible(bIsVisible );
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    final Foods ObjFoodTemp = ObjFood;
                    int iFoodId = new FoodsDB().InsertUpdate(ObjFoodTemp);
                    for (Foods_Additions Obj_Foods_Additions : Obj_Foods_Additions_List)
                    {
                        Obj_Foods_Additions.setFoodId(iFoodId);
                        new Foods_AdditionsDB().InsertUpdate(Obj_Foods_Additions);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),Utils.GetResourceName(getContext(),R.string.DataSavedSuccessfuly,new Settings(getActivity()).getCurrentLanguageId()),Toast.LENGTH_LONG).show();
                            new Utils().ShowActivity(getContext(),null,"Main");
                        }
                    });
                }
            });
            t.start();



        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {
            try {

                //Bundle extras = data.getExtras();
                //Bitmap imageBitmap = (Bitmap) extras.get("data");
                Bitmap ObjBitmap = (Bitmap) data.getExtras().get("data");

                int iWidth = ObjBitmap.getWidth();
                int iHeight = ObjBitmap.getHeight();
                int inewHeight = 290;
                int inewWidth = (int) (((float) (iWidth * inewHeight)) / ((float) iHeight));
                imgAddFoodPhoto.setImageBitmap(Bitmap.createScaledBitmap(ObjBitmap, inewWidth, inewHeight, false));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (requestCode == PICKPHOTO_RESULT && resultCode == RESULT_OK) {
            if (data == null)
                return;
            try {
                InputStream ObjInputStream = getContext().getContentResolver().openInputStream(data.getData());
                Bitmap imageBitmap = BitmapFactory.decodeStream(ObjInputStream);
                int iWidth = imageBitmap.getWidth();
                int iHeight = imageBitmap.getHeight();
                int inewHeight = 290;
                int inewWidth = (int) (((float) (iWidth * inewHeight)) / ((float) iHeight));

                imgAddFoodPhoto.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, inewWidth, inewHeight, false));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    private List<Categories> FillCategories() {
        Settings ObjSettings = new Settings(getActivity());
        List<Categories> ObjCategoriesListTemp = new CategoriesDB().SelectAll(ObjSettings.getCurrentLanguageId());
        List<String> ObjCategoreisListTemp2 = new ArrayList<String>();
        for (Categories ObjTemp : ObjCategoriesListTemp) {
            ObjCategoreisListTemp2.add(ObjTemp.getName());
        }

        final ArrayAdapter<String> CategoriesAdaptor = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Arrays.copyOf(ObjCategoreisListTemp2.toArray(), ObjCategoreisListTemp2.size(), String[].class));
        final Spinner ddlAddCategory = (Spinner) Include_ddlAddCategory.findViewById(R.id.ddlSpinner);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ddlAddCategory.setAdapter(CategoriesAdaptor);
            }
        });


        return ObjCategoriesListTemp;


    }

    private void FillDropDowns() {


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ObjCategoriesList = FillCategories();
            }
        });
        t.start();

        final ArrayAdapter<String> ObjAdapterMinutes_From = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Utils.GetResourceArrayName(getContext(), R.array.minutes, new Settings(getContext()).getCurrentLanguageId()));
        final ArrayAdapter<String> ObjAdapterHours_From = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Utils.GetResourceArrayName(getContext(), R.array.hours, new Settings(getContext()).getCurrentLanguageId()));
        final ArrayAdapter<String> ObjAdapterDayNight_From = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Utils.GetResourceArrayName(getContext(), R.array.DayNight, new Settings(getContext()).getCurrentLanguageId()));

        final ArrayAdapter<String> ObjAdapterMinutes_To = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Utils.GetResourceArrayName(getContext(), R.array.minutes, new Settings(getContext()).getCurrentLanguageId()));
        final ArrayAdapter<String> ObjAdapterHours_To = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Utils.GetResourceArrayName(getContext(), R.array.hours, new Settings(getContext()).getCurrentLanguageId()));
        final ArrayAdapter<String> ObjAdapterDayNight_To = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Utils.GetResourceArrayName(getContext(), R.array.DayNight, new Settings(getContext()).getCurrentLanguageId()));

        ddlRequestTimeFromMinutes.setAdapter(ObjAdapterMinutes_From);
        ddlRequestTimeFromHours.setAdapter(ObjAdapterHours_From);
        dllRequestTimeFromDayNight.setAdapter(ObjAdapterDayNight_From);

        ddlRequestTimeToMinutes.setAdapter(ObjAdapterMinutes_To);
        ddlRequestTimeToHours.setAdapter(ObjAdapterHours_To);
        ddlRequestTimeToDayNight.setAdapter(ObjAdapterDayNight_To);

        final ArrayAdapter<String> ObjAdapterYesNo = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,Utils.GetResourceArrayName(getContext(),R.array.myboolean,new Settings(getActivity()).getCurrentLanguageId()));
        ddlShowToCustomer.setAdapter(ObjAdapterYesNo);
    }


}


