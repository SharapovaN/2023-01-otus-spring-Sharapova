package spring.ru.otus.homework.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import spring.ru.otus.homework.exception.WrongDataException;
import spring.ru.otus.homework.model.QuestionDto;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@SpringBootTest()
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QuestionServiceTest {

    @Autowired
    private QuestionServiceImpl questionService;

    @Test
    public void createQuestionIfDataIsOkTest() {
        QuestionDto question = questionService.getQuestions(new String[]{"Question1,1AnswerA,1AnswerB,1AnswerC,1AnswerD,1",
                "Question2,2AnswerA,2AnswerB,2AnswerC,2AnswerD,1"}).get(0);

        Assert.assertEquals("Question1", question.getQuestion());
        Assert.assertEquals("1AnswerA", question.getAnswers().get(0));
        Assert.assertEquals("1AnswerB", question.getAnswers().get(1));
        Assert.assertEquals("1AnswerC", question.getAnswers().get(2));
        Assert.assertEquals("1AnswerD", question.getAnswers().get(3));
        Assert.assertEquals(1, question.getRightAnswerIndex());
    }

    @Test
    public void checkAnswerIfAnswerIsOkTest() {
        List<QuestionDto> questions = questionService.getQuestions(new String[]{"Question1,1AnswerA,1AnswerB,1AnswerC,1AnswerD,1",
                "Question2,2AnswerA,2AnswerB,2AnswerC,2AnswerD,1"});
        assertTrue(questionService.checkAnswer(1, questions.get(0)));
    }

    @Test
    public void createQuestionListIfDataIsOk() {
        List<QuestionDto> questions = questionService.getQuestions(new String[]{"Question1,1AnswerA,1AnswerB,1AnswerC,1AnswerD,1",
                "Question2,2AnswerA,2AnswerB,2AnswerC,2AnswerD,1"});

        assertFalse(questions.isEmpty());
        assertEquals(2, questions.size());
    }

    @Test
    public void checkAnswerIfAnswerIsNotOkTest() {
        List<QuestionDto> questions = questionService.getQuestions(new String[]{"Question1,1AnswerA,1AnswerB,1AnswerC,1AnswerD,1",
                "Question2,2AnswerA,2AnswerB,2AnswerC,2AnswerD,1"});
        assertFalse(questionService.checkAnswer(2, questions.get(0)));
    }

    @Test
    public void createQuestionListIfDataIsEmpty() {
        Exception exception = assertThrows(WrongDataException.class,
                () -> questionService.getQuestions(new String[] {}));

        String expectedMessage = "Impossible to create question list due to wrong input array";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createQuestionListIfSomeStringsIsEmpty() {
        Exception exception = assertThrows(WrongDataException.class,
                () -> questionService.getQuestions(new String[]{"Question1,1AnswerA,1AnswerB,1AnswerC,1AnswerD,1", "",
                        "Question3,3AnswerA,3AnswerB,3AnswerC,3AnswerD,1"}));

        String expectedMessage = "Impossible to create question due to wrong input data";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createQuestionIfDataIsNotOkTest() {
        Exception exception = assertThrows(WrongDataException.class,
                () -> questionService.getQuestions(new String[]{"Question1,1AnswerA"}));

        String expectedMessage = "Impossible to create question due to wrong input data";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}