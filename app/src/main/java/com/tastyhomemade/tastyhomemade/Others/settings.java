package com.tastyhomemade.tastyhomemade.Others;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Raed on 11/23/2016.
 */

public class Settings {

    private Context context;

    public Settings (Context p_context)
    {
        context = p_context;
    }

    public  int getUserId ()
    {
        SharedPreferences Preferences = ((AppCompatActivity)context).getPreferences(Context.MODE_PRIVATE);
        return Preferences.getInt("UserId",-1);
    }

    public  String getUserName ()
    {
        SharedPreferences Preferences = ((AppCompatActivity)context).getPreferences(Context.MODE_PRIVATE);
        return Preferences.getString("UserName","");
    }

    public  int getCurrentLanguageId ()
    {
        SharedPreferences Preferences = ((AppCompatActivity)context).getPreferences(Context.MODE_PRIVATE);
        return Preferences.getInt("LanguageId",1);
    }

    public  void setUserId (int p_iUserId)
    {
        SharedPreferences Preferences = ((AppCompatActivity)context).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Preferences.edit();
        editor.putInt("UserId",p_iUserId);
        editor.commit();
    }

    public  void setUserName (String p_sUserName)
    {
        SharedPreferences Preferences = ((AppCompatActivity)context).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Preferences.edit();
        editor.putString("UserName",p_sUserName);
        editor.commit();
    }

    public  void setCurrentLanguageId (int p_iLanguageId)
    {
        SharedPreferences Preferences = ((AppCompatActivity)context).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Preferences.edit();
        editor.putInt("LanguageId",p_iLanguageId);
        editor.commit();
    }
}
