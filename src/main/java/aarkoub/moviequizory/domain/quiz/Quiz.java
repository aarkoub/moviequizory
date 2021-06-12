package aarkoub.moviequizory.domain.quiz;

import aarkoub.moviequizory.domain.question.Question;

public class Quiz {

    private Question[] questions;

    public Quiz(Question[] questions){
        this.questions = questions;
    }

    public Question[] getQuestions() {
        return questions;
    }
}
