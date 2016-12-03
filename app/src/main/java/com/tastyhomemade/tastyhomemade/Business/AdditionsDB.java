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
                            "  ,@Name=?\n" +
                            "  ,@Price=?\n" +
                            "  ,@Photo=?\n"

            );
            stmt.setInt(1,p_ObjAdditions.getId());
            stmt.setInt(2,p_ObjAdditions.getLanguageId());
            stmt.setString(3,p_ObjAdditions.getName());
            stmt.setFloat(4,p_ObjAdditions.getPrice());
            stmt.setString(5,Base64.encodeToString(p_ObjAdditions.getPhoto(),Base64.DEFAULT));

            ResultSet ObjResultSet = stmt.executeQuery();

            if (ObjResultSet.next())
                return ObjResultSet.getInt(1);
            return -1;


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return -1;


    }

    public Additions Select (int p_iId,int p_iLanguageId)
    {
        try {

            java.sql.Connection ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Additions_Select\n" +
                            "   @Id=?\n" +
                            "  ,@LanguageId=?"

            );
            stmt.setInt(1,p_iId);
            stmt.setInt(2,p_iLanguageId);

            ResultSet ObjResultSet = stmt.executeQuery();

            Additions ObjAddition = new Additions();
            if (ObjResultSet.next())
            {
                ObjAddition.setId(ObjResultSet.getInt(1));
                ObjAddition.setLanguageId(ObjResultSet.getInt(2));
                ObjAddition.setName(ObjResultSet.getString(3));
                ObjAddition.setPhoto(Base64.decode(ObjResultSet.getString(4),Base64.DEFAULT));
                ObjAddition.setPrice(ObjResultSet.getFloat(5));
                return ObjAddition;

            }
            return new Additions();


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;


    }
}
