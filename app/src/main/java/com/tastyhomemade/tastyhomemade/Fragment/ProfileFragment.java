package com.tastyhomemade.tastyhomemade.Fragment;

import android.content.Intent;
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
import com.tastyhomemade.tastyhomemade.Business.OnGetCity;
import com.tastyhomemade.tastyhomemade.Business.OnTaskCompleted;
import com.tastyhomemade.tastyhomemade.Business.RegisterTypes;
import com.tastyhomemade.tastyhomemade.Business.RegisterTypesDB;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.Others.WaitDialog;
import com.tastyhomemade.tastyhomemade.R;
import com.tastyhomemade.tastyhomemade.Services.GPSTracker;
import com.tastyhomemade.tastyhomemade.Services.GPSTrackerBackground;

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
    int GPS_SETTINGS_REQUEST_CODE = 3;


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
            if (!txtPassword.getText().toString().equals(txtPasswordConfirm.getText().toString())) {
                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.Error_PasswordDoesNotMatch, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_SHORT).show();
                return;
            } else if (iCurrentLatitude == -1 || iCurrentLongtitude == -1) {
                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.Error_CantFindCity, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();

            } else {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bundle ObjBundle = getArguments();
                        int UserId = ObjBundle.getInt("UserId");
                        User ObjUser = new UserDB().Select(UserId);
                        ObjUser.setPassword(txtPassword.getText().toString().trim());
                        ObjUser.setCurrentLocation_Latitude(iCurrentLatitude);
                        ObjUser.setCurrentLocation_Longitude(iCurrentLongtitude);
                        new UserDB().InsertUpdate(ObjUser);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.DataSavedSuccessfuly, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();

                                new Utils().ShowActivity(getContext(), null, "Main", "-1");
                            }
                        });

                    }
                });
                t.start();
            }


        } else if (v == btnRegisterGPS) {

            final WaitDialog ObjWaitDialog = new WaitDialog(getActivity());


            try {
                OnTaskCompleted ObjOnTaskCompleted = new OnTaskCompleted() {
                    @Override
                    public void OnTaskCompleted(List<Double> Results) {
                        if (Results.size() > 0) {
                            iCurrentLatitude = Results.get(0);
                            iCurrentLongtitude = Results.get(1);
                        }
                    }
                };

                GPSTrackerBackground ObjGPSTrackerBackground = new GPSTrackerBackground(this,ObjOnTaskCompleted);
                ObjGPSTrackerBackground.execute();
                GPSTracker ObjGPSTracker = ObjGPSTrackerBackground.getGPSTracker();
                if (ObjGPSTracker.getCanGetLocation()) {
                    OnGetCity ObjOnGetCity = new OnGetCity() {
                        @Override
                        public void GetCity(String result) {
                            int iSelectedIndex = 0;
                            for (int i = 0; i < ObjCitiesList.size(); i++) {
                                if (result.contains(ObjCitiesList.get(i).getName())) {
                                    iSelectedIndex = i;
                                    break;
                                }
                            }

                            if (iSelectedIndex == 0) {
                                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.Error_CantFindCity, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                                iCurrentLatitude = -1;
                                iCurrentLongtitude = -1;
                            } else {
                                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.SuccessfullyGotGPSLocation, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();

                                ddlCity.setSelection(iSelectedIndex);
                            }
                        }
                    };
                    Utils.GoogleMapClassCity ObjGoogleMapClass = new Utils().new GoogleMapClassCity(getContext(),ObjOnGetCity);
                    ObjGoogleMapClass.execute(ObjGPSTracker.getLatitude(), ObjGPSTracker.getlongtitude());
                    //String sCurrentCity = ObjGoogleMapClass.getsCurrentCity();
                    iCurrentLatitude = ObjGPSTracker.getLatitude();
                    iCurrentLongtitude = ObjGPSTracker.getlongtitude();

                } else {
                    ObjGPSTracker.ShowSettingsAlert();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }



        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_SETTINGS_REQUEST_CODE) {

            try {
                OnTaskCompleted ObjOnTaskCompleted = new OnTaskCompleted() {
                    @Override
                    public void OnTaskCompleted(List<Double> Results) {
                        if (Results.size() > 0) {
                            iCurrentLatitude = Results.get(0);
                            iCurrentLongtitude = Results.get(1);
                        }
                    }
                };

                GPSTrackerBackground ObjGPSTrackerBackground = new GPSTrackerBackground(this,ObjOnTaskCompleted);
                ObjGPSTrackerBackground.execute();
                GPSTracker ObjGPSTracker = ObjGPSTrackerBackground.getGPSTracker();

                if (ObjGPSTracker.getCanGetLocation()) {
                    OnGetCity ObjOnGetCity = new OnGetCity() {
                        @Override
                        public void GetCity(String result) {
                            int iSelectedIndex = 0;
                            for (int i = 0; i < ObjCitiesList.size(); i++) {
                                if (result.contains(ObjCitiesList.get(i).getName())) {
                                    iSelectedIndex = i;
                                    break;
                                }
                            }

                            if (iSelectedIndex == 0) {
                                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.Error_CantFindCity, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                                iCurrentLatitude = -1;
                                iCurrentLongtitude = -1;
                            } else {
                                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.SuccessfullyGotGPSLocation, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();

                                ddlCity.setSelection(iSelectedIndex);
                            }
                        }
                    };
                    Utils.GoogleMapClassCity ObjGoogleMapClass = new Utils().new GoogleMapClassCity(getContext(),ObjOnGetCity);
                    ObjGoogleMapClass.execute(ObjGPSTracker.getLatitude(), ObjGPSTracker.getlongtitude());
                   // String sCurrentCity = ObjGoogleMapClass.getsCurrentCity();
                    iCurrentLatitude = ObjGPSTracker.getLatitude();
                    iCurrentLongtitude = ObjGPSTracker.getlongtitude();

                } else {
                    ObjGPSTracker.ShowSettingsAlert();
                }
            } catch (Exception ex) {
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
                ddlCity.setEnabled(false);
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
                txtPassword.setText(ObjUser.getPassword());
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

        iCurrentLatitude = ObjUser.getCurrentLocation_Latitude();
        iCurrentLongtitude = ObjUser.getCurrentLocation_Longitude();


    }
}
