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

import com.tastyhomemade.tastyhomemade.R;



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
        btnNext = (Button)view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
