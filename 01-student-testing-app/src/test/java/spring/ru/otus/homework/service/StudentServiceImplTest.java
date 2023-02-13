package spring.ru.otus.homework.service;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import spring.ru.otus.homework.model.StudentDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentServiceImplTest {

    @Mock
    private IOService ioService;

    private final StudentService studentService = new StudentServiceImpl(ioService, 4);

    @Test
    public void increaseRightAnswersCounterTest() {
        StudentDto studentDto = new StudentDto("name", "surname");
        studentService.increaseRightAnswersCounter(studentDto);
        assertEquals(1, studentDto.getRightAnswersCount());
    }

}