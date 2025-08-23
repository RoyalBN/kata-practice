package uglytrivia;

import uglytrivia.enums.Categories;

import java.util.*;

public class TriviaGame {
    private final int MINIMUM_NUMBER_OF_PLAYERS = 2;
    private final int BOARD_SIZE = 12;
    private final int MAX_COINS = 6;
    private final int MAX_PLAYERS = 6;

    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    private Questions questions;

    public TriviaGame(){
        questions = new Questions();
    }

    public boolean isPlayable() {
        return (numberOfPlayers() >= MINIMUM_NUMBER_OF_PLAYERS);
    }

    public void add(String playerName) {
        if(numberOfPlayers() >= MAX_PLAYERS) {
            throw new IllegalArgumentException("A game cannot have more than " + MAX_PLAYERS + " players");
        }

        players.add(
                Player.builder()
                .name(playerName)
                .coins(0)
                .place(0)
                .inPenaltyBox(false)
                .build()
        );

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    private Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public int numberOfPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(currentPlayer().getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (currentPlayer().isInPenaltyBox()) {
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
            System.out.println(currentPlayer().getName() + " is getting out of the penalty box");
            moveAndAskQuestion(roll);
        } else {
            System.out.println(currentPlayer().getName() + " is not getting out of the penalty box");
        }
    }


    private void moveAndAskQuestion(int roll) {
        moveAndPrintInfos(roll);
        askQuestion();
    }

    private void moveAndPrintInfos(int roll) {
        currentPlayer().move(roll, BOARD_SIZE);
        System.out.println(currentPlayer().getName() + "'s new location is " + currentPlayer().getPlace());
        System.out.println("The category is " + currentCategory());
    }


    private void askQuestion() {
        System.out.println(questions.nextQuestion(currentCategory()));
    }

    private Categories currentCategory() {
        int playerCurrentPlace = currentPlayer().getPlace();
        return switch (playerCurrentPlace % 4) {
            case 0 -> Categories.POP;
            case 1 -> Categories.SCIENCE;
            case 2 -> Categories.SPORTS;
            default -> Categories.ROCK;
        };
    }

    public boolean wasCorrectlyAnswered() {
        if (currentPlayer().isInPenaltyBox()){
            System.out.println("Answer was correct!!!!");
            currentPlayer().leavePenaltyBox();
        } else {
            System.out.println("Answer was correct!!!!");
        }

        currentPlayer().reward();
        boolean winner = currentPlayer().hasWon(MAX_COINS);

        nextPlayer();
        return winner;
    }

    private void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.size()) currentPlayerIndex = 0;
    }

    public boolean wrongAnswer(){
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer().getName() + " was sent to the penalty box");
        currentPlayer().sendToPenaltyBox();

        nextPlayer();
        return true;
    }

    public int getPurse(int playerIndex) {
        return players.get(playerIndex).getCoins();
    }

    public boolean inPenaltyBox(int playerIndex) {
        return players.get(playerIndex).isInPenaltyBox();
    }



}
