package com.cgm.quiz.up.service;

import com.cgm.quiz.up.entity.Answer;
import com.cgm.quiz.up.entity.Question;
import com.cgm.quiz.up.helper.QuizUpHelper;
import com.cgm.quiz.up.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionAnswerServiceImplTest {

  private final String QUESTION = "How are you";
  private final String ANSWER = "\"Fine\"\"Not Fine\"";

  @Mock private InputValidatorService inputValidatorService;
  @Mock private QuestionRepository questionRepository;
  @Mock private QuizUpHelper quizUpHelper;
  @InjectMocks private QuestionAnswerServiceImpl questionAnswerService;

  @Test
  void Given_Valid_QuestionAnswer_String_Save_Successfully() {
    when(inputValidatorService.isValidMaxCharSize(any())).thenReturn(true);
    questionAnswerService.addQuestionAnswer(QUESTION + "?" + ANSWER);
    verify(questionRepository, atLeast(1)).save(any());
    verify(quizUpHelper, atLeast(2)).print(any());
  }

  @Test
  void Given_InValid_QuestionAnswer_String_Call_PrintErrorMessage() {
    questionAnswerService.addQuestionAnswer(QUESTION + ANSWER);
    verify(quizUpHelper, atLeast(1)).print(any());
  }

  @Test
  void Given_QuestionAnswer_WithMoreThan_255Char_String_Call_PrintErrorMessage() {
    questionAnswerService.addQuestionAnswer(QUESTION + "?" + ANSWER);
    verify(quizUpHelper, atLeast(1)).print(any());
  }

  @Test
  void Given_Existing_QuestionAnswer_Expect_PrintError() {
    when(inputValidatorService.isValidMaxCharSize(any())).thenReturn(true);
    Question question = buildQuestionEntity();
    when(questionRepository.findByQuestionVal(QUESTION)).thenReturn(Optional.of(question));
    questionAnswerService.addQuestionAnswer(QUESTION + "?" + ANSWER);
    verify(quizUpHelper, atLeast(1)).printError(any());
  }

  @Test
  void Given_InvalidAnswer_StringFormat_Expect_PrintError() {
    when(inputValidatorService.isValidMaxCharSize(any())).thenReturn(true);
    questionAnswerService.addQuestionAnswer(QUESTION + "?" + "wrong answer format");
    verify(quizUpHelper, atLeast(1)).printError(any());
  }

  @Test
  void Given_EmptyAnswer_String_Expect_PrintError() {
    when(inputValidatorService.isValidMaxCharSize(any())).thenReturn(true);
    questionAnswerService.addQuestionAnswer(QUESTION + "?" + "\"\"");
    verify(quizUpHelper, atLeast(1)).print(any());
  }

  @Test
  void Given_Question_CallGetAnswer_Successfully() {
    when(inputValidatorService.isValidMaxCharSize(any())).thenReturn(true);
    questionAnswerService.getAnswer(QUESTION);
    verify(questionRepository, atLeast(1)).findByQuestionVal(any());
    verify(quizUpHelper, atLeast(1)).printAnswer(any());
  }

  @Test
  void Given_Question_CallGetAnswer_ReturnQuestion_Successfully() {
    when(inputValidatorService.isValidMaxCharSize(any())).thenReturn(true);
    Question question = buildQuestionEntity();
    when(questionRepository.findByQuestionVal(QUESTION)).thenReturn(Optional.of(question));
    questionAnswerService.getAnswer(QUESTION);
    verify(questionRepository, atLeast(1)).findByQuestionVal(any());
    verify(quizUpHelper, atLeast(1)).printAnswer(any());
  }

  @Test
  void Given_Invalid_Question_CallGetAnswer_Successfully() {
    questionAnswerService.getAnswer(QUESTION);
    verify(quizUpHelper, atLeast(1)).printError(any());
  }

  @Test
  void Given_valid_Question_AnswerList_Call_Save_Successfully() {
    questionAnswerService.saveQuestionAnswer(QUESTION, getAnswers("Fine"));
    verify(questionRepository, atLeast(1)).save(any());
    verify(quizUpHelper, atLeast(2)).print(any());
  }

  private Question buildQuestionEntity() {
    Set<Answer> answers = getAnswers(ANSWER);
    return new Question(1, QUESTION, answers);
  }

  private Set<Answer> getAnswers(String answer) {
    Set<Answer> answers = new HashSet<>();
    answers.add(new Answer(answer));
    return answers;
  }
}
