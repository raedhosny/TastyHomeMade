package com.tastyhomemade.tastyhomemade.Business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raed on 11/23/2016.
 */

public class RegisterTypesDB {

    public List<RegisterTypes> SelectAll(int p_iLanguageId) {
        java.sql.Connection MyConnection =null;
        try {
            MyConnection = new DB().CreateConnection();
            PreparedStatement stmt = MyConnection.prepareStatement("EXECUTE SP_RegisterTypes_SelectAll @LanguageId=?");

            stmt.setInt(1, p_iLanguageId);
            ResultSet ObjResultSet = stmt.executeQuery();


            List<RegisterTypes> ObjRegisterTypesList = new ArrayList<RegisterTypes>();

            String sAll = "";
            if (p_iLanguageId == 1)
                sAll = "--اختر--";
            else if (p_iLanguageId == 2)
                sAll = "--Select--";

            ObjRegisterTypesList.add(0, new RegisterTypes(-1, p_iLanguageId, sAll));

            while (ObjResultSet.next()) {
                RegisterTypes ObjRegisterTypes = new RegisterTypes();
                ObjRegisterTypes.setId(ObjResultSet.getInt("RegisterTypesId"));
                ObjRegisterTypes.setLanguageId(ObjResultSet.getInt("LanguageId"));
                ObjRegisterTypes.setName(ObjResultSet.getString("Name"));
                ObjRegisterTypesList.add(ObjRegisterTypes);
            }


            return ObjRegisterTypesList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                MyConnection.close();
            }
            catch (Exception ex)
            {

            }
        }

        return new ArrayList<RegisterTypes>();
    }

    public RegisterTypes Select(int p_iId, int p_iLanguageId) {
        try {
            java.sql.Connection MyConnection = new DB().CreateConnection();
            PreparedStatement stmt = MyConnection.prepareStatement("EXECUTE SP_RegisterTypes_Select @Id=?,@LanguageId=?");
            stmt.setInt(1, p_iId);
            stmt.setInt(2, p_iLanguageId);
            ResultSet ObjResultSet = stmt.executeQuery();
            RegisterTypes ObjRegisterType = new RegisterTypes();

            if (ObjResultSet.next()) {

                RegisterTypes ObjRegisterTypes = new RegisterTypes();
                ObjRegisterTypes.setId(ObjResultSet.getInt(1));
                ObjRegisterTypes.setLanguageId(ObjResultSet.getInt(3));
                ObjRegisterTypes.setName(ObjResultSet.getString(4));
                return ObjRegisterTypes;
            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new RegisterTypes();
    }
}
