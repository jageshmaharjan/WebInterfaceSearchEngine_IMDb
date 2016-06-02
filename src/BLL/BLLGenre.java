package BLL;

import DAL.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jagesh on 05/30/2016.
 */
public class BLLGenre
{
    public Map<Integer, List<String>> getAllGenreByMovieID(List<Integer> movieIds) throws SQLException, ClassNotFoundException
    {
        Map<Integer, List<String>> result = new HashMap<>();

        String query = "SELECT genreName FROM genre,movie_genre WHERE genre.idgenre = movie_genre.genreid AND movie_genre.movieid=?";
        try(Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);)
        {
            for (Integer movieid : movieIds)
            {
                ps.setInt(1,movieid);
                ResultSet rs = ps.executeQuery();
                List<String> gennames = new ArrayList<String>();
                while (rs.next())
                {
                    gennames.add(rs.getString("genreName"));
                }
                result.put(movieid,gennames);
            }
        }
        return result;
    }
}
