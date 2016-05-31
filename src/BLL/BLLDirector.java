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
public class BLLDirector
{
    public List<String> getAllDirectorByMovieID(int movieid) throws SQLException, ClassNotFoundException
    {
        List<String> dir_lst = new ArrayList<>();

        Connection con = DAO.getConnection();
        String sql = "SELECT * FROM movie_director WHERE movieID=? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,movieid);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            int dirid = rs.getInt("directorID");
            String sql_dir = "SELECT * FROM director WHERE iddirector= ?";
            PreparedStatement ps_dir = con.prepareStatement(sql_dir);
            ps_dir.setInt(1,dirid);
            ResultSet rs_dir = ps_dir.executeQuery();
            while (rs_dir.next())
            {
                dir_lst.add(rs_dir.getString("Name"));
            }
        }
        return dir_lst;
    }
}
