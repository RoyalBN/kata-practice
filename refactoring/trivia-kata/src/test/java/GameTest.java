import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import uglytrivia.Board;
import uglytrivia.PlayerManager;
import uglytrivia.Questions;
import uglytrivia.TriviaGame;
import uglytrivia.enums.Categories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private TriviaGame game;
    private PlayerManager playerManager;
    private Questions questions;
    private Board board;

    @BeforeEach
    void setup() {
        game = new TriviaGame();
    }

    @Test
    @DisplayName("[PenaltyBox] In penalty box, roll odd number to get out")
    void should_get_out_of_penalty_box_when_roll_is_odd() {
        // Arrange
        game.add("Chat");
        game.add("Pat");
        game.add("Pam");
        game.add("Sue");
        game.add("Sally");
        game.add("Barry");

        // Act
        for (int i = 0; i < 6; i++) {
            game.roll(7);
            game.wrongAnswer();
        }
        game.roll(7);
        game.wasCorrectlyAnswered();

        // Assert
        assertThat(game.inPenaltyBox(0)).isFalse();
    }

    @Test
    @DisplayName("[PenaltyBox] In penalty box, roll even number to stay")
    void should_stay_in_penalty_box_when_roll_is_even() {
        // Arrange
        game.add("Chat");
        game.add("Pat");
        game.add("Pam");
        game.add("Sue");
        game.add("Sally");
        game.add("Barry");

        // Act
        for (int i = 0; i < 6; i++) {
            game.roll(4);
            game.wrongAnswer();
        }

        // Assert
        assertThat(game.inPenaltyBox(0)).isTrue();
    }

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
        game.roll(4);
        game.wasCorrectlyAnswered();

        // Assert
        assertThat(game.getPlayer(0).getPosition()).isEqualTo(4);
    }

    @Test
    @DisplayName("[Place] Move to place 4 when rolling 4")
    void should_move_to_place_4_when_rolling_4() {
        // Arrange
        game.add("Chat");
        game.add("Pat");
        game.add("Pam");
        game.add("Sue");
        game.add("Sally");
        game.add("Barry");

        // Act
        game.roll(4);
        game.wasCorrectlyAnswered();

        // Assert
        assertThat(game.getPlayer(0).getPosition()).isEqualTo(4);
    }

    @Test
    @DisplayName("[Place] Come back to place 0 when reaching place 12")
    void should_reset_place_to_zero_when_reaching_place_12() {
        // Arrange
        game.add("Chat");
        game.add("Pat");
        game.add("Pam");
        game.add("Sue");
        game.add("Sally");
        game.add("Barry");

        // Act
        for (int i = 0; i <= 12; i++) {
            game.roll(4);
            game.wasCorrectlyAnswered();
        }

        // Assert
        assertThat(game.getPlayer(0).getPosition()).isZero();
    }

    @Test
    @DisplayName("[Purse] Penalty Box & correct answer --> increase player's purse")
    void should_increase_player_purse_when_in_penalty_box_and_correct_answer() {
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

        for (int i = 0; i <= 12; i++) {
            game.roll(5);
            game.wasCorrectlyAnswered();
        }

        // Assert
        assertThat(game.getPurse(0)).isEqualTo(2);

    }

    @Test
    @DisplayName("[Purse] Not in penalty box & correct answer --> increase player's purse")
    void should_increase_player_purse_when_not_in_penalty_box_and_correct_answer() {
        // Arrange
        game.add("Chat");
        game.add("Pat");
        game.add("Pam");
        game.add("Sue");
        game.add("Sally");
        game.add("Barry");

        // Act
        for (int i = 0; i <= 12; i++) {
            game.roll(5);
            game.wasCorrectlyAnswered();
        }

        // Assert
        assertThat(game.getPurse(0)).isEqualTo(3);
    }

    @Test
    @DisplayName("[PenaltyBox] Wrong answer --> put player in penalty box")
    void should_put_player_in_penalty_box_when_wrong_answer() {
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

        // Assert
        assertThat(game.inPenaltyBox(0)).isTrue();
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


    /**
     * --------------------------------------------------------------------------------------
     * BUG : A player that gets into prison always stays there
     *
     * --> Other than just fixing the bug, try to understand what’s
     *     wrong with the design and fix the root cause
     * --------------------------------------------------------------------------------------
     */

    @Test
    @DisplayName("[PenaltyBox] Right answer --> remove player from penalty box")
    void should_remove_player_from_penalty_box_when_right_answer() {
        // Arrange
        game.add("Chat");
        game.add("Pat");
        game.add("Pam");
        game.add("Sue");
        game.add("Sally");
        game.add("Barry");

        // Act
        for (int i = 0; i < 12; i++) {
            game.roll(5);
            game.wrongAnswer();
        }
        game.roll(5);
        game.wasCorrectlyAnswered();

        // Assert
        assertThat(game.inPenaltyBox(0)).isFalse();
    }


    /**
     * --------------------------------------------------------------------------------------
     * BUG : The deck could run out of questions
     *
     * --> Make sure that can’t happen (a deck with 1 billion questions is cheating :)
     * --------------------------------------------------------------------------------------
     */

    @Test
    @DisplayName("[Deck] The deck cannot run out of questions")
    void should_not_run_out_of_questions() {
        // Arrange
        game.add("Chat");
        game.add("Pat");

        // Act
        for (int i = 0; i < 200; i++) {
            game.roll(5);
            game.wrongAnswer();
        }

        // Assert
        assertThat(game.remainingQuestions(Categories.SCIENCE)).isGreaterThanOrEqualTo(0);
    }


    /**
     * --------------------------------------------------------------------------------------
     * BUG : Introducing new categories of questions seems like tricky business
     *
     * --> Could you make sure all places have the “right” question and that the
     *     question distribution is always correct?
     * --------------------------------------------------------------------------------------
     */

    @DisplayName("[Board] Every position has a valid category")
    @Test
    void should_have_valid_category_for_every_position() {
        Board board = new Board();
        Categories[] categories = Categories.values();

        for (int pos = 0; pos < 2 * board.getBoardSize(); pos++) {
            Categories category = board.getCategoryForPosition(pos);
            assertNotNull(category, "Category should not be null for position " + pos);

            assertTrue(
                    java.util.Arrays.asList(categories).contains(category),
                    "Category " + category + " must be in Categories enum"
            );
        }
    }

    @DisplayName("[Board] Category distribution cycles correctly")
    @Test
    void should_distribute_categories_correctly() {
        Board board = new Board();
        Categories[] categories = Categories.values();

        for (int pos = 0; pos < categories.length * 3; pos++) {
            Categories expected = categories[pos % categories.length];
            Categories actual = board.getCategoryForPosition(pos);
            assertEquals(expected, actual,
                    "Expected " + expected + " but got " + actual + " at position " + pos);
        }
    }

    /**
     * --------------------------------------------------------------------------------------
     * BUG : Similarly changing the board size greatly affects the questions distribution
     * --------------------------------------------------------------------------------------
     */
    @Test
    @DisplayName("[Board] Changing board size does not affect questions distribution")
    void should_not_affect_questions_distribution_when_board_size_is_changed() {
        // Arrange
        Board board = new Board();
        int boardSize = 14;

        // Act
        for (int pos = 0; pos < boardSize; pos++) {
            Categories category = board.getCategoryForPosition(pos);

            // Check that category comes every 4 places
            Categories expected = switch (pos % 4) {
                case 0 -> Categories.POP;
                case 1 -> Categories.SCIENCE;
                case 2 -> Categories.SPORTS;
                default -> Categories.ROCK;
            };

            // Assert
            assertThat(category).isEqualTo(expected);
        }
    }

}
