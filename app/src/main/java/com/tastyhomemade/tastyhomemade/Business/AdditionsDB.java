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
        java.sql.Connection ObjConnection = null;
        try {

            ObjConnection = new DB().CreateConnection();
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
        finally {
            try {
                ObjConnection.close();
            }
            catch (Exception ex)
            {

            }
        }

        return -1;


    }

    public Additions Select (int p_iId,int p_iLanguageId)
    {
        java.sql.Connection ObjConnection = null;
        try {

            ObjConnection = new DB().CreateConnection();
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
                ObjAddition.setId(ObjResultSet.getInt("Id"));
                ObjAddition.setLanguageId(ObjResultSet.getInt("LanguageId"));
                ObjAddition.setName(ObjResultSet.getString("Name"));
                ObjAddition.setPhoto(Base64.decode(ObjResultSet.getString("Photo"),Base64.DEFAULT));
                ObjAddition.setPrice(ObjResultSet.getFloat("Price"));
                return ObjAddition;

            }
            return new Additions();


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
            catch (Exception ex) {}
        }

        return null;


    }
}
