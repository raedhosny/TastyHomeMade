package com.tastyhomemade.tastyhomemade.Business;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raed on 12/1/2016.
 */

public class FoodsDB {

    public FoodsDB()
    {

    }

    public int InsertUpdate (Foods p_ObjFoods)
    {
        try {

            java.sql.Connection ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE [dbo].[SP_Foods_InsertUpdate] \n" +
                    "   @Id=?\n" +
                    "  ,@LanguageId=?\n" +
                    "  ,@CategoryId=?\n" +
                    "  ,@Name=?\n" +
                    "  ,@Description=?\n" +
                    "  ,@UserId=?\n" +
                    "  ,@RequestTimeFrom=?\n" +
                    "  ,@RequestTimeTo=?\n" +
                    "  ,@Photo=?\n" +
                    "  ,@Price=?"

                    );
            stmt.setInt(1,p_ObjFoods.getId());
            stmt.setInt(2,p_ObjFoods.getLanguageId());
            stmt.setInt(3,p_ObjFoods.getCategoryId());
            stmt.setString(4,p_ObjFoods.getName());
            stmt.setString(5,p_ObjFoods.getDescription());
            stmt.setInt(6,p_ObjFoods.getUserId());
            stmt.setTime(7,p_ObjFoods.getRequestTimeFrom());
            stmt.setTime(8,p_ObjFoods.getRequestTimeTo());
            stmt.setBinaryStream(9,  new ByteArrayInputStream(p_ObjFoods.getPhoto()));
            stmt.setFloat(10,p_ObjFoods.getPrice());

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
