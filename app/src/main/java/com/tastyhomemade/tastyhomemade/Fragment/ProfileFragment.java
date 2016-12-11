package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;

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
import com.tastyhomemade.tastyhomemade.Business.RegisterTypes;
import com.tastyhomemade.tastyhomemade.Business.RegisterTypesDB;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;
import com.tastyhomemade.tastyhomemade.Services.GPSTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by raed on 11/20/2016.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    EditText txtEmail;
    EditText txtUserName;
    EditText txtPassword;
    EditText txtPasswordConfirm;
    Spinner ddlCity;
    Button btnRegisterGPS;
    EditText txtRegisterType;
    Button btnSave;
    Settings ObjSettings;
    List<Cities> ObjCitiesList;
    ArrayAdapter<String> CitiesAdapter;
    double iCurrentLatitude = -1, iCurrentLongtitude = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtUserName = (EditText) view.findViewById(R.id.txtUserName);
        txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        txtPasswordConfirm = (EditText) view.findViewById(R.id.txtPasswordConfirm);
        ddlCity = (Spinner) view.findViewById(R.id.Include_ddlCity).findViewById(R.id.ddlSpinner);
        btnRegisterGPS = (Button) view.findViewById(R.id.Include_btnRegisterGPS).findViewById(R.id.btnRegisterGPS);
        btnRegisterGPS.setOnClickListener(this);
        txtRegisterType = (EditText) view.findViewById(R.id.txtRegisterType);

        ObjSettings = new Settings(getContext());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                FillCities();
                FillData();
            }
        });
        t.start();

    }


    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            Toast.makeText(this.getActivity(), "Hiiiii", Toast.LENGTH_LONG).show();
        } else if (v == btnRegisterGPS) {
            try {
                GPSTracker ObjGPSTracker = new GPSTracker(this);
                Utils.GoogleMapClassCity ObjGoogleMapClass = new Utils().new GoogleMapClassCity(getContext());
                String sResult = ObjGoogleMapClass.execute(ObjGPSTracker.getLatitude(), ObjGPSTracker.getlongtitude()).get();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }


    private void FillCities() {

        List<String> ObjStringsList = new ArrayList<String>();
        ObjCitiesList = new CitiesDB().SelectAll(ObjSettings.getCurrentLanguageId());
        for (Cities ObjTemp : ObjCitiesList) {
            ObjStringsList.add(ObjTemp.getName());
        }


        CitiesAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Arrays.copyOf(ObjStringsList.toArray(), ObjStringsList.toArray().length, String[].class));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                ddlCity.setAdapter(CitiesAdapter);
            }
        });


    }

    private void FillData() {


        Bundle ObjBundle = getArguments();
        int iUserId = ObjBundle.getInt("UserId");
        final User ObjUser = new UserDB().Select(iUserId);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                txtEmail.setText(ObjUser.getEmail());
                txtUserName.setText(ObjUser.getUsername());
            }
        });

        String sCityName = "";
        for (Cities ObjCity : ObjCitiesList) {
            if (ObjCity.getId() == ObjUser.getCityId()) {
                sCityName = ObjCity.getName();
                break;
            }
        }

        final int iIndex = CitiesAdapter.getPosition(sCityName);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                ddlCity.setSelection(iIndex);
            }
        });
        final RegisterTypes ObjRegisterType = new RegisterTypesDB().Select(ObjUser.getRegisterTypeId(), ObjSettings.getCurrentLanguageId());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                txtRegisterType.setText(ObjRegisterType.getName());
            }
        });


    }
}
