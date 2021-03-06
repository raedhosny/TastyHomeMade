package com.tastyhomemade.tastyhomemade.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Business.Cities;
import com.tastyhomemade.tastyhomemade.Business.CitiesDB;
import com.tastyhomemade.tastyhomemade.Business.ConnectionProperties;
import com.tastyhomemade.tastyhomemade.Business.DB;
import com.tastyhomemade.tastyhomemade.Business.OnGetCity;
import com.tastyhomemade.tastyhomemade.Business.OnTaskCompleted;
import com.tastyhomemade.tastyhomemade.Business.RegisterTypes;
import com.tastyhomemade.tastyhomemade.Business.RegisterTypesDB;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.Others.GMailSender;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.Others.ViewMode;
import com.tastyhomemade.tastyhomemade.Others.WaitDialog;
import com.tastyhomemade.tastyhomemade.R;
import com.tastyhomemade.tastyhomemade.Services.GPSTracker;
import com.tastyhomemade.tastyhomemade.Services.GPSTrackerBackground;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by raed on 11/22/2016.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {

    EditText txtRegisterEmail;
    EditText txtRegisterUserName;
    EditText txtRegisterPassword;
    EditText txtRegisterConfirmPassword;
    Spinner ddlRegisterCity;
    Spinner ddlRegisterType;
    Spinner ddlRepresentive;
    Button btnRegisterSave;
    Button btnRegisterGPS;
    List<Cities> ObjCitesList;
    List<RegisterTypes> ObjRegisterTypesList;
    double iCurrentLatitude = -1, iCurrentLongtitude = -1;
    Settings ObjSettings;
    int GPS_SETTINGS_REQUEST_CODE = 3;
    WaitDialog ObjWaitDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtRegisterEmail = (EditText) view.findViewById(R.id.txtRegisterEmail);
        txtRegisterUserName = (EditText) view.findViewById(R.id.txtRegisterUserName);
        txtRegisterPassword = (EditText) view.findViewById(R.id.txtRegisterPassword);
        txtRegisterConfirmPassword = (EditText) view.findViewById(R.id.txtRegisterConfirmPassword);
        ddlRegisterCity = (Spinner) view.findViewById(R.id.Include_RegisterddlRegisterCity).findViewById(R.id.ddlSpinner);
        ddlRegisterType = (Spinner) view.findViewById(R.id.Include_RegisterddlRegisterType).findViewById(R.id.ddlSpinner);
        ddlRepresentive = (Spinner) view.findViewById(R.id.Include_RegisterddlRepresentive).findViewById(R.id.ddlSpinner);
        btnRegisterGPS = (Button) view.findViewById(R.id.Include_RegisterbtnRegisterGPS).findViewById(R.id.btnRegisterGPS);
        btnRegisterGPS.setOnClickListener(this);

        btnRegisterSave = (Button) view.findViewById(R.id.btnRegisterSave);
        btnRegisterSave.setOnClickListener(this);
        ddlRegisterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (view.getParent() == ddlRegisterType) {
                    LinearLayout lblRegisterRepresentative_Linear1 = (LinearLayout) getActivity().findViewById(R.id.lblRegisterRepresentative_Linear1);
                    if (ddlRegisterType.getSelectedItemPosition() == 1) {
                        lblRegisterRepresentative_Linear1.setVisibility(View.VISIBLE);
                    } else {
                        lblRegisterRepresentative_Linear1.setVisibility(View.INVISIBLE);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ObjSettings = new Settings(getContext());
        ObjWaitDialog = new WaitDialog(getContext());
        ObjWaitDialog.ShowDialog();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                ObjCitesList = FillCities();

                ObjRegisterTypesList = FillRegisterTypes();

                FillHaveRepresentive();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ObjWaitDialog.HideDialog();
                    }
                });

            }
        });
        t.start();


    }


    public void onClick(View v) {

        if (v == btnRegisterSave) {


            // Validate Email
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(txtRegisterEmail.getText()).matches() == false) {
                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.Error_InvalidEmailAddress, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate Password
            String sPassword = txtRegisterPassword.getText().toString().trim();
            String sConfirmPassword = txtRegisterConfirmPassword.getText().toString().trim();

            if (sPassword.length() == 0) {
                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.Error_PasswordCanNotBeZeroCharacters, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_SHORT).show();
                return;
            }

            if (!sPassword.equals(sConfirmPassword)) {
                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.Error_PasswordDoesNotMatch, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_SHORT).show();
                return;
            }

            //Validate City
            if (iCurrentLatitude == -1 || iCurrentLongtitude == -1) {
                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.Error_PleaseSelectCity, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_SHORT).show();
                return;
            }


            //Validate Register Type
            if (ddlRegisterType.getSelectedItemPosition() == 0) {
                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.Error_PleaseSelectRegisterType, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_SHORT).show();
                return;
            }

            //Validate Have Representative
            if (ddlRegisterType.getSelectedItemPosition() == 1 && ddlRepresentive.getSelectedItemPosition() == 0) {
                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.Error_PleaseSelectRepresentative, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                return;
            }


            // get City id

            Cities ObjCityTemp = new Cities();
            for (Cities ObjCity : ObjCitesList) {
                if (ObjCity.getName().equals(ddlRegisterCity.getSelectedItem())) {
                    ObjCityTemp = ObjCity;
                    break;
                }
            }
            final int iCityId = ObjCityTemp.getId();


            // get Register Type id
            RegisterTypes ObjRegisterTypesTemp = new RegisterTypes();
            for (RegisterTypes ObjRegisterTypes : ObjRegisterTypesList) {
                if (ObjRegisterTypes.getName().equals(ddlRegisterType.getSelectedItem())) {
                    ObjRegisterTypesTemp = ObjRegisterTypes;
                    break;
                }
            }

            final int iRegisterTypeId = ObjRegisterTypesTemp.getId();

            boolean IsHaveDelivary = false;

            if (ddlRepresentive.getSelectedItemPosition() == 1)
                IsHaveDelivary = true;
            else if (ddlRepresentive.getSelectedItemPosition() == 2)
                IsHaveDelivary = false;
            final boolean IsHaveDelivaryResult = IsHaveDelivary;

            final String sActivationCode = java.util.UUID.randomUUID().toString().replace("-", "");

            ObjWaitDialog.ShowDialog();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    User ObjUser = new User();
                    ObjUser.setId(-1);
                    ObjUser.setName("");
                    ObjUser.setUsername(txtRegisterUserName.getText().toString());
                    ObjUser.setPassword(txtRegisterPassword.getText().toString());
                    ObjUser.setEmail(txtRegisterEmail.getText().toString());
                    ObjUser.setRegisterTypeId(iRegisterTypeId);
                    ObjUser.setCurrentLocation_Latitude(iCurrentLatitude);
                    ObjUser.setCurrentLocation_Longitude(iCurrentLongtitude);
                    ObjUser.setCityId(iCityId);
                    ObjUser.setDistrict("Nothing");
                    ObjUser.setStreet("Nothing");
                    ObjUser.setBuilding("Nothing");
                    ObjUser.setApartment("Nothing");
                    ObjUser.setActive(false);
                    ObjUser.setActivationCode(sActivationCode);
                    ObjUser.setHaveDelivary(IsHaveDelivaryResult);

                    int iResult = new UserDB().InsertUpdate(ObjUser);
                    SendEmail(txtRegisterUserName.getText().toString().trim(),txtRegisterEmail.getText().toString(),sActivationCode);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.DataSavedSuccessfuly, new Settings(getContext()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                            new Utils().ShowActivity(getContext(), null, "Main", ViewMode.NormalMode.name(), "-1");
                            ObjWaitDialog.HideDialog();
                        }
                    });

                }
            });
            t.start();

        } else if (v == btnRegisterGPS) {

            ObjWaitDialog.ShowDialog();
            try {
                OnTaskCompleted ObjOnTaskCompleted = new OnTaskCompleted() {
                    @Override
                    public void OnTaskCompleted(List<Double> Results) {
                        if (Results.size() > 0) {
                            iCurrentLatitude = Results.get(0);
                            iCurrentLongtitude = Results.get(1);
                            ObjWaitDialog.HideDialog();
                        }
                    }
                };

                GPSTrackerBackground ObjGPSTrackerBackground = new GPSTrackerBackground(this, ObjOnTaskCompleted);
                ObjGPSTrackerBackground.execute();
                GPSTracker ObjGPSTracker = ObjGPSTrackerBackground.getGPSTracker();

                if (ObjGPSTracker.getCanGetLocation()) {

                    OnGetCity ObjOnGetCity = new OnGetCity() {
                        @Override
                        public void GetCity(String result) {
                            int iSelectedIndex = 0;
                            for (int i = 0; i < ObjCitesList.size(); i++) {
                                if (result.contains(ObjCitesList.get(i).getName())) {
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



                                final int iSelectedIndexFinal = iSelectedIndex;


                                ddlRegisterCity.setSelection(iSelectedIndexFinal);


                            }
                        }
                    };

                    Utils.GoogleMapClassCity ObjGoogleMapClass = new Utils().new GoogleMapClassCity(getContext(),ObjOnGetCity);
                    Log.d("Latitude is " , String.valueOf(ObjGPSTracker.getLatitude()));
                    Log.d("Longitude is " , String.valueOf(ObjGPSTracker.getlongtitude()));

                    ObjGoogleMapClass.execute(ObjGPSTracker.getLatitude(), ObjGPSTracker.getlongtitude());
                    //String sCurrentCity = ObjGoogleMapClass.getsCurrentCity();
                    //Log.d("City is ",sCurrentCity);
                    //iCurrentLatitude = ObjGPSTracker.getLatitude();
                    //iCurrentLongtitude = ObjGPSTracker.getlongtitude();

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

            ObjWaitDialog.ShowDialog();
            try {

                OnTaskCompleted ObjOnTaskCompleted = new OnTaskCompleted() {
                    @Override
                    public void OnTaskCompleted(List<Double> Results) {
                        if (Results.size() > 0) {
                            iCurrentLatitude = Results.get(0);
                            iCurrentLongtitude = Results.get(1);
                        }
                        ObjWaitDialog.HideDialog();
                    }
                };

                GPSTrackerBackground ObjGPSTrackerBackground = new GPSTrackerBackground(this, ObjOnTaskCompleted);
                ObjGPSTrackerBackground.execute();
                GPSTracker ObjGPSTracker = ObjGPSTrackerBackground.getGPSTracker();

                if (ObjGPSTracker.getCanGetLocation()) {
                    OnGetCity ObjOnGetCity = new OnGetCity() {
                        @Override
                        public void GetCity(String result) {
                            int iSelectedIndex = 0;
                            for (int i = 0; i < ObjCitesList.size(); i++) {
                                if (result.contains(ObjCitesList.get(i).getName())) {
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

                                ddlRegisterCity.setSelection(iSelectedIndex);
                            }
                        }
                    };

                    Utils.GoogleMapClassCity ObjGoogleMapClass = new Utils().new GoogleMapClassCity(getContext(),ObjOnGetCity);
                    ObjGoogleMapClass.execute(ObjGPSTracker.getLatitude(), ObjGPSTracker.getlongtitude());
                    //String sCurrentCity =ObjGoogleMapClass.getsCurrentCity();
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


    private List<Cities> FillCities() {

        Settings ObjSettings = new Settings(getActivity());
        List<String> ObjStringsList = new ArrayList<String>();


        List<Cities> ObjCitiesList = new CitiesDB().SelectAll(ObjSettings.getCurrentLanguageId());
        for (Cities ObjTemp : ObjCitiesList) {
            ObjStringsList.add(ObjTemp.getName());
        }


        final ArrayAdapter<String> CitiesAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Arrays.copyOf(ObjStringsList.toArray(), ObjStringsList.size(), String[].class));

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ddlRegisterCity.setAdapter(CitiesAdapter);
                ddlRegisterCity.setEnabled(false);
            }
        });
        return ObjCitiesList;
    }

    private List<RegisterTypes> FillRegisterTypes() {

        Settings ObjSettings = new Settings(getActivity());
        List<String> ObjStringsList = new ArrayList<String>();
        List<RegisterTypes> ObjRegisterTypesList = new RegisterTypesDB().SelectAll(ObjSettings.getCurrentLanguageId());
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


        return ObjRegisterTypesList;

    }

    private void FillHaveRepresentive() {


        final ArrayAdapter<String> ObjHaveRepresentiveAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Utils.GetResourceArrayName(getContext(), R.array.myboolean, new Settings(getContext()).getCurrentLanguageId()));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ddlRepresentive.setAdapter(ObjHaveRepresentiveAdapter);
            }
        });


    }

    private void SendEmail (String p_sUserName,String p_sEmail,String p_ActivationCode)
    {
        try {
            URL ObjUrl = new URL(ConnectionProperties.SiteUrl + "/Template/UserActivationMessage.html");
            InputStreamReader ObjStream = new InputStreamReader( ObjUrl.openStream());
            BufferedReader ObjReader = new BufferedReader(ObjStream);
            String sMessageBody = "";
            String sTemp = "";
            while ((sTemp =ObjReader.readLine()) != null)
                sMessageBody  +=sTemp;

            sMessageBody= sMessageBody.replace("&lt;user&gt;",p_sUserName).replace("&lt;ActivateCode&gt;",p_ActivationCode);

        GMailSender sender = new GMailSender("tastyhomemade2016@gmail.com", "tested12");
        sender.sendMail("مرحبا "+p_sUserName ,
                sMessageBody,
                "tastyhomemade2016@gmail.com",
                p_sEmail);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
