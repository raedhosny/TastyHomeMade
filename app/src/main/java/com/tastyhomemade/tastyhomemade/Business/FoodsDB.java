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
        java.sql.Connection ObjConnection= null;

        try {

            ObjConnection = new DB().CreateConnection();
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
            stmt.setString(9,  p_ObjFoods.getPhoto());
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
        finally {
            try
            {
                ObjConnection.close();
            }
            catch (Exception ex)
            {

            }
        }

        return -1;


    }

    public Foods Select (int p_iId,int p_iLanguageId)
    {
        java.sql.Connection ObjConnection = null;
        try {

            ObjConnection = new DB().CreateConnection();
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
                ObjFood.setPhoto(ObjResultSet.getString(6));
                ObjFood.setPrice(ObjResultSet.getFloat(7));
                ObjFood.setLanguageId(ObjResultSet.getInt(11));
                ObjFood.setName(ObjResultSet.getString(12));
                ObjFood.setDescription(ObjResultSet.getString(13));
                return ObjFood;
            }
            return new Foods();


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

        return null;


    }

    public List<Foods> SelectByCategoryId (int p_iCategoryId,int p_iLanguageId)
    {
        java.sql.Connection ObjConnection= null;
        try {

            ObjConnection = new DB().CreateConnection();
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
                ObjFood.setPhoto(ObjResultSet.getString(6));
                ObjFood.setPrice(ObjResultSet.getFloat(7));
                ObjFood.setLanguageId(ObjResultSet.getInt(11));
                ObjFood.setName(ObjResultSet.getString(12));
                ObjFood.setDescription(ObjResultSet.getString(13));

                ObjFoodList.add(ObjFood);
            }
            return  ObjFoodList;


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

        return null;


    }

    public List<Foods> SearchbyCustomer (String p_sName, int p_iCategoryId,int p_iUserId)
    {
        java.sql.Connection ObjConnection = null;
        try {

            ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Foods_SearchByCustomer\n" +
                            "   @Name=?\n" +
                            "  ,@CategoryId=?\n" +
                            "  ,@UserId=?\n"

            );
            stmt.setString(1,p_sName);
            stmt.setInt(2,p_iCategoryId);
            stmt.setInt(3,p_iUserId);


            ResultSet ObjResultSet = stmt.executeQuery();



            List<Foods> ObjFoodList = new ArrayList<Foods>();

            while(ObjResultSet.next())
            {
                Foods ObjFood = new Foods();

                ObjFood.setId(ObjResultSet.getInt("Id"));
                ObjFood.setCategoryId(ObjResultSet.getInt("CategoryId"));
                ObjFood.setUserId(ObjResultSet.getInt("UserId"));
                ObjFood.setRequestTimeFrom(ObjResultSet.getTime("RequestTimeFrom"));
                ObjFood.setRequestTimeTo(ObjResultSet.getTime("RequestTimeTo"));
                ObjFood.setPhoto(ObjResultSet.getString("Photo"));
                ObjFood.setPrice(ObjResultSet.getFloat("Price"));
                ObjFood.setLanguageId(ObjResultSet.getInt("LanguageId"));
                ObjFood.setName(ObjResultSet.getString("Name"));
                ObjFood.setDescription(ObjResultSet.getString("Description"));

                ObjFoodList.add(ObjFood);
            }
            return  ObjFoodList;


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

        return null;


    }

    public List<Foods> SearchbyFoodMaker (String p_sName, int p_iCategoryId,int p_iUserId)
    {
        java.sql.Connection ObjConnection = null;
        try {

            ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Foods_SearchByFoodMaker\n" +
                            "   @Name=?\n" +
                            "  ,@CategoryId=?\n" +
                            "  ,@UserId=?\n"

            );
            stmt.setString(1,p_sName);
            stmt.setInt(2,p_iCategoryId);
            stmt.setInt(3,p_iUserId);


            ResultSet ObjResultSet = stmt.executeQuery();



            List<Foods> ObjFoodList = new ArrayList<Foods>();

            while(ObjResultSet.next())
            {
                Foods ObjFood = new Foods();

                ObjFood.setId(ObjResultSet.getInt("Id"));
                ObjFood.setCategoryId(ObjResultSet.getInt("CategoryId"));
                ObjFood.setUserId(ObjResultSet.getInt("UserId"));
                ObjFood.setRequestTimeFrom(ObjResultSet.getTime("RequestTimeFrom"));
                ObjFood.setRequestTimeTo(ObjResultSet.getTime("RequestTimeTo"));
                ObjFood.setPhoto(ObjResultSet.getString("Photo"));
                ObjFood.setPrice(ObjResultSet.getFloat("Price"));
                ObjFood.setLanguageId(ObjResultSet.getInt("LanguageId"));
                ObjFood.setName(ObjResultSet.getString("Name"));
                ObjFood.setDescription(ObjResultSet.getString("Description"));

                ObjFoodList.add(ObjFood);
            }
            return  ObjFoodList;


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

        return null;


    }

    public List<Foods> SelectByRequestCount (int p_iLanguageId)
    {
        java.sql.Connection ObjConnection = null;
        try {

            ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Foods_SelectByRequestCount\n" +
                            "   @LanguageId=?"

            );
            stmt.setInt(1,p_iLanguageId);


            ResultSet ObjResultSet = stmt.executeQuery();



            List<Foods> ObjFoodList = new ArrayList<Foods>();

            while(ObjResultSet.next())
            {
                Foods ObjFood = new Foods();

                ObjFood.setId(ObjResultSet.getInt("Id"));
                ObjFood.setCategoryId(ObjResultSet.getInt("CategoryId"));
                ObjFood.setUserId(ObjResultSet.getInt("UserId"));
                ObjFood.setRequestTimeFrom(ObjResultSet.getTime("RequestTimeFrom"));
                ObjFood.setRequestTimeTo(ObjResultSet.getTime("RequestTimeTo"));
                ObjFood.setPhoto(ObjResultSet.getString("Photo"));
                ObjFood.setPrice(ObjResultSet.getFloat("Price"));
                ObjFood.setLanguageId(ObjResultSet.getInt("LanguageId"));
                ObjFood.setName(ObjResultSet.getString("Name"));
                ObjFood.setDescription(ObjResultSet.getString("Description"));
                ObjFood.setNumberOfRequestsCount(ObjResultSet.getInt("RequestCount"));

                ObjFoodList.add(ObjFood);
            }
            return  ObjFoodList;


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

        return null;


    }

}
