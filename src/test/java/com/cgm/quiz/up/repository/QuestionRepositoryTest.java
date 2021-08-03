package com.cgm.quiz.up.repository;

import com.cgm.quiz.up.entity.Answer;
import com.cgm.quiz.up.entity.Question;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
class QuestionRepositoryTest {

  private final String QUESTION = "How are you";
  private final String ANSWER1 = "Fine";
  private final String ANSWER2 = "Not Fine";

  @Autowired private QuestionRepository questionRepository;
  @Autowired private TestEntityManager entityManager;

  @Test
  void injectedComponentsAreNotNull() {
    Assertions.assertThat(questionRepository).isNotNull();
    Assertions.assertThat(entityManager).isNotNull();
  }

  @Test
  void getAnswerForQuestionTest() {
    Question questionEntity = buildQuestionEntity();
    questionRepository.save(questionEntity);
    Optional<Question> question = questionRepository.findByQuestionVal("How are you");
    Assertions.assertThat(question).isPresent();
    Assertions.assertThat(question.get().getAnswers().size()).isEqualTo(2);
  }

  private Question buildQuestionEntity() {
    Set<Answer> answers = new HashSet<>();
    answers.add(new Answer(ANSWER1));
    answers.add(new Answer(ANSWER2));
    return new Question(1, QUESTION, answers);
  }
}
