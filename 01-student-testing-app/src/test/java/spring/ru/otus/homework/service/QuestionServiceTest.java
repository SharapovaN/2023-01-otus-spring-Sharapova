package spring.ru.otus.homework.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.ru.otus.homework.exception.WrongDataException;
import spring.ru.otus.homework.model.QuestionDto;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private IOService ioService;

    private FileService fileService = new CsvFileServiceImpl("01-student-testing-app/src/test/resources/TestQuestionsForTest.csv");

    private QuestionServiceImpl questionService = new QuestionServiceImpl(ioService, fileService);

    @Test
    public void createQuestionIfDataIsOkTest() throws FileNotFoundException {
        QuestionDto question = questionService.getQuestions().get(0);

        Assert.assertEquals("Question1", question.getQuestion());
        Assert.assertEquals("1AnswerA", question.getAnswers().get(0));
        Assert.assertEquals("1AnswerB", question.getAnswers().get(1));
        Assert.assertEquals("1AnswerC", question.getAnswers().get(2));
        Assert.assertEquals("1AnswerD", question.getAnswers().get(3));
        Assert.assertEquals(1, question.getRightAnswerIndex());
    }

    @Test
    public void checkAnswerIfAnswerIsOkTest() throws FileNotFoundException {
        List<QuestionDto> questions = questionService.getQuestions();
        assertTrue(questionService.checkAnswer(1, questions.get(0)));
    }

    @Test
    public void checkAnswerIfAnswerIsNotOkTest() throws FileNotFoundException {
        List<QuestionDto> questions = questionService.getQuestions();
        assertFalse(questionService.checkAnswer(2, questions.get(0)));
    }

    @Test
    public void createQuestionListIfDataIsOk() throws FileNotFoundException {
        List<QuestionDto> questions = questionService.getQuestions();

        assertFalse(questions.isEmpty());
        assertEquals(5, questions.size());
    }

    @Test
    public void createQuestionListIfDataIsEmpty() {
        fileService = new CsvFileServiceImpl("01-student-testing-app/src/test/resources/EmptyTestQuestions.csv");
        questionService = new QuestionServiceImpl(ioService, fileService);
        Exception exception = assertThrows(WrongDataException.class,
                () -> questionService.getQuestions());

        String expectedMessage = "Impossible to create question list due to wrong input array";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createQuestionListIfSomeStringsIsEmpty() {
        fileService = new CsvFileServiceImpl("01-student-testing-app/src/test/resources/EmptyStringTestQuestions.csv");
        questionService = new QuestionServiceImpl(ioService, fileService);
        Exception exception = assertThrows(WrongDataException.class,
                () -> questionService.getQuestions());

        String expectedMessage = "Impossible to create question due to wrong input data";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createQuestionIfDataIsNotOkTest() {
        fileService = new CsvFileServiceImpl("01-student-testing-app/src/test/resources/WrongDataForCreateQuestionTest.csv");
        questionService = new QuestionServiceImpl(ioService, fileService);

        Exception exception = assertThrows(WrongDataException.class,
                () -> questionService.getQuestions());

        String expectedMessage = "Impossible to create question due to wrong input data";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}