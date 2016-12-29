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

import com.tastyhomemade.tastyhomemade.Business.ConnectionProperties;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.MainActivity;
import com.tastyhomemade.tastyhomemade.Others.GMailSender;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Raed on 11/23/2016.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    EditText txtLoginUserName, txtLoginPassword;
    Button btnLoginLogin;
    TextView lblRecoverPassword;
    Settings ObjSettings;

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
        lblRecoverPassword = (TextView) view.findViewById(R.id.lblRecoverPassword);
        lblRecoverPassword.setOnClickListener(this);
        ObjSettings = new Settings(getContext());

    }

    @Override
    public void onClick(View v) {
        if (v == btnLoginLogin) {
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

                                ((MainActivity) getActivity()).LoadMainInfo();
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
        } else if (v == lblRecoverPassword) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!txtLoginUserName.getText().toString().trim().equals("")) {
                        SendEmail(txtLoginUserName.getText().toString().trim());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.PleaseCheckYourEmailInbox, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.PleaseEnterUserName, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                            }
                        });
                }
            });
            t.start();
        }
    }

    private void SendEmail(String p_sUserName) {
        try {
            User ObjUser = new UserDB().SelectByUserName(p_sUserName);

            URL ObjUrl = new URL(ConnectionProperties.SiteUrl +"/Template/RecoverPasswordMessage.html");
            InputStreamReader ObjStream = new InputStreamReader(ObjUrl.openStream());
            BufferedReader ObjReader = new BufferedReader(ObjStream);
            String sMessageBody = "";
            String sTemp = "";
            while ((sTemp = ObjReader.readLine()) != null)
                sMessageBody += sTemp;

            sMessageBody = sMessageBody.replace("&lt;user&gt;", ObjUser.getUsername()).replace("&lt;password&gt;", ObjUser.getPassword());

            GMailSender sender = new GMailSender("tastyhomemade2016@gmail.com", "tested12");
            sender.sendMail("مرحبا " + p_sUserName,
                    sMessageBody,
                    "tastyhomemade2016@gmail.com",
                    ObjUser.getEmail());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
