package BLL;

import DAL.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jagesh on 05/30/2016.
 */
public class BLLActor
{
    public List<String> getAllActorsByMovie(int movieid) throws SQLException, ClassNotFoundException
    {
        List<String> actor_name = new ArrayList<>();

        Connection con = DAO.getConnection();
        String sql = "SELECT * FROM movie_actor WHERE movieID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,movieid);

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next())
        {
            int starid = resultSet.getInt("starID");
            String sql_act = "SELECT * FROM stars WHERE idstars=?";
            PreparedStatement prpstm = con.prepareStatement(sql_act);
            prpstm.setInt(1,starid);
            ResultSet rs_act = prpstm.executeQuery();
            while (rs_act.next())
            {
                actor_name.add(rs_act.getString("Name"));
            }
        }
        return actor_name;
    }

    public List<String> getAllActorUrlByMovie(int movieid) throws SQLException, ClassNotFoundException
    {
        List<String> actor_url = new ArrayList<>();
        Connection con = DAO.getConnection();
        String sql = "SELECT * FROM movie_actor WHERE movieID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,movieid);

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next())
        {
            int starid = resultSet.getInt("starID");
            String sql_url= "SELECT * FROM stars WHERE idstars =?";
            PreparedStatement ps_url = con.prepareStatement(sql_url);
            ps_url.setInt(1,starid);
            ResultSet rs_url = ps_url.executeQuery();
            while (rs_url.next())
            {
                actor_url.add(rs_url.getString("url"));
            }
        }
        return actor_url;
    }
}
