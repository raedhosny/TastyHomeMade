package com.tastyhomemade.tastyhomemade.Adapter;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Business.Cities;
import com.tastyhomemade.tastyhomemade.Business.CitiesDB;
import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.Business.Orders;
import com.tastyhomemade.tastyhomemade.Business.Orders_Actions;
import com.tastyhomemade.tastyhomemade.Business.Orders_ActionsDB;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by raed on 12/20/2016.
 */

public class CustomersOrdersAdapter extends BaseAdapter {

    Context context;
    List<Orders> ObjOrdersList;
    Settings ObjSettings;
    SimpleDateFormat sdf;


    public CustomersOrdersAdapter(List<Orders> p_ObjOrdersList, Context p_context) {
        ObjOrdersList = p_ObjOrdersList;
        context = p_context;
        ObjSettings = new Settings(p_context);

    }

    @Override
    public int getCount() {
        return ObjOrdersList.size();
    }

    @Override
    public Orders getItem(int position) {
        return ObjOrdersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ObjOrdersList.get(position).getId();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {


        View v = View.inflate(context, R.layout.customerorder_listview_item, null);
        final RatingBar ctlRating = (RatingBar) v.findViewById(R.id.ctlRating);
        final ImageView ImageItem = (ImageView) v.findViewById(R.id.ImageItem);
        final TextView lblFoodName = (TextView) v.findViewById(R.id.lblFoodName);
        final TextView lblNumberOfOrders = (TextView) v.findViewById(R.id.lblNumberOfOrders);
        final TextView lblRequestDate = (TextView) v.findViewById(R.id.lblRequestDate);
        final TextView lblRequestTime = (TextView) v.findViewById(R.id.lblRequestTime);
        final TextView lblDeliveryDate = (TextView) v.findViewById(R.id.lblDeliveryDate);
        final TextView lblDeliveryTime = (TextView) v.findViewById(R.id.lblDeliveryTime);
        final TextView lblCustomerName = (TextView) v.findViewById(R.id.lblCustomerName);
        final TextView lblMobile = (TextView) v.findViewById(R.id.lblMobile);
        final TextView lblAddress = (TextView) v.findViewById(R.id.lblAddress);
        final WebView webview_Address = (WebView) v.findViewById(R.id.webview_Address);
        webview_Address.setWebViewClient(new WebViewClient());
        webview_Address.getSettings().setJavaScriptEnabled(true);


        final Button btnSendOrder = (Button) v.findViewById(R.id.btnSendOrder);
        final Button btnOrderReceived = (Button) v.findViewById(R.id.btnOrderReceived);
        final Button btnOrderRejected = (Button) v.findViewById(R.id.btnOrderRejected);

        btnSendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {


                        int iOrderId = ObjOrdersList.get(position).getId();
                        int iActionId = 2;
                        Orders_Actions ObjOrderAction = new Orders_Actions();
                        ObjOrderAction.setOrderId(iOrderId);
                        ObjOrderAction.setActionId(iActionId);
                        Calendar ObjCalendar = Calendar.getInstance();
                        ObjOrderAction.setActionDate(new java.sql.Timestamp(ObjCalendar.getTimeInMillis()));
                        new Orders_ActionsDB().InsertUpdate(ObjOrderAction);

                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //notifyDataSetChanged();
                                btnSendOrder.setEnabled(false);
                                btnSendOrder.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnSendOrder.setTextColor(Color.parseColor("#929292"));
                                btnOrderReceived.setEnabled(true);
                                btnOrderReceived.setBackgroundResource(R.drawable.button_style3_enabled);
                                btnOrderReceived.setTextColor(Color.parseColor("#ffffff"));
                                btnOrderRejected.setEnabled(true);
                                btnOrderRejected.setBackgroundResource(R.drawable.button_style3_enabled);
                                btnOrderRejected.setTextColor(Color.parseColor("#ffffff"));
                                Toast.makeText(context, Utils.GetResourceName(context,R.string.OrderHasSentToClient,ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                t.start();
            }
        });

        btnOrderReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int iOrderId = ObjOrdersList.get(position).getId();
                        int iActionId = 3;
                        Orders_Actions ObjOrderAction = new Orders_Actions();
                        ObjOrderAction.setOrderId(iOrderId);
                        ObjOrderAction.setActionId(iActionId);
                        Calendar ObjCalendar = Calendar.getInstance();
                        ObjOrderAction.setActionDate(new java.sql.Timestamp(ObjCalendar.getTimeInMillis()));
                        new Orders_ActionsDB().InsertUpdate(ObjOrderAction);


                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //notifyDataSetChanged();
                                btnSendOrder.setEnabled(false);
                                btnSendOrder.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnSendOrder.setTextColor(Color.parseColor("#929292"));
                                btnOrderReceived.setEnabled(true);
                                btnOrderReceived.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnOrderReceived.setTextColor(Color.parseColor("#929292"));
                                btnOrderRejected.setEnabled(true);
                                btnOrderRejected.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnOrderRejected.setTextColor(Color.parseColor("#929292"));
                                Toast.makeText(context, Utils.GetResourceName(context,R.string.OrderHasBeenReceived,ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });
                t.start();
            }
        });

        btnOrderRejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int iOrderId = ObjOrdersList.get(position).getId();
                        int iActionId = 4;
                        Orders_Actions ObjOrderAction = new Orders_Actions();
                        ObjOrderAction.setOrderId(iOrderId);
                        ObjOrderAction.setActionId(iActionId);
                        Calendar ObjCalendar = Calendar.getInstance();
                        ObjOrderAction.setActionDate(new java.sql.Timestamp(ObjCalendar.getTimeInMillis()));
                        new Orders_ActionsDB().InsertUpdate(ObjOrderAction);
                        notifyDataSetChanged();

                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnSendOrder.setEnabled(false);
                                btnSendOrder.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnSendOrder.setTextColor(Color.parseColor("#929292"));
                                btnOrderReceived.setEnabled(true);
                                btnOrderReceived.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnOrderReceived.setTextColor(Color.parseColor("#929292"));
                                btnOrderRejected.setEnabled(true);
                                btnOrderRejected.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnOrderRejected.setTextColor(Color.parseColor("#929292"));
                                Toast.makeText(context, Utils.GetResourceName(context,R.string.OrderHasBeenRejected,ObjSettings.getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                t.start();
            }
        });


        /// Do Rating
        //////////////////////////
        //////////////////////////////
        ///////////////////////////////

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                final Foods ObjFoods = new FoodsDB().Select(ObjOrdersList.get(position).getFood_Id(), ObjSettings.getCurrentLanguageId());
                byte[] Photo = Base64.decode(ObjFoods.getPhoto(), Base64.DEFAULT);
                final Bitmap ObjBitmap = BitmapFactory.decodeByteArray(Photo, 0, Photo.length);

                // Food Image
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageItem.setImageBitmap(ObjBitmap);
                    }
                });

                // Food Name
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblFoodName.setText(ObjFoods.getName());
                    }
                });

                // Number of Orders
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblNumberOfOrders.setText(String.valueOf(ObjOrdersList.get(position).getNumberOfOrders()));
                    }
                });

                //

                // Request Date

                if (ObjSettings.getCurrentLanguageId() == 1) // Arabic
                    sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("ar"));
                else
                    sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("en"));

                final SimpleDateFormat sdfFinal1 = sdf;

                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblRequestDate.setText(sdfFinal1.format(ObjOrdersList.get(position).getRequestDate()));
                    }
                });


                // Request Time
                if (ObjSettings.getCurrentLanguageId() == 1) // Arabic
                    sdf = new SimpleDateFormat("h:mm a", new Locale("ar"));
                else
                    sdf = new SimpleDateFormat("h:mm a");


                final SimpleDateFormat sdfFinal2 = sdf;
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblRequestTime.setText(sdfFinal2.format(ObjOrdersList.get(position).getRequestDate()));
                    }
                });

                // Setting Delivery Date and Time
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnSendOrder.setEnabled(true);
                        btnSendOrder.setBackgroundResource(R.drawable.button_style3_enabled);
                        btnSendOrder.setTextColor(Color.parseColor("#ffffff"));
                        btnOrderReceived.setEnabled(false);
                        btnOrderReceived.setTextColor(Color.parseColor("#929292"));
                        btnOrderReceived.setBackgroundResource(R.drawable.button_style3_disabled);
                        btnOrderRejected.setEnabled(false);
                        btnOrderRejected.setBackgroundResource(R.drawable.button_style3_disabled);
                        btnOrderRejected.setTextColor(Color.parseColor("#929292"));
                    }
                });


                List<Orders_Actions> ObjOrdersActionsList = new Orders_ActionsDB().SelectByOrderId(ObjOrdersList.get(position).getId(),1);

                for (int i = 0; i < ObjOrdersActionsList.size(); i++) {
                    if (ObjOrdersActionsList.get(i).getActionId() == 1) { // Order Request by customer
                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnSendOrder.setEnabled(true);
                                btnSendOrder.setBackgroundResource(R.drawable.button_style3_enabled);
                                btnSendOrder.setTextColor(Color.parseColor("#929292"));
                                btnOrderReceived.setEnabled(true);
                                btnOrderReceived.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnOrderReceived.setTextColor(Color.parseColor("#ffffff"));
                                btnOrderRejected.setEnabled(true);
                                btnOrderRejected.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnOrderRejected.setTextColor(Color.parseColor("#ffffff"));
                            }
                        });
                    }

                    if (ObjOrdersActionsList.get(i).getActionId() == 2) { // Order sent by foodmaker
                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                btnSendOrder.setEnabled(false);
                                btnSendOrder.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnSendOrder.setTextColor(Color.parseColor("#929292"));
                                btnOrderReceived.setEnabled(true);
                                btnOrderReceived.setBackgroundResource(R.drawable.button_style3_enabled);
                                btnOrderReceived.setTextColor(Color.parseColor("#ffffff"));
                                btnOrderRejected.setEnabled(true);
                                btnOrderRejected.setBackgroundResource(R.drawable.button_style3_enabled);
                                btnOrderRejected.setTextColor(Color.parseColor("#ffffff"));
                            }
                        });
                    }

                    if (ObjOrdersActionsList.get(i).getActionId() == 3 || ObjOrdersActionsList.get(i).getActionId() == 4) {
                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnSendOrder.setEnabled(false);
                                btnSendOrder.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnSendOrder.setTextColor(Color.parseColor("#929292"));
                                btnOrderReceived.setEnabled(false);
                                btnOrderReceived.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnOrderReceived.setTextColor(Color.parseColor("#929292"));
                                btnOrderRejected.setEnabled(false);
                                btnOrderRejected.setBackgroundResource(R.drawable.button_style3_disabled);
                                btnOrderRejected.setTextColor(Color.parseColor("#929292"));
                            }
                        });
                    }


                    if (ObjOrdersActionsList.get(i).getActionId() == 3) {
                        if (ObjSettings.getCurrentLanguageId() == 1) // Arabic
                            sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("ar"));
                        else
                            sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("en"));

                        final SimpleDateFormat sdfFinal3 = sdf;
                        // Delivery Date
                        final Orders_Actions ObjOrderAction = ObjOrdersActionsList.get(i);
                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lblDeliveryDate.setText(sdfFinal3.format(ObjOrderAction.getActionDate()));
                            }
                        });

                        if (ObjSettings.getCurrentLanguageId() == 1) // Arabic
                            sdf = new SimpleDateFormat("h:mm a", new Locale("ar"));
                        else
                            sdf = new SimpleDateFormat("h:mm a");

                        final SimpleDateFormat sdfFinal4 = sdf;
                        // Delivery Time
                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lblDeliveryTime.setText(sdfFinal4.format(ObjOrderAction.getActionDate()));
                            }
                        });


                        break;
                    }
                }

                // CustomerName
                final User ObjCustomerUser = new UserDB().Select(ObjOrdersList.get(position).getUser_Id());
                final User ObjFoodMakerUser = new UserDB().Select(ObjFoods.getUserId());
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblCustomerName.setText(ObjCustomerUser.getUsername());
                    }
                });

                // Mobile
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblMobile.setText("");
                    }
                });

                // Address
                if (ObjOrdersList.get(position).isShippingToClient()) {

                    final String sAddress;

                    Cities ObjCity = new CitiesDB().Select(ObjOrdersList.get(position).getShippingCountryId(), ObjSettings.getCurrentLanguageId());

                    if (ObjOrdersList.get(position).getOrderAddress() == "") {
                        sAddress =
                                ObjCity.getName() +
                                        ", " +
                                        ObjOrdersList.get(position).getShippingDistrict() +
                                        " " +
                                        ObjOrdersList.get(position).getShippingStreet() +
                                        " " +
                                        ObjOrdersList.get(position).getShippingBuilding() +
                                        " " +
                                        ObjOrdersList.get(position).getShippingApartment() +
                                        "\n" +
                                        ObjOrdersList.get(position).getShippingOtherDetails();
                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lblAddress.setText(sAddress);
                                webview_Address.setVisibility(View.GONE);
                            }
                        });

                    } else {
                        sAddress = ObjOrdersList.get(position).getOrderAddress();
                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lblAddress.setText(sAddress);
                                webview_Address.loadUrl(Utils.GetGoogleMapUrl((AppCompatActivity) context, ObjOrdersList.get(position).getShipping_Latitude(), ObjOrdersList.get(position).getShipping_Longitude()));
                            }
                        });

                    }


                } else {

                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblAddress.setText(Utils.GetResourceName(context, R.string.ClientComeToGetHisOrderByHimSelf, ObjSettings.getCurrentLanguageId()));
                            webview_Address.setVisibility(View.GONE);
                            btnSendOrder.setVisibility(View.GONE);
                            btnOrderReceived.setVisibility(View.GONE);
                            btnOrderRejected.setVisibility(View.GONE);
                        }
                    });

                }


            }
        });

        t.start();


        return v;
    }
}
