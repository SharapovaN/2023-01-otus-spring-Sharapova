package spring.ru.otus.homework.model;

public class StudentDto {
    private String name;
    private String surname;
    private int rightAnswersCount = 0;

    public StudentDto(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getRightAnswersCount() {
        return rightAnswersCount;
    }

    public void setRightAnswersCount(int rightAnswersCount) {
        this.rightAnswersCount = rightAnswersCount;
    }
}
