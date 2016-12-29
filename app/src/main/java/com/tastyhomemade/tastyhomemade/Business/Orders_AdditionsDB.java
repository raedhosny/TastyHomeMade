package com.tastyhomemade.tastyhomemade.Business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Raed on 12/9/2016.
 */

public class Orders_AdditionsDB {

    public int InsertUpdate (Orders_Additions p_Orders_Additions)
    {
        java.sql.Connection ObjConnection= null;
        try {

            ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE [SP_Orders_Additions_InsertUpdate] \n" +
                            "   @Id=?\n" +
                            "  ,@OrderId=?\n" +
                            "  ,@AdditionId=?\n" +
                            "  ,@Quantity=?"
            );
            stmt.setEscapeProcessing(true);
            stmt.setQueryTimeout(60);
            stmt.setInt(1,p_Orders_Additions.getId());
            stmt.setInt(2,p_Orders_Additions.getOrderId());
            stmt.setInt(3,p_Orders_Additions.getAdditionId());
            stmt.setInt(4,p_Orders_Additions.getQuantity());

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


    public Orders_Additions Select (int p_iId)
    {
        java.sql.Connection ObjConnection = null;
        try {

            ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                            "EXECUTE SP_Orders_Additions_Select\n" +
                            "   @Id"

            );

            stmt.setEscapeProcessing(true);
            stmt.setQueryTimeout(60);
            stmt.setInt(1,p_iId);

            ResultSet ObjResultSet = stmt.executeQuery();

            Orders_Additions Obj_Order_Addition = new Orders_Additions();

            if (ObjResultSet.next())
            {
                Obj_Order_Addition.setId(ObjResultSet.getInt(1));
                Obj_Order_Addition.setOrderId(ObjResultSet.getInt(2));
                Obj_Order_Addition.setAdditionId(ObjResultSet.getInt(3));
                Obj_Order_Addition.setQuantity(ObjResultSet.getInt(4));

                return Obj_Order_Addition;
            }
            return new Orders_Additions();


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
