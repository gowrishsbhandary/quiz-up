package com.cgm.quiz.up.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuizUpException extends Exception {
  private final String errorMessage;
}
