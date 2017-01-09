package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tastyhomemade.tastyhomemade.Adapter.OrdersFollowupAdapter;
import com.tastyhomemade.tastyhomemade.Business.Orders;
import com.tastyhomemade.tastyhomemade.Business.OrdersDB;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.Others.WaitDialog;
import com.tastyhomemade.tastyhomemade.R;

import java.util.List;

/**
 * Created by raed on 12/13/2016.
 */

public class OrdersFollowUpFragment extends Fragment {

    WaitDialog ObjWaitDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ordersfollowup, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LinearLayout lvOrders = (LinearLayout) view.findViewById(R.id.lvOrders);
        Bundle ObjBundle = getArguments();
        final int iUserId = ObjBundle.getInt("UserId");
        ObjWaitDialog = new WaitDialog(getContext());
        ObjWaitDialog.ShowDialog();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Orders> ObjOrdersList = new OrdersDB().SelectByUserId(iUserId);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // lvOrders.setAdapter(ObjOrdersFollowUpAdapter);
                        // Utils.setListViewHeightBasedOnChildren(lvOrders);
                        ObjWaitDialog.HideDialog();
                    }
                });
                OrdersFollowupAdapter ObjOrdersFollowUpAdapter = new OrdersFollowupAdapter(getActivity(), ObjOrdersList);
                ObjOrdersFollowUpAdapter.FillList(lvOrders);

            }
        });
        t.start();

    }
}
