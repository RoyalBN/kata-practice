package uglytrivia;

import uglytrivia.enums.Categories;

public class TriviaGame {

    private final PlayerManager playerManager;
    private final Questions questions;
    private final Board board;

    public TriviaGame(){
        playerManager = new PlayerManager();
        questions = new Questions();
        board = new Board();
    }

    public boolean isPlayable() {
        return playerManager.hasEnoughPlayers();
    }

    public void add(String playerName) {
        playerManager.addPlayer(playerName);
    }

    private Player currentPlayer() {
        return playerManager.getCurrentPlayer();
    }

    public Player getPlayer(int index) {
        return playerManager.getPlayer(index);
    }


    public void roll(int roll) {
        GameRound round = new GameRound(currentPlayer(), board, questions);
        if (playerManager.hasEnoughPlayers()) {
            round.play(roll);
        } else {
            throw new IllegalArgumentException("Not enough players to play");
        }
    }

    public boolean wasCorrectlyAnswered() {
        System.out.println("Answer was correct!!!!");
        if (currentPlayer().isInPenaltyBox()){
            currentPlayer().leavePenaltyBox();
        }

        currentPlayer().reward();
        boolean winner = currentPlayer().hasWon();

        nextPlayer();
        return winner;
    }

    private void nextPlayer() {
        playerManager.nextPlayer();
    }

    public boolean wrongAnswer(){
        System.out.println("Question was incorrectly answered");
        System.out.println(currentPlayer().getName() + " was sent to the penalty box");
        currentPlayer().sendToPenaltyBox();

        nextPlayer();
        return true;
    }

    public int getPurse(int playerIndex) {
        return playerManager.getPlayer(playerIndex).getCoins();
    }

    public boolean inPenaltyBox(int playerIndex) {
        return getPlayer(playerIndex).isInPenaltyBox();
    }

    public int remainingQuestions(Categories categorie) {
        return questions.remainingQuestions(categorie);
    }
}
