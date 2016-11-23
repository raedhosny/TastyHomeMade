package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tastyhomemade.tastyhomemade.R;

/**
 * Created by Raed on 11/23/2016.
 */

public class LoginFragment extends Fragment {

    EditText txtLoginUserName,txtLoginPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtLoginUserName = (EditText) view.findViewById(R.id.txtLoginUserName);
        txtLoginPassword = (EditText) view.findViewById(R.id.txtLoginPassword);


    }
}
