package shed;

/**
 * Enum that specifies the game type for the shed game. Each different gameType changes the rules of the game and how
 * long a single game lasts.
 */
public enum GameType {

    /**
     * Each player starts with 9 cards, 3 in each hand.
     *     The draw pile is only used to deal cards at the start, and once the game starts, becomes empty
     *     The player cannot stack cards, and can only play one card each turn (including 10's)
     *     Player wins when they no longer have any cards in any hands
     */
    BasicFast,

    /**
     * Each player starts with 9 cards, 3 in each hand.
     *     The draw pile is drawn from by each player when they have less than 3 cards.
     *         The draw pile is drawn from until it is empty.
     *     The player cannot stack cards, and can only play one card each turn (including 10's)
     *     Player wins when they no longer have any cards in any hands
     */
    Basic,

    /**
     * Each player starts with 9 cards, 3 in each hand.
     *     The draw pile is drawn from by each player when they have less than 3 cards.
     *         The draw pile is drawn from until it is empty.
     *     The player can stack cards of the same value, stacking 4 of the same will result in an extra turn.
     *     10's now also allow the player to play another card afterwards.
     *     Player wins when they no longer have any cards in any hands.
     */
    RegularFast,

    /**
     * Each player starts with 9 cards, 3 in each hand.
     *     The draw pile is only used to deal cards at the start, and once the game starts, becomes empty
     *     The player can stack cards of the same value, stacking 4 of the same will result in an extra turn.
     *     10's now also allow the player to play another card afterwards.
     *     Player wins when they no longer have any cards in any hands.
     */
    Regular
}
