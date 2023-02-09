package spring.ru.otus.homework.service;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class FileServiceTest {

    private FileService fileService = new FileService("01-student-testing-app/src/test/resources/TestQuestionsForTest.csv");

    @Test
    public void parseCSVToStringArrayWhenUrlOkTest() throws FileNotFoundException {
        String[] strings = fileService.parseCSVToStringArray();

        assertEquals(5, strings.length);
    }

}