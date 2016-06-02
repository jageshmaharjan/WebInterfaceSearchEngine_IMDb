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
public class BLLLanguage
{
    public Map<Integer, List<String > > getAllLanguageByMovieID(List<Integer> movieIds) throws SQLException, ClassNotFoundException
    {
        Map<Integer, List<String>> result = new HashMap<>();

        String query = "SELECT name FROM languages, movie_language WHERE languages.idlanguage = movie_language.languageID AND movie_language.movieID=?";
        try(Connection con = DAO.getConnection();
            PreparedStatement ps = con.prepareStatement(query); )
        {
            for (Integer movieid : movieIds)
            {
                ps.setInt(1,movieid);
                ResultSet rs = ps.executeQuery();
                List<String> languages = new ArrayList<>();
                while (rs.next())
                {
                    languages.add(rs.getString("name"));
                }
                result.put(movieid,languages);
            }
        }
        return result;
    }
}
