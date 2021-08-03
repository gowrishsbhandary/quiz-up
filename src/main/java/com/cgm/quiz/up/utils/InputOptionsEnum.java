package com.cgm.quiz.up.utils;

public enum InputOptionsEnum {
  A("A"),
  B("B"),
  C("C");

  private final String option;

  InputOptionsEnum(String option) {
    this.option = option;
  }

  public String getOption() {
    return option;
  }
}
