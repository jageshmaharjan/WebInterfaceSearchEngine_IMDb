package BO;

import java.util.List;

/**
 * Created by jagesh on 06/07/2016.
 */
public class BOActor
{
    public int movieId;
    public List<String> actors;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }
}
