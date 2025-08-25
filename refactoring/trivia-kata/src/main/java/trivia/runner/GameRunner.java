package trivia.runner;

import uglytrivia.TriviaGame;

import java.util.Random;

public class GameRunner {

    private final TriviaGame game;
    private final Random random = new Random();
    private static boolean notAWinner;

    public GameRunner(TriviaGame game) {
        this.game = game;
    }

    public static void main(String[] args) {
        TriviaGame aGame = new TriviaGame();

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random rand = new Random();
        boolean gameContinues = true;

        while (gameContinues) {
            aGame.roll(rand.nextInt(5) + 1);

            gameContinues = (rand.nextInt(9) == 7)
                    ? aGame.wrongAnswer()
                    : aGame.wasCorrectlyAnswered();
        }


    }
}
