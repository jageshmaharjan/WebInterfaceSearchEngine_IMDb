package BLL;

import BO.BOSentiment;
import DAL.DAO;
import io.indico.api.text.Emotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jagesh on 06/07/2016.
 */
public class BLLSentiment
{
    public List<BOSentiment> getSentimentsFromMovie(List<Integer> movieIds) throws Exception
    {
        Map<Emotion,Double> sentiments = new HashMap<>();
        List<BOSentiment> sentilist = new ArrayList<>();

        String query = "SELECT * FROM movie_sentiment WHERE movieID= ?";

        try(Connection conn = DAO.getConnection();
            PreparedStatement ps = conn.prepareStatement(query))
        {
            for (Integer movieid : movieIds )
            {
                BOSentiment sentiment = new BOSentiment();

                sentiment.setMovieid(movieid);
                ps.setInt(1,movieid);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    sentiment.setSentiment(rs.getString("sentiment"));
                    sentiment.setScore(rs.getFloat("score"));
                }
                sentilist.add(sentiment);
            }
        }
        return sentilist;
    }
}
