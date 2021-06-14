package aarkoub.moviequizory.domain.quiz.service;


import aarkoub.moviequizory.apiclient.MovieDBAPIClient;
import aarkoub.moviequizory.domain.actor.Actor;
import aarkoub.moviequizory.domain.movie.Movie;
import aarkoub.moviequizory.domain.quiz.Quiz;
import aarkoub.moviequizory.domain.quiz.generator.QuizGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuizService implements IQuizService  {

    @Autowired
    MovieDBAPIClient client ;

    @Autowired
    QuizGenerator generator;

    public Quiz getQuiz() {
        Map<Integer, Movie> movieMap = client.getMovies(6);
        Map<Integer, List<Actor>> actorsInMovies = client.getActorsPerMovie(movieMap);
        return generator.generate(movieMap, actorsInMovies);
    }
}
