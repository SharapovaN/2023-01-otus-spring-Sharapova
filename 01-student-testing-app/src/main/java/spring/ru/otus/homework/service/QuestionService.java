package spring.ru.otus.homework.service;

import spring.ru.otus.homework.model.QuestionDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface QuestionService {
    void createQuestionsList(String[] questionData);

    void printQuestion(QuestionDto question);

    boolean checkAnswer(int answerNumber, QuestionDto question);

    List<QuestionDto> getQuestions() throws FileNotFoundException;

    QuestionDto createQuestion(String questionString);
}
