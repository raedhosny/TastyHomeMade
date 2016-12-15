package com.tastyhomemade.tastyhomemade.Business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raed on 12/3/2016.
 */

public class Foods_AdditionsDB {

    public int InsertUpdate (Foods_Additions p_Foods_Additions)
    {
        java.sql.Connection ObjConnection= null;
        try {

            ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Foods_Additions_InsertUpdate \n" +
                    "   @Id=?\n" +
                            ",@FoodId=?\n" +
                            ",@AdditionId=?"
            );
            stmt.setInt(1,p_Foods_Additions.getId());
            stmt.setInt(2,p_Foods_Additions.getFoodId());
            stmt.setInt(3,p_Foods_Additions.getAdditionId());

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

    public List<Foods_Additions> SelectByFoodId (int p_iFoodId)
    {
        java.sql.Connection ObjConnection= null;
        try {

            ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Foods_Additions_SelectByFoodsId\n" +
                            "   @FoodId=?"

            );

            stmt.setInt(1,p_iFoodId);

            ResultSet ObjResultSet = stmt.executeQuery();

            List<Foods_Additions> ObjFoodsAdditionsList = new ArrayList<Foods_Additions>();

            while(ObjResultSet.next())
            {
                Foods_Additions ObjFoodAddition = new Foods_Additions();

                ObjFoodAddition.setId(ObjResultSet.getInt("Id"));
                ObjFoodAddition.setFoodId(ObjResultSet.getInt("FoodId"));
                ObjFoodAddition.setAdditionId(ObjResultSet.getInt("AdditionId"));


                ObjFoodsAdditionsList.add(ObjFoodAddition);
            }
            return  ObjFoodsAdditionsList;


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
