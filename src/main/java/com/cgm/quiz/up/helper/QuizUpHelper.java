package com.cgm.quiz.up.helper;

import com.cgm.quiz.up.entity.Question;
import com.cgm.quiz.up.service.InputValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static com.cgm.quiz.up.utils.QuizUpConstants.DEFAULT_ANSWER_KEY;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuizUpHelper {

  private final Environment environment;
  private final InputValidatorService inputValidatorService;

  public void printAnswer(Question question) {
    if (inputValidatorService.isValidQuestionObj(question)) {
      log.info("Answer(s) is/are :");
      question.getAnswers().forEach(answer -> log.info("{}", answer.getAnswer()));
    } else {
      print(DEFAULT_ANSWER_KEY);
    }
  }

  public void print(final String logString) {
    log.info(environment.getProperty(logString));
  }

  public void printError(final String logString) {
    log.error(logString);
  }
}
