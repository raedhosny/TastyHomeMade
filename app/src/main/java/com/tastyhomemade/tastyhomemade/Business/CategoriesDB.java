package com.tastyhomemade.tastyhomemade.Business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raed on 11/28/2016.
 */

public class CategoriesDB {

    public List<Categories> SelectAll (int p_iLanguageId)
    {
        try {

            java.sql.Connection ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement("EXECUTE SP_Categories_SelectAll @LanguageId=?");
            stmt.setInt(1,p_iLanguageId);
            ResultSet ObjResultSet = stmt.executeQuery();
            List<Categories> ObjCategoriesList = new ArrayList<Categories>();

            String sAll = "";
            if (p_iLanguageId==1)
                sAll = "--الكل--";
            else if (p_iLanguageId==2)
                sAll = "--All--";

            ObjCategoriesList.add(0,new Categories(-1,p_iLanguageId,sAll));

            while (ObjResultSet.next())
            {
                Categories ObjCategory = new Categories();
                ObjCategory .setId(ObjResultSet.getInt("id"));
                ObjCategory .setLanguageId(ObjResultSet.getInt("LanguageId"));
                ObjCategory .setName(ObjResultSet.getString("Name"));
                ObjCategoriesList.add(ObjCategory);
            }


            return ObjCategoriesList;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return new ArrayList<Categories>();

    }

}