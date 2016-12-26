package com.tastyhomemade.tastyhomemade.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tastyhomemade.tastyhomemade.Adapter.NearestFoodsAdapter;
import com.tastyhomemade.tastyhomemade.Business.Foods;
import com.tastyhomemade.tastyhomemade.Business.FoodsDB;
import com.tastyhomemade.tastyhomemade.Business.OnGetDistance;
import com.tastyhomemade.tastyhomemade.Business.User;
import com.tastyhomemade.tastyhomemade.Business.UserDB;
import com.tastyhomemade.tastyhomemade.Others.Settings;
import com.tastyhomemade.tastyhomemade.Others.Utils;
import com.tastyhomemade.tastyhomemade.Others.WaitDialog;
import com.tastyhomemade.tastyhomemade.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raed on 12/3/2016.
 */

public class NearestFoodsFragment extends Fragment {

    Settings ObjSettings;
    List<Foods> ObjFoodsList= null;
    int iTotalSize = 0;
    int iUpdatedSize = 0;
    WaitDialog ObjWaitDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nearestfoods, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ObjWaitDialog= new WaitDialog(getContext());
        ObjSettings = new Settings(getContext());

        FillData();

    }

    private void FillData() {

        ObjWaitDialog.ShowDialog();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ObjFoodsList = new ArrayList<Foods>();
                    List<Foods> ObjUnknowFoodsList = new ArrayList<Foods>();
                    ObjFoodsList.addAll(new FoodsDB().SelectByCategoryId( -1,new Settings(getActivity()).getCurrentLanguageId()));
                    iTotalSize = ObjFoodsList.size();


                    for (int i=0;i<ObjFoodsList.size();i++)
                    {

                        User ObjFoodMakerUser =  new UserDB().Select(ObjFoodsList.get(i).getUserId());
                        User ObjCustomerUser = new UserDB().Select(new Settings(getContext()).getUserId());

                        final int j = i;
                        OnGetDistance ObjOnGetDistance = new OnGetDistance() {
                            @Override
                            public void GetDistance(String result) {
                                ObjFoodsList.get(j).setDistance(result);
                                iUpdatedSize ++;
                            }
                        };
                        Utils.GoogleMapClassDistance  ObjGoogleMapClassDistance = new Utils().new GoogleMapClassDistance(ObjOnGetDistance,ObjSettings);
                        ObjGoogleMapClassDistance.execute(ObjCustomerUser.getCurrentLocation_Latitude(),ObjCustomerUser.getCurrentLocation_Longitude(),ObjFoodMakerUser.getCurrentLocation_Latitude(),ObjFoodMakerUser.getCurrentLocation_Longitude());

                    }

                    while (iTotalSize != iUpdatedSize)
                    {
                    }

                    List<Foods> Indeces = new ArrayList<Foods>();

                    for (int i=0;i<ObjFoodsList.size();i++) {
                        if ((ObjFoodsList.get(i).getDistance() .equals("Unknown"))
                                ||
                                (ObjFoodsList.get(i).getDistance() .equals("غير معروف"))

                                )
                        {
                          Indeces.add(ObjFoodsList.get(i));
                        }
                    }

                    for (int i =Indeces.size()-1;i>=0;i--)
                    {
                        ObjUnknowFoodsList.add(Indeces.get(i));
                        ObjFoodsList.remove(Indeces.get(i));
                    }

                    // Now Arrange
                    for (int i = 0; i < ObjFoodsList.size() - 1; i++)
                    {
                        for (int j = 0; j < ObjFoodsList.size() - i - 1; j++)
                        {
                            if (Integer.parseInt(ObjFoodsList.get(j).getDistance()) > Integer.parseInt(ObjFoodsList.get(j+1).getDistance())) /* For decreasing order use < */
                            {
                                Foods ObjFoodswap = ObjFoodsList.get(j);
                                ObjFoodsList.set(j,ObjFoodsList.get(j+1));
                                ObjFoodsList.set(j+1,ObjFoodswap);
                            }
                        }
                    }

                    ObjFoodsList.addAll(ObjUnknowFoodsList);



                    final NearestFoodsAdapter ObjFoodsListAdapter = new NearestFoodsAdapter(getContext(), ObjFoodsList);
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
