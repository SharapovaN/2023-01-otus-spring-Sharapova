package spring.ru.otus.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import spring.ru.otus.homework.model.StudentDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource("classpath:application-test.yml")
public class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void increaseRightAnswersCounterTest() {
        StudentDto studentDto = new StudentDto("name", "surname");
        studentService.increaseRightAnswersCounter(studentDto);
        assertEquals(1, studentDto.getRightAnswersCount());
    }

    @Test
    public void getStudentTestingResultIfTestFailed() {
        StudentDto studentDto = new StudentDto("name", "surname");
        studentService.increaseRightAnswersCounter(studentDto);
        studentService.increaseRightAnswersCounter(studentDto);
        assertTrue(studentService.getStudentTestingResult(studentDto).contains("The test failed"));
    }

    @Test
    public void getStudentTestingResultIfTestOk() {
        StudentDto studentDto = new StudentDto("name", "surname");
        studentService.increaseRightAnswersCounter(studentDto);
        studentService.increaseRightAnswersCounter(studentDto);
        studentService.increaseRightAnswersCounter(studentDto);
        studentService.increaseRightAnswersCounter(studentDto);
        assertTrue(studentService.getStudentTestingResult(studentDto).contains("The test successfully passed"));
    }

}