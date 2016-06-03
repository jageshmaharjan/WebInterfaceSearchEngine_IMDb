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
public class BLLActor
{
    public Map<Integer, List<String> > getAllActorsFromMovies(List<Integer> movieIds) throws SQLException, ClassNotFoundException
    {
        Map<Integer, List<String>> results = new HashMap<>(movieIds.size());

        String query = "SELECT Name FROM stars, movie_actor WHERE movie_actor.starID = stars.idstars AND movie_actor.movieID=?";

        try (Connection conn = DAO.getConnection();
             PreparedStatement ps = conn.prepareStatement(query))
        {
            for (Integer movieId : movieIds)
            {
                ps.setInt(1, movieId);
                ResultSet rs = ps.executeQuery();

                List<String> actors = new ArrayList<>();

                while (rs.next())
                {
                    actors.add(rs.getString("Name"));
                }

                results.put(movieId, actors);
                rs.close();
            }
        }
        return results;
    }
}
