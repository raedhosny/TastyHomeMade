package com.tastyhomemade.tastyhomemade.Fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.tastyhomemade.tastyhomemade.Others.WaitDialog;
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

    EditText txtAddFoodNameAr;
    EditText txtAddFoodNameEn;
    View Include_ddlAddCategory;
    EditText txtAddFoodDescriptionAr;
    EditText txtAddFoodDescriptionEn;
    EditText txtAddFoodOrderPrice;
    Button btnAddFoodCameraPhoto;
    Button btnAddFoodFromStoragePhoto;
    ImageView imgAddFoodPhoto;
    EditText txtAddGradientAr;
    EditText txtAddGradientEn;
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
    WaitDialog ObjWaitDialog;
    Foods ObjFood = null;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_foods_and_drinks, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtAddFoodNameAr = (EditText) view.findViewById(R.id.txtAddFoodNameAr);
        txtAddFoodNameEn = (EditText) view.findViewById(R.id.txtAddFoodNameEn);
        Include_ddlAddCategory = (View) view.findViewById(R.id.Include_ddlAddCategory);
        txtAddFoodDescriptionAr = (EditText) view.findViewById(R.id.txtAddFoodDescriptionAr);
        txtAddFoodDescriptionEn = (EditText) view.findViewById(R.id.txtAddFoodDescriptionEn);
        txtAddFoodOrderPrice = (EditText) view.findViewById(R.id.txtAddFoodOrderPrice);
        btnAddFoodCameraPhoto = (Button) view.findViewById(R.id.btnAddFoodCameraPhoto);
        btnAddFoodCameraPhoto.setOnClickListener(this);
        btnAddFoodFromStoragePhoto = (Button) view.findViewById(R.id.btnAddFoodFromStoragePhoto);
        btnAddFoodFromStoragePhoto.setOnClickListener(this);
        imgAddFoodPhoto = (ImageView) view.findViewById(R.id.imgAddFoodPhoto);
        txtAddGradientAr = (EditText) view.findViewById(R.id.txtAddGradientAr);
        txtAddGradientEn = (EditText) view.findViewById(R.id.txtAddGradientEn);
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
            IntentFoodCamera.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
            ObjWaitDialog = new WaitDialog(getContext());
            ObjWaitDialog.ShowDialog();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (txtAddGradientAr.getText().toString().trim().equals("") || txtAddGradientEn.getText().toString().trim().equals("") || txtAddGradientPrice.getText().toString().trim().equals("") )
                        return;

                    Additions ObjAddition = new Additions();
                    ObjAddition.setLanguageId(1); // Arabic
                    ObjAddition.setName(txtAddGradientAr.getText().toString().trim());
                    ObjAddition.setPrice(Float.parseFloat(txtAddGradientPrice.getText().toString().trim()));
                   // imgGradientPhoto.setDrawingCacheEnabled(true);
                    //imgGradientPhoto.buildDrawingCache(true);
                    //Bitmap ObjBitmapTemp = Bitmap.createBitmap(imgGradientPhoto.getDrawingCache());
                    //imgGradientPhoto.setDrawingCacheEnabled(false);
                    //ByteArrayOutputStream ObjArrayTemp = new ByteArrayOutputStream();
                    //ObjBitmapTemp.compress(Bitmap.CompressFormat.PNG,100,ObjArrayTemp);
                    //ObjAddition.setPhoto(Base64.encode(ObjArrayTemp.toByteArray(),Base64.DEFAULT));
                    int iAdditionId = new AdditionsDB().InsertUpdate(ObjAddition);
                    ObjAddition = new Additions();
                    ObjAddition.setId(iAdditionId);
                    ObjAddition.setLanguageId(2); // English
                    ObjAddition.setName(txtAddGradientEn.getText().toString().trim());
                    ObjAddition.setPrice(Float.parseFloat(txtAddGradientPrice.getText().toString().trim()));
                    new AdditionsDB().InsertUpdate(ObjAddition);

                    Foods_Additions ObjFoods_Additions = new Foods_Additions(-1,-1,iAdditionId);
                    Obj_Foods_Additions_List.add(ObjFoods_Additions);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GradientAdapter ObjAdapter = new GradientAdapter(getActivity(),Obj_Foods_Additions_List);

                            lvAddFoodGradient.setAdapter(ObjAdapter);
                            txtAddGradientAr.setText("");
                            txtAddGradientEn.setText("");
                            txtAddGradientPrice.setText("");
                            ObjWaitDialog.HideDialog();
                        }
                    });



                }
            });
            t.start();


        }
        if (view == btnAddFoodSave) {


            if (txtAddFoodNameAr.getText().toString().trim().length() == 0) {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseEnterFoodName, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                return;
            }

            if (txtAddFoodNameEn.getText().toString().trim().length() == 0) {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseEnterFoodName, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                return;
            }

            Spinner ddlAddCategory = (Spinner) Include_ddlAddCategory.findViewById(R.id.ddlSpinner);
            if (ddlAddCategory.getSelectedItemPosition() == 0) {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseSelectFoodCategory, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                return;
            }

            if (txtAddFoodDescriptionAr.getText().toString().length() == 0) {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseEnterFooDescription, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                return;
            }

            if (txtAddFoodDescriptionEn.getText().toString().length() == 0) {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseEnterFooDescription, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                return;
            }

            if (txtAddFoodOrderPrice.getText().toString().trim().length() == 0 || txtAddFoodOrderPrice.getText().toString().trim() == "0")
            {
                Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseEnterPrice, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
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


            if (txtAddGradientAr.getText().toString().trim().length() == 0)
            {

            }

            if (txtAddGradientEn.getText().toString().trim().length() == 0)
            {

            }

            ObjWaitDialog = new WaitDialog(getContext());
            ObjWaitDialog.ShowDialog();

             //Insert New Food
            ObjFood = new Foods();
            ObjFood.setCategoryId(ObjCategoriesList.get(ddlAddCategory.getSelectedItemPosition()).getId());
            ObjFood.setUserId(new Settings(getActivity()).getUserId());
            //ObjFood.setLanguageId(new Settings(getActivity()).getCurrentLanguageId());
            ObjFood.setLanguageId(1); //Arabic
            int iRequestTimeFromMinutes = Integer.parseInt(ddlRequestTimeFromMinutes.getSelectedItem().toString());
            int iRequestTimeFromHours = Integer.parseInt(ddlRequestTimeFromHours.getSelectedItem().toString());
            if (dllRequestTimeFromDayNight.getSelectedItem().toString() .equals("مساءا")
                    ||
                    dllRequestTimeFromDayNight.getSelectedItem().toString() .equals("PM")
                    )
            iRequestTimeFromHours = iRequestTimeFromHours + 12 ;
            ObjFood.setRequestTimeFrom(new Time(iRequestTimeFromHours,iRequestTimeFromMinutes,0));

            int iRequestTimeToMinutes = Integer.parseInt(ddlRequestTimeToMinutes.getSelectedItem().toString());
            int iRequestTimeToHours = Integer.parseInt(ddlRequestTimeToHours.getSelectedItem().toString());
            if (ddlRequestTimeToDayNight.getSelectedItem().toString() .equals("مساءا")
                    ||
                    ddlRequestTimeToDayNight.getSelectedItem().toString() .equals("PM")
                    )
                iRequestTimeToHours = iRequestTimeToHours + 12;
            ObjFood.setRequestTimeTo(new Time(iRequestTimeToHours,iRequestTimeToMinutes,0));
            //Bitmap ObjBitmapTemp = ((BitmapDrawable)imgAddFoodPhoto.getDrawable()).getBitmap();
            ByteArrayOutputStream ObjByteArrayTemp = new ByteArrayOutputStream();
            ObjBitmapTemp.compress(Bitmap.CompressFormat.JPEG,100,ObjByteArrayTemp );
            try {
                ObjFood.setPhoto(Utils.SaveImage(ObjByteArrayTemp.toByteArray()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ObjFood.setPrice(Float.parseFloat(txtAddFoodOrderPrice.getText().toString()) );
            ObjFood.setName(txtAddFoodNameAr.getText().toString().trim());
            ObjFood.setDescription(txtAddFoodDescriptionAr.getText().toString().trim());
            boolean bIsVisible =  false;
            if (ddlShowToCustomer.getSelectedItemPosition() == 1)
                bIsVisible = true;
            else if (ddlShowToCustomer.getSelectedItemPosition() == 2)
                bIsVisible = false;
            ObjFood.setVisible(bIsVisible );
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    int iFoodId = new FoodsDB().InsertUpdate(ObjFood);
                    ObjFood.setId(iFoodId);
                    ObjFood.setLanguageId(2); // English
                    ObjFood.setName(txtAddFoodNameEn.getText().toString().trim());
                    ObjFood.setDescription(txtAddFoodDescriptionEn.getText().toString().trim());
                    new FoodsDB().InsertUpdate(ObjFood);

                    for (Foods_Additions Obj_Foods_Additions : Obj_Foods_Additions_List)
                    {
                        Obj_Foods_Additions.setFoodId(iFoodId);
                        new Foods_AdditionsDB().InsertUpdate(Obj_Foods_Additions);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(),Utils.GetResourceName(getContext(),R.string.DataSavedSuccessfuly,new Settings(getActivity()).getCurrentLanguageId()),Toast.LENGTH_LONG).show();
                            ((MainActivity)getActivity()).LoadMainInfo();
                            ObjWaitDialog.HideDialog();
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

       ObjWaitDialog = new WaitDialog(getContext());
        ObjWaitDialog.ShowDialog();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ObjCategoriesList = FillCategories();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                ObjWaitDialog.HideDialog();
                    }
                });
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


