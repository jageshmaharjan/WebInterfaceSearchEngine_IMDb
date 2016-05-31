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
public class BLLGenre
{
    public List<String> getAllGenreByMovieID(int movieid) throws SQLException, ClassNotFoundException
    {
        List<String> genlst = new ArrayList<>();

        Connection con = DAO.getConnection();

        String sql ="SELECT * FROM movie_genre WHERE movieid =?  ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,movieid);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            int genid = rs.getInt("genreid");
            String sql_gen = "SELECT * FROM genre WHERE idgenre =? ";
            PreparedStatement ps_gen = con.prepareStatement(sql_gen);
            ps_gen.setInt(1,genid);
            ResultSet rs_gen = ps_gen.executeQuery();
            while (rs_gen.next())
            {
                genlst.add(rs_gen.getString("genreName"));
            }
        }
        return genlst;
    }
}
