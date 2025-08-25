package uglytrivia;

import uglytrivia.enums.Categories;

public class GameRound {
    private final Player currentPlayer;
    private final Board board;
    private final Questions questions;

    public GameRound(Player currentPlayer, Board board, Questions questions) {
        this.currentPlayer = currentPlayer;
        this.board = board;
        this.questions = questions;
    }

    public void play(int roll) {
        System.out.println(currentPlayer.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            playTurnInPenaltyBox(roll);
        } else {
            playTurnNormally(roll);
        }
    }

    private void playTurnNormally(int roll) {
        moveAndAskQuestion(roll);
    }

    private void playTurnInPenaltyBox(int roll) {
        boolean isOdd = roll % 2 != 0;
        if (isOdd) {
            System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
            moveAndAskQuestion(roll);
        } else {
            System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
        }
    }

    private void moveAndAskQuestion(int roll) {
        int newPosition = board.calculateNewPosition(currentPlayer.getPosition(), roll);
        currentPlayer.setPosition(newPosition);
        System.out.println(currentPlayer.getName() + "'s new location is " + currentPlayer.getPosition());

        Categories category = board.getCategoryForPosition(currentPlayer.getPosition());
        System.out.println("The category is " + category);
        System.out.println(questions.nextQuestion(category));

    }
}
