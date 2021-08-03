package com.cgm.quiz.up.service;

import com.cgm.quiz.up.entity.Question;
import com.cgm.quiz.up.utils.InputOptionsEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.cgm.quiz.up.utils.QuizUpConstants.MAX_INPUT_CHARS_SIZE;

@Service
public class InputValidatorService {

  public boolean isValidInputOption(String option) {
    return StringUtils.hasText(option)
        && (option.equals(InputOptionsEnum.A.getOption())
            || option.equals(InputOptionsEnum.B.getOption())
            || option.equals(InputOptionsEnum.C.getOption()));
  }

  public boolean isValidMaxCharSize(String input) {
    return StringUtils.hasText(input) && input.length() <= MAX_INPUT_CHARS_SIZE;
  }

  public boolean isValidQuestionObj(Question question) {
    return null != question && null != question.getAnswers() && !question.getAnswers().isEmpty();
  }
}
