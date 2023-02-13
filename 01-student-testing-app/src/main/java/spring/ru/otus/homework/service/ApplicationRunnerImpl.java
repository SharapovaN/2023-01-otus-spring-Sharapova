package spring.ru.otus.homework.service;

import org.springframework.stereotype.Service;
import spring.ru.otus.homework.model.QuestionDto;
import spring.ru.otus.homework.model.StudentDto;
import spring.ru.otus.homework.utils.StringConstants;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final IOService ioService;
    private final StudentService studentService;
    private final QuestionService questionService;
    private final ApplicationStopService applicationStopService;

    public ApplicationRunnerImpl(IOService ioService, StudentService studentService,
                                 QuestionService questionService, ApplicationStopService applicationStopService) {
        this.ioService = ioService;
        this.studentService = studentService;
        this.questionService = questionService;
        this.applicationStopService = applicationStopService;
    }

    @Override
    public void run() {
        while (applicationStopService.isApplicationRunning()) {

            ioService.outputString(StringConstants.WELCOME_PHRASE);
            StudentDto student = studentService.createStudent();
            ioService.outputString(StringConstants.TEST_START_PHRASE);
            List<QuestionDto> questions = null;

            try {
                questions = questionService.getQuestions();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            for (QuestionDto question : questions) {
                questionService.printQuestion(question);
                boolean isAnswerRight = questionService.checkAnswer(ioService.readInt(), question);
                if (isAnswerRight) {
                    studentService.increaseRightAnswersCounter(student);
                }
            }

            ioService.outputString(studentService.getStudentTestingResult(student));
            applicationStopService.stopTesting();
        }
    }
}
