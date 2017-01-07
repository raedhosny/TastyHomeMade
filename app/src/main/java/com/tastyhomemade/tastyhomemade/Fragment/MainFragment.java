package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tastyhomemade.tastyhomemade.Adapter.HomeFoodsAdapter;
import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.ViewMode;
import com.tastyhomemade.tastyhomemade.Others.WaitDialog;
import com.tastyhomemade.tastyhomemade.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raed on 12/3/2016.
 */

public class MainFragment extends Fragment {


    WaitDialog ObjWaitDialog = null;
    int iCategoryId = -1;
    String sKeyword = "";
    ViewMode ObjCurrentMode = null;
    Settings ObjSetting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ObjSetting = new Settings(getContext());

        if (getArguments().size() == 3) {
            ObjCurrentMode = ViewMode.valueOf(getArguments().getString("ViewMode"));
            iCategoryId = getArguments().getInt("CategoryId");
            sKeyword = getArguments().getString("Keyword");
        } else {
            ObjCurrentMode = ViewMode.valueOf(getArguments().getString("ViewMode"));
            iCategoryId = getArguments().getInt("CategoryId");
        }

        ObjWaitDialog = new WaitDialog(getContext());

        if (ObjCurrentMode == ViewMode.NormalMode)
            FillData(iCategoryId);
        else if (ObjCurrentMode == ViewMode.SearchMode)
            FillData(sKeyword);
    }

    List<Foods> ObjFoodsList = null;

    boolean IsComplete = false;

    private void FillData(int p_iCategoryId) {

      //  ObjWaitDialog.ShowDialog();

        final int iCategoryId = p_iCategoryId;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ObjFoodsList = new ArrayList<Foods>();
                    ObjFoodsList.addAll(new FoodsDB().SelectByCategoryId(iCategoryId, new Settings(getActivity()).getCurrentLanguageId()));
                    HomeFoodsAdapter ObjFoodsListAdapter = new HomeFoodsAdapter(getContext(), ObjFoodsList);
                    LinearLayout lvMainFoodsList = (LinearLayout) getActivity().findViewById(R.id.lvMainFoodsList);
                    ObjFoodsListAdapter.FillList(lvMainFoodsList);
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ObjWaitDialog.HideDialog();
//                        }
//                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        t.start();


    }

    private void FillData(String p_sKeyword) {

        //ObjWaitDialog.ShowDialog();

        final String sKeywordFinal = p_sKeyword;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Foods> ObjFoodsList = new ArrayList<Foods>();
                    ObjFoodsList.addAll(new FoodsDB().GlobalSearchByCustomer(sKeywordFinal, ObjSetting.getCurrentLanguageId()));
                    HomeFoodsAdapter ObjFoodsListAdapter = new HomeFoodsAdapter(getContext(), ObjFoodsList);
                    LinearLayout lvMainFoodsList = (LinearLayout) getActivity().findViewById(R.id.lvMainFoodsList);
                    ObjFoodsListAdapter.FillList(lvMainFoodsList);
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ObjWaitDialog.HideDialog();
//                        }
//                    });


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        t.start();


    }
}
