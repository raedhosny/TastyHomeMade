package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.tastyhomemade.tastyhomemade.Adapter.EditFoodsAdapter;
import com.tastyhomemade.tastyhomemade.Adapter.HomeFoodsAdapter;
import com.tastyhomemade.tastyhomemade.Business.Categories;
import com.tastyhomemade.tastyhomemade.Business.CategoriesDB;
import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.ViewMode;
import com.tastyhomemade.tastyhomemade.Others.WaitDialog;
import com.tastyhomemade.tastyhomemade.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Raed on 12/3/2016.
 */

public class ListOfFoodsandDrinksFragment extends Fragment {

    EditText txtName;
    Spinner ddlCategories;
    Settings ObjSettings;
    Button btnSearch;
    List<Categories> ObjCategoriesList;
    WaitDialog ObjWaitDialog;
    ViewMode ObjCurrentViewMode;
    String sKeyword ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_of_foods_and_drinks, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtName = (EditText) view.findViewById(R.id.txtName);
        ddlCategories = (Spinner) view.findViewById(R.id.Include_ddlCategories).findViewById(R.id.ddlSpinner);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        ObjSettings = new Settings(getContext());

        ObjWaitDialog = new WaitDialog(getContext());



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ObjWaitDialog.ShowDialog();
                Categories ObjCategories = ObjCategoriesList.get(ddlCategories.getSelectedItemPosition());
                FillData(txtName.getText().toString().trim(),ObjCategories.getId());
            }
        });


        if (getArguments() .size() == 1)
        {
            ObjCurrentViewMode = ViewMode.valueOf(getArguments().getString("ViewMode"));
        }
        else  if (getArguments() .size() == 2)
        {
            ObjCurrentViewMode = ViewMode.valueOf(getArguments().getString("ViewMode"));
            sKeyword = getArguments().getString("Keyword");

        }

        FillCategories();
        if (ObjCurrentViewMode == ViewMode.NormalMode) {
            FillData(txtName.getText().toString(), -1);
        }
        else if (ObjCurrentViewMode == ViewMode.SearchMode) {
            FillData(sKeyword);
        }

    }

    private void FillCategories() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                ObjCategoriesList = new CategoriesDB().SelectAll(ObjSettings.getCurrentLanguageId());
                List<String> ObjCategoriesTemp = new ArrayList<String>();

                for (Categories ObjCategory : ObjCategoriesList )
                {
                    ObjCategoriesTemp.add(ObjCategory.getName());
                }

               final  ArrayAdapter<String> ObjCategoriesAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item, Arrays.copyOf(ObjCategoriesTemp.toArray(),ObjCategoriesTemp.size(),String[].class));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ddlCategories.setAdapter(ObjCategoriesAdapter);
                    }
                });


            }
        });
        t.start();

    }

    private void FillData(String p_sName,  int p_iCategoryId) {

        final String sName = p_sName;
        final int iCategoryId = p_iCategoryId;
        ObjWaitDialog.ShowDialog();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Foods> ObjFoodsList = new ArrayList<Foods>();
                    ObjFoodsList.addAll(new FoodsDB().SearchbyFoodMaker(sName,iCategoryId, ObjSettings.getUserId(),ObjSettings.getCurrentLanguageId() ));
                    getActivity().runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    ObjWaitDialog.HideDialog();
                                }
                            }
                    );
                     EditFoodsAdapter ObjFoodsListAdapter = new EditFoodsAdapter(getContext(), ObjFoodsList);
                     LinearLayout lvMainFoodsList = (LinearLayout) getActivity().findViewById(R.id.lvMainFoodsList);
                    ObjFoodsListAdapter.FillList(lvMainFoodsList);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        t.start();


    }

    private void FillData(String p_sKeyword) {

        final String sKeywordFinal  = p_sKeyword;
        ObjWaitDialog.ShowDialog();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Foods> ObjFoodsList = new ArrayList<Foods>();
                            ObjFoodsList.addAll(new FoodsDB().GlobalSearchByFoodMaker(sKeywordFinal,ObjSettings.getCurrentLanguageId(), ObjSettings.getUserId() ));

                    getActivity().runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                  //  lvMainFoodsList.setAdapter(ObjFoodsListAdapter);
                                    ObjWaitDialog.HideDialog();
                                }
                            }
                    );

                    final EditFoodsAdapter ObjFoodsListAdapter = new EditFoodsAdapter(getContext(), ObjFoodsList);
                    final LinearLayout lvMainFoodsList = (LinearLayout) getActivity().findViewById(R.id.lvMainFoodsList);
                    ObjFoodsListAdapter.FillList(lvMainFoodsList );

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        t.start();


    }
}
