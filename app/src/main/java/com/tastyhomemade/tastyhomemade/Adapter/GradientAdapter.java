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
import com.tastyhomemade.tastyhomemade.Business.Foods_Additions;
import com.tastyhomemade.tastyhomemade.Business.Foods_AdditionsDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.R;

import java.util.List;

/**
 * Created by Raed on 12/3/2016.
 */

public class GradientAdapter extends BaseAdapter {

    List<Foods_Additions> Obj_Foods_Additions_List;
    Context context;

    public GradientAdapter(Context p_context,List<Foods_Additions> p_Foods_Additions_List)
    {
        context = p_context;
        Obj_Foods_Additions_List = p_Foods_Additions_List;
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
        for (Foods_Additions Obj_Foods_Addtions : Obj_Foods_Additions_List)
        {
            if (Obj_Foods_Addtions .getId() == i)
                return Obj_Foods_Addtions.getId();
        }
        return -1;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.control_dropdown_remove,null);
        final TextView txtDropDownItem  = (TextView) v.findViewById(R.id.txtDropDownItem);
        ImageView imgDropDownRemove = (ImageView) v.findViewById(R.id.imgDropDownRemove);
        final int iPostition = i;
        imgDropDownRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int iFoodId = Obj_Foods_Additions_List.get(iPostition).getFoodId();
                final int iAdditionId = Obj_Foods_Additions_List.get(iPostition).getAdditionId();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Foods_AdditionsDB().Delete(iFoodId,iAdditionId);
                    }
                });
                t.start();
                Obj_Foods_Additions_List.remove(iPostition);
                notifyDataSetChanged();



            }
        });

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int iAdditionId = Obj_Foods_Additions_List.get(iPostition).getAdditionId();
                final Additions ObjAddition = new AdditionsDB().Select(iAdditionId,new Settings(context).getCurrentLanguageId());
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtDropDownItem.setText(ObjAddition.getName());
                    }
                });

            }
        });

        t.start();
        return v;
    }
}
