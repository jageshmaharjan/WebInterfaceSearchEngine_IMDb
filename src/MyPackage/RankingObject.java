package MyPackage;

import DAL.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by jagesh on 06/05/2016.
 */
public class RankingObject
{
    public static float k = (float) 1.2;
    public static float b = (float) 0.75;

//    public static void main(String[] args) throws Exception
//    {
//        RankingObject ro = new RankingObject();
//        ro.rank();
//    }

    //BM25 Ranking Algorithm processing
    public static List<Integer> rank(String q_strings) throws Exception //String q_strings
    {
//        String q_strings = "comedy and lots of dance";

        String processing_query = Stopwords.removeStemmedStopWords(q_strings.toLowerCase());
        String[] query_strings = processing_query.split(" |\\.|\\//|\\:|\\?|\\/|\\,|\\'|\\(|\\|\\!|\\)|\\&|\\;|\\@|\\[|\\{|\\}|\\''|\\]|\\-|\\*\\ ");

        List<Integer> docs = new ArrayList<>();
        docs = getNosOfDocs(query_strings);             // Required Documents as per query, D

        long totalDocInCorpus = getTotalDocumentInCorpus();             //N

        double avgdl = getAvgDocLen(totalDocInCorpus );         //avgdl

        List<Integer> term_frequency = new ArrayList<>();
        term_frequency =  getTermFrequency(query_strings,docs);      //as f(D,qi)

        //not working
        List<Integer> docs_frequency = new ArrayList<>();
        docs_frequency  = getDocsFrequency(query_strings, docs);                    //as |D|

        long idf = idfcalculation(totalDocInCorpus,query_strings);                  // IDF

        Map<Double ,Integer> doc_rank = new HashMap<>();                                     //score Calculation
        doc_rank = performBM25Ranking(docs,idf,term_frequency,docs_frequency,avgdl);

        List<Integer> rankedDocs = new ArrayList<>();
        rankedDocs = getRankedList(doc_rank);

        return rankedDocs;
    }

    private static List<Integer> getRankedList(Map<Double, Integer> doc_rank)
    {
        List<Integer> rankedDocs = new ArrayList<>();

        for (Map.Entry<Double,Integer> doc : doc_rank.entrySet())
        {
            rankedDocs.add(doc.getValue());
        }
        return rankedDocs;
    }


    private static Map<Double ,Integer> performBM25Ranking(List<Integer> docsIds,long idf, List<Integer> term_frequency, List<Integer> docs_frequency, double avgdl)
    {
        Map<Double ,Integer> document_rank = new TreeMap<>(Comparator.reverseOrder());

        for (int i=0; i<docsIds.size(); i++ )
        {
            float val = 0 ;
             val = (idf * ( (term_frequency.get(i) * (k+1)) /(float)((term_frequency.get(i) + k +(1-b + (b * (docs_frequency.get(i)/(float)avgdl)))) ) ));
            document_rank.put( (double) val ,docsIds.get(i));
        }

        return document_rank;
    }

    private static long idfcalculation(long totalDocInCorpus,String[] query_strings) throws Exception
    {
        long idf = 0;

        StringJoiner joiner = queryJoiner(query_strings);

        String query = "SELECT sum(docs) as totDocs FROM dictionarynew WHERE " + joiner ;
        Connection conn = DAO.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            idf = rs.getInt("totDocs");
        }
        return idf;
    }

    private static List<Integer> getDocsFrequency(String[] query_str, List<Integer> docsIds) throws Exception
    {
        List<Integer> docLen = new ArrayList<>();

        StringJoiner joiner = docIdJoiner(docsIds);

        String query = "SELECT * FROM document WHERE " + joiner;
        Connection conn = DAO.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            docLen.add(rs.getInt("doclength"));
        }
        return docLen;
    }

    private static List<Integer> getNosOfDocs(String[] u_query) throws Exception
    {
        List<Integer> docs = new ArrayList<>();

        StringJoiner joiner = queryJoiner(u_query);

        String query = "SELECT DISTINCT movieID FROM postingtblnew WHERE " + joiner;
        try(Connection conn = DAO.getConnection();
            PreparedStatement ps= conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                docs.add(rs.getInt("movieID"));
            }
        }
        return docs;
    }

    private static long getTotalDocumentInCorpus() throws Exception
    {
        long N = 0;

        String query = "select count(*) as N FROM document";
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

    private static double getAvgDocLen(long totalDocInCorpus) throws Exception
    {
        long totalDocLenInCorpus = totalDocLenInCorpus();
        double avgdl = totalDocLenInCorpus/(float)totalDocInCorpus;

        return avgdl;
    }

    private static long totalDocLenInCorpus() throws Exception
    {
        long totalLenOfCorpus = 0;
        String query = "select sum(doclength) as tFreq FROM document";
        Connection conn = DAO.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            totalLenOfCorpus = rs.getInt("tFreq");
        }
        return totalLenOfCorpus;
    }

    private static List<Integer> getTermFrequency(String[] query_strings, List<Integer> docsids) throws Exception
    {
        List<Integer> termFrequency = new ArrayList<>();

        StringJoiner q_joiner = queryJoiner(query_strings);
        StringJoiner id_joiner = idJoiner(docsids);

        String query = "SELECT DISTINCT movieID, sum(frequency) as frequency FROM postingtblnew WHERE " + q_joiner.toString() + " GROUP BY movieID";

        try(Connection conn = DAO.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                termFrequency.add(rs.getInt("frequency"));
            }
        }
        return termFrequency;
    }

    public static StringJoiner queryJoiner(String[] query_strings)
    {
        StringJoiner joiner = new StringJoiner(" OR ");

        for (String usr_query : query_strings)
        {
            joiner.add("term=" + '"' +usr_query + '"');
        }
        return joiner;
    }

    public static StringJoiner idJoiner(List<Integer> docsids)
    {
        StringJoiner intjoiner = new StringJoiner(" OR");
        for (int i=0;i<docsids.size() ;i ++)
        {
            intjoiner.add("movieID=" + docsids.get(i));
        }
        return intjoiner;
    }

    public static StringJoiner docIdJoiner(List<Integer> docsids)
    {
        StringJoiner intjoiner = new StringJoiner(" OR ");
        for (int i=0;i<docsids.size() ;i ++)
        {
            intjoiner.add("docID=" + docsids.get(i));
        }
        return intjoiner;
    }

}
