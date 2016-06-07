package BO;

/**
 * Created by jagesh on 06/07/2016.
 */
public class BOSentiment
{
    public int movieid;
    public String sentiment;
    public float score;

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
