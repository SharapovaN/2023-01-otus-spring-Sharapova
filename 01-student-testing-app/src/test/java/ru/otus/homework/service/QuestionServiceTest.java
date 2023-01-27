package ru.otus.homework.service;

import org.junit.Assert;
import org.junit.Test;
import ru.otus.homework.exception.WrongDataException;
import ru.otus.homework.model.Question;

import static org.junit.Assert.*;

public class QuestionServiceTest {

    private QuestionService questionService = new QuestionService();

    @Test
    public void createQuestionIfDataIsOkTest() {
        Question question = questionService.createQuestion("Question1,1AnswerA,1AnswerB,1AnswerC,1AnswerD");

        Assert.assertEquals("Question1", question.getQuestion());
        Assert.assertEquals("1AnswerA", question.getAnswerA());
        Assert.assertEquals("1AnswerB", question.getAnswerB());
        Assert.assertEquals("1AnswerC", question.getAnswerC());
        Assert.assertEquals("1AnswerD", question.getAnswerD());
    }

    @Test
    public void createQuestionIfDataIsNotOkTest() {
        Exception exception = assertThrows(WrongDataException.class, () -> {
            questionService.createQuestion("Question1,1AnswerA,1AnswerB,1AnswerC");
        });

        String expectedMessage = "Impossible to create question due to wrong input data";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}