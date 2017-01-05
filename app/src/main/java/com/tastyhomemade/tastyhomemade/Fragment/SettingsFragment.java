package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.Others.ViewMode;
import com.tastyhomemade.tastyhomemade.R;

/**
 * Created by Raed on 11/22/2016.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    Button btnsettings_arabic,btnsettings_English,btnSave;
    Settings ObjSettings;
    boolean IsArabic=false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnsettings_arabic =(Button)view.findViewById(R.id.settings_arabic);
        btnsettings_English =(Button)view.findViewById(R.id.settings_English);
        btnSave = (Button)view.findViewById(R.id.btnSave);
        btnsettings_arabic.setOnClickListener(this);
        btnsettings_English.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        ObjSettings = new Settings(getContext());
        if (ObjSettings.getCurrentLanguageId() ==1)
        {
            btnsettings_arabic.setBackgroundResource(R.drawable.settings_on);
            btnsettings_English.setBackgroundResource(R.drawable.settings_off);
            IsArabic = true;
        }
        else if (ObjSettings.getCurrentLanguageId() ==2)
        {
            btnsettings_arabic.setBackgroundResource(R.drawable.settings_off);
            btnsettings_English.setBackgroundResource(R.drawable.settings_on);
            IsArabic = false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnsettings_arabic)
        {
            btnsettings_arabic.setBackgroundResource(R.drawable.settings_on);
            btnsettings_English.setBackgroundResource(R.drawable.settings_off);
            IsArabic =true;
        }
        else if (v == btnsettings_English)
        {
            btnsettings_arabic.setBackgroundResource(R.drawable.settings_off);
            btnsettings_English.setBackgroundResource(R.drawable.settings_on);
            IsArabic =false;
        }
        else if (v == btnSave)
        {
            if (IsArabic)
            {
                Utils.SetCurrentLanguage(getContext(),1);
                Toast.makeText(getContext(), R.string.DataSavedSuccessfuly, Toast.LENGTH_SHORT).show();

            }
            else if (!IsArabic)
            {
                Utils.SetCurrentLanguage(getContext(),2);
                Toast.makeText(getContext(), R.string.DataSavedSuccessfuly, Toast.LENGTH_SHORT).show();
            }

            if (ObjSettings.getUserType().equals(Settings.enumUserType.FoodMaker.toString()))
                new Utils().ShowActivity(getActivity(), null, "MainForFoodMaker",ViewMode.NormalMode.name());
            else
                new Utils().ShowActivity(getActivity(), null, "Main", ViewMode.NormalMode.name(),"-1");

        }
    }
}
