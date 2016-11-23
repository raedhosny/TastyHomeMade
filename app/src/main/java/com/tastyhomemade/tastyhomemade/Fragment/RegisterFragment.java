package com.tastyhomemade.tastyhomemade.Fragment;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Business.Cities;
import com.tastyhomemade.tastyhomemade.Business.CitiesDB;
import com.tastyhomemade.tastyhomemade.Business.DB;
import com.tastyhomemade.tastyhomemade.Business.RegisterTypes;
import com.tastyhomemade.tastyhomemade.Business.RegisterTypesDB;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by raed on 11/22/2016.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {

    EditText txtRegisterName;
    EditText txtRegisterEmail;
    EditText txtRegisterUserName;
    EditText txtRegisterPassword;
    EditText txtRegisterConfirmPassword;
    Spinner ddlRegisterCity;
    Spinner ddlRegisterType;
    Button btnRegisterSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtRegisterName = (EditText) view.findViewById(R.id.txtRegisterName);
        txtRegisterEmail = (EditText) view.findViewById(R.id.txtRegisterEmail);
        txtRegisterUserName = (EditText) view.findViewById(R.id.txtRegisterUserName);
        txtRegisterPassword = (EditText) view.findViewById(R.id.txtRegisterPassword);
        txtRegisterConfirmPassword = (EditText) view.findViewById(R.id.txtRegisterConfirmPassword);
        ddlRegisterCity = (Spinner) view.findViewById(R.id.ddlRegisterCity);
        ddlRegisterType = (Spinner) view.findViewById(R.id.ddlRegisterType);
        btnRegisterSave = (Button) view.findViewById(R.id.btnRegisterSave);
        btnRegisterSave.setOnClickListener(this);


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                FillCities();

                FillRegisterTypes();
            }
        });
        t.start();

    }

    @Override
    public void onClick(View v) {
        if (v == btnRegisterSave) {
            Configuration ObjConfiguration = getResources().getConfiguration();
            ObjConfiguration.setLocale(new Locale("ar"));
            Resources ObjResources = new Resources(getContext().getAssets(), getResources().getDisplayMetrics(), ObjConfiguration);


            // Validate Email
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(txtRegisterEmail.getText()).matches() == false) {
                Toast.makeText(getContext(), ObjResources.getString(R.string.Error_InvalidEmailAddress), Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate Password
            String sPassword = txtRegisterPassword.getText().toString().trim();
            String sConfirmPassword = txtRegisterConfirmPassword.getText().toString().trim();

            if (!sPassword.equals(sConfirmPassword)) {
                Toast.makeText(getContext(), ObjResources.getString(R.string.Error_PasswordDoesNotMatch), Toast.LENGTH_SHORT).show();
                return;
            }

            //Validate City
            if (ddlRegisterCity.getSelectedItemPosition() == 0)
            {
                Toast.makeText(getContext(), ObjResources.getString(R.string.Error_PleaseSelectCity), Toast.LENGTH_SHORT).show();
                return;
            }


            //Validate Register Type
            if (ddlRegisterType.getSelectedItemPosition() == 0)
            {
                Toast.makeText(getContext(), ObjResources.getString(R.string.Error_PleaseSelectCity), Toast.LENGTH_SHORT).show();
                return;
            }

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    User ObjUser= new User();
                    ObjUser.setId(-1);
                    ObjUser.setName(txtRegisterName.getText().toString());
                    ObjUser.setUsername(txtRegisterUserName.getText().toString());
                    ObjUser.setPassword(txtRegisterPassword.getText().toString());
                    ObjUser.setEmail(txtRegisterEmail.getText().toString());
                    ObjUser.setRegisterTypeId(1);
                    ObjUser.setCurrentLocation_Latitude(-1);
                    ObjUser.setCurrentLocation_Longitude(-1);
                    ObjUser.setCityId(1);
                    ObjUser.setDistrict("Nothing");
                    ObjUser.setStreet("Nothing");
                    ObjUser.setBuilding("Nothing");
                    ObjUser.setApartment("Nothing");
                    ObjUser.setActive(false);
                    ObjUser.setActivationCode(java.util.UUID.randomUUID().toString().replace("-",""));

                    int iResult = new UserDB().InsertUpdate(ObjUser);

                }
            });
            t.start();

        }
    }


    private void FillCities() {

        List<String> ObjStringsList = new ArrayList<String>();

        List<Cities> ObjCitiesList = new CitiesDB().SelectAll(1);
        for (Cities ObjTemp : ObjCitiesList) {
            ObjStringsList.add(ObjTemp.getName());
        }


        final ArrayAdapter<String> CitiesAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Arrays.copyOf(ObjStringsList.toArray(), ObjStringsList.size(), String[].class));

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ddlRegisterCity.setAdapter(CitiesAdapter);
            }
        });
    }

    private void FillRegisterTypes() {
        List<String> ObjStringsList = new ArrayList<String>();
        List<RegisterTypes> ObjRegisterTypesList = new RegisterTypesDB().SelectAll(1);
        for (RegisterTypes ObjTemp : ObjRegisterTypesList) {
            ObjStringsList.add(ObjTemp.getName());
        }


        final ArrayAdapter<String> RegisterTypesAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Arrays.copyOf(ObjStringsList.toArray(), ObjStringsList.size(), String[].class));

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ddlRegisterType.setAdapter(RegisterTypesAdapter);
            }
        });

    }

}
