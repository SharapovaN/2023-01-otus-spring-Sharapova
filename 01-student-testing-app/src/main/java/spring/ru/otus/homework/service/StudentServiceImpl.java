package spring.ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import spring.ru.otus.homework.model.StudentDto;
import spring.ru.otus.homework.utils.StringConstants;

public class StudentServiceImpl implements StudentService {

    private final IOService ioService;
    @Value("${number.right.answers.for.positive.result}")
    private int necessaryRightAnswersNumber;

    public StudentServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public StudentDto createStudent() {
        String name = ioService.readStringWithPrompt(StringConstants.ASK_STUDENT_NAME);
        String surname = ioService.readStringWithPrompt(StringConstants.ASK_STUDENT_SURNAME);
        return new StudentDto(name, surname);
    }

    @Override
    public String getStudentTestingResult(StudentDto studentDto) {

        return studentDto.getRightAnswersCount() >= necessaryRightAnswersNumber ?
                StringConstants.TEST_SUCCESSFULLY_PASSED_RESPONSE : StringConstants.TEST_FAILED_RESPONSE;
    }

    public void increaseRightAnswersCounter(StudentDto studentDto) {
        studentDto.setRightAnswersCount(studentDto.getRightAnswersCount() + 1);
    }

}
