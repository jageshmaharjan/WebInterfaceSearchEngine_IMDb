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
public class BLLDirector
{
    public Map<Integer, List<String>> getAllDirectorByMovieID(List<Integer> movieIds) throws SQLException, ClassNotFoundException
    {
        Map<Integer, List<String> > result = new HashMap<>();

        String query = "SELECT Name FROM director, movie_director WHERE movie_director.directorID = director.iddirector AND movie_director.movieID = ?";

        try(Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query);)
        {
            for (Integer movieid : movieIds)
            {
                ps.setInt(1,movieid);
                ResultSet rs = ps.executeQuery();
                List<String> directors = new ArrayList<>();
                while (rs.next())
                {
                    directors.add(rs.getString("Name"));
                }
                result.put(movieid,directors);
            }
        }
        return result;
    }
}
