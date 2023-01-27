package ru.otus.homework.service;

import ru.otus.homework.exception.WrongDataException;
import ru.otus.homework.model.Question;

public class QuestionService {

    public Question createQuestion(String questionString) {

        String[] questionData = questionString.split(",");
        Question question = new Question();

        if (questionData.length == 5) {
            question.setQuestion(questionData[0]);
            question.setAnswerA(questionData[1]);
            question.setAnswerB(questionData[2]);
            question.setAnswerC(questionData[3]);
            question.setAnswerD(questionData[4]);
        } else {
            throw new WrongDataException("Impossible to create question due to wrong input data");
        }

        return question;

    }

    public void printQuestion(Question question) {

        System.out.println("Question : " + question.getQuestion());
        System.out.println("Answer options : " + question.getAnswerA());
        System.out.println("                 " + question.getAnswerB());
        System.out.println("                 " + question.getAnswerC());
        System.out.println("                 " + question.getAnswerD());

    }
}
