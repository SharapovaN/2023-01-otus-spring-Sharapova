package ru.otus.homework.service;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class FileServiceTest {

    private FileService fileService = new FileService();

    @Test
    public void parseCSVToStringArrayWhenUrlOkTest() throws FileNotFoundException {
        String[] strings = fileService.parseCSVToStringArray("01-student-testing-app/src/test/resourses/TestQuestionsForTest.csv");

        assertEquals(5, strings.length);
    }

    @Test
    public void parseCSVToStringArrayWhenUrlNotOkTest() throws FileNotFoundException {
        assertThrows(FileNotFoundException.class, () -> {
            fileService.parseCSVToStringArray("01-student-testing-app/src/test/resourses/TestQuestionsForTest1.csv");
        });
    }

}