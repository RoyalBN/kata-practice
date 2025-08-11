package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The exercise is simple: it's about opening/closing tags:
 * - We have a method that takes a string as a parameter. Within this string, there are opening tags "(", "{", "[" and closing tags ")", "}", "]".
 * - We want to check if, for an opening tag (e.g., "{"), there is an equivalent closing tag (e.g., "}").
 * - The idea is to check if the order of the opening and closing tags is respected.
 * Here are some examples:
 * _(_)_{_}_ => true
 * _(_{_}_)_ => true
 * _(_{_(_(_)_)_}_)_ => true
 * _(_{_(_{_}_)_}_)_ => true
 * _(_{_)_}_ => false
 * _}_(_)_{_ => false
 * _}_{_)_{_ => false
 * _(_}_(_(_)_)_}_)_ => false
 * _(_{_(_(_)_)_)_}_)_ => true
 */

public class BracketValidatorTest {

    @Test
    @DisplayName("[VALID] No brackets")
    void should_return_true_for_no_brackets() {
        // Arrange
        String sentence = "";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[VALID] Empty parenthesis")
    void should_return_true_for_empty_parenthesis() {
        // Arrange
        String sentence = "()";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[VALID] Empty square brackets")
    void should_return_true_for_empty_square_brackets() {
        // Arrange
        String sentence = "[]";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[VALID] Empty curly brackets")
    void should_return_true_for_empty_curly_brackets() {
        // Arrange
        String sentence = "{}";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[VALID] Combined parenthesis and square brackets")
    void should_return_true_for_combined_parenthesis_and_square_brackets() {
        // Arrange
        String sentence = "()[]";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[VALID] Combined parenthesis, square brackets and curly brackets")
    void should_return_true_for_combined_parenthesis_square_brackets_and_curly_brackets() {
        // Arrange
        String sentence = "(){}[]";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[VALID] Combined brackets in different order")
    void should_return_true_for_combined_brackets_in_different_order() {
        // Arrange
        String sentence = "{}()[]";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[VALID] Nested brackets")
    void should_return_true_for_nested_brackets() {
        // Arrange
        String sentence = "{()}[]";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[VALID] Complex nested brackets")
    void should_return_true_for_complex_nested_brackets() {
        // Arrange
        String sentence = "_(_{_(_(_)_)_}_)_";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[INVALID] Missing closing bracket")
    void should_return_false_for_missing_closing_bracket() {
        // Arrange
        String sentence = "(_{_}";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("[INVALID] Missing opening bracket")
    void should_return_false_for_missing_opening_bracket() {
        // Arrange
        String sentence = "{_)_}";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("[INVALID] Wrong closing bracket")
    void should_return_false_for_wrong_closing_bracket() {
        // Arrange
        String sentence = "(_{_}]";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("[INVALID] Wrong order")
    void should_return_false_for_wrong_order() {
        // Arrange
        String sentence = "{)(}[]";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("[INVALID] Nested brackets")
    void should_return_false_for_nested_brackets() {
        // Arrange
        String sentence = "_(_}_(_(_)_)_}_)_";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("[INVALID] Complex nested brackets")
    void should_return_false_for_complex_nested_brackets() {
        // Arrange
        String sentence = "_(_{_(_(_)_)_)_}_)_";

        // Act
        Boolean result = BracketValidator.isValid(sentence);

        // Assert
        assertThat(result).isFalse();
    }
}