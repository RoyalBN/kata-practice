import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uglytrivia.Game;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    private Game game;

    @BeforeEach
    void setup() {
        game = new Game();
    }

    /**
     * --------------------------------------------------------------------------------------
     * BUG : A Game could have less than two players - make sure it always has at least two.
     *
     * --> Use a compiled language or a static type checker like flowtype
     * --------------------------------------------------------------------------------------
     */

    @Test
    @DisplayName("[Players] A Game must have at least 2 players")
    void should_have_at_least_two_players_when_game_is_created() {
        // Arrange
        game.add("Chat");
        game.add("Pat");

        // Act
        boolean isPlayable = game.isPlayable();

        // Assert
        assertThat(isPlayable).isTrue();
    }

    // [Players] A Game cannot have less than two players
    @Test
    @DisplayName("[Players] A Game cannot have less than two players")
    void should_throw_exception_when_game_has_less_than_two_players() {
        // Arrange
        game.add("Chat");

        // Act
        boolean isPlayable = game.isPlayable();

        // Assert
        assertThat(isPlayable).isFalse();
    }


/**
 * --------------------------------------------------------------------------------------
 * BUG : A Game could have 7 players, make it have at most 6.
 *
 * --> or slightly easier allow for 7 players or more
 * --------------------------------------------------------------------------------------
 */

// [Players] A Game could have at most 6 players


/**
 * --------------------------------------------------------------------------------------
 * BUG : A player that get’s into prison always stays there
 *
 * --> Other than just fixing the bug, try to understand what’s
 *     wrong with the design and fix the root cause
 * --------------------------------------------------------------------------------------
 */

// [Players] A player that gets into prison should get out


/**
 * --------------------------------------------------------------------------------------
 * BUG : The deck could run out of questions
 *
 * --> Make sure that can’t happen (a deck with 1 billion questions is cheating :)
 * --------------------------------------------------------------------------------------
 */

// [Deck] The deck cannot run out of questions


/**
 * --------------------------------------------------------------------------------------
 * BUG : Introducing new categories of questions seems like tricky business
 *
 * --> Could you make sure all places have the “right” question and that the
 *     question distribution is always correct?
 * --------------------------------------------------------------------------------------
 */


/**
 * --------------------------------------------------------------------------------------
 * BUG : Similarly changing the board size greatly affects the questions distribution
 * --------------------------------------------------------------------------------------
 */

}
