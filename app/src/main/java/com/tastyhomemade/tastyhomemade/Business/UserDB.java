package com.tastyhomemade.tastyhomemade.Business;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by raed on 11/22/2016.
 */

public class UserDB {
    public UserDB() {
    }

    public int InsertUpdate (User p_User)
    {
        try {

            java.sql.Connection myConnection = new DB().CreateConnection();
            PreparedStatement stmt = myConnection.prepareStatement("EXECUTE [dbo].[SP_Users_InsertUpdate] \n" +
                    "   @Id=?\n" +
                    "  ,@Name=?\n" +
                    "  ,@Username=?\n" +
                    "  ,@Password=?\n" +
                    "  ,@Email=?\n" +
                    "  ,@RegisterTypeId=?\n" +
                    "  ,@CurrentLocation_Longitude=?\n" +
                    "  ,@CurrentLocation_Latitude=?\n" +
                    "  ,@CityId=?\n" +
                    "  ,@District=?\n" +
                    "  ,@Street=?\n" +
                    "  ,@Building=?\n" +
                    "  ,@Apartment=?\n" +
                    "  ,@IsActive=?\n" +
                    "  ,@ActivationCode=?\n"+
                    ",@IsHaveDelivery=?");


            stmt.setInt(1,p_User.getId()); //id
            stmt.setString(2,p_User.getName()); //name
            stmt.setString(3,p_User.getUsername()); //username
            stmt.setString(4,p_User.getPassword()); //password
            stmt.setString(5,p_User.getEmail()); //email
            stmt.setInt(6,p_User.getRegisterTypeId()); //registertypeid
            stmt.setDouble(7,p_User.getCurrentLocation_Longitude()); //current location longtitude
            stmt.setDouble(8,p_User.getCurrentLocation_Latitude()); // current location latitude
            stmt.setInt (9,p_User.getCityId()); // cityid
            stmt.setString(10,p_User.getDistrict()); //district
            stmt.setString(11,p_User.getStreet()); //street
            stmt.setString(12,p_User.getBuilding());// building
            stmt.setString(13,p_User.getApartment()); // apartment
            stmt.setBoolean(14,p_User.getActive());// IsActive
            stmt.setString(15,p_User.getActivationCode()); //activationcode
            stmt.setBoolean(16,p_User.isHaveDelivary()); //IsHaveDelivary
            ResultSet res =  stmt.executeQuery();
            res.next();
            int iResult = res.getInt(1);
            return iResult;
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return -1;

    }

    public User Login(String p_sUsername,String p_sPassword)
    {
        User ObjUser = new User();
        ObjUser.setId(-1);

        try {

            java.sql.Connection myConnection = new DB().CreateConnection();
            PreparedStatement stmt = myConnection.prepareStatement("EXECUTE [SP_users_Login] \n" +
                    "   @Username=?\n" +
                    "  ,@Password=?\n");


            stmt.setString(1,p_sUsername); //id
            stmt.setString(2,p_sPassword); //name



            ResultSet res =  stmt.executeQuery();
            if (res.next()) {
                ObjUser.setId(res.getInt(1)); //id
                ObjUser.setName(res.getString(2)); //name
                ObjUser.setUsername(res.getString(3)); //username
                ObjUser.setPassword(res.getString(4)); //password
                ObjUser.setEmail(res.getString(5)); //email
                ObjUser.setRegisterTypeId(res.getInt(6)); //registertypeid
                ObjUser.setCurrentLocation_Longitude(res.getDouble(7)); //current location longtitude
                ObjUser.setCurrentLocation_Latitude(res.getDouble(8)); // current location latitude
                ObjUser.setCityId(res.getInt(9)); // cityid
                ObjUser.setDistrict(res.getString(10)); //district
                ObjUser.setStreet(res.getString(11)); //street
                ObjUser.setBuilding(res.getString(12));// building
                ObjUser.setApartment(res.getString(13)); // apartment
                ObjUser.setActive(res.getBoolean(14));// IsActive
                ObjUser.setActivationCode(res.getString(15)); //activationcode
                ObjUser.setHaveDelivary(res.getBoolean(16)); //activationcode
                return ObjUser;
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ObjUser;
    }

}
