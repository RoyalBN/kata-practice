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
    @DisplayName("[VALID] Empty double quotes")
    void should_return_true_if_empty_bracket() {
        assertThat(BracketValidator.isValid("")).isTrue();
    }

    // [VALID] Empty parenthesis
    // [VALID] Empty square brackets
    // [VALID] Empty curly brackets


    // [INVALID] Empty parenthesis
    // [INVALID] Empty square brackets
    // [INVALID] Empty curly brackets
}