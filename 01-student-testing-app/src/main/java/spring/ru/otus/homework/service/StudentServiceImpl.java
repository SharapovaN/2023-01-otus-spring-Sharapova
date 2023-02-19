package spring.ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import spring.ru.otus.homework.config.AppProps;
import spring.ru.otus.homework.model.StudentDto;

@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;
    private final MessageSource messageSource;
    private final AppProps props;
    private final int necessaryRightAnswersNumber;


    public StudentServiceImpl(IOService ioService, MessageSource messageSource, AppProps props,
                              @Value("${application.number.right.answers.for.positive.result}") int necessaryRightAnswersNumber) {
        this.ioService = ioService;
        this.necessaryRightAnswersNumber = necessaryRightAnswersNumber;
        this.messageSource = messageSource;
        this.props = props;
    }

    @Override
    public StudentDto createStudent() {
        String name = ioService.readStringWithPrompt(messageSource.getMessage("ask.student.name", null,
                props.getLocale()));
        String surname = ioService.readStringWithPrompt(messageSource.getMessage("ask.student.surname", null,
                props.getLocale()));
        return new StudentDto(name, surname);
    }

    @Override
    public String getStudentTestingResult(StudentDto studentDto) {

        return studentDto.getRightAnswersCount() >= necessaryRightAnswersNumber ?
                messageSource.getMessage("test.successfully.passed.response", null, props.getLocale()) :
                messageSource.getMessage("test.failed.response", null, props.getLocale());
    }

    @Override
    public void increaseRightAnswersCounter(StudentDto studentDto) {
        studentDto.setRightAnswersCount(studentDto.getRightAnswersCount() + 1);
    }

}
