package org.example;

import java.util.*;

public class BracketValidator {

    public static boolean isValid(String sentence) {
        Deque<Character> bracketMap = new ArrayDeque<>();

        for(int i = 0; i < sentence.length(); i++) {
            if(sentence.charAt(i) == '(') {
                bracketMap.push(')');
            }
            else if(sentence.charAt(i) == '{') {
                bracketMap.push('}');
            }
            else if(sentence.charAt(i) == '[') {
                bracketMap.push(']');
            }
            else if(sentence.charAt(i) == ')' || sentence.charAt(i) == '}' || sentence.charAt(i) == ']') {
                if (bracketMap.isEmpty() || sentence.charAt(i) != bracketMap.pop()) {
                    return false;
                }
            }

        }
        return bracketMap.isEmpty();
    }
}
