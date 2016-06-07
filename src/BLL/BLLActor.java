package BLL;

import BO.BOActor;
import DAL.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by jagesh on 05/30/2016.
 */
public class BLLActor
{
    public List<BOActor> getAllActorsFromMovies(List<Integer> movieIds) throws SQLException, ClassNotFoundException
    {
//        Map<Integer, List<String>> results = new HashMap<>();
        List<BOActor> boActors = new ArrayList<>(); //1

        String query = "SELECT Name FROM stars, movie_actor WHERE movie_actor.starID = stars.idstars AND movie_actor.movieID=?";

        try (Connection conn = DAO.getConnection();
             PreparedStatement ps = conn.prepareStatement(query))
        {
            for (Integer movieid : movieIds)
            {
                ps.setInt(1, movieid);
                ResultSet rs = ps.executeQuery();

                List<String> actors = new ArrayList<>();
                BOActor actor = new BOActor();  //1

                actor.setMovieId(movieid);
                while (rs.next())
                {
                    actors.add(rs.getString("Name"));
                }
                actor.setActors(actors);

//                results.put(movieid, actors);
                boActors.add(actor);

            }
        }
        return boActors;
    }
}
