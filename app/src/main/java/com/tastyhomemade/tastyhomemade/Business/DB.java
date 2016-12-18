package com.tastyhomemade.tastyhomemade.Business;

import java.sql.DriverManager;

/**
 * Created by raed on 11/23/2016.
 */

public class DB {
    public void DataAccess()
    {

    }

    public java.sql.Connection CreateConnection ()  {
        try {
            ConnectionProperties MyProperties =  new ConnectionProperties();
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            java.sql.Connection myConnection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ MyProperties.IP+":"+ MyProperties.Port+"/DB_A1543F_tastyhomemade;user=" + MyProperties.UserName + ";password=" + MyProperties.Password);

            return myConnection;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }


}
