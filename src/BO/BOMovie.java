package BO;

/**
 * Created by jagesh on 06/02/2016.
 */
public class BOMovie
{
    private int movieid;
    private String moviename;
    private String releasedate;
    private float rating;
    private String storyline;
    private String movieurl;
    private String coverurl;

    public BOMovie()
    {

    }

    public BOMovie(int movieid) {
        this.movieid = movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public void setMovieurl(String movieurl) {
        this.movieurl = movieurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }


    public int getMovieid() {
        return movieid;
    }

    public String getMoviename() {
        return moviename;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public float getRating() {
        return rating;
    }

    public String getStoryline() {
        return storyline;
    }

    public String getMovieurl() {
        return movieurl;
    }

    public String getCoverurl() {
        return coverurl;
    }
}
