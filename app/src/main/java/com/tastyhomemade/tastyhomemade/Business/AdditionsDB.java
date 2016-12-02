package com.tastyhomemade.tastyhomemade.Business;

import android.util.Base64;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Raed on 12/3/2016.
 */

public class AdditionsDB {

    public int InsertUpdate (Additions p_ObjAdditions)
    {
        try {

            java.sql.Connection ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Additions_InsertUpdate \n" +
                            "   @Id=?\n" +
                            "  ,@LanguageId=?\n" +
                            "  ,@Name=?"

            );
            stmt.setInt(1,p_ObjAdditions.getId());
            stmt.setInt(2,p_ObjAdditions.getLanguageId());
            stmt.setString(3,p_ObjAdditions.getName());

            ResultSet ObjResultSet = stmt.executeQuery();

            if (ObjResultSet.next())
                return ObjResultSet.getInt(0);
            return -1;


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return -1;


    }
}
