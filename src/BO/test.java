package BO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jagesh on 06/02/2016.
 */
public class test
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        test t = new test();
        List<Integer> num = new ArrayList<>();
        for (int i= 1;i<10;i++)
        {
            num.add(i);
        }
       // t.getAllActorsFromMovies(num);
    }

//    public Map<Integer, List<String> > getAllActorsFromMovies(List<Integer> movieIds) throws SQLException, ClassNotFoundException
//    {
//        Map<Integer, List<String>> results = new HashMap<>(movieIds.size());
//
//        String query = "SELECT Name FROM stars, movie_actor WHERE movie_actor.starID = stars.idstars AND movie_actor.movieID=?";
//
//        try (Connection conn = DAO.getConnection();
//             PreparedStatement ps = conn.prepareStatement(query))
//        {
//            for (Integer movieId : movieIds)
//            {
//                ps.setInt(1, movieId);
//                ResultSet rs = ps.executeQuery();
//
//                List<String> actors = new ArrayList<>();
//
//                while (rs.next())
//                {
//                    actors.add(rs.getString("Name"));
//                }
//                results.put(movieId, actors);
//                rs.close();
//            }
//        }
//        return results;
//    }
}
