package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.exception.QuestionNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceTest {
    private final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
    private final QuestionService questionService = applicationContext.getBean(QuestionService.class);

    @Test
    @DisplayName("Find all questions")
    void findAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        assertEquals(5, questions.size());
    }

    @Test
    @DisplayName("Find one question")
    void findOneQuestion() {
        Question expectedQuestion = new Question();
        expectedQuestion.setId(1);
        expectedQuestion.setText("What specialist is engaged in the study of unidentified flying objects?");
        expectedQuestion.setAnswer(new Answer("Ufologist"));
        expectedQuestion.setVariants(List.of(new Answer("Sexologist"), new Answer("Programmer"),
                new Answer("Psychologist"), new Answer("Ufologist")));
        Question actualQuestion = questionService.getQuestionById(1);
        assertNotNull(actualQuestion);
        assertEquals(expectedQuestion, actualQuestion);
    }

    @Test
    @DisplayName("Throw question not found exception")
    void throwQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class, () -> questionService.getQuestionById(6));
    }
}