package uglytrivia;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player {
    private String name;
    private int coins;
    private int place;
    private boolean inPenaltyBox;

    public Player(String name, int coins, int place, boolean inPenaltyBox) {
        this.name = name;
        this.coins = coins;
        this.place = place;
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

    public int getPlayerPlace() {
        return this.place;
    }

    public void move(int roll, int boardSize) {
        place += roll;
        if (place >= boardSize) {
            place -= boardSize;
        }
    }

    public boolean hasWon(int maxCoins) {
        return coins == maxCoins;
    }


}
