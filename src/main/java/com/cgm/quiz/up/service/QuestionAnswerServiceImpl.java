package com.cgm.quiz.up.service;

import com.cgm.quiz.up.entity.Answer;
import com.cgm.quiz.up.entity.Question;
import com.cgm.quiz.up.exception.QuizUpException;
import com.cgm.quiz.up.helper.QuizUpHelper;
import com.cgm.quiz.up.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cgm.quiz.up.utils.QuizUpConstants.*;

@Service
@RequiredArgsConstructor
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

  private final InputValidatorService inputValidatorService;
  private final QuestionRepository questionRepository;
  private final QuizUpHelper quizUpHelper;

  @Override
  public void addQuestionAnswer(String questionAndAnswer) {
    try {
      String[] questionAnswerArray = questionAndAnswer.split("\\?");
      String questionVal = questionAnswerArray[0];
      if (questionAnswerArray.length > 1 && inputValidatorService.isValidMaxCharSize(questionVal)) {
        String answerVal = questionAnswerArray[1];
        if (questionRepository.findByQuestionVal(questionVal).isPresent()) {
          throw new QuizUpException(
              questionVal + " - is already in the system, Please enter new question and answer!");
        }
        processQuestionAnswer(questionVal, answerVal);
      } else {
        quizUpHelper.print(INVALID_QUESTION_KEY);
      }
    } catch (Exception e) {
      quizUpHelper.printError("Error : {}" + e.getMessage());
    }
  }

  @Override
  public void getAnswer(String questionVal) {
    try {
      if (inputValidatorService.isValidMaxCharSize(questionVal)) {
        Question question = questionRepository.findByQuestionVal(questionVal).orElse(null);
        quizUpHelper.printAnswer(question);
      } else {
        quizUpHelper.printError("Question should be within 255 character limit!");
      }
    } catch (Exception e) {
      quizUpHelper.printError("Error while processing Question {}" + e.getMessage());
    }
  }

  private void processQuestionAnswer(String questionVal, String answerVal) {
    Pattern pattern = Pattern.compile(ANSWER_FORMAT);
    Matcher matcher = pattern.matcher(answerVal);
    Set<Answer> answersList = new HashSet<>();
    while (matcher.find()) {
      if (inputValidatorService.isValidMaxCharSize(matcher.group(1))) {
        answersList.add(new Answer(matcher.group(1)));
      } else {
        quizUpHelper.printError("Answer should be within 255 character limit!");
      }
    }
    saveQuestionAnswer(questionVal, answersList);
  }

  @Transactional
  public void saveQuestionAnswer(String questionVal, Set<Answer> answersList) {
    if (!answersList.isEmpty()) {
      Question question = new Question();
      question.setQuestion(questionVal);
      question.setAnswers(answersList);
      questionRepository.save(question);

      quizUpHelper.print(QUESTION_ANSWER_SAVED_KEY);
      quizUpHelper.print(OPTION_TEXT_KEY);
    } else {
      quizUpHelper.printError(
          "A question should at least have one answer in \"<answer1>\" format!");
    }
  }
}
