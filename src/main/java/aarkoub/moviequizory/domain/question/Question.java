package aarkoub.moviequizory.domain.question;

import aarkoub.moviequizory.domain.actor.Actor;
import aarkoub.moviequizory.domain.movie.Movie;

public class Question {

    private String content;
    private Actor actor;
    private Movie movie;
    private boolean isTrue;

    public Question(Actor actor, Movie movie, boolean isTrue){
        this.actor = actor;
        this.movie = movie;
        this.isTrue = isTrue;
        content  = "Did "+actor.getName()+" play in "+movie.getTitle()+"?";
    }

    public String getContent(){
        return content;
    }

    public Actor getActor() {
        return actor;
    }

    public Movie getMovie() {
        return movie;
    }

    public boolean isTrue() {
        return isTrue;
    }

    @Override
    public String toString(){
        return content;
    }
}
