package com.tastyhomemade.tastyhomemade.Services;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.tastyhomemade.tastyhomemade.Business.OnTaskCompleted;
import com.tastyhomemade.tastyhomemade.Others.WaitDialog;
import com.tastyhomemade.tastyhomemade.Services.GPSTracker;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raed on 12/17/2016.
 */

public class GPSTrackerBackground extends AsyncTask<Void, Void, List<Double>> {


    WaitDialog ObjWaitDialog = null;
    Fragment fragment;
    GPSTracker ObjGPSTracker = null;
    List<Double> ObjResultsList = null;
    OnTaskCompleted MyOnTaskCompleted;

    public GPSTrackerBackground(Fragment p_Activity, OnTaskCompleted p_TaskCompleted) {
        super();
        ObjWaitDialog = new WaitDialog(p_Activity.getActivity());
        fragment = p_Activity;
        MyOnTaskCompleted = p_TaskCompleted;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        ObjGPSTracker = new GPSTracker(fragment);

        ObjWaitDialog.ShowDialog();

        ObjResultsList = new ArrayList<Double>();
    }

    @Override
    protected List<Double> doInBackground(Void... params) {

        while (ObjGPSTracker.getCanGetLocation() &&  !ObjGPSTracker.getIsGotNewLocation())
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        ObjResultsList.add(ObjGPSTracker.getLatitude());
        ObjResultsList.add(ObjGPSTracker.getlongtitude());

        return ObjResultsList;

    }

    @Override
    protected void onPostExecute(List<Double> aDouble) {
        //super.onPostExecute(aDouble);
        MyOnTaskCompleted.OnTaskCompleted(aDouble);
        ObjWaitDialog.HideDialog();

    }

    public GPSTracker getGPSTracker ()
    {
        if (ObjGPSTracker != null)
            return ObjGPSTracker;
        return null;
    }

}
