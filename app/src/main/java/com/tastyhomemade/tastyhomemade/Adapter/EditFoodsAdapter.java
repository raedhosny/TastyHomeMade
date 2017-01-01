package com.tastyhomemade.tastyhomemade.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

/**
 * Created by raed on 12/4/2016.
 */

public class EditFoodsAdapter extends BaseAdapter {
    Context context;
    List<Foods> ObjFoodsList;
    Settings ObjSettings;

    public EditFoodsAdapter(Context p_context, List<Foods> p_ObjFoodsList) {
        context = p_context;
        ObjFoodsList = p_ObjFoodsList;
        ObjSettings = new Settings(p_context);
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

        View v =  convertView;
        if ( v == null) {
            v = View.inflate(context, R.layout.editfood_list_item, null);

            RatingBar ObjRatingBar = (RatingBar) v.findViewById(R.id.txtHomeMenuItemRating);
            ImageView ObjImagedeliverable = (ImageView) v.findViewById(R.id.txtHomeMenuItemDeliverable);
            TextView ObjHomeMenuItemPrice = (TextView) v.findViewById(R.id.txtHomeMenuItemPrice);
            TextView lblHomeMenuItemName = (TextView) v.findViewById(R.id.lblHomeMenuItemName);
            TextView lblHomeMenuItemDescription = (TextView) v.findViewById(R.id.lblHomeMenuItemDescription);
            TextView lblHomeMenuItemTimeFromTo = (TextView) v.findViewById(R.id.lblHomeMenuItemTimeFromTo);
            final ImageView ImageHomeMenuItem = (ImageView) v.findViewById(R.id.ImageHomeMenuItem);
            Button BtnItemDelete = (Button) v.findViewById(R.id.BtnItemDelete);
            Button BtnEditItem = (Button) v.findViewById(R.id.BtnEditItem);

            int iUserId = ObjFoodsList.get(position).getUserId();

            User ObjUser = new UserDB().Select(iUserId);
            if (ObjUser.isHaveDelivary()) {
                ObjImagedeliverable.setVisibility(View.VISIBLE);
            } else {
                ObjImagedeliverable.setVisibility(View.GONE);
            }

            ObjHomeMenuItemPrice.setText(String.valueOf(ObjFoodsList.get(position).getPrice()) + " " + new Utils().GetResourceName(context, R.string.Currency, new Settings(context).getCurrentLanguageId()));
            lblHomeMenuItemName.setText((ObjFoodsList.get(position).getName()));
            lblHomeMenuItemDescription.setText(ObjFoodsList.get(position).getDescription());
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

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {


                    final Bitmap[] ObjBitmap = new Bitmap[1];
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

            BtnItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder ctlAlertDialog = new AlertDialog.Builder(context);
                    // ctlAlertDialog.setTitle(Utils.GetResourceName(context, R.string.AreYouSure, ObjSettings.getCurrentLanguageId()));
                    ctlAlertDialog.setMessage(Utils.GetResourceName(context, R.string.AreYouSure, ObjSettings.getCurrentLanguageId()));
                    ctlAlertDialog.setPositiveButton(Utils.GetResourceName(context, R.string.Yes, ObjSettings.getCurrentLanguageId()), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    new FoodsDB().Delete(ObjFoodsList.get(position).getId());
                                    ObjFoodsList.remove(position);
                                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            notifyDataSetChanged();
                                        }
                                    });


                                }
                            });
                            t.start();
                        }
                    });
                    ctlAlertDialog.setNegativeButton(Utils.GetResourceName(context, R.string.No, ObjSettings.getCurrentLanguageId()), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ctlAlertDialog.show();
                }
            });

            BtnEditItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new Utils().ShowActivity(context, null, "UpdateFoodsandDrinks", String.valueOf(ObjFoodsList.get(position).getId()));

                }
            });

        }
        return v;
    }
}

