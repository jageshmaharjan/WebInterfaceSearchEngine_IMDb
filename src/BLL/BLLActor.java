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
    public ArrayList<String> getAllActorsByMovie(int movieid) throws SQLException, ClassNotFoundException
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

        return (ArrayList<String>) actor_name;
    }
}
