package com.tastyhomemade.tastyhomemade.Business;

import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
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
                    "  ,@Price=?\n" +
                    "   ,@IsVisible=?\n"

                    );
            stmt.setInt(1,p_ObjFoods.getId());
            stmt.setInt(2,p_ObjFoods.getLanguageId());
            stmt.setInt(3,p_ObjFoods.getCategoryId());
            stmt.setString(4,p_ObjFoods.getName());
            stmt.setString(5,p_ObjFoods.getDescription());
            stmt.setInt(6,p_ObjFoods.getUserId());
            stmt.setTime(7,p_ObjFoods.getRequestTimeFrom());
            stmt.setTime(8,p_ObjFoods.getRequestTimeTo());
            stmt.setString(9, Base64.encodeToString( p_ObjFoods.getPhoto(),Base64.DEFAULT));
            stmt.setFloat(10,p_ObjFoods.getPrice());
            stmt.setBoolean(11,p_ObjFoods.isVisible());

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

    public Foods Select (int p_iId,int p_iLanguageId)
    {
        try {

            java.sql.Connection ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Foods_Select \n" +
                            "   @Id=?\n" +
                            "  ,@LanguageId=?\n"

            );
            stmt.setInt(1,p_iId);
            stmt.setInt(2,p_iLanguageId);


            ResultSet ObjResultSet = stmt.executeQuery();

            Foods ObjFood = new Foods();

            if (ObjResultSet.next())
            {
                ObjFood.setId(ObjResultSet.getInt(1));
                ObjFood.setCategoryId(ObjResultSet.getInt(2));
                ObjFood.setUserId(ObjResultSet.getInt(3));
                ObjFood.setRequestTimeFrom(ObjResultSet.getTime(4));
                ObjFood.setRequestTimeTo(ObjResultSet.getTime(5));
                ObjFood.setPhoto(Base64.decode(ObjResultSet.getString(6),Base64.DEFAULT));
                ObjFood.setPrice(ObjResultSet.getFloat(7));
                ObjFood.setLanguageId(ObjResultSet.getInt(10));
                ObjFood.setName(ObjResultSet.getString(11));
                ObjFood.setDescription(ObjResultSet.getString(12));
                return ObjFood;
            }
            return new Foods();


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;


    }

    public List<Foods> SelectByCategoryId (int p_iCategoryId,int p_iLanguageId)
    {
        try {

            java.sql.Connection ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Foods_SelectByCategoryId \n" +
                            "   @CategoryId=?\n" +
                            "  ,@LanguageId=?"

            );
            stmt.setInt(1,p_iCategoryId);
            stmt.setInt(2,p_iLanguageId);


            ResultSet ObjResultSet = stmt.executeQuery();

            List<Foods> ObjFoodList = new ArrayList<Foods>();

           while(ObjResultSet.next())
            {
                Foods ObjFood = new Foods();

                ObjFood.setId(ObjResultSet.getInt(1));
                ObjFood.setCategoryId(ObjResultSet.getInt(2));
                ObjFood.setUserId(ObjResultSet.getInt(3));
                ObjFood.setRequestTimeFrom(ObjResultSet.getTime(4));
                ObjFood.setRequestTimeTo(ObjResultSet.getTime(5));
                ObjFood.setPhoto(Base64.decode(ObjResultSet.getString(6),Base64.DEFAULT));
                ObjFood.setPrice(ObjResultSet.getFloat(7));
                ObjFood.setLanguageId(ObjResultSet.getInt(10));
                ObjFood.setName(ObjResultSet.getString(11));
                ObjFood.setDescription(ObjResultSet.getString(12));

                ObjFoodList.add(ObjFood);
            }
            return  ObjFoodList;


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;


    }
}
