package com.tastyhomemade.tastyhomemade.Adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class OrdersActionsAdapter extends BaseAdapter {

    List<Orders_Actions> Obj_Orders_Actions_List;
    Context context;
    Settings ObjSettings;

    public OrdersActionsAdapter(Context p_context, List<Orders_Actions> p_Orders_Actions_List) {
        context = p_context;
        Obj_Orders_Actions_List = p_Orders_Actions_List;
        ObjSettings = new Settings(p_context);
    }

    @Override
    public int getCount() {
        return Obj_Orders_Actions_List.size();
    }

    @Override
    public Orders_Actions getItem(int position) {
        return Obj_Orders_Actions_List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Obj_Orders_Actions_List.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v= convertView;
        if (v == null) {
            v = View.inflate(context, R.layout.orderfollowup_listview_item, null);

            final TextView lblTime = (TextView) v.findViewById(R.id.lblTime);
            final TextView lblAction = (TextView) v.findViewById(R.id.lblAction);

            final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy h:mm a", ObjSettings.getCurrentLanguageId() == 1 ? new Locale("ar") : new Locale("en"));

            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lblTime.setText(sdf.format(Obj_Orders_Actions_List.get(position).getActionDate()));
                }
            });


            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    final Actions ObjAction = new ActionsDB().Select(Obj_Orders_Actions_List.get(position).getActionId(), ObjSettings.getCurrentLanguageId());
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblAction.setText(ObjAction.getName());
                        }
                    });
                }
            });
            t.start();

        }
        return v;
    }
}
