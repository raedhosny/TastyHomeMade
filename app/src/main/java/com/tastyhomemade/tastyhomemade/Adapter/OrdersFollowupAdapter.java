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

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by raed on 12/13/2016.
 */

public class OrdersFollowupAdapter extends BaseAdapter {
    List<Orders> ObjOrderList;
    Context context;
    Settings ObjSettings;

    public OrdersFollowupAdapter(Context p_context, List<Orders> p_OrdersList) {
        ObjOrderList = p_OrdersList;
        context = p_context;
        ObjSettings = new Settings(p_context);
    }


    @Override
    public int getCount() {
        return ObjOrderList.size();
    }

    @Override
    public Orders getItem(int position) {
        return ObjOrderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ObjOrderList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.orderfollowup_list_item, null);

        final View vv = v;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                final Orders ObjOrder = ObjOrderList.get(position);
                final Foods ObjFood = new FoodsDB().Select(ObjOrder.getFood_Id(), ObjSettings.getCurrentLanguageId());
                final User ObjUser = new UserDB().Select(ObjFood.getUserId());

                final Button btnReportDelay = (Button) vv.findViewById(R.id.btnReportDelay);
                btnReportDelay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ObjOrder.setReportDelayed(true);


                        new OrdersDB().InsertUpdate(ObjOrder);

                        ((MainActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                        btnReportDelay.setVisibility(View.GONE);
                            }
                        });
                        // notifyDataSetChanged();
                    }
                });

                if (ObjOrderList.get(position).isReportDelayed())
                    btnReportDelay.setVisibility(View.GONE);
                //notifyDataSetChanged();

                final ImageView ImageFood = (ImageView) vv.findViewById(R.id.ImageFood);


                byte[] Photo = Base64.decode(ObjFood.getPhoto(), Base64.DEFAULT);
                final Bitmap ObjBitmap = BitmapFactory.decodeByteArray(Photo, 0, Photo.length);
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageFood.setImageBitmap(ObjBitmap);
                    }
                });


                final ImageView ImageDeliverable = (ImageView) vv.findViewById(R.id.ImageDeliverable);

                if (ObjUser.isHaveDelivary()) {
                    ((MainActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageDeliverable.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    ((MainActivity) context).runOnUiThread(new Runnable() {
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
                final RatingBar FoodRating = (RatingBar) vv.findViewById(R.id.FoodRating);
                // Code for Rating will be Later
                // Code for Rating will be Later
                // Code for Rating will be Later
                // Code for Rating will be Later

                final TextView lblName = (TextView) vv.findViewById(R.id.lblName);
                final String sNameTemp = ObjFood.getName();
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblName.setText(sNameTemp);
                    }
                });

                final TextView lblTimeFromTo = (TextView) vv.findViewById(R.id.lblTimeFromTo);

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

                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        lblTimeFromTo.setText(sTempFinal);
                    }
                });

                final ListView lvOrderActions = (ListView) vv.findViewById(R.id.lvOrderActions);

                final List<Orders_Actions> Obj_Orders_Actions_List = new Orders_ActionsDB().SelectByOrderId(ObjOrder.getId(),2);

                final OrdersActionsAdapter ObjOrdersActionsAdapter = new OrdersActionsAdapter(context, Obj_Orders_Actions_List);

                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        View Header = vv.inflate(context,R.layout.orderfollowup_listview_header, null);
                        if (lvOrderActions.getHeaderViewsCount() == 0)
                            lvOrderActions.addHeaderView(Header);

                        lvOrderActions.setAdapter(ObjOrdersActionsAdapter);
                        Utils.setListViewHeightBasedOnChildren(lvOrderActions);
                        ///lvOrderActions.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT + Obj_Orders_Actions_List.size()*26));


                    }
                });

            }
        });
        t.start();

        return v;
    }
}
