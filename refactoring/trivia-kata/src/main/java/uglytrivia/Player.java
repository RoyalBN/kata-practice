package uglytrivia;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player {
    private final String name;
    private final int coins;
    private final int place;
    private final boolean inPenaltyBox;

    public Player(String name, int coins, int place, boolean inPenaltyBox) {
        this.name = name;
        this.coins = coins;
        this.place = place;
        this.inPenaltyBox = inPenaltyBox;
    }

}
