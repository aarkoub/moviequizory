package aarkoub.moviequizory.domain.quiz.generator;

import aarkoub.moviequizory.domain.actor.Actor;
import aarkoub.moviequizory.domain.movie.Movie;
import aarkoub.moviequizory.domain.quiz.Quiz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizGeneratorTest {

    @Test
    public void generate() {
        QuizGenerator gen = new QuizGenerator();
        Map<Integer, Movie> moviesMap = new HashMap<>();
        Map<Integer, List<Actor>> actorsInMovies = new HashMap<>();

        moviesMap.put(1, new Movie("abc", null));
        moviesMap.put(2, new Movie("def", null));

        List<Actor> a1 = new ArrayList<>();
        a1.add(new Actor("harry", null));
        a1.add(new Actor("hermione", null));

        List<Actor> a2 = new ArrayList<>();
        a2.add(new Actor("ron", null));
        a2.add(new Actor("pettigrew", null));

        actorsInMovies.put(1, a1);
        actorsInMovies.put(2, a2);

        Quiz quiz = gen.generate(moviesMap, actorsInMovies);

        Assertions.assertEquals(200, quiz.getQuestions().length);

        for (int i = 0; i < quiz.getQuestions().length; i++) {

            System.out.println(quiz.getQuestions()[i] + " : " + quiz.getQuestions()[i].isTrue());

            switch (quiz.getQuestions()[i].getActor().getName()) {
                case ("harry"):
                    switch (quiz.getQuestions()[i].getMovie().getTitle()) {
                        case ("abc"):
                            Assertions.assertTrue(quiz.getQuestions()[i].isTrue());
                            break;
                        case ("def"):
                            Assertions.assertFalse(quiz.getQuestions()[i].isTrue());
                            break;
                    }
                    break;
                case ("hermione"):
                    switch (quiz.getQuestions()[i].getMovie().getTitle()) {
                        case ("abc"):
                            Assertions.assertTrue(quiz.getQuestions()[i].isTrue());
                            break;
                        case ("def"):
                            Assertions.assertFalse(quiz.getQuestions()[i].isTrue());
                            break;
                    }
                    break;
                case ("ron"):
                    switch (quiz.getQuestions()[i].getMovie().getTitle()) {
                        case ("abc"):
                            Assertions.assertFalse(quiz.getQuestions()[i].isTrue());
                            break;
                        case ("def"):
                            Assertions.assertTrue(quiz.getQuestions()[i].isTrue());
                            break;
                    }
                    break;
                case ("pettigrew"):
                    switch (quiz.getQuestions()[i].getMovie().getTitle()) {
                        case ("abc"):
                            Assertions.assertFalse(quiz.getQuestions()[i].isTrue());
                            break;
                        case ("def"):
                            Assertions.assertTrue(quiz.getQuestions()[i].isTrue());
                            break;
                    }
                    break;
            }

        }

    }

}
