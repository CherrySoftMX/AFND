package me.hikingcarrot7.afnd.core.afnd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AFNDInputTest {
  private AFNDInput input;

  @BeforeEach
  void setUp() {
    input = new AFNDInput("test");
  }

  @Test
  void whenGetFirstChar_thenReturnFirstCharWithoutModifyingCurrentState() {
    String firstChar = input.getFirstChar();

    assertEquals("t", firstChar);
    assertEquals("test", input.toString());
  }

  @Test
  void whenGetFirstCharOnEmptyInput_thenThrowsAnException() {
    emptyInput(input);

    assertThrows(IllegalStateException.class, () -> {
      input.getFirstChar();
    });
  }

  @Test
  void whenRemoveFirstChar_thenReturnsTrue_andFirstCharIsRemovedFromInput() {
    boolean shouldBeTrue = input.removeFirstChar();

    assertTrue(shouldBeTrue);
    assertEquals("est", input.toString());
  }

  @Test
  void whenTryingToRemoveFirstCharFromEmptyInput_thenReturnsFalse() {
    emptyInput(input);

    boolean shouldBeFalse = input.removeFirstChar();

    assertFalse(shouldBeFalse);
  }

  @Test
  void whenMakeCopy_thenReturnsNewObjectWithCurrentState() {
    input.removeFirstChar();

    AFNDInput inputCopy = input.makeCopy();

    assertNotEquals(inputCopy, input);
    assertEquals("est", inputCopy.toString());
  }

  private void emptyInput(AFNDInput input) {
    for (char ch : input.toString().toCharArray()) {
      input.removeFirstChar();
    }
  }

}