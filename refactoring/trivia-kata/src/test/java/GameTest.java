import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import uglytrivia.Game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {

    private Game game;

    @BeforeEach
    void setup() {
        game = new Game();
    }


    // [Roll] In penalty box, roll odd number to get out
    //@Test
    //@DisplayName("[Roll] In penalty box, roll odd number to get out")
    //void should_get_out_of_penalty_box_when_roll_is_odd() {
    //    // Arrange
    //    game.add("Chat");
    //    game.add("Pat");
    //    game.add("Pam");
    //    game.add("Sue");
    //    game.add("Sally");
    //    game.add("Barry");
    //
    //    // Act
    //    game.roll(1);
    //    game.wrongAnswer();
    //    game.roll(2);
    //    game.wrongAnswer();
    //    game.roll(3);
    //    game.wrongAnswer();
    //    game.roll(4);
    //    game.wrongAnswer();
    //    game.roll(5);
    //    game.wasCorrectlyAnswered();
    //
    //    // Assert
    //
    //}

    // [Roll] In penalty box, roll even number to stay

    // [Roll] Not in penalty box, roll any number to move
    @Test
    @DisplayName("[Roll] Not in penalty box, roll any number to move")
    void should_move_when_not_in_penalty_box() {
        // Arrange
        game.add("Chat");
        game.add("Pat");
        game.add("Pam");
        game.add("Sue");
        game.add("Sally");
        game.add("Barry");

        // Act
        game.roll(1);
        game.wrongAnswer();
        game.roll(2);
        game.wrongAnswer();
        game.roll(3);
        game.wrongAnswer();
        game.roll(4);
        game.wrongAnswer();
        game.roll(5);
        game.wasCorrectlyAnswered();

        // Assert
        assertThat(game.getPlace(0)).isEqualTo(1);


    }


    /**
     * --------------------------------------------------------------------------------------
     * BUG : A Game could have less than two players - make sure it always has at least two.
     *
     * --> Use a compiled language or a static type checker like flowtype
     * --------------------------------------------------------------------------------------
     */

    @Test
    @DisplayName("[Game] A Game must have at least 2 players")
    void should_have_at_least_two_players_when_game_is_created() {
        // Arrange
        game.add("Chat");
        game.add("Pat");

        // Act
        boolean isPlayable = game.isPlayable();

        // Assert
        assertThat(isPlayable).isTrue();
    }

    @Tag("Bug")
    @Test
    @DisplayName("[Game] A Game cannot have less than two players")
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

    @Test
    @DisplayName("[Game] A Game cannot have 7 players")
    void should_throw_exception_when_game_has_more_than_six_players() {
        // Arrange
        game.add("Chat");
        game.add("Pat");
        game.add("Pam");
        game.add("Sue");
        game.add("Sally");
        game.add("Barry");

        // Act & Assert
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> game.add("Bob"));

        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A game cannot have more than 6 players");
    }

    // [Game] Roll should be between 1 and 6


    /**
     * --------------------------------------------------------------------------------------
     * BUG : A player that gets into prison always stays there
     *
     * --> Other than just fixing the bug, try to understand what’s
     *     wrong with the design and fix the root cause
     * --------------------------------------------------------------------------------------
     */

    // [Players] A player that gets into prison should get out
    //@Tag("characterization")
    //@Test
    //@DisplayName("[Players] A player that gets into prison should get out")
    //void should_get_out_of_prison() {
    //    // Arrange
    //    Game game = new Game();
    //    game.add("Chat");
    //    game.add("Pat");
    //    game.add("Pam");
    //    game.add("Sue");
    //    game.add("Sally");
    //    game.add("Barry");
    //
    //    // Act
    //    game.roll(1);
    //    game.wrongAnswer();
    //    game.roll(2);
    //    game.wrongAnswer();
    //    game.roll(3);
    //    game.wrongAnswer();
    //    game.roll(4);
    //    game.wrongAnswer();
    //    game.roll(5);
    //    game.wrongAnswer();
    //    game.roll(6);
    //    game.wrongAnswer();
    //
    //    // Assert
    //    //assertThat(game.isGettingOutOfPenaltyBox()).isTrue();
    //
    //}


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
