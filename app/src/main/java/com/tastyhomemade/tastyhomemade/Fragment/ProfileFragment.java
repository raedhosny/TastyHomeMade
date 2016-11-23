package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Business.Cities;
import com.tastyhomemade.tastyhomemade.Business.CitiesDB;
import com.tastyhomemade.tastyhomemade.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raed on 11/20/2016.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    Button btnProfileSave;
    Spinner ddlProfileCity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnProfileSave = (Button)view.findViewById(R.id.btnProfileSave);
        ddlProfileCity = (Spinner)view.findViewById(R.id.ddlProfileCity);
        btnProfileSave.setOnClickListener(this);

        FillCities();

    }


    @Override
    public void onClick(View v) {
        if (v == btnProfileSave)
        {
            Toast.makeText(this.getActivity(),"Hiiiii",Toast.LENGTH_LONG).show();

        }
    }


    private void FillCities ()
    {
//        List<String> ObjStringsList = new ArrayList<String>();
//        List<Cities> ObjCitiesList = new CitiesDB().SelectAll(1);
//        for(Cities ObjTemp : ObjCitiesList)
//        {
//            ObjStringsList.add(ObjTemp.getName());
//        }
//
//
//        ArrayAdapter<String> CitiesAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,(String[])ObjStringsList.toArray());
//        ddlProfileCity.setAdapter(CitiesAdapter );
    }
}
