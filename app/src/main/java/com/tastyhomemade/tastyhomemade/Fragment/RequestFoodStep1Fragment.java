package com.tastyhomemade.tastyhomemade.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.Business.Orders;
import com.tastyhomemade.tastyhomemade.Business.OrdersDB;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import java.util.Calendar;


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
    int iFoodId =-1;
    EditText txtNumberOfOrders;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_requestfoodanddrink_step1,null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageFood = (ImageView) view.findViewById(R.id.include_details_item).findViewById(R.id.ImageFood);
        lblPrice = (TextView)view.findViewById(R.id.include_details_item).findViewById(R.id.lblPrice);
        ImageView ImageDeliverable = (ImageView)view.findViewById(R.id.include_details_item).findViewById(R.id.ImageDeliverable);
        FoodRating  = (RatingBar)view.findViewById(R.id.include_details_item).findViewById(R.id.FoodRating);
        lblName = (TextView)view.findViewById(R.id.include_details_item).findViewById(R.id.lblName);
        lblDescription = (TextView)view.findViewById(R.id.include_details_item).findViewById(R.id.lblDescription);
        lvGradients = (ListView)view.findViewById(R.id.lvGradients);
        txtAdditionalGradients = (EditText) view.findViewById(R.id.txtAdditionalGradients);
        txtNumberOfOrders = (EditText)view.findViewById(R.id.txtNumberOfOrders);
        btnNext = (Button)view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        Bundle ObjBundle =  getArguments();
        iFoodId = ObjBundle.getInt("FoodId");
    }

    @Override
    public void onClick(View view) {

        if (txtNumberOfOrders.getText().toString().trim().length() == 0)
        {
            Toast.makeText(getActivity(), Utils.GetResourceName(getActivity(),R.string.Error_PleaseEnterNumberOfOrders,new Settings(getActivity()).getCurrentLanguageId()),Toast.LENGTH_LONG).show();
            return ;
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {


        Calendar c = Calendar.getInstance();
        Foods ObjFood = new FoodsDB().Select(iFoodId,new Settings(getContext()).getCurrentLanguageId());
        User ObjFoodMakerUser = new UserDB().Select(ObjFood.getUserId());


        Orders ObjOrder = new Orders(-1,
                                    iFoodId,
                                    new Settings(getContext()).getUserId(),
                                    new java.sql.Date(Calendar.getInstance().getTime().getYear(),Calendar.getInstance().getTime().getMonth(),Calendar.getInstance().getTime().getDay()),
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
                                    false
                                    );

        int iOrderId = new OrdersDB().InsertUpdate(ObjOrder);
        // Go to step 2
        new Utils().ShowActivity(getContext(),null,"RequestFormStep2",String.valueOf(iOrderId));
            }
        });

        t.start();
    }
}
