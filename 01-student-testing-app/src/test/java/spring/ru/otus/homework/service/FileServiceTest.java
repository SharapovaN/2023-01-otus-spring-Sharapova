package spring.ru.otus.homework.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-test.yml")
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    public void parseCSVToStringArrayWhenUrlOkTest() throws FileNotFoundException {
        String[] strings = fileService.getDataFromFile();

        assertEquals(5, strings.length);
    }

}
