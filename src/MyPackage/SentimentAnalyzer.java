package MyPackage;

import DAL.DAO;
import io.indico.Indico;
import io.indico.api.text.Emotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by jagesh on 06/04/2016.
 */
public class SentimentAnalyzer
{
//    public static void main(String[] args) throws  Exception
//    {
//        String query = "lion killed the hyena";
//        SentimentAnalyzer sa = new SentimentAnalyzer();
//
//        sa.getSentiment(query);
//
//    }
    public Map<Emotion, Double> getSentiment(String query) throws Exception
    {
        Map params = new HashMap();
        params.put("threshold", 0.25);

        Indico indico = new Indico("4c89f284b1c43ea223525cc2f8bbb7f2");
        Map<Emotion,Double> results = indico.emotion.predict(query,params).getEmotion();

        return results;
    }

    public List<Integer> getDocIds(Map<Emotion,Double> results) throws Exception
    {
        List<Integer> docsIds = new ArrayList<>();

        List<String> emotions = new ArrayList<>();
        List<Double> score = new ArrayList<>();

        for (Map.Entry<Emotion,Double> entry : results.entrySet())
        {
            emotions.add(String.valueOf(entry.getKey()));
            score.add(entry.getValue());
        }
        docsIds = findRequiredMovieIds(emotions,score);

//        RankingObject rankobj = new RankingObject();
//        rankobj.rank(query,docsIds);

        return docsIds;
    }

    public List<Integer> findRequiredMovieIds(List<String> emotions, List<Double> score) throws Exception
    {
        List<Integer> docIds = new ArrayList<>();

        float t = (float) 0.1;
        StringJoiner joiner = new StringJoiner(" OR ");
        for (int i =0 ;i < emotions.size(); i++)
        {
            joiner.add("sentiment="+'"' + emotions.get(i).toString() +'"' + " AND ( score <"+ (score.get(i)+t) + " AND score > " + (score.get(i)-t) +")");
        }

        String query = "SELECT DISTINCT movieID FROM movie_sentiment WHERE " + joiner.toString();
        try (Connection conn = DAO.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                docIds.add(rs.getInt("MovieID"));
            }
        }
        return docIds;
    }

}
