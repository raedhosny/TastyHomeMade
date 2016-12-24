package com.tastyhomemade.tastyhomemade.Services;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

/**
 * Created by Raed on 12/10/2016.
 */

public class GPSTracker extends Service implements LocationListener {

    boolean IsGPSEnabled;
    boolean IsNetworkEnabled;
    boolean IsCanGetLocation;

    Location objCurrentLocation;
    LocationManager ObjLocationManager;
    double latitude;
    double longtitude;
    Context context;
    Fragment fragment;
    boolean GotNewLocation = false;
    static final long MIN_TIME_BW_UPDATES = 0;
    static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 Meters
    Settings ObjSettings;
    int GPS_SETTINGS_REQUEST_CODE= 3;

    public GPSTracker(Fragment p_fragment) {
        fragment = p_fragment;
        context = p_fragment.getContext();
        ObjSettings = new Settings(context);
        GetLocation();


    }

    public GPSTracker(Context p_context) {
        context = p_context;
        ObjSettings = new Settings(context);
        GetLocation();
    }

    public Location GetLocation() {
        try {

            ObjLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            IsGPSEnabled = ObjLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            IsNetworkEnabled = ObjLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!IsGPSEnabled && !IsNetworkEnabled) {
                IsCanGetLocation = false;
                // Make Alert Here for User Must Enable GPS
            } else {
                IsCanGetLocation = true;
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        ||
                        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        ) {
                    IsCanGetLocation = false;
                    return null;
                }

                if (IsNetworkEnabled) {

                    ObjLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Thread.sleep(1000);
//                    ObjLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
//                    try {
//                        Thread.sleep(5000);
//                    }
//                    catch (Exception ex)
//                    {
//                        ex.printStackTrace();
//                    }
                    if (ObjLocationManager != null) {
                        objCurrentLocation = ObjLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (objCurrentLocation != null ) {
                            latitude = objCurrentLocation.getLatitude();
                            longtitude = objCurrentLocation.getLongitude();
                            GotNewLocation = true;

                        }
                    }
                }

                if (IsGPSEnabled) {
                    if (objCurrentLocation == null) {
                        ObjLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Thread.sleep(1000);
//                        try {
//                            Thread.sleep(5000);
//                        }
//                        catch (Exception ex)
//                        {
//                            ex.printStackTrace();
//                        }
                        if (ObjLocationManager != null) {
                            objCurrentLocation = ObjLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (objCurrentLocation != null ) {

                                latitude = objCurrentLocation.getLatitude();
                                longtitude = objCurrentLocation.getLongitude();
                                GotNewLocation = true;
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return objCurrentLocation;
    }

    public void StopUsingGPS() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
            IsCanGetLocation = false;
        }

        if (ObjLocationManager != null) {
            ObjLocationManager.removeUpdates(GPSTracker.this);
        }
    }

    public double getLatitude() {

        return latitude;
    }

    public double getlongtitude() {

        return longtitude;
    }

    public boolean getIsGotNewLocation() {

        return GotNewLocation;
    }
    public boolean getCanGetLocation() {
        return IsCanGetLocation;
    }

    public void ShowSettingsAlert() {
        final AlertDialog.Builder ctlAlertDialog = new AlertDialog.Builder(context);
        ctlAlertDialog.setTitle(Utils.GetResourceName(context, R.string.GpsSettingsTitle, ObjSettings.getCurrentLanguageId()));
        ctlAlertDialog.setMessage(Utils.GetResourceName(context, R.string.GpsSettingsDescription, ObjSettings.getCurrentLanguageId()));
        ctlAlertDialog.setPositiveButton(Utils.GetResourceName(context, R.string.GpsSettingsPositiveButton, ObjSettings.getCurrentLanguageId()), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent ObjIntent = new Intent(ACTION_LOCATION_SOURCE_SETTINGS);
                        fragment.startActivityForResult(ObjIntent,GPS_SETTINGS_REQUEST_CODE);
                    }
                }

        );

        ctlAlertDialog.setNegativeButton(Utils.GetResourceName(context, R.string.GpsSettingsNegativeButton, ObjSettings.getCurrentLanguageId()), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }

        );

        ctlAlertDialog.show();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longtitude = location.getLongitude();
        GotNewLocation = true;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
