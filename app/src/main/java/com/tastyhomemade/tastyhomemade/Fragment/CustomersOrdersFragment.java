package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tastyhomemade.tastyhomemade.Business.Orders;
import com.tastyhomemade.tastyhomemade.Business.OrdersDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.R;

import java.util.List;

/**
 * Created by raed on 12/20/2016.
 */

public class CustomersOrdersFragment extends Fragment {

    Settings ObjSettings;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customersorders,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObjSettings = new Settings(getContext());

        FillData();

    }

    private void FillData ()
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Orders>  ObjOrdersList = new OrdersDB().SelectByFoodMakerId(ObjSettings.getUserId());

            }
        });


    }


}
