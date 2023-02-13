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

    @Mock
    private FileService fileService;

    private final QuestionServiceImpl questionService = new QuestionServiceImpl(ioService, fileService);

    @Test
    public void createQuestionIfDataIsOkTest() {
        QuestionDto question = questionService.createQuestion("Question1,1AnswerA,1AnswerB,1AnswerC,1AnswerD,1,");

        Assert.assertEquals("Question1", question.getQuestion());
        Assert.assertEquals("1AnswerA", question.getAnswers().get(0));
        Assert.assertEquals("1AnswerB", question.getAnswers().get(1));
        Assert.assertEquals("1AnswerC", question.getAnswers().get(2));
        Assert.assertEquals("1AnswerD", question.getAnswers().get(3));
        Assert.assertEquals(1, question.getRightAnswerIndex());
    }

    @Test
    public void createQuestionIfDataIsNotOkTest() {
        Exception exception = assertThrows(WrongDataException.class,
                () -> questionService.createQuestion("Question1,1AnswerA"));

        String expectedMessage = "Impossible to create question due to wrong input data";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void checkAnswerIfAnswerIsOkTest() {
        QuestionDto question = questionService.createQuestion("Question1,1AnswerA,1AnswerB,1AnswerC,1AnswerD,1,");
        assertTrue(questionService.checkAnswer(1, question));
    }

    @Test
    public void checkAnswerIfAnswerIsNotOkTest() {
        QuestionDto question = questionService.createQuestion("Question1,1AnswerA,1AnswerB,1AnswerC,1AnswerD,1,");
        assertFalse(questionService.checkAnswer(2, question));
    }

    @Test
    public void createQuestionListIfDataIsOk() throws FileNotFoundException {
        String[] strings = new String[] {"Question1,1AnswerA,1AnswerB,1AnswerC,1AnswerD,1,",
                "Question2,2AnswerA,2AnswerB,2AnswerC,2AnswerD,1,"};
        questionService.createQuestionsList(strings);
        List<QuestionDto> questions = questionService.getQuestions();

        assertFalse(questions.isEmpty());
        assertEquals(2, questions.size());
    }

    @Test
    public void createQuestionListIfDataIsEmpty() {
        String[] strings = new String[0];
        Exception exception = assertThrows(WrongDataException.class,
                () -> questionService.createQuestionsList(strings));

        String expectedMessage = "Impossible to create question list due to wrong input array";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createQuestionListIfSomeStringsIsEmpty() {
        String[] strings = new String[] {"Question1,1AnswerA,1AnswerB,1AnswerC,1AnswerD,1,", "",
                "Question2,2AnswerA,2AnswerB,2AnswerC,2AnswerD,1,"};
        Exception exception = assertThrows(WrongDataException.class,
                () -> questionService.createQuestionsList(strings));

        String expectedMessage = "Impossible to create question due to wrong input data";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}