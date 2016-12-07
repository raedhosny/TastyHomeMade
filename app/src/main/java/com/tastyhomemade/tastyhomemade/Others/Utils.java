package com.tastyhomemade.tastyhomemade.Others;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.tastyhomemade.tastyhomemade.Business.MainMenuItem;
import com.tastyhomemade.tastyhomemade.Fragment.*;
import java.util.List;
import java.util.Locale;

import com.tastyhomemade.tastyhomemade.R;

/**
 * Created by raed on 11/22/2016.
 */

public class Utils {
    public Utils ()
    {

    }

    public void ShowActivity(Context p_context, List<MainMenuItem> p_ItemsList,  String sSelectedItem, String ... args) {
        FragmentManager Manager = ((AppCompatActivity)p_context).getSupportFragmentManager();

        Manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction Transaction = Manager.beginTransaction();

        if (sSelectedItem== "Main")
        {
            MainFragment ObjMainFragment = new MainFragment();
            Bundle ObjBundle = new Bundle();
            ObjBundle.putInt("CategoryId",Integer.parseInt(args[0]));
            ObjMainFragment.setArguments(ObjBundle);
            Transaction.replace(R.id.main_content ,ObjMainFragment);
            Transaction.commit();
        }

        else if (sSelectedItem== "Register")
        {
            Transaction.replace(R.id.main_content ,new RegisterFragment());
            Transaction.commit();
        }
        else
        if (sSelectedItem== "Login")
        {
            Transaction.replace(R.id.main_content,new LoginFragment());
            Transaction.commit();
        }
        else
        if (sSelectedItem== "RequestForm")
        {
            RequestFoodStep1Fragment ObjRequestFoodStep1Fragment = new RequestFoodStep1Fragment();
            Bundle ObjBundle = new Bundle();
            ObjBundle.putInt("FoodId",Integer.parseInt(args[0]));
            ObjRequestFoodStep1Fragment.setArguments(ObjBundle);
            Transaction.replace(R.id.main_content,ObjRequestFoodStep1Fragment);
            Transaction.commit();
        }
        else
        if (sSelectedItem.equals( p_ItemsList.get(0).getName()))
        {
            Transaction.replace(R.id.main_content ,new ProfileFragment());

            Transaction.commit();
        }
        else if (sSelectedItem.equals( p_ItemsList.get(1).getName()))
        {
            Transaction.replace(R.id.main_content ,new SettingsFragment());
            Transaction.commit();
        }
        else if (sSelectedItem.equals(p_ItemsList.get(5).getName()) )  // Add Foods and Drinks
        {
            Transaction.replace(R.id.main_content,new AddFoodsAndDrinksFragment());
            Transaction.commit();

        }
    }

    public static String GetResourceName(Context p_Context, int p_iResourceName,int p_iLanguage)
    {
        Configuration ObjConfiguration = p_Context.getResources().getConfiguration();
        if (p_iLanguage == 1) // Arabic
            ObjConfiguration.setLocale(new Locale("ar"));
        else if (p_iLanguage == 2) // English
            ObjConfiguration.setLocale(new Locale("en"));
        Resources ObjResources = new Resources(p_Context.getAssets(), p_Context.getResources().getDisplayMetrics(),ObjConfiguration);

        return ObjResources.getString(p_iResourceName);
    }

    public static String[] GetResourceArrayName(Context p_Context, int p_iResourceName,int p_iLanguage)
    {
        Configuration ObjConfiguration = p_Context.getResources().getConfiguration();
        if (p_iLanguage == 1) // Arabic
            ObjConfiguration.setLocale(new Locale("ar"));
        else if (p_iLanguage == 2) // English
            ObjConfiguration.setLocale(new Locale("en"));
        Resources ObjResources = new Resources(p_Context.getAssets(), p_Context.getResources().getDisplayMetrics(),ObjConfiguration);

        String[] sArrayTemp = ObjResources.getStringArray(p_iResourceName);
        return sArrayTemp;
    }

}
