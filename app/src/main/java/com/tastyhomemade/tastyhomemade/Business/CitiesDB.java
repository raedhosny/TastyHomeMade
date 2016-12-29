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
        java.sql.Connection ObjConnection = null;
        try {

           ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement("EXECUTE SP_Cities_SelectAll @LanguageId=?");
            stmt.setEscapeProcessing(true);
            stmt.setQueryTimeout(60);
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
                ObjCity.setId(ObjResultSet.getInt("Cityid"));
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
        finally {
            try
            {
                ObjConnection.close();
            }
            catch (Exception ex)
            {

            }
        }

        return new ArrayList<Cities>();


    }


    public Cities Select (int p_iId, int p_iLanguageId)
    {
        java.sql.Connection ObjConnection = null;
        try {

            ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement("EXECUTE SP_Cities_SelectAll @Id=?,@LanguageId=?");
            stmt.setEscapeProcessing(true);
            stmt.setQueryTimeout(60);
            stmt.setInt(1,p_iLanguageId);
            ResultSet ObjResultSet = stmt.executeQuery();

            Cities  ObjCity = new Cities();

            if (ObjResultSet.next())
            {
                ObjCity.setId(ObjResultSet.getInt("Cityid"));
                ObjCity.setLanguageId(ObjResultSet.getInt("LanguageId"));
                ObjCity.setName(ObjResultSet.getString("Name"));
            }


            return ObjCity;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            try
            {
                ObjConnection.close();
            }
            catch (Exception ex)
            {

            }
        }

        return new Cities();


    }
}
