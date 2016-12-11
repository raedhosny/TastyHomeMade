package com.tastyhomemade.tastyhomemade.Business;

import android.util.Base64;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Raed on 12/6/2016.
 */

public class OrdersDB {

    public int InsertUpdate (Orders p_ObjOrder)
    {
        try {

            java.sql.Connection ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE [dbo].[SP_Orders_InsertUpdate] \n" +
                            "   @Id=?\n" +
                            "  ,@Food_Id=?\n" +
                            "  ,@User_Id=?\n" +
                            "  ,@RequestDate=?\n" +
                            "  ,@IsShippingToClient=?\n" +
                            "  ,@Shipping_Longitude=?\n" +
                            "  ,@Shipping_Latitude=?\n" +
                            "  ,@ShippingCountryId=?\n" +
                            "  ,@ShippingDistrict=?\n" +
                            "  ,@ShippingStreet=?\n" +
                            "  ,@ShippingBuilding=?\n" +
                            "  ,@ShippingApartment=?\n" +
                            "  ,@ShippingOtherDetails=?\n" +
                            "  ,@ShippingDeliveryDate=?\n" +
                            "  ,@NumberOfOrders=?\n" +
                            "  ,@OrderAddress=?\n"+
                            "  ,@IsCompleteOrder=?"
                            );


            stmt.setInt(1,p_ObjOrder.getId());
            stmt.setInt(2,p_ObjOrder.getFood_Id());
            stmt.setInt(3,p_ObjOrder.getUser_Id());
            stmt.setDate(4,p_ObjOrder.getRequestDate());
            stmt.setBoolean(5,p_ObjOrder.isShippingToClient());
            stmt.setDouble(6,p_ObjOrder.getShipping_Longitude());
            stmt.setDouble(7,p_ObjOrder.getShipping_Latitude());
            stmt.setInt(8,p_ObjOrder.getShippingCountryId());
            stmt.setString(9, p_ObjOrder.getShippingDistrict());
            stmt.setString(10,p_ObjOrder.getShippingStreet());
            stmt.setString(11,p_ObjOrder.getShippingBuilding());
            stmt.setString(12,p_ObjOrder.getShippingApartment());
            stmt.setString(13,p_ObjOrder.getShippingOtherDetails());
            stmt.setDate(14,p_ObjOrder.getShippingDeliveryDate());
            stmt.setInt(15,p_ObjOrder.getNumberOfOrders());
            stmt.setString(16,p_ObjOrder.getOrderAddress());
            stmt.setBoolean(17,p_ObjOrder.isCompleteOrder());

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

    public Orders Select (int p_iId)
    {
        try {

            java.sql.Connection ObjConnection = new DB().CreateConnection();
            PreparedStatement stmt = ObjConnection.prepareStatement(
                    "EXECUTE SP_Orders_Select \n" +
                            "   @Id=?"

            );
            stmt.setInt(1,p_iId);



            ResultSet ObjResultSet = stmt.executeQuery();

            Orders ObjOrder = new Orders();

            if (ObjResultSet.next())
            {
                ObjOrder.setId(ObjResultSet.getInt(1));
                ObjOrder.setFood_Id(ObjResultSet.getInt(2));
                ObjOrder.setUser_Id(ObjResultSet.getInt(3));
                ObjOrder.setRequestDate(ObjResultSet.getDate(4));
                ObjOrder.setShippingToClient(ObjResultSet.getBoolean(5));
                ObjOrder.setShipping_Longitude(ObjResultSet.getDouble(6));
                ObjOrder.setShipping_Latitude(ObjResultSet.getDouble(7));
                ObjOrder.setShippingCountryId(ObjResultSet.getInt(8));
                ObjOrder.setShippingDistrict(ObjResultSet.getString(9));
                ObjOrder.setShippingStreet(ObjResultSet.getString(10));
                ObjOrder.setShippingBuilding(ObjResultSet.getString(11));
                ObjOrder.setShippingApartment(ObjResultSet.getString(12));
                ObjOrder.setShippingOtherDetails(ObjResultSet.getString(13));
                ObjOrder.setShippingDeliveryDate(ObjResultSet.getDate(14));
                ObjOrder.setNumberOfOrders(ObjResultSet.getInt(15));
                ObjOrder.setOrderAddress(ObjResultSet.getString(16));
                ObjOrder.setCompleteOrder(ObjResultSet.getBoolean(17));

                return ObjOrder;
            }
            return new Orders();


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }
}
