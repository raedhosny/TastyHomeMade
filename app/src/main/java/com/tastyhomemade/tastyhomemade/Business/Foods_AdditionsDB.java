package com.tastyhomemade.tastyhomemade.Business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Raed on 12/3/2016.
 */

public class Foods_AdditionsDB {

    public int InsertUpdate (Foods_Additions p_Foods_Additions)
    {
        try {

            java.sql.Connection ObjConnection = new DB().CreateConnection();
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

        return -1;


    }

}
