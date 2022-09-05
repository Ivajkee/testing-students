package ru.otus.service;

import lombok.Data;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;
import ru.otus.exception.QuestionNotFoundException;

import java.util.List;

@Data
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;

    @Override
    public Question getQuestionById(long id) {
        return questionDao.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question with id = " + id + " not found!"));
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }
}
