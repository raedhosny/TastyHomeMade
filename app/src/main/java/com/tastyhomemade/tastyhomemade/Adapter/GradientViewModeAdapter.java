package com.tastyhomemade.tastyhomemade.Adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tastyhomemade.tastyhomemade.Business.Additions;
import com.tastyhomemade.tastyhomemade.Business.AdditionsDB;
import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.Business.Foods_Additions;
import com.tastyhomemade.tastyhomemade.Business.OnDataChangedListener;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Raed on 12/3/2016.
 */

public class GradientViewModeAdapter extends BaseAdapter {

    List<Foods_Additions> Obj_Foods_Additions_List;
    Context context;
    Settings ObjSettings;
    OnDataChangedListener mOnDataChangedListener;


    public GradientViewModeAdapter(Context p_context, List<Foods_Additions> p_Foods_Additions_List) {
        context = p_context;
        Obj_Foods_Additions_List = p_Foods_Additions_List;
        ObjSettings = new Settings(p_context);
    }

    @Override
    public int getCount() {
        return Obj_Foods_Additions_List.size();
    }

    @Override
    public Object getItem(int i) {
        return Obj_Foods_Additions_List.get(i);
    }

    @Override
    public long getItemId(int i) {
        for (Foods_Additions Obj_Foods_Addtions : Obj_Foods_Additions_List) {
            if (Obj_Foods_Addtions.getId() == i)
                return Obj_Foods_Addtions.getId();
        }
        return -1;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
             v = View.inflate(context, R.layout.gradient_viewmode_item, null);
            final TextView txtDropDownItem = (TextView) v.findViewById(R.id.Include_control_dropdown_addremove).findViewById(R.id.txtDropDownItem);
            final TextView lblGradient = (TextView) v.findViewById(R.id.lblGradient);
            final TextView lblPrice = (TextView) v.findViewById(R.id.lblPrice);
            final TextView lblCurrency = (TextView) v.findViewById(R.id.lblCurrency);
            txtDropDownItem.setEnabled(false);
            txtDropDownItem.setText("0");
            ImageView imgDropDownDecrease = (ImageView) v.findViewById(R.id.imgDropDownDecrease);
            ImageView imgDropDownIncrease = (ImageView) v.findViewById(R.id.imgDropDownIncrease);
            final int iPostition = i;
            imgDropDownDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int iCount = Integer.parseInt(txtDropDownItem.getText().toString().trim());
                    if (iCount == 0)
                        return;
                    else
                        iCount = iCount - 1;
                    final int iCountFinal = iCount;

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final Additions ObjAddition = new AdditionsDB().Select(Obj_Foods_Additions_List.get(iPostition).getAdditionId(), ObjSettings.getCurrentLanguageId());
                            ((AppCompatActivity) context).runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            txtDropDownItem.setText(String.valueOf(iCountFinal));
                                            lblPrice.setText(String.valueOf(iCountFinal * ObjAddition.getPrice()));
                                            if (mOnDataChangedListener != null)
                                                mOnDataChangedListener.onDataChanged();
                                        }
                                    }
                            );

                        }
                    });
                    t.start();


                }
            });

            imgDropDownIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int iCount = Integer.parseInt(txtDropDownItem.getText().toString().trim());
                    iCount = iCount + 1;
                    final int iCountFinal = iCount;

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final Additions ObjAddition = new AdditionsDB().Select(Obj_Foods_Additions_List.get(iPostition).getAdditionId(), ObjSettings.getCurrentLanguageId());
                            ((AppCompatActivity) context).runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            txtDropDownItem.setText(String.valueOf(iCountFinal));
                                            lblPrice.setText(String.valueOf(iCountFinal * ObjAddition.getPrice()));
                                            if (mOnDataChangedListener != null)
                                                mOnDataChangedListener.onDataChanged();

                                        }
                                    }
                            );

                        }
                    });
                    t.start();
                }
            });

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    final Additions ObjAddition = new AdditionsDB().Select(Obj_Foods_Additions_List.get(iPostition).getAdditionId(), ObjSettings.getCurrentLanguageId());
                    final Foods ObjFood = new FoodsDB().Select(Obj_Foods_Additions_List.get(iPostition).getFoodId(), ObjSettings.getCurrentLanguageId());

                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lblGradient.setText(ObjAddition.getName());
                            lblPrice.setText("0"); // Must be initiated as Zero first time not item price
                            lblCurrency.setText(Utils.GetResourceName(context, R.string.Currency, ObjSettings.getCurrentLanguageId()));
                        }
                    });

                }
            });

            t.start();

        }
        return v;
    }


    public void setmOnDataChangedListener(OnDataChangedListener mOnDataChangedListener) {
        this.mOnDataChangedListener = mOnDataChangedListener;
    }


}
