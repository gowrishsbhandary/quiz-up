package com.cgm.quiz.up.service;

import com.cgm.quiz.up.entity.Answer;
import com.cgm.quiz.up.entity.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class InputValidatorServiceTest {

  private final String QUESTION = "How are you";
  private final String[] ANSWER = {"a", "b"};
  @InjectMocks private InputValidatorService inputValidatorService;

  @Test
  void Given_validOptions_Expect_True() {
    Assertions.assertTrue(inputValidatorService.isValidInputOption("A"));
    Assertions.assertTrue(inputValidatorService.isValidInputOption("B"));
    Assertions.assertTrue(inputValidatorService.isValidInputOption("C"));
  }

  @Test
  void Given_InvalidOptions_Expect_False() {
    Assertions.assertFalse(inputValidatorService.isValidInputOption("X"));
    Assertions.assertFalse(inputValidatorService.isValidInputOption(""));
    Assertions.assertFalse(inputValidatorService.isValidInputOption(null));
  }

  @Test
  void Given_InputWithin_255Char_Expect_True() {
    Assertions.assertTrue(inputValidatorService.isValidMaxCharSize("XYZ"));
  }

  @Test
  void Given_InputNotWithin_255Char_Expect_True() {
    Assertions.assertFalse(
        inputValidatorService.isValidMaxCharSize(
            " XY ZQ WE RTYU IO PA SDFG HHH HHH KKK LLL ASFDSA DFASF SAFASFA "
                + " SFSAF AFA HJ KLA XCVB NM QW DDDD DDFFF FREERER DSDSDS"
                + " ERTYU UIO APSD FGHJKL ZXCV BNMA "
                + " LSKD JFH GY UTI DK FHFE DFE UHF IHFIHEFI HWFIIWHF IHD WVFI "
                + " FIVH ID FVH IDH IHF ID FHID HF HIDSA FISAH FH IH FIHA "));
    Assertions.assertFalse(inputValidatorService.isValidMaxCharSize(""));
    Assertions.assertFalse(inputValidatorService.isValidMaxCharSize(null));
  }

  @Test
  void Given_Valid_Question_Expect_True() {
    Assertions.assertTrue(inputValidatorService.isValidQuestionObj(buildQuestionEntity()));
  }

  @Test
  void Given_InValid_Question_Expect_False() {
    Assertions.assertFalse(inputValidatorService.isValidQuestionObj(null));
    Assertions.assertFalse(inputValidatorService.isValidQuestionObj(new Question()));
  }

  private Question buildQuestionEntity() {
    Set<Answer> answers = new HashSet(Arrays.asList(ANSWER));
    return new Question(1, QUESTION, answers);
    }
}
