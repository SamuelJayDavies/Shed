package shed;

/**
 * Enum used for when a deck is created. If the deck is a draw type, it will populate the deck. If the deck
 * is a discard deck, it will be left empty.
 */
public enum DeckType {

    /**
     * For a draw pile or deck. Starts populated and cards are only taken from it.
     */
    DRAW,

    /**
     * For a discard pile or deck. Starts empty and cards are added and taken.
     */
    DISCARD
}
