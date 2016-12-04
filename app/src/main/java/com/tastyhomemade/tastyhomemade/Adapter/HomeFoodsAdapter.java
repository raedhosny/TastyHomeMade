package com.tastyhomemade.tastyhomemade.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by raed on 12/4/2016.
 */

public class HomeFoodsAdapter extends BaseAdapter {
    Context context;
    List<Foods> ObjFoodsList;

    public HomeFoodsAdapter(Context p_context, List<Foods> p_ObjFoodsList)
    {
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
        View v = View.inflate(context,R.layout.home_menu_item,null);

        RatingBar ObjRatingBar = (RatingBar) v.findViewById(R.id.txtHomeMenuItemRating);
        ImageView Objdeliverable =  (ImageView) v.findViewById(R.id.txtHomeMenuItemDeliverable);
        TextView ObjHomeMenuItemPrice = (TextView) v.findViewById(R.id.txtHomeMenuItemPrice);
        TextView  lblHomeMenuItemName = (TextView) v.findViewById(R.id.lblHomeMenuItemName);
        TextView lblHomeMenuItemDescription = (TextView) v.findViewById(R.id.lblHomeMenuItemDescription);
        TextView lblHomeMenuItemTimeFromTo = (TextView) v.findViewById(R.id.lblHomeMenuItemTimeFromTo);

        int iUserId = ObjFoodsList.get(position).getUserId();

        new UsersDB().


        return v;
    }
}
