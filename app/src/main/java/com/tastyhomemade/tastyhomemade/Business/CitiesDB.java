package com.tastyhomemade.tastyhomemade.Business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raed on 11/23/2016.
 */

public class CitiesDB {
    public CitiesDB()
    {}

    public List<Cities> SelectAll (int p_iLanguageId)
    {
        try {

           java.sql.Connection ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement("EXECUTE SP_Cities_SelectAll @LanguageId=?");
            stmt.setInt(1,p_iLanguageId);
            ResultSet ObjResultSet = stmt.executeQuery();
            List<Cities> ObjCitiesList = new ArrayList<Cities>();

            String sAll = "";
            if (p_iLanguageId==1)
                sAll = "--الكل--";
            else if (p_iLanguageId==2)
                sAll = "--All--";

            ObjCitiesList.add(0,new Cities(-1,p_iLanguageId,sAll));

            while (ObjResultSet.next())
            {
                Cities ObjCity = new Cities();
                ObjCity.setId(ObjResultSet.getInt("id"));
                ObjCity.setLanguageId(ObjResultSet.getInt("LanguageId"));
                ObjCity.setName(ObjResultSet.getString("Name"));
                ObjCitiesList.add(ObjCity);
            }


            return ObjCitiesList;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return new ArrayList<Cities>();


    }
}
