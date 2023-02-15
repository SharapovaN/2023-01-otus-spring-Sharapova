package spring.ru.otus.homework.service;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    private final FileService fileService = new CsvFileServiceImpl("resources/TestQuestionsForTest.csv");

    @Test
    public void parseCSVToStringArrayWhenUrlOkTest() throws FileNotFoundException {
        String[] strings = fileService.parseFileToStringArray();

        assertEquals(5, strings.length);
    }

}