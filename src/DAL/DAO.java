package DAL;

import com.mysql.jdbc.MysqlParameterMetadata;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jagesh on 05/30/2016.
 */
public class DAO
{
    //Acquiring mysql connection through Driver or adapter...
    public static Connection getConnection() throws ClassNotFoundException, SQLException
    {
        String driver = "com.mysql.jdbc.Driver";
        String connection = "jdbc:mysql://localhost:3306/movieanalysis";
        String user = "jagesh";
        String password = "jagesh007";

        Class.forName(driver);
        Connection con = DriverManager.getConnection(connection, user, password);
//        if (con.isClosed())
//        {
//            con.close();
//        }
        return con;
    }

    //to display mysql table data on HTMl file as DataTable display
    public static ArrayList getTable(String sql, MysqlParameterMetadata[] parm)
    {
        ArrayList list = new ArrayList();
        Statement stm = null;
        String query = sql;
        ResultSet rs = null;
        try {
            stm = (Statement) getConnection();
            rs = stm.executeQuery(query);
            while (rs.next())
            {
                //Need to populate data, now will hold only first column as being 0,i will do in a moment
                list.add(rs.getRow());
            }
        }catch (Exception e)
        { System.out.println("My SQL Erroe: "+ e);}

        return list;
    }

    //For insert, updte, delete on mysql database
    public static int IUD(String sql)
    {
        Statement stm =null;
        String query = sql;
        int i=0;
        try {
            stm = (Statement) getConnection();
            i = stm.executeUpdate(query);
        }catch (Exception e)
        {System.out.println("MY SQL Error: "+e);}

        return i;
    }

}
