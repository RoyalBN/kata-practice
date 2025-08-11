package org.example;

import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName()); // ➋ add logger

    public static void main(String[] args) {

        String[] samples = {
                "(_)_{_}_",
                "(_{_}_)_",
                "(_{_(_(_)_)_}_)_",
                "(_{_(_{_}_)_}_)_",
                "(_{_)_}_",
                "}_(_)_{_",
                "}_{_)_{_",
                "(_}_(_(_)_)_}_)_",
                "(_{_(_(_)_)_)_}_)_"
        };

        for (String expr : samples) {
            boolean result = BracketValidator.isValid(expr);
            logger.info(() -> String.format("%s => %s", expr, result));
        }
    }
}