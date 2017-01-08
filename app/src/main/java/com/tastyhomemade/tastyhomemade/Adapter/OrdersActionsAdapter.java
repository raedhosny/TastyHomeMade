package com.tastyhomemade.tastyhomemade.Adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tastyhomemade.tastyhomemade.Business.Actions;
import com.tastyhomemade.tastyhomemade.Business.ActionsDB;
import com.tastyhomemade.tastyhomemade.Business.Orders_Actions;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by raed on 12/13/2016.
 */

public class OrdersActionsAdapter {

    List<Orders_Actions> Obj_Orders_Actions_List;
    Context context;
    Settings ObjSettings;

    public OrdersActionsAdapter(Context p_context, List<Orders_Actions> p_Orders_Actions_List) {
        context = p_context;
        Obj_Orders_Actions_List = p_Orders_Actions_List;
        ObjSettings = new Settings(p_context);
    }


    public void FillList(LinearLayout p_Linear) {

        final AppCompatActivity CurrentActivity = (AppCompatActivity) context;

        final LinearLayout LinearFinal = p_Linear;

        CurrentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearFinal.removeAllViews();
            }
        });


        for (int i = 0; i <= Obj_Orders_Actions_List.size() - 1; i++) {
            final int iFinal = i;

            final View v = View.inflate(context, R.layout.orderfollowup_listview_item, null);

            CurrentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LinearFinal.addView(v);
                }
            });

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {


                    final TextView lblTime = (TextView) v.findViewById(R.id.lblTime);
                    final TextView lblAction = (TextView) v.findViewById(R.id.lblAction);

                    final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy h:mm a", ObjSettings.getCurrentLanguageId() == 1 ? new Locale("ar") : new Locale("en"));

                    CurrentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblTime.setText(sdf.format(Obj_Orders_Actions_List.get(iFinal).getActionDate()));
                        }
                    });


                    final Actions ObjAction = new ActionsDB().Select(Obj_Orders_Actions_List.get(iFinal).getActionId(), ObjSettings.getCurrentLanguageId());
                    CurrentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblAction.setText(ObjAction.getName());
                        }
                    });

                }
            });
            t.start();
        }

    }
}
