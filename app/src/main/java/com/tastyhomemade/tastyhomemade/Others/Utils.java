package com.tastyhomemade.tastyhomemade.Others;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tastyhomemade.tastyhomemade.Business.MainMenuItem;
import com.tastyhomemade.tastyhomemade.Business.OnGetCity;
import com.tastyhomemade.tastyhomemade.Fragment.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.tastyhomemade.tastyhomemade.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
        TextView lblHeader  = (TextView)((AppCompatActivity) p_context).findViewById(R.id.lblHeader);

        Manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction Transaction = Manager.beginTransaction();

        if (sSelectedItem == "Main") {
            lblHeader.setText(Utils.GetResourceName(p_context,R.string.FoodsAndDrinks,new Settings(p_context).getCurrentLanguageId()));
            MainFragment ObjMainFragment = new MainFragment();
            Bundle ObjBundle = new Bundle();
            ObjBundle.putInt("CategoryId", Integer.parseInt(args[0]));
            ObjMainFragment.setArguments(ObjBundle);
            Transaction.replace(R.id.main_content, ObjMainFragment);
            Transaction.commit();
        } else if (sSelectedItem == "Register") {
            lblHeader.setText(Utils.GetResourceName(p_context,R.string.Register,new Settings(p_context).getCurrentLanguageId()));
            Transaction.replace(R.id.main_content, new RegisterFragment());
            Transaction.commit();
        } else if (sSelectedItem == "Login") {
            lblHeader.setText(Utils.GetResourceName(p_context,R.string.Login,new Settings(p_context).getCurrentLanguageId()));
            Transaction.replace(R.id.main_content, new LoginFragment());
            Transaction.commit();
        } else if (sSelectedItem == "RequestForm") {

            lblHeader.setText(Utils.GetResourceName(p_context,R.string.RequestFoodsorDrinks,new Settings(p_context).getCurrentLanguageId()));
            RequestFoodStep1Fragment ObjRequestFoodStep1Fragment = new RequestFoodStep1Fragment();
            Bundle ObjBundle = new Bundle();
            ObjBundle.putInt("FoodId", Integer.parseInt(args[0]));
            ObjRequestFoodStep1Fragment.setArguments(ObjBundle);
            Transaction.replace(R.id.main_content, ObjRequestFoodStep1Fragment);
            Transaction.commit();
        } else if (sSelectedItem == "RequestFormStep2") {
            final Context contextfinal = p_context;
            final TextView lblHeaderFinal = lblHeader;
            ((AppCompatActivity) p_context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lblHeaderFinal.setText(Utils.GetResourceName(contextfinal,R.string.RequestFoodsorDrinks,new Settings(contextfinal).getCurrentLanguageId()));
                }
            });

            RequestFoodStep2Fragment ObjRequestFoodStep2Fragment = new RequestFoodStep2Fragment();
            Bundle ObjBundle = new Bundle();
            ObjBundle.putInt("OrderId", Integer.parseInt(args[0]));
            ObjRequestFoodStep2Fragment.setArguments(ObjBundle);
            Transaction.replace(R.id.main_content, ObjRequestFoodStep2Fragment);
            Transaction.commit();
        }
        else if (sSelectedItem.equals(p_ItemsList.get(0).getName())) { // Profile
            lblHeader.setText(Utils.GetResourceName(p_context,R.string.Profile,new Settings(p_context).getCurrentLanguageId()));
            Bundle ObjBundle = new Bundle();
            ObjBundle.putInt("UserId", new Settings(p_context).getUserId());
            ProfileFragment ObjProfileFragment = new ProfileFragment();
            ObjProfileFragment.setArguments(ObjBundle);
            Transaction.replace(R.id.main_content, ObjProfileFragment);

            Transaction.commit();
        } else if (sSelectedItem.equals(p_ItemsList.get(1).getName())) { // Settings
            lblHeader.setText(Utils.GetResourceName(p_context,R.string.Settings,new Settings(p_context).getCurrentLanguageId()));
            Transaction.replace(R.id.main_content, new SettingsFragment());
            Transaction.commit();
        }
        else if (sSelectedItem.equals(p_ItemsList.get(4).getName())) {  // Orders Follow Up
            lblHeader.setText(Utils.GetResourceName(p_context,R.string.OrdersFollowup,new Settings(p_context).getCurrentLanguageId()));
            OrdersFollowUpFragment ObjOrdersFollowUpFragment = new OrdersFollowUpFragment();
            Bundle ObjBundle = new Bundle();
            ObjBundle.putInt("UserId", new Settings(p_context).getUserId());
            ObjOrdersFollowUpFragment.setArguments(ObjBundle);
            Transaction.replace(R.id.main_content, ObjOrdersFollowUpFragment);
            Transaction.commit();
        }
        else if (sSelectedItem.equals(p_ItemsList.get(6).getName())) {  // My Choices
            lblHeader.setText(Utils.GetResourceName(p_context,R.string.MyChoices,new Settings(p_context).getCurrentLanguageId()));
            MyChoicesFragment ObjMyChoicesFragment= new MyChoicesFragment();
            Transaction.replace(R.id.main_content, ObjMyChoicesFragment);
            Transaction.commit();
        }

        else if (sSelectedItem.equals(p_ItemsList.get(5).getName()))  // Add Foods and Drinks // Add Food And Drinks
        {
            lblHeader.setText(Utils.GetResourceName(p_context,R.string.AddFoodsAndDrinks,new Settings(p_context).getCurrentLanguageId()));
            Transaction.replace(R.id.main_content, new AddFoodsAndDrinksFragment());
            Transaction.commit();

        }
        else if (sSelectedItem.equals(p_ItemsList.get(7).getName()))  // Mostly Requested Item
        {
            lblHeader.setText(Utils.GetResourceName(p_context,R.string.MostlyRequested,new Settings(p_context).getCurrentLanguageId()));
            Transaction.replace(R.id.main_content, new MostlyRequestedFragment());
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

    public static String GetGoogleMapAddress(double p_Latitude, double p_Longitude, int p_iLanguageId) {
        String sObjTemp = "";
        if (p_iLanguageId == 1) // Arabic
            sObjTemp = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + p_Latitude + "," + p_Longitude + "&sensor=true&language=ar";
        else
            sObjTemp = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + p_Latitude + "," + p_Longitude + "&sensor=true";
        try {
            URL ObjUrl = new URL(sObjTemp);
            HttpURLConnection ObjUrlCon = (HttpURLConnection) ObjUrl.openConnection();
            ObjUrlCon.setRequestMethod("GET");
            ObjUrlCon.setRequestProperty("User-Agent", "Mozilla/5.0");
            ObjUrlCon.getResponseCode();
            BufferedReader ObjReader = new BufferedReader(new InputStreamReader(ObjUrlCon.getInputStream()));
            StringBuffer ObjStringBuffer = new StringBuffer();
            String sTemp = "";
            while ((sTemp = ObjReader.readLine()) != null) {
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
                        if (sResult.equals("formatted_address")) {

                            sResult = ObjArray.getJSONObject(i).getString("formatted_address");
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

    public static String GetGoogleMapCity(double p_Latitude, double p_Longitude, int p_iLanguageId) {
        String sObjTemp = "";
        if (p_iLanguageId == 1) // Arabic
            sObjTemp = "http://maps.googleapis.com/maps/api/geocode/xml?latlng=" + p_Latitude + "," + p_Longitude + "&sensor=true&language=ar";
        else
            sObjTemp = "http://maps.googleapis.com/maps/api/geocode/xml?latlng=" + p_Latitude + "," + p_Longitude + "&sensor=true";
        try {


            DocumentBuilderFactory ObjFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder ObjBuilder = ObjFactory.newDocumentBuilder();
            Document ObjDoc = ObjBuilder.parse(new URL(sObjTemp).openStream());

            ObjDoc.getDocumentElement().normalize();
            NodeList ObjNodeList = ObjDoc.getElementsByTagName("result");

            Node ObjNodeToSearch = null;
            int iItemFoundLocation =0;
            int iCurrentNode =0;
            for (int i=0;i<ObjNodeList.getLength();i++)
            {
                if (iItemFoundLocation == 2) {
                    iCurrentNode = i - 1;
                    break;
                }
                if (ObjNodeList.item(i).getNodeType() == Node.ELEMENT_NODE)
                {
                    iItemFoundLocation ++;
                }
            }
            ObjNodeToSearch = ObjNodeList.item(iCurrentNode);


            ObjNodeList = ((Element)ObjNodeToSearch).getElementsByTagName("address_component");

            String sCity ="";
            List<Node> ObjNodeListTemp = new ArrayList<Node>();
            for (int i = 0; i < ObjNodeList.getLength(); i++) {
                ObjNodeListTemp.clear();
                for (int j=0;j< ObjNodeList.item(i).getChildNodes().getLength() ; j++)
                {
                    if (ObjNodeList.item(i).getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE)
                    {
                        ObjNodeListTemp.add(ObjNodeList.item(i).getChildNodes().item(j));
                    }
                }

                if (ObjNodeListTemp.size() == 4 && ObjNodeListTemp.get(2).getTextContent() .equals("administrative_area_level_1") && ObjNodeListTemp.get(3).getTextContent() .equals("political") )
                {
                    sCity = ObjNodeListTemp.get(0).getTextContent();
                    break;
                }

            }
            return sCity;


        } catch (Exception ex) {
           ex.printStackTrace();
        }
        return "";
    }

    public class GoogleMapClass extends AsyncTask<Double, String, String> {
        Settings ObjSettings;
        String sAddress;

        public String getCurrentAddress() {
            return sAddress;
        }

        public GoogleMapClass(Context p_Context) {
            ObjSettings = new Settings(p_Context);
            sAddress ="";
        }

        @Override
        protected String doInBackground(Double... params) {

            sAddress = Utils.GetGoogleMapAddress(params[0], params[1], ObjSettings.getCurrentLanguageId());
            return sAddress;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    public class GoogleMapClassCity extends AsyncTask<Double, String, String> {
        Settings ObjSettings;
        String sCurrentCity;
        OnGetCity ObjOnGetCity;

        public String getsCurrentCity() {
            return sCurrentCity;
        }

        public GoogleMapClassCity(Context p_Context,OnGetCity p_ObjOnGetCity) {
            ObjSettings = new Settings(p_Context);
            sCurrentCity = "";
            ObjOnGetCity = p_ObjOnGetCity;
        }

        @Override
        protected String doInBackground(Double... params) {

            sCurrentCity = Utils.GetGoogleMapCity(params[0], params[1], ObjSettings.getCurrentLanguageId());
            return sCurrentCity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            ObjOnGetCity.GetCity(s);

        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}
