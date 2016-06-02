package BLL;

import BO.BOMovie;
import DAL.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by jagesh on 05/30/2016.
 */
public class BLLMovies
{
    public List<BOMovie> getAllMoviesFromMovies(List<Integer> movieid) throws SQLException, ClassNotFoundException
    {
        List<BOMovie> results = new ArrayList<>(movieid.size());

        StringJoiner joiner = new StringJoiner(" OR ");

        for (Integer aMovieid : movieid)
        {
            joiner.add("MovieID=" + aMovieid);
        }

        String query = "SELECT * FROM movie WHERE " + joiner.toString();

        try (Connection connection = DAO.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query))
        {
            while (rs.next())
            {
                BOMovie bomovie = new BOMovie();

                bomovie.setMovieid(rs.getInt("MovieID"));
                bomovie.setMoviename(rs.getString("MovieName"));
                bomovie.setReleasedate(rs.getString("ReleaseDate"));
                bomovie.setRating(rs.getFloat("Rating"));
                bomovie.setStoryline(rs.getString("Storyline"));
                bomovie.setMovieurl(rs.getString("url"));
                bomovie.setCoverurl(rs.getString("coverpicurl"));

                results.add(bomovie);
            }
        }
        return results;
    }
}
