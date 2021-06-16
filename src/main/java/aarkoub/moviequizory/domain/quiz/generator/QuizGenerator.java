package aarkoub.moviequizory.domain.quiz.generator;

import aarkoub.moviequizory.domain.actor.Actor;
import aarkoub.moviequizory.domain.movie.Movie;
import aarkoub.moviequizory.domain.question.Question;
import aarkoub.moviequizory.domain.quiz.Quiz;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class QuizGenerator {

    Random rand;
    int maxSizeQuiz = 100;

    public QuizGenerator(){
        rand = new Random();
    }

    public QuizGenerator(int maxSizeQuiz){
        this.maxSizeQuiz = maxSizeQuiz;
        new QuizGenerator();
    }

    public Quiz generate(Map<Integer, Movie> moviesMap, Map<Integer, List<Actor>> actorsInMovieMap){
        Object[] movieIds = moviesMap.keySet().toArray();
        Question[] questions = new Question[maxSizeQuiz];
        for (int i = 0; i < maxSizeQuiz; i++) {
            int ind = Math.abs(rand.nextInt()) % movieIds.length;
            Movie movie = moviesMap.get(movieIds[ind]);
            boolean isTrue = rand.nextBoolean();
            Actor actor;
            if (isTrue) {
                List<Actor> actors = actorsInMovieMap.get(movieIds[ind]);
                actor = actors.get(Math.abs(rand.nextInt()) % actors.size());
            } else {
                int otherMovieInd = Math.abs(rand.nextInt()) % movieIds.length;
                while (otherMovieInd == ind) {
                    otherMovieInd = Math.abs(rand.nextInt()) % movieIds.length;
                }
                List<Actor> actors = actorsInMovieMap.get(movieIds[otherMovieInd]);
                actor = actors.get(Math.abs(rand.nextInt()) % actors.size());
            }
            questions[i] = new Question(actor, movie, isTrue);
        }

        return new Quiz(questions);
    }


}
