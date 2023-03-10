package spring.ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.ru.otus.homework.exception.WrongDataException;
import spring.ru.otus.homework.model.QuestionDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final IOService ioService;
    private List<QuestionDto> questions;

    @Override
    public void printQuestion(QuestionDto question) {
        List<String> answers = question.getAnswers();
        int k;

        ioService.outputString(question.getQuestion());
        for (int i = 0; i < answers.size(); i++) {
            k = i + 1;
            ioService.outputString(" " + k + ") " + answers.get(i));
        }
    }

    @Override
    public boolean checkAnswer(int answerNumber, QuestionDto question) {
        return answerNumber == question.getRightAnswerIndex();
    }

    @Override
    public List<QuestionDto> getQuestions(String[] questionData) {
        if (questions == null) {
            createQuestionsList(questionData);
        }
        return questions;
    }

    private QuestionDto createQuestion(String questionString) {

        String questionDataString = questionString.replaceAll("\r", "");
        String[] questionData = questionDataString.split(",");
        QuestionDto question = new QuestionDto();

        if (questionData.length > 3) {
            question.setQuestion(questionData[0]);
            setAnswers(questionData, question);
            question.setRightAnswerIndex(Integer.parseInt(questionData[questionData.length - 1]));
        } else {
            throw new WrongDataException("Impossible to create question due to wrong input data");
        }
        return question;
    }

    private void setAnswers(String[] questionData, QuestionDto questionDto) {
        List<String> answers = new ArrayList<>();
        for (int i = 1; i < questionData.length - 1; i++) {
            answers.add(questionData[i]);
        }
        questionDto.setAnswers(answers);
    }

    private void createQuestionsList(String[] questionData) {
        questions = new ArrayList<>();
        if (questionData.length > 0) {
            for (String questionString : questionData) {
                questions.add(createQuestion(questionString));
            }
        } else {
            throw new WrongDataException("Impossible to create question list due to wrong input array");
        }
    }

}
