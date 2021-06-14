package aarkoub.moviequizory.domain.quiz.service;

import aarkoub.moviequizory.apiclient.MovieDBAPIClient;
import aarkoub.moviequizory.domain.actor.Actor;
import aarkoub.moviequizory.domain.movie.Movie;
import aarkoub.moviequizory.domain.question.Question;
import aarkoub.moviequizory.domain.quiz.Quiz;
import aarkoub.moviequizory.domain.quiz.generator.QuizGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class QuizServiceTest {

    @MockBean
    MovieDBAPIClient client;

    @MockBean
    QuizGenerator gen;

    @Autowired
    IQuizService service;

    @Test
    public void getQuiz(){

        Map<Integer, Movie> moviesMap = new HashMap<>();
        Map<Integer, List<Actor>> actorsInMovies = new HashMap<>();
        Quiz quiz = new Quiz(new Question[10]);

        Mockito.when(client.getMovies(2)).thenReturn(moviesMap);
        Mockito.when(client.getActorsPerMovie(moviesMap)).thenReturn(actorsInMovies);
        Mockito.when(gen.generate(moviesMap, actorsInMovies)).thenReturn(quiz);

        Assertions.assertEquals(quiz, service.getQuiz());

    }

}
