package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tastyhomemade.tastyhomemade.Adapter.HomeFoodsAdapter;
import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.MainActivity;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.WaitDialog;
import com.tastyhomemade.tastyhomemade.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raed on 12/3/2016.
 */

public class MainFragment extends Fragment {


    WaitDialog ObjWaitDialog = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final int iCategoryId = getArguments().getInt("CategoryId");

        ObjWaitDialog = new WaitDialog(getContext());

        FillData(iCategoryId);

    }

    private void FillData(int p_iCategoryId) {

        ObjWaitDialog.ShowDialog();

        final int iCategoryId = p_iCategoryId;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Foods> ObjFoodsList = new ArrayList<Foods>();
                    ObjFoodsList.addAll(new FoodsDB().SelectByCategoryId(iCategoryId, new Settings(getActivity()).getCurrentLanguageId()));
                    final HomeFoodsAdapter ObjFoodsListAdapter = new HomeFoodsAdapter(getContext(), ObjFoodsList);
                    final ListView lvMainFoodsList = (ListView) getActivity().findViewById(R.id.lvMainFoodsList);
                    getActivity().runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    lvMainFoodsList.setAdapter(ObjFoodsListAdapter);
                                    ObjWaitDialog.HideDialog();
                                }
                            }
                    );

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        t.start();


    }
}
