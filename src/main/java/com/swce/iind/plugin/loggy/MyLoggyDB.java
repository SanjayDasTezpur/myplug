package com.swce.iind.plugin.loggy;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Created by sanjayda on 6/25/2018 at 5:29 PM
 */
public class MyLoggyDB {
    private static MyLoggyDB _instance = new MyLoggyDB();

    private static final String MYSQL_JDBC_DRIVER_NAME = "jdbc:mysql";
    private static final String MYSQL_JDBC_DRIVER_TYPE = "com.mysql.jdbc.Driver";

    private static final String DATABASE_HOST = "inlvt28.iind.intel.com";
    private static final int DEFAULT_DATABASE_PORT = 3306;

    private static final String USERNAME = "logger";
    private static final String PASSWORD = "Loggy@28";
    private static final String DB = "/logger";

    private static final String FULL_DATABASE_URL = MYSQL_JDBC_DRIVER_NAME + "://" + DATABASE_HOST
            + ":" + DEFAULT_DATABASE_PORT + DB;

    private MyLoggyDB() {
        try
        {
            Class.forName(MYSQL_JDBC_DRIVER_TYPE);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static MyLoggyDB getInstance()
    {
        return _instance;
    }
    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(FULL_DATABASE_URL, USERNAME, PASSWORD);
    }
    public Collection<Object[]> executeStatement(String statement, boolean bGetResults) throws Exception
    {
        Collection<Object[]> res = new ArrayList<Object[]>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try
        {
            con = getConnection();
            stmt = con.prepareStatement(statement);
            stmt.execute();
            if (!bGetResults) return null;
            rs = stmt.getResultSet();
            while (rs.next())
            {
                int count = rs.getMetaData().getColumnCount();
                Object[] arrObj = new Object[count];
                for (int i = 1; i <= count; i++)
                {
                    arrObj[i - 1] = rs.getObject(i);
                }
                res.add(arrObj);
            }
            return res;
        }
        catch (Exception e)
        {
            throw new Exception("Error executing <" + statement + ">", e);
        }
        finally
        {
            if (null != rs) rs.close();
            if (null != stmt) stmt.close();
            if (null != con) con.close();
        }
    }

}
