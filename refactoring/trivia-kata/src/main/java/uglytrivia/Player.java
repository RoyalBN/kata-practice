package uglytrivia;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player {
    private String name;
    private int coins;
    private int position;
    private boolean inPenaltyBox;
    private static final int MAX_COINS = 6;

    public Player(String name, int coins, int position, boolean inPenaltyBox) {
        this.name = name;
        this.coins = coins;
        this.position = position;
        this.inPenaltyBox = inPenaltyBox;
    }

    public void reward() {
        coins++;
        System.out.println(name + " now has " + coins + " Gold Coins.");
    }

    public void sendToPenaltyBox() {
        this.inPenaltyBox = true;
    }

    public void leavePenaltyBox() {
        this.inPenaltyBox = false;
    }

    public boolean hasWon() {
        return coins == MAX_COINS;
    }
}
