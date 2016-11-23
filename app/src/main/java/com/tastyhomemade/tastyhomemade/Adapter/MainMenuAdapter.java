package com.tastyhomemade.tastyhomemade.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.tastyhomemade.tastyhomemade.Business.MainMenuItem;
import com.tastyhomemade.tastyhomemade.Fragment.ProfileFragment;
import com.tastyhomemade.tastyhomemade.Fragment.SettingsFragment;
import com.tastyhomemade.tastyhomemade.MainActivity;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;
import java.util.List;

/**
 * Created by Raed on 11/18/2016.
 */

public class MainMenuAdapter extends BaseAdapter{

    private Context context;



    private List<MainMenuItem> ItemsList;

    public MainMenuAdapter(Context context, List<MainMenuItem> items) {
        this.context = context;
        ItemsList = items;
    }

    @Override
    public int getCount() {
        return ItemsList.size();
    }

    @Override
    public MainMenuItem getItem(int i) {
        return ItemsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate (context,R.layout.main_menu_item,null);
        TextView lblItem = (TextView) v.findViewById(R.id.lblItem);
        lblItem.setText(ItemsList.get(i).getName());

        lblItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sSelectedItem  =((TextView)view).getText().toString();
                new Utils().ShowActivity ((MainActivity)context,ItemsList,sSelectedItem);
                ((DrawerLayout)((MainActivity)context).findViewById(R.id.Drawer_Layout)).closeDrawer(((MainActivity)context).findViewById(R.id.lvMainMenu));;

            }
        });

        return v;
    }


//    private void ShowActivity(String sSelectedItem) {
//        FragmentManager Manager = ((MainActivity)context).getSupportFragmentManager();
//
//        Manager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        FragmentTransaction Transaction = Manager.beginTransaction();
//
//
//
//        if (sSelectedItem== ItemsList.get(0).getName())
//        {
//            Transaction.replace(R.id.main_content ,new ProfileFragment());
//
//            Transaction.commit();
//        }
//        else if (sSelectedItem== ItemsList.get(1).getName())
//        {
//            Transaction.replace(R.id.main_content ,new SettingsFragment());
//            Transaction.commit();
//        }
//
//
//    }
}
