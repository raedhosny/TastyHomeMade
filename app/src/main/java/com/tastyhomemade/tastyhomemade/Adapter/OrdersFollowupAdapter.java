package com.tastyhomemade.tastyhomemade.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.Business.Orders;
import com.tastyhomemade.tastyhomemade.Business.OrdersDB;
import com.tastyhomemade.tastyhomemade.Business.Orders_Actions;
import com.tastyhomemade.tastyhomemade.Business.Orders_ActionsDB;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.MainActivity;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by raed on 12/13/2016.
 */

public class OrdersFollowupAdapter {
    List<Orders> ObjOrderList;
    Context context;
    Settings ObjSettings;

    public OrdersFollowupAdapter(Context p_context, List<Orders> p_OrdersList) {
        ObjOrderList = p_OrdersList;
        context = p_context;
        ObjSettings = new Settings(p_context);
    }

    public void FillList(LinearLayout p_Linear) {
//        v= convertView;
//        if (v == null)
// {
        final AppCompatActivity CurrentActivity = (AppCompatActivity)context;

        final LinearLayout LinearFinal = p_Linear;

        CurrentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearFinal.removeAllViews();
            }
        });


        for (int i=0;i<=ObjOrderList.size()-1;i++) {
            final int iFinal = i;

            final View v = View.inflate(context, R.layout.orderfollowup_list_item, null);

            CurrentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LinearFinal.addView(v);
                }
            });

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {


                    final Orders ObjOrder = ObjOrderList.get(iFinal);
                    final Foods ObjFood = new FoodsDB().Select(ObjOrder.getFood_Id(), ObjSettings.getCurrentLanguageId());
                    final User ObjUser = new UserDB().Select(ObjFood.getUserId());

                    final Button btnReportDelay = (Button) v.findViewById(R.id.btnReportDelay);
                    btnReportDelay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ObjOrder.setReportDelayed(true);


                            new OrdersDB().InsertUpdate(ObjOrder);

                            CurrentActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnReportDelay.setVisibility(View.GONE);
                                }
                            });
                            // notifyDataSetChanged();
                        }
                    });

                    if (ObjOrderList.get(iFinal).isReportDelayed())
                        btnReportDelay.setVisibility(View.GONE);
                    //notifyDataSetChanged();

                    final ImageView ImageFood = (ImageView) v.findViewById(R.id.ImageFood);


                    final Bitmap[] ObjBitmap = new Bitmap[1];
                    try {
                        ObjBitmap[0] = Utils.LoadImage(ObjFood.getPhoto());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    CurrentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageFood.setImageBitmap(ObjBitmap[0]);
                        }
                    });


                    final ImageView ImageDeliverable = (ImageView) v.findViewById(R.id.ImageDeliverable);

                    if (ObjUser.isHaveDelivary()) {
                        CurrentActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageDeliverable.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        CurrentActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageDeliverable.setVisibility(View.GONE);
                            }
                        });
                    }


                    // Code for Rating will be Later
                    // Code for Rating will be Later
                    // Code for Rating will be Later
                    // Code for Rating will be Later
                    final RatingBar FoodRating = (RatingBar) v.findViewById(R.id.FoodRating);
                    // Code for Rating will be Later
                    // Code for Rating will be Later
                    // Code for Rating will be Later
                    // Code for Rating will be Later

                    final TextView lblName = (TextView) v.findViewById(R.id.lblName);
                    final String sNameTemp = ObjFood.getName();
                    CurrentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblName.setText(sNameTemp);
                        }
                    });

                    final TextView lblTimeFromTo = (TextView) v.findViewById(R.id.lblTimeFromTo);

                    String sTemp = Utils.GetResourceName(context, R.string.RequestTimeFromTo, new Settings(context).getCurrentLanguageId());

                    Time ObjRequestTimeFrom = ObjFood.getRequestTimeFrom();
                    Time ObjRequestTimeTo = ObjFood.getRequestTimeTo();

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

                            lblTimeFromTo.setText(sTempFinal);
                        }
                    });

                    final LinearLayout lvOrderActions = (LinearLayout) v.findViewById(R.id.lvOrderActions);

                    final List<Orders_Actions> Obj_Orders_Actions_List = new Orders_ActionsDB().SelectByOrderId(ObjOrder.getId(), 2);

                    final OrdersActionsAdapter ObjOrdersActionsAdapter = new OrdersActionsAdapter(context, Obj_Orders_Actions_List);

                    CurrentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            View Header = v.inflate(context, R.layout.orderfollowup_listview_header, null);

                            lvOrderActions.addView(Header);

                            ObjOrdersActionsAdapter.FillList(lvOrderActions);
                            //lvOrderActions.setAdapter(ObjOrdersActionsAdapter);
                           //Utils.setListViewHeightBasedOnChildren(lvOrderActions);
                            ///lvOrderActions.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT + Obj_Orders_Actions_List.size()*26));


                        }
                    });

                }
            });
            t.start();
        }
        //}

    }
}
