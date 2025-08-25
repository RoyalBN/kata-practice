package uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 6;

    public void addPlayer(String playerName) {
        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be empty");
        }

        if(players.size() >= MAX_PLAYERS) {
            throw new IllegalArgumentException("A game cannot have more than " + MAX_PLAYERS + " players");
        }

        Player newPlayer = Player.builder()
                .name(playerName)
                .coins(0)
                .position(0)
                .inPenaltyBox(false)
                .build();

        players.add(newPlayer);

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public boolean hasEnoughPlayers() {
        return players.size() >= MIN_PLAYERS;
    }
}
