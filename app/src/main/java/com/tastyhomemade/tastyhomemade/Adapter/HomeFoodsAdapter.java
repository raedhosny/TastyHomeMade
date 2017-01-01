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
import com.tastyhomemade.tastyhomemade.Others.ViewMode;
import com.tastyhomemade.tastyhomemade.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by raed on 12/4/2016.
 */

public class HomeFoodsAdapter extends BaseAdapter {
    Context context;
    List<Foods> ObjFoodsList;
    View v = null;

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
    public View getView(final int position, final View convertView, ViewGroup parent) {

        v = convertView;
        if (v == null) {

            v = View.inflate(context, R.layout.home_menu_item, null);

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

                    int iUserId = ObjFoodsList.get(position).getUserId();

                    User ObjUser = new UserDB().Select(iUserId);
                    if (ObjUser.isHaveDelivary()) {
                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ObjImagedeliverable.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ObjImagedeliverable.setVisibility(View.GONE);
                            }
                        });
                    }

                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ObjHomeMenuItemPrice.setText(String.valueOf(ObjFoodsList.get(position).getPrice()) + " " + new Utils().GetResourceName(context, R.string.Currency, new Settings(context).getCurrentLanguageId()));
                            lblHomeMenuItemName.setText((ObjFoodsList.get(position).getName()));
                            lblHomeMenuItemDescription.setText(ObjFoodsList.get(position).getDescription());
                        }
                    });
                    String sTemp = Utils.GetResourceName(context, R.string.RequestTimeFromTo, new Settings(context).getCurrentLanguageId());

                    Time ObjRequestTimeFrom = ObjFoodsList.get(position).getRequestTimeFrom();
                    Time ObjRequestTimeTo = ObjFoodsList.get(position).getRequestTimeTo();

                    SimpleDateFormat formater = new SimpleDateFormat("h:mm a");

                    sTemp = sTemp.replace("[X]", formater.format(ObjRequestTimeFrom));
                    sTemp = sTemp.replace("[Y]", formater.format(ObjRequestTimeTo));
                    if (new Settings(context).getCurrentLanguageId() == 1) {
                        sTemp = sTemp.replace("PM", "مساءا").replace("AM", "صباحا");
                    }

                    final String sTempFinal = sTemp;
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblHomeMenuItemTimeFromTo.setText(sTempFinal);
                        }
                    });


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

                    BtnHomeMenuItemRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (new Settings(context).getUserId() != -1) {
                                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new Utils().ShowActivity(context, null, "RequestForm", String.valueOf(ObjFoodsList.get(position).getId()));
                                    }
                                });
                            } else {
                                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
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

        return v;
    }
}

