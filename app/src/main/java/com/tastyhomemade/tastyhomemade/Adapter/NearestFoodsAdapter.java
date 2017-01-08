package com.tastyhomemade.tastyhomemade.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by raed on 12/4/2016.
 */

public class NearestFoodsAdapter {
    Context context;
    List<Foods> ObjFoodsList;

    public NearestFoodsAdapter(Context p_context, List<Foods> p_ObjFoodsList) {
        context = p_context;
        ObjFoodsList = p_ObjFoodsList;
    }


    public void FillList(LinearLayout p_Linear) {

        final AppCompatActivity CurrentActivity = (AppCompatActivity) context;
        final LinearLayout LinearFinal = p_Linear;
        CurrentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearFinal.removeAllViews();
            }
        });





        for (int i = 0; i <= ObjFoodsList.size() - 1; i++) {
            final int iFinal = i;

            final View v = View.inflate(context, R.layout.mostlyrequested_list_item, null);

            CurrentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LinearFinal.addView(v);
                }
            });

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {


                    final RatingBar ObjRatingBar = (RatingBar) v.findViewById(R.id.txtHomeMenuItemRating);
                    final ImageView ObjImagedeliverable = (ImageView) v.findViewById(R.id.txtHomeMenuItemDeliverable);
                    final TextView ObjHomeMenuItemPrice = (TextView) v.findViewById(R.id.txtHomeMenuItemPrice);
                    final TextView lblHomeMenuItemName = (TextView) v.findViewById(R.id.lblHomeMenuItemName);
                    final TextView lblHomeMenuItemDescription = (TextView) v.findViewById(R.id.lblHomeMenuItemDescription);
                    final TextView lblHomeMenuItemTimeFromTo = (TextView) v.findViewById(R.id.lblHomeMenuItemTimeFromTo);
                    final ImageView ImageHomeMenuItem = (ImageView) v.findViewById(R.id.ImageHomeMenuItem);
                    final Button BtnHomeMenuItemRequest = (Button) v.findViewById(R.id.BtnHomeMenuItemRequest);
                    final TextView lblFoodMakerDistance = (TextView) v.findViewById(R.id.lblNumberOfRequests);


                    // Set Delivery availability
                    int iUserId = ObjFoodsList.get(iFinal).getUserId();

                    User ObjUser = new UserDB().Select(iUserId);
                    if (ObjUser.isHaveDelivary()) {
                        CurrentActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ObjImagedeliverable.setVisibility(View.VISIBLE);
                            }
                        });

                    } else {
                        CurrentActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ObjImagedeliverable.setVisibility(View.GONE);
                            }
                        });
                    }

                    CurrentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Set Food Price
                            ObjHomeMenuItemPrice.setText(String.valueOf(ObjFoodsList.get(iFinal).getPrice()) + " " + new Utils().GetResourceName(context, R.string.Currency, new Settings(context).getCurrentLanguageId()));

                            // Set Food Name
                            lblHomeMenuItemName.setText((ObjFoodsList.get(iFinal).getName()));

                            // Set Food Description
                            lblHomeMenuItemDescription.setText(ObjFoodsList.get(iFinal).getDescription());
                        }
                    });

                    // Set Request Time From To
                    String sTemp = Utils.GetResourceName(context, R.string.RequestTimeFromTo, new Settings(context).getCurrentLanguageId());

                    Time ObjRequestTimeFrom = ObjFoodsList.get(iFinal).getRequestTimeFrom();
                    Time ObjRequestTimeTo = ObjFoodsList.get(iFinal).getRequestTimeTo();

                    SimpleDateFormat formater = new SimpleDateFormat("h:mm a");

                    sTemp = sTemp.replace("[X]", formater.format(ObjRequestTimeFrom));
                    sTemp = sTemp.replace("[Y]", formater.format(ObjRequestTimeTo));
                    if (new Settings(context).getCurrentLanguageId() == 1) {
                        sTemp = sTemp.replace("PM", "مساءا").replace("AM", "صباحا");
                    }

                    final String sTempFinal = sTemp;

                    CurrentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblHomeMenuItemTimeFromTo.setText(sTempFinal);
                        }
                    });


                    // Set Food Photo

                    final Bitmap[] ObjBitmap = new Bitmap[1];


                    try {
                        ObjBitmap[0] = Utils.LoadImage(ObjFoodsList.get(iFinal).getPhoto());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    CurrentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            ImageHomeMenuItem.setImageBitmap(ObjBitmap[0]);
                        }
                    });


                    // Set Food Rating
                    // /////////////////////
                    ////////////////////////


                    ////////////////////////
                    ////////////////////////

                    // Distance In Meter
                    String ObjTemp = Utils.GetResourceName(context, R.string.AboutMeters, new Settings(context).getCurrentLanguageId());
                    if (!ObjTemp.equals("Unknow") || !ObjTemp.equals("غير معروف"))
                        ObjTemp = ObjTemp.replace("X", ObjFoodsList.get(iFinal).getDistance());


                    final String ObjTempFinal = ObjTemp;

                    CurrentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblFoodMakerDistance.setText(ObjTempFinal);
                        }
                    });


                    BtnHomeMenuItemRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (new Settings(context).getUserId() != -1) {
                                Calendar ObjCalender = Calendar.getInstance();
                                Calendar ObjCalendarFrom = Calendar.getInstance();
                                Calendar ObjCalendarTo = Calendar.getInstance();
                                ObjCalendarFrom.set(Calendar.HOUR, ObjFoodsList.get(iFinal).getRequestTimeFrom().getHours());
                                ObjCalendarFrom.set(Calendar.MINUTE, ObjFoodsList.get(iFinal).getRequestTimeFrom().getMinutes());

                                ObjCalendarTo.set(Calendar.HOUR, ObjFoodsList.get(iFinal).getRequestTimeTo().getHours());
                                ObjCalendarTo.set(Calendar.MINUTE, ObjFoodsList.get(iFinal).getRequestTimeTo().getMinutes());

                                if (ObjCalender.compareTo(ObjCalendarFrom) > 0 && ObjCalender.compareTo(ObjCalendarTo) < 0
//                            ||
//                            (iCurrentTime >= iTimeTo&&
//                                    iCurrentTime <= iTimeFrom )
                                        )
                                    CurrentActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new Utils().ShowActivity(context, null, "RequestForm", String.valueOf(ObjFoodsList.get(iFinal).getId()));
                                        }
                                    });
                                else
                                    CurrentActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, Utils.GetResourceName(context, R.string.Error_YouCantRequestThisOrderAtThisTime, new Settings(context).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                                        }
                                    });
                            } else {
                                CurrentActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, new Utils().GetResourceName(context, R.string.Error_YouAreNotLoginYet, new Settings(context).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });

                }
            });
            t.start();


        }
    }
}

