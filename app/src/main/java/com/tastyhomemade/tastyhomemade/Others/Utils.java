package com.tastyhomemade.tastyhomemade.Others;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.tastyhomemade.tastyhomemade.Business.MainMenuItem;
import com.tastyhomemade.tastyhomemade.Fragment.*;
import java.util.List;
import com.tastyhomemade.tastyhomemade.R;

/**
 * Created by raed on 11/22/2016.
 */

public class Utils {
    public Utils ()
    {

    }

    public void ShowActivity(Context p_context, List<MainMenuItem> p_ItemsList,  String sSelectedItem) {
        FragmentManager Manager = ((AppCompatActivity)p_context).getSupportFragmentManager();

        Manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction Transaction = Manager.beginTransaction();


        if (sSelectedItem== "Register")
        {
            Transaction.replace(R.id.main_content ,new RegisterFragment());
            Transaction.commit();
        }
        else
        if (sSelectedItem== p_ItemsList.get(0).getName())
        {
            Transaction.replace(R.id.main_content ,new ProfileFragment());

            Transaction.commit();
        }
        else if (sSelectedItem== p_ItemsList.get(1).getName())
        {
            Transaction.replace(R.id.main_content ,new SettingsFragment());
            Transaction.commit();
        }
    }

}
