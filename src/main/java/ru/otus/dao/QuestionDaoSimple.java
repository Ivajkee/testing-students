package ru.otus.dao;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.config.Config;
import ru.otus.domain.Question;

import java.util.List;
import java.util.Optional;

public class QuestionDaoSimple implements QuestionDao {
    private final List<Question> questions;

    @SneakyThrows
    public QuestionDaoSimple(Config config) {
        Resource resource = new ClassPathResource(config.getResourceUrl());
        ObjectMapper objectMapper = new CsvMapper();
        CsvSchema csvSchema = CsvSchema.builder()
                .setSkipFirstDataRow(true)
                .addColumn("id")
                .addColumn("text")
                .addColumn("answer.text")
                .addArrayColumn("variants")
                .build();
        MappingIterator<Question> questionMappingIterator = objectMapper
                .reader(csvSchema)
                .forType(Question.class)
                .readValues(resource.getInputStream());
        questions = questionMappingIterator.readAll();
    }

    @Override
    public Optional<Question> findById(long id) {
        return questions.stream()
                .filter(question -> question.getId() == id)
                .findFirst();
    }

    @Override
    public List<Question> findAll() {
        return questions;
    }
}
