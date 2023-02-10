package spring.ru.otus.homework.service;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    private String url = "01-student-testing-app/src/test/resources/TestQuestionsForTest.csv";

    @Mock
    private QuestionService questionService;

    private final FileService fileService = new CsvFileServiceImpl(url, questionService);

    @Test
    public void parseCSVToStringArrayWhenUrlOkTest() throws FileNotFoundException {
        String[] strings = fileService.parseFileToStringArray();

        assertEquals(5, strings.length);
    }

}