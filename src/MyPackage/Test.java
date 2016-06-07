package MyPackage;

import DAL.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by jagesh on 06/04/2016.
 */
public class Test
{
    public float k = (float) 1.2;
    public float b = (float) 0.75;

    public static void main(String[] args) throws Exception
    {
        String user_query = "very terrible and mysterious";
        String processing_query = Stopwords.removeStemmedStopWords(user_query.toLowerCase());
        String[] query_strings = processing_query.split(" |\\.|\\//|\\:|\\?|\\/|\\,|\\'|\\(|\\|\\!|\\)|\\&|\\;|\\@|\\[|\\{|\\}|\\''|\\]|\\-|\\*");

        //total documents that contains the given query
        Set<Long> movieIds = new HashSet<>();
        movieIds = getDocumentsWithQuery(query_strings);        // D

        //total no.s of documents in a corpus
        long totalDocInCorpus = getTotalDocumentInCorpus();     //as N

        double avgdl = getAvgDocLen(totalDocInCorpus,movieIds );

        Map<Long,Long> term_frequency = new HashMap<>();
        term_frequency =  getTermFrequency(query_strings,movieIds);      //as f(D,qi)


//
//        List<Long> nosOfDocs = getNosOfDocuments(query_strings);    //as n(qi)
//
//        List<Double> idf = new ArrayList<>();
//        idf = calculateIDF(totalDocInCorpus, nosOfDocs);        //idf
//
//
//
//        calculateBM25(movieIds,idf,nosOfDocs,term_frequency);

    }

    private static double getAvgDocLen(long totalDocInCorpus, Set<Long> movieIds)
    {
        double avgdl = totalDocInCorpus/(float)movieIds.size();

        return avgdl;
    }

    private static void calculateBM25(Set<Long> movieIds, List<Double> idf, List<Long> nosOfDocs, List<Long> term_frequency)
    {


    }

    private static Set<Long> getDocumentsWithQuery(String[] query_strings) throws Exception
    {
        Set<Long> movieIds = new HashSet<>();

        //StringJoiner joiner = new StringJoiner(" OR ");

        String query = "select distinct MovieID from postingtbl where term =?";
        for (String u_query : query_strings)
        {
            //joiner.add("term="+ u_query);
            Connection conn = DAO.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, u_query);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                movieIds.add(rs.getLong("MovieID"));
            }
        }
        return movieIds;
    }

    private static List<Double> calculateIDF(long N, List<Long> nosOfDocs)
    {
        List<Double> idf = new ArrayList<>();

        for (int i=0;i<nosOfDocs.size(); i++)
        {
            idf.add(Math.log((N - nosOfDocs.get(i) + 0.5 ) / (nosOfDocs.get(i) + 0.5))) ;
        }
        return idf;
    }

    private static List<Long> getNosOfDocuments(String[] query_strings) throws Exception
    {
        List<Long> nosOfDocs = new ArrayList<>();

        String query = "SELECT * FROM  dictionarytbl WHERE term= ?";
        for (String u_query : query_strings)
        {
            try( Connection conn = DAO.getConnection();
                  PreparedStatement ps = conn.prepareStatement(query))
            {
                ps.setString(1,u_query);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    nosOfDocs.add(rs.getLong("docs"));
                }
            }
        }
        return nosOfDocs;
    }

    private static long getTotalDocumentInCorpus() throws Exception
    {
        long N = 0;

        String query = "SELECT COUNT(*) AS N FROM movie";
        try(Connection conn = DAO.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);)
        {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                N = rs.getLong("N");
            }
        }
        return N;
    }

    private static Map<Long, Long> getTermFrequency(String[] query_strings, Set<Long> movieids) throws Exception
    {
        Map<Long,Long> termFrequency = new HashMap<>();

        StringJoiner joiner = new StringJoiner(" OR ");

        for (String usr_query : query_strings)
        {
            joiner.add("term=" + '"' + usr_query + '"');
        }

        //select * from postingtbl where MovieID = 1 and (term="alive" or term="angry" ) ;
        String query = "SELECT * FROM postingtbl WHERE movieID= ? AND " + joiner.toString() ;

        for (Long movieID : movieids)
        {
            try(Connection conn = DAO.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);)
            {
                ps.setLong(1,movieID);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    termFrequency.put(movieID, (long) rs.getInt("frequency"));
                }
            }
        }
        return termFrequency;
    }

}
