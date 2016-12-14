package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tastyhomemade.tastyhomemade.Adapter.OrdersFollowupAdapter;
import com.tastyhomemade.tastyhomemade.Business.Orders;
import com.tastyhomemade.tastyhomemade.Business.OrdersDB;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import java.util.List;

/**
 * Created by raed on 12/13/2016.
 */

public class OrdersFollowUpFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ordersfollowup, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ListView lvOrders = (ListView) view.findViewById(R.id.lvOrders);
        Bundle ObjBundle = getArguments();
        final int iUserId = ObjBundle.getInt("UserId");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Orders> ObjOrdersList = new OrdersDB().SelectByUserId(iUserId);
                final OrdersFollowupAdapter ObjOrdersFollowUpAdapter = new OrdersFollowupAdapter(getActivity(), ObjOrdersList);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        lvOrders.setAdapter(ObjOrdersFollowUpAdapter);
                        Utils.setListViewHeightBasedOnChildren(lvOrders);
                    }
                });
            }
        });
        t.start();

    }
}
