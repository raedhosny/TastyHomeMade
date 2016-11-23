package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tastyhomemade.tastyhomemade.R;

/**
 * Created by Raed on 11/22/2016.
 */

public class SettingsFragment extends Fragment {

    Button btnsettings_arabic,btnsettings_English;


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
    }
}
