package spring.ru.otus.homework.service;

import spring.ru.otus.homework.model.QuestionDto;

import java.util.List;

public interface QuestionService {
    void printQuestion(QuestionDto question);

    boolean checkAnswer(int answerNumber, QuestionDto question);

    List<QuestionDto> getQuestions(String[] questionData);
}
