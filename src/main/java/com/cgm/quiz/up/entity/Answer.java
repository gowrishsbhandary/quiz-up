package com.cgm.quiz.up.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "answer_id")
  private int id;

  @Column(name = "answer")
  private String answerVal;

  @ManyToOne private Question question;

  public Answer(String answerVal) {
    super();
    this.answerVal = answerVal;
  }

  public String getAnswer() {
    return answerVal;
  }
}
