package com.tastyhomemade.tastyhomemade.Others;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.tastyhomemade.tastyhomemade.Business.MainMenuItem;
import com.tastyhomemade.tastyhomemade.Fragment.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.tastyhomemade.tastyhomemade.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by raed on 11/22/2016.
 */

public class Utils {
    public Utils() {

    }

    public void ShowActivity(Context p_context, List<MainMenuItem> p_ItemsList, String sSelectedItem, String... args) {
        FragmentManager Manager = ((AppCompatActivity) p_context).getSupportFragmentManager();

        Manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction Transaction = Manager.beginTransaction();

        if (sSelectedItem == "Main") {
            MainFragment ObjMainFragment = new MainFragment();
            Bundle ObjBundle = new Bundle();
            ObjBundle.putInt("CategoryId", Integer.parseInt(args[0]));
            ObjMainFragment.setArguments(ObjBundle);
            Transaction.replace(R.id.main_content, ObjMainFragment);
            Transaction.commit();
        } else if (sSelectedItem == "Register") {
            Transaction.replace(R.id.main_content, new RegisterFragment());
            Transaction.commit();
        } else if (sSelectedItem == "Login") {
            Transaction.replace(R.id.main_content, new LoginFragment());
            Transaction.commit();
        } else if (sSelectedItem == "RequestForm") {
            RequestFoodStep1Fragment ObjRequestFoodStep1Fragment = new RequestFoodStep1Fragment();
            Bundle ObjBundle = new Bundle();
            ObjBundle.putInt("FoodId", Integer.parseInt(args[0]));
            ObjRequestFoodStep1Fragment.setArguments(ObjBundle);
            Transaction.replace(R.id.main_content, ObjRequestFoodStep1Fragment);
            Transaction.commit();
        } else if (sSelectedItem == "RequestFormStep2") {
            RequestFoodStep2Fragment ObjRequestFoodStep2Fragment = new RequestFoodStep2Fragment();
            Bundle ObjBundle = new Bundle();
            ObjBundle.putInt("OrderId", Integer.parseInt(args[0]));
            ObjRequestFoodStep2Fragment.setArguments(ObjBundle);
            Transaction.replace(R.id.main_content, ObjRequestFoodStep2Fragment);
            Transaction.commit();
        } else if (sSelectedItem.equals(p_ItemsList.get(0).getName())) {
            Bundle ObjBundle = new Bundle();
            ObjBundle.putInt("UserId",new Settings(p_context).getUserId());
            ProfileFragment ObjProfileFragment = new ProfileFragment();
            ObjProfileFragment.setArguments(ObjBundle);
            Transaction.replace(R.id.main_content, ObjProfileFragment);

            Transaction.commit();
        } else if (sSelectedItem.equals(p_ItemsList.get(1).getName())) {
            Transaction.replace(R.id.main_content, new SettingsFragment());
            Transaction.commit();
        } else if (sSelectedItem.equals(p_ItemsList.get(5).getName()))  // Add Foods and Drinks
        {
            Transaction.replace(R.id.main_content, new AddFoodsAndDrinksFragment());
            Transaction.commit();

        }
    }

    public static void SetCurrentLanguage(Context p_Context, int p_iLanguage) {
        Configuration ObjConfiguration = p_Context.getResources().getConfiguration();
        if (p_iLanguage == 1) // Arabic
        {
            Locale.setDefault(new Locale("ar"));
            ObjConfiguration.setLocale(new Locale("ar"));
            ObjConfiguration.setLayoutDirection(new Locale("ar"));
        } else if (p_iLanguage == 2) // English
        {
            Locale.setDefault(new Locale("en"));
            ObjConfiguration.setLocale(new Locale("en"));
            ObjConfiguration.setLayoutDirection(new Locale("en"));
        }
        p_Context.getResources().updateConfiguration(ObjConfiguration, p_Context.getResources().getDisplayMetrics());

    }


    public static String GetResourceName(Context p_Context, int p_iResourceName, int p_iLanguage) {
        Configuration ObjConfiguration = p_Context.getResources().getConfiguration();
        if (p_iLanguage == 1) // Arabic
            ObjConfiguration.setLocale(new Locale("ar"));
        else if (p_iLanguage == 2) // English
            ObjConfiguration.setLocale(new Locale("en"));
        Resources ObjResources = new Resources(p_Context.getAssets(), p_Context.getResources().getDisplayMetrics(), ObjConfiguration);

        return ObjResources.getString(p_iResourceName);
    }

    public static String[] GetResourceArrayName(Context p_Context, int p_iResourceName, int p_iLanguage) {
        Configuration ObjConfiguration = p_Context.getResources().getConfiguration();
        if (p_iLanguage == 1) // Arabic
            ObjConfiguration.setLocale(new Locale("ar"));
        else if (p_iLanguage == 2) // English
            ObjConfiguration.setLocale(new Locale("en"));
        Resources ObjResources = new Resources(p_Context.getAssets(), p_Context.getResources().getDisplayMetrics(), ObjConfiguration);

        String[] sArrayTemp = ObjResources.getStringArray(p_iResourceName);
        return sArrayTemp;
    }

    public static String GetGoogleMapUrl(double p_Latitude, double p_Longitude) {
        return "https://maps.googleapis.com/maps/api/staticmap?center=" + p_Latitude + "," + p_Longitude + "&zoom=13&size=300x200&markers=color:red|label:|" + p_Latitude + "," + p_Longitude;
    }

    public static String GetGoogleMapAddress(double p_Latitude, double p_Longitude,int p_iLanguageId) {
        String sObjTemp = "";
        if (p_iLanguageId==1) // Arabic
            sObjTemp = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + p_Latitude + "," + p_Longitude + "&sensor=true&language=ar";
        else
            sObjTemp = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + p_Latitude + "," + p_Longitude + "&sensor=true";
        try {
            URL ObjUrl = new URL(sObjTemp);
            HttpURLConnection ObjUrlCon = (HttpURLConnection) ObjUrl.openConnection();
            ObjUrlCon.setRequestMethod("GET");
            ObjUrlCon.setRequestProperty("User-Agent","Mozilla/5.0");
            ObjUrlCon.getResponseCode();
            BufferedReader ObjReader = new BufferedReader(new InputStreamReader(ObjUrlCon.getInputStream()));
            StringBuffer ObjStringBuffer = new StringBuffer();
            String sTemp ="";
            while ((sTemp = ObjReader.readLine()) !=null)
            {
                ObjStringBuffer.append(sTemp);
            }
            ObjReader.close();

            sObjTemp = ObjStringBuffer.toString();

            JSONObject ObjJSONObject = new JSONObject(sObjTemp);
            JSONArray ObjArray = ObjJSONObject.getJSONArray("results");
            for (int i = 0; i < ObjArray.length(); i++) {
                if (ObjArray.getJSONObject(i).getString("formatted_address") != null) {
                    Iterator<String> ObjKeysList = ObjArray.getJSONObject(i).keys();

                    while (ObjKeysList.hasNext()) {
                        String sResult = ObjKeysList.next();
                        if ( sResult.equals("formatted_address"))
                        {

                            sResult =ObjArray.getJSONObject(i).getString("formatted_address");
                            return sResult;
                        }
                    }
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static String GetGoogleMapCity(double p_Latitude, double p_Longitude,int p_iLanguageId) {
        String sObjTemp = "";
        if (p_iLanguageId==1) // Arabic
            sObjTemp = "http://maps.googleapis.com/maps/api/geocode/xml?latlng=" + p_Latitude + "," + p_Longitude + "&sensor=true&language=ar";
        else
            sObjTemp = "http://maps.googleapis.com/maps/api/geocode/xml?latlng=" + p_Latitude + "," + p_Longitude + "&sensor=true";
        try {
//            URL ObjUrl = new URL(sObjTemp);
//            HttpURLConnection ObjUrlCon = (HttpURLConnection) ObjUrl.openConnection();
//            ObjUrlCon.setRequestMethod("GET");
//            ObjUrlCon.setRequestProperty("User-Agent","Mozilla/5.0");
//            ObjUrlCon.getResponseCode();
//            BufferedReader ObjReader = new BufferedReader(new InputStreamReader(ObjUrlCon.getInputStream()));
//            StringBuffer ObjStringBuffer = new StringBuffer();
//            String sTemp ="";
//            while ((sTemp = ObjReader.readLine()) !=null)
//            {
//                ObjStringBuffer.append(sTemp);
//            }
//            ObjReader.close();
//
//            sObjTemp = ObjStringBuffer.toString();

            DocumentBuilderFactory ObjFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder ObjBuilder = ObjFactory.newDocumentBuilder();
            Document  ObjDoc = ObjBuilder.parse(new URL(sObjTemp).openStream());

            ObjDoc.getDocumentElement().normalize();

            NodeList ObjNodeList =ObjDoc.getElementsByTagName("type");
            Node ObjMainNode= null;
            for (int i=0;i<ObjNodeList .getLength();i++)
            {
                if (ObjNodeList.item(i).getNodeType() == Node.ELEMENT_NODE &&  ObjNodeList.item(i).getTextContent() != null && ObjNodeList.item(i).getTextContent() .equals("postal_code")) {
                    ObjMainNode = ObjNodeList.item(i).getParentNode();
                    break;
                }
            }

            int FoundNodes=0;
            Node ObjTargetNode = null;
            if (ObjMainNode != null)
            {
                for (int i=0;i<ObjMainNode.getChildNodes().getLength(); i++)
                {
                    Node ObjNodeTemp = ObjMainNode.getChildNodes().item(i);
                    if (ObjNodeTemp.getNodeType() == Node.ELEMENT_NODE &&  ObjNodeTemp .getNodeName()=="address_component")
                    {
                        FoundNodes = 0;
                       for (int j=0;j< ObjNodeTemp.getChildNodes().getLength() ; j++)
                       {
                           if (FoundNodes == 2)
                           {
                               ObjTargetNode = ObjNodeTemp.getChildNodes().item(0);
                               break;
                           }

                           if (ObjNodeTemp.getChildNodes().item(j).getNodeName() == "administrative_area_level_1")
                               FoundNodes = FoundNodes +1;
                           else if (ObjNodeTemp.getChildNodes().item(j).getNodeName()== "political")
                               FoundNodes = FoundNodes +1;
                       }
                    }
                }
            }



            if (ObjTargetNode !=null)
                return ObjTargetNode.getTextContent();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }


    public class GoogleMapClass extends AsyncTask<Double,String,String>
    {
        Settings ObjSettings;
        public GoogleMapClass(Context p_Context)
        {
            ObjSettings = new Settings(p_Context);
        }

        @Override
        protected String doInBackground(Double... params) {

            String sResult = Utils.GetGoogleMapAddress(params[0],params[1],ObjSettings.getCurrentLanguageId());
            return sResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    public class GoogleMapClassCity extends AsyncTask<Double,String,String>
    {
        Settings ObjSettings;
        public GoogleMapClassCity(Context p_Context)
        {
            ObjSettings = new Settings(p_Context);
        }

        @Override
        protected String doInBackground(Double... params) {

            String sResult = Utils.GetGoogleMapCity(params[0],params[1],ObjSettings.getCurrentLanguageId());
            return sResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }



}
