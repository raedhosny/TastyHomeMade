package com.tastyhomemade.tastyhomemade.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Business.Categories;
import com.tastyhomemade.tastyhomemade.Business.Foods;
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
 * Created by raed on 12/4/2016.
 */

public class CategoriesAdapter extends BaseAdapter {
    Context context;
    List<Categories> ObjCategoriesList;

    public CategoriesAdapter(Context p_context, List<Categories> p_ObjCategoriesList) {
        context = p_context;
        ObjCategoriesList = p_ObjCategoriesList;
    }


    @Override
    public int getCount() {
        return ObjCategoriesList.size();
    }

    @Override
    public Categories getItem(int position) {
        return ObjCategoriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ObjCategoriesList.get(position).getId();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View v = View.inflate (context,R.layout.main_menu_item,null);
        TextView lblItem = (TextView) v.findViewById(R.id.lblItem);
        lblItem.setText(ObjCategoriesList.get(position).getName());

        lblItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Utils().ShowActivity (context,null,"Main", String.valueOf(ObjCategoriesList.get(position).getId()) );
                ((DrawerLayout)((MainActivity)context).findViewById(R.id.Drawer_Layout)).closeDrawer(((MainActivity)context).findViewById(R.id.Linear_SideMenu));

            }
        });

        return v;


    }
}

