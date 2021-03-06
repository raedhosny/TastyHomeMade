package com.tastyhomemade.tastyhomemade.Business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raed on 12/12/2016.
 */

public class Orders_ActionsDB {

    public int InsertUpdate (Orders_Actions p_Orders_Actions)
    {
        java.sql.Connection ObjConnection = null;
        try {

            ObjConnection = new DB().CreateConnection();
            ObjConnection.createStatement();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Orders_Actions_InsertUpdate\n" +
                            "   @Id=?\n" +
                            "  ,@OrderId=?\n" +
                            "  ,@ActionId=?\n" +
                            "  ,@ActionDate=?\n"
            );
            stmt.setEscapeProcessing(true);
            stmt.setQueryTimeout(60);
            stmt.setInt(1,p_Orders_Actions.getId());
            stmt.setInt(2,p_Orders_Actions.getOrderId());
            stmt.setInt(3,p_Orders_Actions.getActionId());
            stmt.setTimestamp(4,p_Orders_Actions.getActionDate());

            ResultSet ObjResultSet = stmt.executeQuery();


            if (ObjResultSet.next())
            {
                return ObjResultSet.getInt(1);
            }
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


    public List<Orders_Actions> SelectByOrderId (int p_iOrderId,int p_iOrderBy)
    {
        java.sql.Connection ObjConnection = null;
        try {

            ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Orders_Actions_SelectByOrderId\n" +
                            "   @OrderId=?," +
                            "   @Orderby=?"

            );
            stmt.setEscapeProcessing(true);
            stmt.setQueryTimeout(60);
            stmt.setInt(1,p_iOrderId);
            stmt.setInt(2,p_iOrderBy);



            ResultSet ObjResultSet = stmt.executeQuery();

            List<Orders_Actions> Obj_Orders_Actions_List = new ArrayList<Orders_Actions>();

            while (ObjResultSet.next())
            {
                Orders_Actions  Obj_Orders_Actions = new Orders_Actions();

                Obj_Orders_Actions.setId(ObjResultSet.getInt("Id"));
                Obj_Orders_Actions.setOrderId(ObjResultSet.getInt("OrderId"));
                Obj_Orders_Actions.setActionId(ObjResultSet.getInt("ActionId"));
                Obj_Orders_Actions.setActionDate(ObjResultSet.getTimestamp("ActionDate"));
                Obj_Orders_Actions_List.add(Obj_Orders_Actions);

            }
            return Obj_Orders_Actions_List;


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

    public Orders_Actions Select (int p_iId)
    {
        java.sql.Connection ObjConnection = null;
        try {

            ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Orders_Actions_Select\n" +
                            "   @Id=?"

            );
            stmt.setEscapeProcessing(true);
            stmt.setQueryTimeout(60);
            stmt.setInt(1,p_iId);

            ResultSet ObjResultSet = stmt.executeQuery();

           Orders_Actions Obj_Orders_Actions = new Orders_Actions();

            while (ObjResultSet.next())
            {
                Obj_Orders_Actions.setId(ObjResultSet.getInt("Id"));
                Obj_Orders_Actions.setOrderId(ObjResultSet.getInt("OrderId"));
                Obj_Orders_Actions.setActionId(ObjResultSet.getInt("ActionId"));
                Obj_Orders_Actions.setActionDate(ObjResultSet.getTimestamp("ActionDate"));
              return Obj_Orders_Actions;

            }
            return new Orders_Actions();


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
