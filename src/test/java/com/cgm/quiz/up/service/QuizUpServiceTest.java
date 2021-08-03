package com.cgm.quiz.up.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizUpServiceTest {

  @Mock private Environment environment;
  @Mock private InputValidatorService inputValidatorService;
  @Mock private QuestionAnswerService questionAnswerService;
  @InjectMocks private QuizUpServiceImpl quizUpServiceImpl;

  @BeforeEach
  public void setUp() {
    when(inputValidatorService.isValidInputOption(any())).thenReturn(true);
    when(environment.getProperty("quiz.up.welcome.text"))
        .thenReturn("Hello There! Welcome to Quiz Up");
    when(environment.getProperty("quiz.up.option.text"))
        .thenReturn(
            "Please enter 'A' to add a question and answer, 'B' to fetch answer for a question, or 'C' to quit!");
  }

  @Test
  void Given_OptionA_Expect_AddQuestionAnswer_MethodCall() {
    String input = "A\nQuestion?answer";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    when(environment.getProperty("quiz.up.enter.question.answer"))
        .thenReturn(
            "Please enter a question and answers in this format : <question>?\"<answer1>\"\"<answer2>\"\"<answerX>.");
    quizUpServiceImpl.process();
    verify(questionAnswerService, atLeast(1)).addQuestionAnswer(any());
  }

  @Test
  void Given_Option_B_Expect_getAnswer_MethodCall() {
    String input = "B\nQuestion";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    when(environment.getProperty("quiz.up.enter.question")).thenReturn("Please enter a question.");
    quizUpServiceImpl.process();
    verify(questionAnswerService, atLeast(1)).getAnswer(any());
  }

  @Test
  void Given_Option_C_Expect_System_Termination() {
    String input = "C";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    when(environment.getProperty("quiz.up.goodbye.text"))
        .thenReturn("Thank you for using Quiz Up! Goodbye.");
    quizUpServiceImpl.process();
  }

  @Test
  void Given_Wrong_Option_Expect_Error() {
    String input = "X";
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);
    when(inputValidatorService.isValidInputOption(any())).thenReturn(false);
    when(environment.getProperty("quiz.up.invalid.option"))
        .thenReturn("Oops! Invalid option! Please try again.");
    quizUpServiceImpl.process();
  }
}
