package com.tastyhomemade.tastyhomemade.Business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raed on 12/12/2016.
 */

public class ActionsDB {

    public Actions Select (int p_iId,int p_iLanguageId)  {
        java.sql.Connection ObjConnection =null;
        try {

            ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement("EXECUTE SP_Actions_Select\n" +
                    "   @Id=?\n" +
                    "  ,@LanguageId=?");
            stmt.setInt(1,p_iId);
            stmt.setInt(2,p_iLanguageId);
            ResultSet ObjResultSet = stmt.executeQuery();



            List<Categories> ObjCategoriesList = new ArrayList<Categories>();

            if  (ObjResultSet.next())
            {
                Actions ObjAction = new Actions();
                ObjAction .setId(ObjResultSet.getInt("id"));
                ObjAction .setLanguageId(ObjResultSet.getInt("LanguageId"));
                ObjAction .setName(ObjResultSet.getString("Name"));
                return ObjAction;
            }


            return new Actions();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
          try {
              ObjConnection.close();
          }
          catch (Exception ex)
          {

          }
        }

        return new Actions();

    }
}
