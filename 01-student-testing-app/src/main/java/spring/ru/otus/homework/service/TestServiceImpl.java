package spring.ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import spring.ru.otus.homework.config.AppProps;
import spring.ru.otus.homework.model.QuestionDto;
import spring.ru.otus.homework.model.StudentDto;

import java.io.FileNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final QuestionService questionService;
    private final FileService fileService;
    private final StudentService studentService;
    private final IOService ioService;
    private final MessageSource messageSource;
    private final AppProps props;

    @Override
    public String runTest(StudentDto student) {
        List<QuestionDto> questions = null;
        try {
            questions = questionService.getQuestions(fileService.getDataFromFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ioService.outputString(messageSource.getMessage("test.start.phrase", null, props.getLocale()));

        for (QuestionDto question : questions) {
            questionService.printQuestion(question);
            boolean isAnswerRight = questionService.checkAnswer(ioService.readInt(), question);
            if (isAnswerRight) {
                studentService.increaseRightAnswersCounter(student);
            }
        }

        return studentService.getStudentTestingResult(student);
    }
}
