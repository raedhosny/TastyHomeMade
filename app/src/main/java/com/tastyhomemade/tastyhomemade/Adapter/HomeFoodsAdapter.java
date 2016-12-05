package com.tastyhomemade.tastyhomemade.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.List;

/**
 * Created by raed on 12/4/2016.
 */

public class HomeFoodsAdapter extends BaseAdapter {
    Context context;
    List<Foods> ObjFoodsList;

    public HomeFoodsAdapter(Context p_context, List<Foods> p_ObjFoodsList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.home_menu_item, null);

        RatingBar ObjRatingBar = (RatingBar) v.findViewById(R.id.txtHomeMenuItemRating);
        ImageView ObjImagedeliverable = (ImageView) v.findViewById(R.id.txtHomeMenuItemDeliverable);
        TextView ObjHomeMenuItemPrice = (TextView) v.findViewById(R.id.txtHomeMenuItemPrice);
        TextView lblHomeMenuItemName = (TextView) v.findViewById(R.id.lblHomeMenuItemName);
        TextView lblHomeMenuItemDescription = (TextView) v.findViewById(R.id.lblHomeMenuItemDescription);
        TextView lblHomeMenuItemTimeFromTo = (TextView) v.findViewById(R.id.lblHomeMenuItemTimeFromTo);
        ImageView ImageHomeMenuItem = (ImageView) v.findViewById(R.id.ImageHomeMenuItem);
        Button BtnHomeMenuItemRequest = (Button)v.findViewById(R.id.BtnHomeMenuItemRequest);

        int iUserId = ObjFoodsList.get(position).getUserId();

        User ObjUser = new UserDB().Select(iUserId);
        if (ObjUser.isHaveDelivary()) {
            ObjImagedeliverable.setVisibility(View.VISIBLE);
        } else {
            ObjImagedeliverable.setVisibility(View.GONE);
        }

        ObjHomeMenuItemPrice.setText(String.valueOf(ObjFoodsList.get(position).getPrice()));
        lblHomeMenuItemName.setText((ObjFoodsList.get(position).getName()));
        lblHomeMenuItemDescription.setText(ObjFoodsList.get(position).getDescription());
        String sTemp = Utils.GetResourceName(context, R.string.RequestTimeFromTo, new Settings(context).getCurrentLanguageId());

        Time ObjRequestTimeFrom = ObjFoodsList.get(position).getRequestTimeFrom();
        Time ObjRequestTimeTo = ObjFoodsList.get(position).getRequestTimeTo();


        sTemp = sTemp.replace("[X]", String.format("%1$tH:%1$tM", ObjRequestTimeFrom));
        sTemp = sTemp.replace("[Y]", String.format("%1$tH:%1$tM", ObjRequestTimeTo));
        if (new Settings(context).getCurrentLanguageId() == 1) {
            sTemp = sTemp.replace("PM", "مساءا").replace("AM", "صباحا");
        }
        lblHomeMenuItemTimeFromTo.setText(sTemp);

        byte [] ObjPhotoBytes = Base64.decode(ObjFoodsList.get(position).getPhoto(),Base64.DEFAULT);
        Bitmap ObjBitmap = BitmapFactory.decodeByteArray(ObjPhotoBytes ,0,ObjPhotoBytes .length);
        ImageHomeMenuItem.setImageBitmap(ObjBitmap);

        BtnHomeMenuItemRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Utils().ShowActivity();
            }
        });

        return v;
    }
}

