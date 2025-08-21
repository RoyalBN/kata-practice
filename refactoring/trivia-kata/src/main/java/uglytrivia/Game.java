package uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    public static final int MINIMUM_NUMBER_OF_PLAYERS = 2;
    public static final int BOARD_SIZE = 12;
    public static final int MAX_COINS = 6;
    public static final int MAX_PLAYERS = 6;

    List<Player> playerList = new ArrayList<Player>();
    List players = new ArrayList();
    int[] places = new int[MAX_PLAYERS];
    int[] purses  = new int[MAX_PLAYERS];
    boolean[] inPenaltyBox  = new boolean[MAX_PLAYERS];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public  Game(){
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    public String createRockQuestion(int index){
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= MINIMUM_NUMBER_OF_PLAYERS);
    }

    public boolean add(String playerName) {
        if(howManyPlayers() >= MAX_PLAYERS) {
            throw new IllegalArgumentException("A game cannot have more than " + MAX_PLAYERS + " players");
        }

        //players.add(playerName);
        playerList.add(
                Player.builder()
                .name(playerName)
                .coins(0)
                .place(0)
                .build()
        );
        //int playerIndex = players.size() - 1;
        int playerIndex = playerList.size() - 1;
        places[playerIndex] = 0;
        purses[playerIndex] = 0;
        inPenaltyBox[playerIndex] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + playerList.size());
        return true;
    }

    public int howManyPlayers() {
        return playerList.size();
    }

    public void roll(int roll) {
        //System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println(playerList.get(currentPlayer).getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                //System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                System.out.println(playerList.get(currentPlayer).getName() + " is getting out of the penalty box");
                places[currentPlayer] += roll;
                if (places[currentPlayer] >= 12) {
                    places[currentPlayer] -= BOARD_SIZE;
                }

                //System.out.println(players.get(currentPlayer) + "'s new location is " + places[currentPlayer]);
                System.out.println(playerList.get(currentPlayer).getName() + "'s new location is " + playerList.get(currentPlayer).getPlace());
                System.out.println("The category is " + currentCategory());
                askQuestion();
            } else {
                //System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                System.out.println(playerList.get(currentPlayer).getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] += roll;
            if (places[currentPlayer] >= 12) {
                places[currentPlayer] -= BOARD_SIZE;
            }

            //System.out.println(players.get(currentPlayer) + "'s new location is " + places[currentPlayer]);
            System.out.println(playerList.get(currentPlayer).getName() + "'s new location is " + playerList.get(currentPlayer).getPlace());
            System.out.println("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void askQuestion() {
        if ("Pop".equals(currentCategory()))
            System.out.println(popQuestions.removeFirst());
        if ("Science".equals(currentCategory()))
            System.out.println(scienceQuestions.removeFirst());
        if ("Sports".equals(currentCategory()))
            System.out.println(sportsQuestions.removeFirst());
        if ("Rock".equals(currentCategory()))
            System.out.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        int playerCurrentPlace = places[currentPlayer];
        return getCategoryFor(playerCurrentPlace);
    }

    private static String getCategoryFor(int playerCurrentPlace) {
        if (playerCurrentPlace == 0) return "Pop";
        if (playerCurrentPlace == 4) return "Pop";
        if (playerCurrentPlace == 8) return "Pop";
        if (playerCurrentPlace == 1) return "Science";
        if (playerCurrentPlace == 5) return "Science";
        if (playerCurrentPlace == 9) return "Science";
        if (playerCurrentPlace == 2) return "Sports";
        if (playerCurrentPlace == 6) return "Sports";
        if (playerCurrentPlace == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]){
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                purses[currentPlayer]++;
                //System.out.println(players.get(currentPlayer) + " now has " + purses[currentPlayer] + " Gold Coins.");
                System.out.println(playerList.get(currentPlayer).getName() + " now has " + playerList.get(currentPlayer).getCoins() + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == playerList.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == playerList.size()) currentPlayer = 0;
                return true;
            }



        } else {

            System.out.println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            //System.out.println(players.get(currentPlayer) + " now has " + purses[currentPlayer] + " Gold Coins.");
            System.out.println(playerList.get(currentPlayer).getName() + " now has " + playerList.get(currentPlayer).getCoins() + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == playerList.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer(){
        System.out.println("Question was incorrectly answered");
        //System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
        System.out.println(playerList.get(currentPlayer).getName() + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == playerList.size()) currentPlayer = 0;
        return true;
    }

    private boolean didPlayerWin() {
        return (purses[currentPlayer] != MAX_COINS);
    }

    public int getPlace(int playerIndex) {
        return places[playerIndex];
    }
}
