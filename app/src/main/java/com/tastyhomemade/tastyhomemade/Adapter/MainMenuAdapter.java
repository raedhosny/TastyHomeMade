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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raed on 11/18/2016.
 */

public class MainMenuAdapter extends BaseAdapter{

    private Context context;
    ArrayList LoadedRecords = new ArrayList();



    private List<MainMenuItem> FilteredItemsList;
    private List<MainMenuItem> AllItems;

    public MainMenuAdapter(Context context, List<MainMenuItem> p_Filtereditems, List<MainMenuItem> p_AllItems ) {
        this.context = context;
        FilteredItemsList = p_Filtereditems;
        AllItems = p_AllItems;
    }

    @Override
    public int getCount() {
        return FilteredItemsList.size();
    }

    @Override
    public MainMenuItem getItem(int i) {
        return FilteredItemsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (Utils.IsDataLoadedBefore(i,LoadedRecords))
            return view;
        LoadedRecords.add(i);

        View v = View.inflate (context,R.layout.main_menu_item,null);
        TextView lblItem = (TextView) v.findViewById(R.id.lblItem);
        lblItem.setText(FilteredItemsList.get(i).getName());

        lblItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sSelectedItem  =((TextView)view).getText().toString();
                new Utils().ShowActivity ((MainActivity)context,AllItems,sSelectedItem);
                ((DrawerLayout)((MainActivity)context).findViewById(R.id.Drawer_Layout)).closeDrawer(((MainActivity)context).findViewById(R.id.Linear_SideMenu));;

            }
        });

        return v;
    }



}
