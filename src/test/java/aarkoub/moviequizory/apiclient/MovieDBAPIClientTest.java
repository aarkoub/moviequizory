package aarkoub.moviequizory.apiclient;

import aarkoub.moviequizory.domain.actor.Actor;
import aarkoub.moviequizory.domain.movie.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class MovieDBAPIClientTest {


    @Autowired
    MovieDBAPIClient moviedbapiclient;

    @Test
    public void getMovies(){
        Map<Integer, Movie> movieMap = moviedbapiclient.getMovies(1);
        Assertions.assertEquals(20, movieMap.keySet().size());
    }

    @Test
    public void getActorsPerMovie(){
        Map<Integer, Movie> movieMap = moviedbapiclient.getMovies(1);
        Map<Integer, List<Actor>> actorsPerMovieMap = moviedbapiclient.getActorsPerMovie(movieMap);
        Assertions.assertEquals(movieMap.keySet(), actorsPerMovieMap.keySet());
    }

}
