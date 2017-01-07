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
import android.widget.LinearLayout;
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

public class EditFoodsAdapter {
    Context context;
    List<Foods> ObjFoodsList;
    Settings ObjSettings;

    public EditFoodsAdapter(Context p_context, List<Foods> p_ObjFoodsList) {
        context = p_context;
        ObjFoodsList = p_ObjFoodsList;
        ObjSettings = new Settings(p_context);
    }

    public void FillList(LinearLayout p_Linear) {



        final LinearLayout LinearFinal = p_Linear;
        final AppCompatActivity CurrentActivity = (AppCompatActivity) context;

        CurrentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearFinal.removeAllViews();
            }
        });

        for (int i = 0; i <= ObjFoodsList.size() - 1; i++) {

            final View v = View.inflate(context, R.layout.editfood_list_item, null);
            CurrentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LinearFinal.addView(v);
                }
            });

            final RatingBar ObjRatingBar = (RatingBar) v.findViewById(R.id.txtHomeMenuItemRating);
            final ImageView ObjImagedeliverable = (ImageView) v.findViewById(R.id.txtHomeMenuItemDeliverable);
            final TextView ObjHomeMenuItemPrice = (TextView) v.findViewById(R.id.txtHomeMenuItemPrice);
            final TextView lblHomeMenuItemName = (TextView) v.findViewById(R.id.lblHomeMenuItemName);
            final TextView lblHomeMenuItemDescription = (TextView) v.findViewById(R.id.lblHomeMenuItemDescription);
            final TextView lblHomeMenuItemTimeFromTo = (TextView) v.findViewById(R.id.lblHomeMenuItemTimeFromTo);
            final ImageView ImageHomeMenuItem = (ImageView) v.findViewById(R.id.ImageHomeMenuItem);
            final Button BtnItemDelete = (Button) v.findViewById(R.id.BtnItemDelete);
            final Button BtnEditItem = (Button) v.findViewById(R.id.BtnEditItem);
            final int iFinal = i;

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {


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
                            ObjHomeMenuItemPrice.setText(String.valueOf(ObjFoodsList.get(iFinal).getPrice()) + " " + new Utils().GetResourceName(context, R.string.Currency, new Settings(context).getCurrentLanguageId()));
                            lblHomeMenuItemName.setText((ObjFoodsList.get(iFinal).getName()));
                            lblHomeMenuItemDescription.setText(ObjFoodsList.get(iFinal).getDescription());
                        }
                    });

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


                                            new FoodsDB().Delete(ObjFoodsList.get(iFinal).getId());
                                            ObjFoodsList.remove(iFinal);
                                        }
                                    });
                                    t.start();
                                    CurrentActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LinearFinal.removeViewAt(iFinal);
                                            //notifyDataSetChanged();

                                        }
                                    });


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
                            CurrentActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    new Utils().ShowActivity(context, null, "UpdateFoodsandDrinks", String.valueOf(ObjFoodsList.get(iFinal).getId()));
                                }
                            });

                        }
                    });
                }
            });
            t.start();
        }

    }


}




