package com.tastyhomemade.tastyhomemade.Fragment;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.MainActivity;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.R;

import java.util.Locale;

/**
 * Created by Raed on 11/23/2016.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    EditText txtLoginUserName, txtLoginPassword;
    Button btnLoginLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtLoginUserName = (EditText) view.findViewById(R.id.txtLoginUserName);
        txtLoginPassword = (EditText) view.findViewById(R.id.txtLoginPassword);
        btnLoginLogin = (Button) view.findViewById(R.id.btnLoginLogin);
        btnLoginLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                User ObjUser = new UserDB().Login(txtLoginUserName.getText().toString().trim(), txtLoginPassword.getText().toString());

                final Settings ObjSettings = new Settings(getContext());
                if (ObjUser.getId() != -1) {
                    ObjSettings.setUserId(ObjUser.getId());
                    ObjSettings.setUserName(ObjUser.getUsername());
                    if (ObjUser.getRegisterTypeId() == 1) // FoodMaker
                        ObjSettings.setUserType(Settings.enumUserType.FoodMaker.name());
                    else if (ObjUser.getRegisterTypeId() == 2) //Customer
                        ObjSettings.setUserType(Settings.enumUserType.Customer.name());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ((MainActivity)getActivity()).LoadMainInfo();
                        }
                    });
                } else {
                    Configuration ObjConfiguration = getResources().getConfiguration();
                    if (ObjSettings.getCurrentLanguageId() == 1) // Arabic
                        ObjConfiguration.setLocale(new Locale("ar"));
                    else if (ObjSettings.getCurrentLanguageId() == 2) // English
                        ObjConfiguration.setLocale(new Locale("en"));

                    final Resources ObjResources = new Resources(getContext().getAssets(), getResources().getDisplayMetrics(), ObjConfiguration);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), ObjResources.getString(R.string.Error_WrongUserNameOrPassword), Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });
        t.start();
    }
}
