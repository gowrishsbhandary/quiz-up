package com.cgm.quiz.up.service;

import com.cgm.quiz.up.utils.InputOptionsEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Scanner;

import static com.cgm.quiz.up.utils.QuizUpConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuizUpServiceImpl implements QuizUpService {

  private final Environment environment;
  private final InputValidatorService inputValidatorService;
  private final QuestionAnswerService questionAnswerService;

  public void process() {

    log.info(
        "\n\n** -------------------- ** -------------------- ** -------------------- "
            + "** -------------------- ** -------------------- ** -------------------- **\n");
    print(WELCOME_TEXT_KEY);
    print(OPTION_TEXT_KEY);
    Scanner reader = new Scanner(System.in);
    while (reader.hasNext()) {
      String optionInput = reader.nextLine();
      if (inputValidatorService.isValidInputOption(optionInput)) {
        InputOptionsEnum option = InputOptionsEnum.valueOf(optionInput);
        switch (option) {
          case A:
            print(ENTER_QUESTION_ANSWER_KEY);
            questionAnswerService.addQuestionAnswer(reader.nextLine());
            break;
          case B:
            print(ENTER_QUESTION_KEY);
            questionAnswerService.getAnswer(reader.nextLine());
            break;
          case C:
            print(GOODBYE_TEXT_KEY);
            System.exit(1);
            break;
        }
      } else {
        print(INVALID_OPTION_KEY);
      }
    }
  }

  private void print(String logString) {
    log.info(environment.getProperty(logString));
  }
}
