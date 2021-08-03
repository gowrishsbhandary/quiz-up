package com.cgm.quiz.up;

import com.cgm.quiz.up.service.QuizUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class QuizUpApplication implements CommandLineRunner {

  private final QuizUpService quizUpService;

  public static void main(String[] args) {
    SpringApplication.run(QuizUpApplication.class, args);
  }

  @Override
  public void run(String... args) {
    quizUpService.process();
  }
}
