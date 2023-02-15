package spring.ru.otus.homework.service;

import spring.ru.otus.homework.model.StudentDto;

public interface StudentService {
    StudentDto createStudent();

    String getStudentTestingResult(StudentDto studentDto);

    void increaseRightAnswersCounter(StudentDto studentDto);
}
