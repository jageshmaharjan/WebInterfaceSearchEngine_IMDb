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
public class BLLLanguage
{
    public List<String> getAllLanguageByMovieID(int movieid) throws SQLException, ClassNotFoundException
    {
        List<String> langlst = new ArrayList<>();

        Connection con = DAO.getConnection();
        String sql = "SELECT * FROM movie_language WHERE  movieID= ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,movieid);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            int langid = rs.getInt("languageID");
            String sql_lang = "SELECT * FROM languages WHERE  idlanguage = ?";
            PreparedStatement ps_lang = con.prepareStatement(sql_lang);
            ps_lang.setInt(1,langid);
            ResultSet rs_lang = ps_lang.executeQuery();
            while (rs_lang.next())
            {
                langlst.add(rs_lang.getString("name"));
            }
        }
        return langlst;
    }
}
