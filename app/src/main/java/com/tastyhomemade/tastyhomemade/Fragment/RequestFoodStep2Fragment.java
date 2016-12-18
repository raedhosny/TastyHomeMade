package com.tastyhomemade.tastyhomemade.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Business.Cities;
import com.tastyhomemade.tastyhomemade.Business.CitiesDB;
import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.Business.OnTaskCompleted;
import com.tastyhomemade.tastyhomemade.Business.Orders;
import com.tastyhomemade.tastyhomemade.Business.OrdersDB;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.Others.WaitDialog;
import com.tastyhomemade.tastyhomemade.R;
import com.tastyhomemade.tastyhomemade.Services.GPSTracker;
import com.tastyhomemade.tastyhomemade.Services.GPSTrackerBackground;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;


/**
 * Created by Raed on 12/8/2016.
 */

public class RequestFoodStep2Fragment extends Fragment implements View.OnClickListener {

    ImageView ImageFood;
    ImageView ImageDeliverable;
    RatingBar FoodRating;
    TextView lblPrice;
    TextView lblName;
    TextView lblTimeFromTo;
    TextView lblDescription;
    View Include_Tab_MakingAddress;
    View Include_Tab_ReceiveAddress;
    Button btnCurrentLocation;
    Button btnotherlocation;
    WebView webview_CurrentLocation_Tab1;
    WebView webview_CurrentLocation_Tab2;
    Button btnConfirmOrder;
    Settings ObjSettings;
    LinearLayout Linear_Tab1, Linear_Tab2, Linear_Tab2_1, Linear_Tab2_2;
    Spinner ddlDays;
    Spinner ddlMonths;
    Spinner ddlYears;

    Spinner ddlMinutes;
    Spinner ddlHours;
    Spinner ddlDayNight;

    ArrayAdapter<String> ObjAdapterDays;
    ArrayAdapter<String> ObjAdapterMonths;
    ArrayAdapter<String> ObjAdapterYears;

    Spinner ddlCity;
    List<Cities> ObjCitiesList;
    TextView lblAddress, lblAddressClient;
    int SelectedTab;
    int SelectedInnerTab;

    EditText txtDistrict, txtStreet, txtBuilding, txtAppartment, txtOtherDetails;

    int GPS_SETTINGS_REQUEST_CODE = 3;
    double iCurrentLatitude;
    double iCurrentLongtitude;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_requestfoodanddrink_step2, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageFood = (ImageView) view.findViewById(R.id.include_details_item).findViewById(R.id.ImageFood);
        ImageDeliverable = (ImageView) view.findViewById(R.id.include_details_item).findViewById(R.id.ImageDeliverable);
        FoodRating = (RatingBar) view.findViewById(R.id.include_details_item).findViewById(R.id.FoodRating);
        lblPrice = (TextView) view.findViewById(R.id.include_details_item).findViewById(R.id.lblPrice);
        lblName = (TextView) view.findViewById(R.id.include_details_item).findViewById(R.id.lblName);
        lblTimeFromTo = (TextView) view.findViewById(R.id.include_details_item).findViewById(R.id.lblTimeFromTo);
        lblDescription = (TextView) view.findViewById(R.id.include_details_item).findViewById(R.id.lblDescription);

        Include_Tab_MakingAddress = view.findViewById(R.id.Include_Tab_MakingAddress);
        Include_Tab_ReceiveAddress = view.findViewById(R.id.Include_Tab_ReceiveAddress);

        btnCurrentLocation = (Button) view.findViewById(R.id.btnCurrentLocation);
        btnotherlocation = (Button) view.findViewById(R.id.btnotherlocation);
        btnCurrentLocation.setOnClickListener(this);
        btnotherlocation.setOnClickListener(this);

        webview_CurrentLocation_Tab1 = (WebView) view.findViewById(R.id.webview_CurrentLocation_Tab1);
        webview_CurrentLocation_Tab2 = (WebView) view.findViewById(R.id.webview_CurrentLocation_Tab2);

        btnConfirmOrder = (Button) view.findViewById(R.id.btnConfirmOrder);
        btnConfirmOrder.setOnClickListener(this);

        Linear_Tab1 = (LinearLayout) view.findViewById(R.id.Linear_Tab1);
        Linear_Tab2 = (LinearLayout) view.findViewById(R.id.Linear_Tab2);
        Linear_Tab2_1 = (LinearLayout) view.findViewById(R.id.Linear_Tab2_1);
        Linear_Tab2_2 = (LinearLayout) view.findViewById(R.id.Linear_Tab2_2);

        ((TextView) Include_Tab_MakingAddress.findViewById(R.id.txtTab)).setText("موقع تجهيز الطلب");
        ((TextView) Include_Tab_ReceiveAddress.findViewById(R.id.txtTab)).setText("عنوان الاستلام");

        ddlDays = (Spinner) view.findViewById(R.id.Include_Calender).findViewById(R.id.Include_Spinner_Days).findViewById(R.id.Spinner_Item);
        ddlMonths = (Spinner) view.findViewById(R.id.Include_Calender).findViewById(R.id.Include_Spinner_Months).findViewById(R.id.Spinner_Item);
        ddlYears = (Spinner) view.findViewById(R.id.Include_Calender).findViewById(R.id.Include_Spinner_Years).findViewById(R.id.Spinner_Item);

        ddlMinutes = (Spinner) view.findViewById(R.id.Include_Time).findViewById(R.id.Include_Spinner_Minutes).findViewById(R.id.Spinner_Item);
        ddlHours = (Spinner) view.findViewById(R.id.Include_Time).findViewById(R.id.Include_Spinner_Hours).findViewById(R.id.Spinner_Item);
        ddlDayNight = (Spinner) view.findViewById(R.id.Include_Time).findViewById(R.id.Include_Spinner_DayNight).findViewById(R.id.Spinner_Item);

        ObjSettings = new Settings(getContext());

        Include_Tab_MakingAddress.setOnClickListener(this);
        Include_Tab_ReceiveAddress.setOnClickListener(this);

        ddlCity = (Spinner) view.findViewById(R.id.Include_ddlCity).findViewById(R.id.ddlSpinner);
        lblAddress = (TextView) view.findViewById(R.id.lblAddress);
        lblAddressClient = (TextView) view.findViewById(R.id.lblAddressClient);

        txtDistrict = (EditText) view.findViewById(R.id.txtDistrict);
        txtStreet = (EditText) view.findViewById(R.id.txtStreet);
        txtBuilding = (EditText) view.findViewById(R.id.txtBuilding);
        txtAppartment = (EditText) view.findViewById(R.id.txtAppartment);
        txtOtherDetails = (EditText) view.findViewById(R.id.txtOtherDetails);

        SelectedTab = 1;
        SelectedInnerTab = 1;

        FillCalender();
        FillTimer();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                FillData();
            }
        });
        t.start();

    }

    private void FillCalender() {
        Calendar ctrCalendar = Calendar.getInstance();

        List<String> ObjListDays = new ArrayList<String>();
        List<String> ObjListMonths = new ArrayList<String>();
        List<String> ObjListYears = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            ObjListMonths.add(String.valueOf(i));
        }

        ObjAdapterMonths = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Arrays.copyOf(ObjListMonths.toArray(), ObjListMonths.toArray().length, String[].class));
        ddlMonths.setAdapter(ObjAdapterMonths);
        ddlMonths.setSelection(ctrCalendar.get(Calendar.MONTH));

        ctrCalendar.add(Calendar.MONTH, 1);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date ObjDateTemp = new Date();
        try {
            ObjDateTemp = sdf.parse(String.valueOf(ctrCalendar.get(Calendar.YEAR)) + "-" + String.valueOf(ctrCalendar.get(Calendar.MONTH) + 1) + "-1");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ctrCalendar.setTime(ObjDateTemp);

        ctrCalendar.add(Calendar.DAY_OF_MONTH, -1);

        int iMaxDay = ctrCalendar.get(Calendar.DAY_OF_MONTH);


        for (int i = 1; i <= iMaxDay; i++) {
            ObjListDays.add(String.valueOf(i));
        }

        ObjAdapterDays = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Arrays.copyOf(ObjListDays.toArray(), ObjListDays.toArray().length, String[].class));
        ddlDays.setAdapter(ObjAdapterDays);
        ctrCalendar = Calendar.getInstance();
        int iPosition = ObjAdapterDays.getPosition(String.valueOf(ctrCalendar.get(Calendar.DAY_OF_MONTH)));
        ddlDays.setSelection(iPosition);

        int iStartYear = ctrCalendar.get(Calendar.YEAR);
        int iEndYear = iStartYear + 1;

        for (int i = iStartYear; i <= iEndYear; i++) {
            ObjListYears.add(String.valueOf(i));
        }


        ObjAdapterYears = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Arrays.copyOf(ObjListYears.toArray(), ObjListYears.toArray().length, String[].class));
        ddlYears.setAdapter(ObjAdapterYears);
        iPosition = ObjAdapterYears.getPosition(String.valueOf(ctrCalendar.get(Calendar.YEAR)));
        ddlYears.setSelection(iPosition);
    }

    private void FillTimer() {

        final ArrayAdapter<String> ObjAdapterMinutes = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Utils.GetResourceArrayName(getContext(), R.array.minutes, new Settings(getContext()).getCurrentLanguageId()));
        final ArrayAdapter<String> ObjAdapterHours = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Utils.GetResourceArrayName(getContext(), R.array.hours, new Settings(getContext()).getCurrentLanguageId()));
        final ArrayAdapter<String> ObjAdapterDayNight = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, Utils.GetResourceArrayName(getContext(), R.array.DayNight, new Settings(getContext()).getCurrentLanguageId()));

        ddlMinutes.setAdapter(ObjAdapterMinutes);
        ddlHours.setAdapter(ObjAdapterHours);
        ddlDayNight.setAdapter(ObjAdapterDayNight);
    }

    private void FillData() {
        Bundle ObjBundle = getArguments();
        int iOrderId = ObjBundle.getInt("OrderId");
        final Orders ObjOrder = new OrdersDB().Select(iOrderId);
        final Foods ObjFood = new FoodsDB().Select(ObjOrder.getFood_Id(), ObjSettings.getCurrentLanguageId());
        final User ObjUser = new UserDB().Select(ObjFood.getUserId());
        // Fill Cities
        ObjCitiesList = new CitiesDB().SelectAll(ObjSettings.getCurrentLanguageId());

        byte[] ObjPhotosList = Base64.decode(ObjFood.getPhoto(), Base64.DEFAULT);
        final Bitmap ObjBitmapTemp = BitmapFactory.decodeByteArray(ObjPhotosList, 0, ObjPhotosList.length);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ImageFood.setImageBitmap(ObjBitmapTemp);

                if (ObjUser.isHaveDelivary())
                    ImageDeliverable.setVisibility(View.VISIBLE);
                else
                    ImageDeliverable.setVisibility(View.GONE);


                lblPrice.setText(String.valueOf(ObjFood.getPrice()));

                List<String> ObjCitiesListTemp = new ArrayList<String>();
                for (Cities ObjCity : ObjCitiesList) {
                    ObjCitiesListTemp.add(ObjCity.getName());
                }
                ArrayAdapter<String> ObjAdapterCities = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, ObjCitiesListTemp);
                ddlCity.setAdapter(ObjAdapterCities);


                webview_CurrentLocation_Tab1.setWebViewClient(new WebViewClient());
                webview_CurrentLocation_Tab1.getSettings().setJavaScriptEnabled(true);
                webview_CurrentLocation_Tab1.loadUrl(Utils.GetGoogleMapUrl(ObjUser.getCurrentLocation_Latitude(), ObjUser.getCurrentLocation_Longitude()));
                Utils.GoogleMapClass ObjGoogleMapClass = new Utils().new GoogleMapClass(getContext());
                try {
                    String sAddress = ObjGoogleMapClass.execute(ObjUser.getCurrentLocation_Latitude(), ObjUser.getCurrentLocation_Longitude()).get();

                    lblAddress.setText(sAddress);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == Include_Tab_MakingAddress) {
            Linear_Tab1.setVisibility(View.VISIBLE);
            Linear_Tab2.setVisibility(View.GONE);


            ((ImageView) Include_Tab_MakingAddress.findViewById(R.id.imgTabLeft)).setImageResource(R.drawable.tab_enabled_left);
            ((ImageView) Include_Tab_MakingAddress.findViewById(R.id.imgTabCenter)).setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.tab_enabled_center)));
            ((ImageView) Include_Tab_MakingAddress.findViewById(R.id.imgTabRight)).setImageResource(R.drawable.tab_enabled_right);

            ((ImageView) Include_Tab_ReceiveAddress.findViewById(R.id.imgTabLeft)).setImageResource(R.drawable.tab_disabled_left);
            ((ImageView) Include_Tab_ReceiveAddress.findViewById(R.id.imgTabCenter)).setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.tab_disabled_center)));
            ((ImageView) Include_Tab_ReceiveAddress.findViewById(R.id.imgTabRight)).setImageResource(R.drawable.tab_disabled_right);

            Bundle ObjBundle = getArguments();
            final int iOrderId = ObjBundle.getInt("OrderId");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {


                    Orders ObjOrder = new OrdersDB().Select(iOrderId);
                    Foods ObjFood = new FoodsDB().Select(ObjOrder.getFood_Id(), ObjSettings.getCurrentLanguageId());
                    User ObjUser = new UserDB().Select(ObjFood.getUserId());

                    webview_CurrentLocation_Tab1.setWebViewClient(new WebViewClient());
                    webview_CurrentLocation_Tab1.getSettings().setJavaScriptEnabled(true);
                    webview_CurrentLocation_Tab1.loadUrl(Utils.GetGoogleMapUrl(ObjUser.getCurrentLocation_Latitude(), ObjUser.getCurrentLocation_Longitude()));
                    Utils.GoogleMapClass ObjGoogleMapClass = new Utils().new GoogleMapClass(getContext());
                    try {
                        ObjGoogleMapClass.execute(ObjUser.getCurrentLocation_Latitude(), ObjUser.getCurrentLocation_Longitude());
                        String sAddress = ObjGoogleMapClass.getCurrentAddress();

                        lblAddress.setText(sAddress);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });

            SelectedTab = 1;

        } else if (v == Include_Tab_ReceiveAddress) {

            //Location ObjLocation = ObjGPSTracker.GetLocation();


            Linear_Tab1.setVisibility(View.GONE);
            Linear_Tab2.setVisibility(View.VISIBLE);

            ((ImageView) Include_Tab_MakingAddress.findViewById(R.id.imgTabLeft)).setImageResource(R.drawable.tab_disabled_left);
            ((ImageView) Include_Tab_MakingAddress.findViewById(R.id.imgTabCenter)).setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.tab_disabled_center)));
            ((ImageView) Include_Tab_MakingAddress.findViewById(R.id.imgTabRight)).setImageResource(R.drawable.tab_disabled_right);

            ((ImageView) Include_Tab_ReceiveAddress.findViewById(R.id.imgTabLeft)).setImageResource(R.drawable.tab_enabled_left);
            ((ImageView) Include_Tab_ReceiveAddress.findViewById(R.id.imgTabCenter)).setBackground(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.tab_enabled_center)));
            ((ImageView) Include_Tab_ReceiveAddress.findViewById(R.id.imgTabRight)).setImageResource(R.drawable.tab_enabled_right);

            OnTaskCompleted ObjOnTaskCompleted = new OnTaskCompleted() {
                @Override
                public void OnTaskCompleted(List<Double> Results) {
                    if (Results.size() > 0) {
                        iCurrentLatitude = Results.get(0);
                        iCurrentLongtitude = Results.get(1);
                    }
                }
            };

            GPSTrackerBackground ObjGPSTrackerBackground = new GPSTrackerBackground(this, ObjOnTaskCompleted);
            ObjGPSTrackerBackground.execute();
            GPSTracker ObjGPSTracker = ObjGPSTrackerBackground.getGPSTracker();


            if (ObjGPSTracker.getCanGetLocation()) {

                webview_CurrentLocation_Tab2.setWebViewClient(new WebViewClient());
                webview_CurrentLocation_Tab2.getSettings().setJavaScriptEnabled(true);
                webview_CurrentLocation_Tab2.loadUrl(Utils.GetGoogleMapUrl(ObjGPSTracker.getLatitude(), ObjGPSTracker.getlongtitude()));

                Utils.GoogleMapClass ObjGoogleMapClass = new Utils().new GoogleMapClass(getContext());
                try {
                    final String sAddress = ObjGoogleMapClass.execute(ObjGPSTracker.getLatitude(), ObjGPSTracker.getlongtitude()).get();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblAddressClient.setText(sAddress);
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                ObjGPSTracker.StopUsingGPS();

                ObjGPSTracker.ShowSettingsAlert();
            }
            SelectedTab = 2;


        } else if (v == btnConfirmOrder) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (SelectedTab == 1) { // If client brings order by himself from food maker
                        Bundle ObjBundle = getArguments();
                        int iOrderId = ObjBundle.getInt("OrderId");
                        Orders ObjOrder = new OrdersDB().Select(iOrderId);
//                        Foods ObjFood = new FoodsDB().Select(ObjOrder.getFood_Id(), ObjSettings.getCurrentLanguageId());
//                        User ObjUser = new UserDB().Select(ObjFood.getUserId());
                        ObjOrder.setOrderAddress(lblAddress.getText().toString());
                        ObjOrder.setShippingToClient(false);
                        ObjOrder.setCompleteOrder(true);
                        new OrdersDB().InsertUpdate(ObjOrder);


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.OrderHasBeenSubmitted, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                                new Utils().ShowActivity(getContext(), null, "Main", "-1");
                            }
                        });
                    } else if (SelectedTab == 2) // if client request to receive order at his current location
                    {
                        if (SelectedInnerTab == 1) {
                            Bundle ObjBundle = getArguments();
                            int iOrderId = ObjBundle.getInt("OrderId");
                            Orders ObjOrder = new OrdersDB().Select(iOrderId);
//                        Foods ObjFood = new FoodsDB().Select(ObjOrder.getFood_Id(), ObjSettings.getCurrentLanguageId());
//                        User ObjUser = new UserDB().Select(ObjFood.getUserId());
                            ObjOrder.setOrderAddress(lblAddressClient.getText().toString());
                            ObjOrder.setShippingToClient(true);
                            ObjOrder.setCompleteOrder(true);
                            new OrdersDB().InsertUpdate(ObjOrder);

                            new OrdersDB().InsertUpdate(ObjOrder);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.OrderHasBeenSubmitted, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                                    new Utils().ShowActivity(getContext(), null, "Main", "-1");
                                }
                            });

                        } else if (SelectedInnerTab == 2) {
                            if (ddlCity.getSelectedItemPosition() == 0) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.Error_PleaseSelectCity, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                                    }
                                });
                                return;
                            }


                            Bundle ObjBundle = getArguments();
                            int iOrderId = ObjBundle.getInt("OrderId");
                            Orders ObjOrder = new OrdersDB().Select(iOrderId);
                            ObjOrder.setShippingToClient(true);
                            ObjOrder.setShipping_Latitude(-1);
                            ObjOrder.setShipping_Longitude(-1);
                            ObjOrder.setShippingCountryId(ObjCitiesList.get(ddlCity.getSelectedItemPosition()).getId());
                            ObjOrder.setShippingDistrict(txtDistrict.getText().toString().trim());
                            ObjOrder.setShippingStreet(txtStreet.getText().toString().trim());
                            ObjOrder.setShippingBuilding(txtBuilding.getText().toString().trim());
                            ObjOrder.setShippingApartment(txtAppartment.getText().toString().trim());
                            ObjOrder.setShippingOtherDetails(txtOtherDetails.getText().toString().trim());
                            Calendar ObjCalendar = Calendar.getInstance();
                            ObjCalendar.set(Calendar.YEAR, Integer.parseInt(ddlYears.getSelectedItem().toString()));
                            ObjCalendar.set(Calendar.MONTH, Integer.parseInt(ddlMonths.getSelectedItem().toString()) - 1);
                            ObjCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ddlDays.getSelectedItem().toString()));
                            ObjCalendar.set(Calendar.MINUTE, Integer.parseInt(ddlMinutes.getSelectedItem().toString()));
                            if (ddlDayNight.getSelectedItemPosition() == 1)
                                ObjCalendar.set(Calendar.HOUR, Integer.parseInt(ddlHours.getSelectedItem().toString()) + 12);
                            else
                                ObjCalendar.set(Calendar.HOUR, Integer.parseInt(ddlHours.getSelectedItem().toString()));

                            ObjOrder.setShippingDeliveryDate(new java.sql.Date(ObjCalendar.get(Calendar.YEAR), ObjCalendar.get(Calendar.MONTH), ObjCalendar.get(Calendar.DAY_OF_MONTH)));
                            ObjOrder.setCompleteOrder(true);
                            new OrdersDB().InsertUpdate(ObjOrder);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), Utils.GetResourceName(getContext(), R.string.OrderHasBeenSubmitted, ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                                    new Utils().ShowActivity(getContext(), null, "Main", "-1");
                                }
                            });

                        }
                    }
                }
            });
            t.start();

        } else if (v == btnCurrentLocation) {
            SelectedInnerTab = 1;
            Bitmap ObjBitMapOn = BitmapFactory.decodeResource(getResources(), R.drawable.settings_on);
            Bitmap ObjBitMapOff = BitmapFactory.decodeResource(getResources(), R.drawable.settings_off);
            btnCurrentLocation.setBackground(new BitmapDrawable(ObjBitMapOn));
            btnotherlocation.setBackground(new BitmapDrawable(ObjBitMapOff));
            Linear_Tab2_1.setVisibility(View.VISIBLE);
            Linear_Tab2_2.setVisibility(View.GONE);

        } else if (v == btnotherlocation) {
            SelectedInnerTab = 2;
            Bitmap ObjBitMapOn = BitmapFactory.decodeResource(getResources(), R.drawable.settings_on);
            Bitmap ObjBitMapOff = BitmapFactory.decodeResource(getResources(), R.drawable.settings_off);
            btnCurrentLocation.setBackground(new BitmapDrawable(ObjBitMapOff));
            btnotherlocation.setBackground(new BitmapDrawable(ObjBitMapOn));
            Linear_Tab2_1.setVisibility(View.GONE);
            Linear_Tab2_2.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_SETTINGS_REQUEST_CODE) {
            try {

                final GPSTracker ObjGPSTracker = new GPSTracker(this);
                final WaitDialog ObjWaitDialog = new WaitDialog(getActivity());

                ObjWaitDialog.ShowDialog();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {


                        if (ObjGPSTracker.getCanGetLocation()) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    webview_CurrentLocation_Tab2.setWebViewClient(new WebViewClient());
                                    webview_CurrentLocation_Tab2.getSettings().setJavaScriptEnabled(true);
                                    webview_CurrentLocation_Tab2.loadUrl(Utils.GetGoogleMapUrl(ObjGPSTracker.getLatitude(), ObjGPSTracker.getlongtitude()));
                                }
                            });

                            Utils.GoogleMapClass ObjGoogleMapClass = new Utils().new GoogleMapClass(getContext());
                            try {
                                final String sAddress = ObjGoogleMapClass.execute(ObjGPSTracker.getLatitude(), ObjGPSTracker.getlongtitude()).get();

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lblAddressClient.setText(sAddress);
                                    }
                                });

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        } else {
                            ObjGPSTracker.StopUsingGPS();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ObjGPSTracker.ShowSettingsAlert();
                                }
                            });


                        }
                        ObjWaitDialog.HideDialog();
                    }
                });

                t.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


}
