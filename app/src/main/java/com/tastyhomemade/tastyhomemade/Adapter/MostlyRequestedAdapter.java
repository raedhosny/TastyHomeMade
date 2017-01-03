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

public class MostlyRequestedAdapter extends BaseAdapter {
    Context context;
    List<Foods> ObjFoodsList;

    public MostlyRequestedAdapter(Context p_context, List<Foods> p_ObjFoodsList) {
        context = p_context;
        ObjFoodsList = p_ObjFoodsList;
    }


    @Override
    public int getCount() {
        return ObjFoodsList.size();
    }

    @Override
    public Foods getItem(int position) {
        return ObjFoodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ObjFoodsList.get(position).getId();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = View.inflate(context, R.layout.mostlyrequested_list_item, null);

            RatingBar ObjRatingBar = (RatingBar) v.findViewById(R.id.txtHomeMenuItemRating);
            ImageView ObjImagedeliverable = (ImageView) v.findViewById(R.id.txtHomeMenuItemDeliverable);
            TextView ObjHomeMenuItemPrice = (TextView) v.findViewById(R.id.txtHomeMenuItemPrice);
            TextView lblHomeMenuItemName = (TextView) v.findViewById(R.id.lblHomeMenuItemName);
            TextView lblHomeMenuItemDescription = (TextView) v.findViewById(R.id.lblHomeMenuItemDescription);
            TextView lblHomeMenuItemTimeFromTo = (TextView) v.findViewById(R.id.lblHomeMenuItemTimeFromTo);
            final ImageView ImageHomeMenuItem = (ImageView) v.findViewById(R.id.ImageHomeMenuItem);
            Button BtnHomeMenuItemRequest = (Button) v.findViewById(R.id.BtnHomeMenuItemRequest);
            TextView lblNumberOfRequests = (TextView) v.findViewById(R.id.lblNumberOfRequests);


            // Set Delivery availability
            int iUserId = ObjFoodsList.get(position).getUserId();

            User ObjUser = new UserDB().Select(iUserId);
            if (ObjUser.isHaveDelivary()) {
                ObjImagedeliverable.setVisibility(View.VISIBLE);
            } else {
                ObjImagedeliverable.setVisibility(View.GONE);
            }

            // Set Food Price
            ObjHomeMenuItemPrice.setText(String.valueOf(ObjFoodsList.get(position).getPrice()) + " " + new Utils().GetResourceName(context, R.string.Currency, new Settings(context).getCurrentLanguageId()));

            // Set Food Name
            lblHomeMenuItemName.setText((ObjFoodsList.get(position).getName()));

            // Set Food Description
            lblHomeMenuItemDescription.setText(ObjFoodsList.get(position).getDescription());

            // Set Request Time From To
            String sTemp = Utils.GetResourceName(context, R.string.RequestTimeFromTo, new Settings(context).getCurrentLanguageId());

            Time ObjRequestTimeFrom = ObjFoodsList.get(position).getRequestTimeFrom();
            Time ObjRequestTimeTo = ObjFoodsList.get(position).getRequestTimeTo();

            SimpleDateFormat formater = new SimpleDateFormat("h:mm a");

            sTemp = sTemp.replace("[X]", formater.format(ObjRequestTimeFrom));
            sTemp = sTemp.replace("[Y]", formater.format(ObjRequestTimeTo));
            if (new Settings(context).getCurrentLanguageId() == 1) {
                sTemp = sTemp.replace("PM", "مساءا").replace("AM", "صباحا");
            }
            lblHomeMenuItemTimeFromTo.setText(sTemp);


            // Set Food Photo

            final Bitmap[] ObjBitmap = new Bitmap[1];
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {


                    try {
                        ObjBitmap[0] = Utils.LoadImage(ObjFoodsList.get(position).getPhoto());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            ImageHomeMenuItem.setImageBitmap(ObjBitmap[0]);
                        }
                    });
                }
            });
            t.start();


            // Set Food Rating
            // /////////////////////
            ////////////////////////


            ////////////////////////
            ////////////////////////

            // Number of total Requests
            lblNumberOfRequests.setText(Utils.GetResourceName(context, R.string.numberofrequests, new Settings(context).getCurrentLanguageId()) + " " + ObjFoodsList.get(position).getNumberOfRequestsCount());


            BtnHomeMenuItemRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (new Settings(context).getUserId() != -1) {
                        Calendar ObjCalender = Calendar.getInstance();
                        Calendar ObjCalendarFrom = Calendar.getInstance();
                        Calendar ObjCalendarTo= Calendar.getInstance();
                        ObjCalendarFrom.set(Calendar.HOUR,ObjFoodsList.get(position).getRequestTimeFrom().getHours());
                        ObjCalendarFrom.set(Calendar.MINUTE,ObjFoodsList.get(position).getRequestTimeFrom().getMinutes());

                        ObjCalendarTo.set(Calendar.HOUR,ObjFoodsList.get(position).getRequestTimeTo().getHours());
                        ObjCalendarTo.set(Calendar.MINUTE,ObjFoodsList.get(position).getRequestTimeTo().getMinutes());

                        if (ObjCalender.compareTo(ObjCalendarFrom) > 0 && ObjCalender.compareTo(ObjCalendarTo) < 0
//                            ||
//                            (iCurrentTime >= iTimeTo&&
//                                    iCurrentTime <= iTimeFrom )
                                )

                            new Utils().ShowActivity(context, null, "RequestForm", String.valueOf(ObjFoodsList.get(position).getId()));
                        else
                            Toast.makeText(context, Utils.GetResourceName(context, R.string.Error_YouCantRequestThisOrderAtThisTime, new Settings(context).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, new Utils().GetResourceName(context, R.string.Error_YouAreNotLoginYet, new Settings(context).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return v;
    }
}

