package com.tastyhomemade.tastyhomemade.Fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tastyhomemade.tastyhomemade.Adapter.GradientAdapter;
import com.tastyhomemade.tastyhomemade.Adapter.GradientViewModeAdapter;
import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.Business.Foods_Additions;
import com.tastyhomemade.tastyhomemade.Business.Foods_AdditionsDB;
import com.tastyhomemade.tastyhomemade.Business.OnDataChangedListener;
import com.tastyhomemade.tastyhomemade.Business.Orders;
import com.tastyhomemade.tastyhomemade.Business.OrdersDB;
import com.tastyhomemade.tastyhomemade.Business.Orders_Additions;
import com.tastyhomemade.tastyhomemade.Business.Orders_AdditionsDB;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.Others.WaitDialog;
import com.tastyhomemade.tastyhomemade.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Raed on 12/5/2016.
 */

public class RequestFoodStep1Fragment extends Fragment implements View.OnClickListener {

    ImageView ImageFood;
    TextView lblPrice;
    ImageView ImageDeliverable;
    RatingBar FoodRating;
    TextView lblName;
    TextView lblDescription;
    ListView lvGradients;
    EditText txtAdditionalGradients;
    TextView lblTotalPrice;
    Button btnNext;
    int iFoodId = -1;
    EditText txtNumberOfOrders;
    TextView lblTimeFromTo;
    TextView lblOrderCountPrice;
    List<Foods_Additions> ObjFoodsAdditionsList = new ArrayList<Foods_Additions>();
    WaitDialog ObjWaitDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_requestfoodanddrink_step1, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageFood = (ImageView) view.findViewById(R.id.include_details_item).findViewById(R.id.ImageFood);
        lblPrice = (TextView) view.findViewById(R.id.include_details_item).findViewById(R.id.lblPrice);
        FoodRating = (RatingBar) view.findViewById(R.id.include_details_item).findViewById(R.id.FoodRating);
        ImageDeliverable = (ImageView) view.findViewById(R.id.include_details_item).findViewById(R.id.ImageDeliverable);
        lblName = (TextView) view.findViewById(R.id.include_details_item).findViewById(R.id.lblName);
        lblDescription = (TextView) view.findViewById(R.id.include_details_item).findViewById(R.id.lblDescription);
        lblTimeFromTo = (TextView) view.findViewById(R.id.include_details_item).findViewById(R.id.lblTimeFromTo);
        lvGradients = (ListView) view.findViewById(R.id.lvGradients);
        lblTotalPrice = (TextView) view.findViewById(R.id.lblTotalPrice);
        txtAdditionalGradients = (EditText) view.findViewById(R.id.txtAdditionalGradients);
        txtNumberOfOrders = (EditText) view.findViewById(R.id.txtNumberOfOrders);
        ObjWaitDialog = new WaitDialog(getContext());

        /////////////////////////////////
        // On Number of orders changes //
        ////////////////////////////////
        txtNumberOfOrders.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CalculateTotalPrice();
            }
        });

        ///////////////////////////////
        // Number of Gradients changed
        //////////////////////////////


        btnNext = (Button) view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        lblOrderCountPrice = (TextView) view.findViewById(R.id.lblOrderCountPrice);


        Bundle ObjBundle = getArguments();
        iFoodId = ObjBundle.getInt("FoodId");
        ObjWaitDialog.ShowDialog();
        FillData(iFoodId);
    }

    @Override
    public void onClick(View view) {

        if (txtNumberOfOrders.getText().toString().trim().length() == 0) {
            Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(), R.string.Error_PleaseEnterNumberOfOrders, new Settings(getActivity()).getCurrentLanguageId()), Toast.LENGTH_LONG).show();
            return;
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


                Calendar c = Calendar.getInstance();
                Foods ObjFood = new FoodsDB().Select(iFoodId, new Settings(getContext()).getCurrentLanguageId());
                User ObjFoodMakerUser = new UserDB().Select(ObjFood.getUserId());

                // Insert Order
                Orders ObjOrder = new Orders(-1,
                        iFoodId,
                        new Settings(getContext()).getUserId(),
                        new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()),
                        ObjFoodMakerUser.isHaveDelivary(),
                        -1,
                        -1,
                        -1,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        Integer.parseInt(txtNumberOfOrders.getText().toString().trim()),
                        null,
                        false,
                        false
                );

                int iOrderId = new OrdersDB().InsertUpdate(ObjOrder);

                // Insert Order Gradients
                List<Foods_Additions> ObjFoodsAdditions = new Foods_AdditionsDB().SelectByFoodId(iFoodId);

                for (int i=0;i< lvGradients.getCount();i++)
                {
                    int iQuantity = Integer.parseInt (((TextView)lvGradients.getChildAt(i).findViewById(R.id.Include_control_dropdown_addremove).findViewById(R.id.txtDropDownItem)).getText().toString());
                    Orders_Additions Obj_Orders_Additions = new Orders_Additions();
                    Obj_Orders_Additions.setOrderId(iOrderId);
                    Obj_Orders_Additions.setAdditionId(ObjFoodsAdditions.get(i).getAdditionId());
                    Obj_Orders_Additions.setQuantity(iQuantity);
                    new Orders_AdditionsDB().InsertUpdate(Obj_Orders_Additions);
                }

                // Go to step 2
                new Utils().ShowActivity(getContext(), null, "RequestFormStep2", String.valueOf(iOrderId));
            }
        });

        t.start();
    }

    private void FillData(int p_FoodId) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                final Foods ObjFood = new FoodsDB().Select(iFoodId, new Settings(getContext()).getCurrentLanguageId());
                final User ObjUser = new UserDB().Select(ObjFood.getUserId());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblPrice.setText(String.valueOf(ObjFood.getPrice()) + " " + Utils.GetResourceName(getContext(), R.string.Currency, new Settings(getContext()).getCurrentLanguageId()));
                    }
                });

                byte[] Photo = Base64.decode(ObjFood.getPhoto(), Base64.DEFAULT);
                final Bitmap ObjBitmapTemp = BitmapFactory.decodeByteArray(Photo, 0, Photo.length);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageFood.setImageBitmap(ObjBitmapTemp);
                        lblName.setText(ObjFood.getName());
                        lblDescription.setText(ObjFood.getDescription());
                        if (ObjUser.isHaveDelivary())
                            ImageDeliverable.setVisibility(View.VISIBLE);
                        else
                            ImageDeliverable.setVisibility(View.GONE);


                        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");

                        String sTemp = Utils.GetResourceName(getContext(), R.string.RequestTimeFromTo, new Settings(getContext()).getCurrentLanguageId());

                        sTemp = sTemp.replace("[X]", sdf.format(ObjFood.getRequestTimeFrom()));
                        sTemp = sTemp.replace("[Y]", sdf.format(ObjFood.getRequestTimeTo()));
                        if (new Settings(getContext()).getCurrentLanguageId() == 1) {
                            sTemp = sTemp.replace("PM", "مساءا").replace("AM", "صباحا");
                        }
                        lblTimeFromTo.setText(sTemp);

                        ////////////////////////////////
                        //FoodRating Doo Ratting Here //
                        ////////////////////////////////

                    }
                });


                ObjFoodsAdditionsList = new Foods_AdditionsDB().SelectByFoodId(iFoodId);
                final GradientViewModeAdapter ObjGradientsAdapter = new GradientViewModeAdapter(getContext(), ObjFoodsAdditionsList);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvGradients.setAdapter(ObjGradientsAdapter);
                        Utils.setListViewHeightBasedOnChildren(lvGradients);
                        ObjGradientsAdapter.setmOnDataChangedListener(new OnDataChangedListener() {
                            @Override
                            public void onDataChanged() {
                                ObjWaitDialog.ShowDialog();
                                CalculateTotalPrice();

                            }
                        });
                        ObjWaitDialog.HideDialog();
                    }
                });


            }
        });

        t.start();
    }

    private void CalculateTotalPrice() {

        int iNumberOfOrders = 0;
        try {
            iNumberOfOrders = Integer.parseInt(txtNumberOfOrders.getText().toString().trim());
        } catch (Exception ex) {
            ex.printStackTrace();
            iNumberOfOrders = 0;
        }

        final int iNumberOfOrderFinal = iNumberOfOrders;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Foods ObjFoods = new FoodsDB().Select(iFoodId, new Settings(getContext()).getCurrentLanguageId());
                double dPrice = ObjFoods.getPrice();
                dPrice = dPrice * iNumberOfOrderFinal;
                final double dPriceFinal = dPrice;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblOrderCountPrice.setText(String.valueOf(dPriceFinal) + " " + Utils.GetResourceName(getContext(), R.string.Currency, new Settings(getContext()).getCurrentLanguageId()));
                    }
                });

                double dTotalGradientPrice = 0;
                for (int i = 0; i < lvGradients.getCount(); i++) {
                    final TextView lblPrice = (TextView) lvGradients.getChildAt(i).findViewById(R.id.lblPrice);
                    final double dGradientPrice = Double.parseDouble(lblPrice.getText().toString().trim());
                    dTotalGradientPrice += dGradientPrice;

                }
                final String sTotalPriceFinal = String.valueOf(dTotalGradientPrice + dPrice) + " " + Utils.GetResourceName(getContext(), R.string.Currency, new Settings(getContext()).getCurrentLanguageId());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lblTotalPrice.setText(sTotalPriceFinal);
                    }
                });

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ObjWaitDialog.HideDialog();
                }
            });
            }
        });

        t.start();
    }
}
