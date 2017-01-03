package com.tastyhomemade.tastyhomemade.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raed on 12/4/2016.
 */

public class HomeFoodsAdapter_Copy extends BaseAdapter {
    Context context;
    List<Foods> ObjFoodsList;
    View v = null;
    List<Item> ObjAllItems = new ArrayList<Item>();
    boolean IsCompleted = false;

    public HomeFoodsAdapter_Copy(Context p_context, List<Foods> p_ObjFoodsList) {
        context = p_context;
        ObjFoodsList = p_ObjFoodsList;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ObjFoodsList.size(); i++) {
                    int iUserId = ObjFoodsList.get(i).getUserId();
                    User ObjUser = new UserDB().Select(iUserId);
                    Item ObjItem = new Item();
                    ObjItem.isHaveDelivary = ObjUser.isHaveDelivary();
                    ObjItem.Name = ObjFoodsList.get(i).getName();
                    ObjItem.Description = ObjFoodsList.get(i).getDescription();
                    ObjItem.Price = ObjFoodsList.get(i).getPrice();
                    Time ObjRequestTimeFrom = ObjFoodsList.get(i).getRequestTimeFrom();
                    Time ObjRequestTimeTo = ObjFoodsList.get(i).getRequestTimeTo();

                    String sTemp = Utils.GetResourceName(context, R.string.RequestTimeFromTo, new Settings(context).getCurrentLanguageId());

                    SimpleDateFormat formater = new SimpleDateFormat("h:mm a");

                    sTemp = sTemp.replace("[X]", formater.format(ObjRequestTimeFrom));
                    sTemp = sTemp.replace("[Y]", formater.format(ObjRequestTimeTo));
                    if (new Settings(context).getCurrentLanguageId() == 1) {
                        sTemp = sTemp.replace("PM", "مساءا").replace("AM", "صباحا");
                    }
                    ObjItem.RequestTimeFromTo = sTemp;
                    final Bitmap[] ObjBitmap = new Bitmap[1];
                    try {
                        ObjBitmap[0] = Utils.LoadImage(ObjFoodsList.get(i).getPhoto());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ObjItem.Photo = ObjBitmap[0];
                    ObjAllItems.add(ObjItem);


                }
                IsCompleted = true;

            }
        });
        t.start();
       while (!IsCompleted) ;
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

        v = convertView;
        if (v == null)
            v = View.inflate(context,R.layout.home_menu_item,null);


        final RatingBar ObjRatingBar = (RatingBar) v.findViewById(R.id.txtHomeMenuItemRating);
        final ImageView ObjImagedeliverable = (ImageView) v.findViewById(R.id.txtHomeMenuItemDeliverable);
        final TextView ObjHomeMenuItemPrice = (TextView) v.findViewById(R.id.txtHomeMenuItemPrice);
        final TextView lblHomeMenuItemName = (TextView) v.findViewById(R.id.lblHomeMenuItemName);
        final TextView lblHomeMenuItemDescription = (TextView) v.findViewById(R.id.lblHomeMenuItemDescription);
        final TextView lblHomeMenuItemTimeFromTo = (TextView) v.findViewById(R.id.lblHomeMenuItemTimeFromTo);
        final ImageView ImageHomeMenuItem = (ImageView) v.findViewById(R.id.ImageHomeMenuItem);
        final Button BtnHomeMenuItemRequest = (Button) v.findViewById(R.id.BtnHomeMenuItemRequest);

        if (ObjAllItems.get(position).isHaveDelivary) {
            ObjImagedeliverable.setVisibility(View.VISIBLE);
        } else {
            ObjImagedeliverable.setVisibility(View.GONE);
        }

        ObjHomeMenuItemPrice.setText(ObjAllItems.get(position).Price + " " + new Utils().GetResourceName(context, R.string.Currency, new Settings(context).getCurrentLanguageId()));
        lblHomeMenuItemName.setText(ObjAllItems.get(position).Name);
        lblHomeMenuItemDescription.setText(ObjAllItems.get(position).Description);
        lblHomeMenuItemTimeFromTo.setText(ObjAllItems.get(position).RequestTimeFromTo);
        ImageHomeMenuItem.setImageBitmap(ObjAllItems.get(position).Photo);

        BtnHomeMenuItemRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new Settings(context).getUserId() != -1) {

                    new Utils().ShowActivity(context, null, "RequestForm", String.valueOf(ObjFoodsList.get(position).getId()));

                } else {

                    Toast.makeText(context, new Utils().GetResourceName(context, R.string.Error_YouAreNotLoginYet, new Settings(context).getCurrentLanguageId()), Toast.LENGTH_LONG).show();

                }
            }
        });


        return v;
    }

    class Item {
        public boolean isHaveDelivary;
        public String Name;
        public String Description;
        public float Price;
        public String RequestTimeFromTo;
        public Bitmap Photo;
    }
}

