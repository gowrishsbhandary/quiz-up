package com.cgm.quiz.up.repository;

import com.cgm.quiz.up.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

  Optional<Question> findByQuestionVal(String question);
}
