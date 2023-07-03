package shed;

import java.util.ArrayList;

/**
 * Models a player of Shed. Each player has three hands, a name and a flag stating if they are a user or a cpu.
 */
public class Player {

    /**
     * The starting hand that needs to be empty before the constrained hand can be used.
     */
    private Hand generalHand;

    /**
     * The hand that sits on top of the hidden hand. ALl the cards need to be removed before hidden cards can be played.
     */
    private Hand constrainedHand;

    /**
     * The last hand of cards. The value of these cards can't be seen until they are played. Once this hand is empty, the
     * player has won the game.
     */
    private Hand hiddenHand;

    /**
     * The name of the player.
     */
    private final String name;

    /**
     * True if the player is the computer, else false for a user.
     */
    private final boolean isCpu;

    /**
     * Creates a new player and each of their hands.
     * @param name The name of the player.
     * @param isCpu True if they are an AI, false if they are a player.
     */
    public Player(String name, boolean isCpu) {
        generalHand = new Hand(HandType.Regular);
        constrainedHand = new Hand(HandType.Constrained);
        hiddenHand = new Hand(HandType.Hidden);
        this.name = name;
        this.isCpu = isCpu;
    }

    /**
     * Returns the general hand of the player.
     * @return The general hand of the player.
     */
    public Hand getGeneralHand() {
        return this.generalHand;
    }

    /**
     * Returns the constrained hand of the player.
     * @return The constrained hand of the player.
     */
    public Hand getConstrainedHand() {
        return this.constrainedHand;
    }

    /**
     * Returns the hidden hand of the player.
     * @return The hidden hand of the player.
     */
    public Hand getHiddenHand() {
        return this.hiddenHand;
    }

    /**
     * Returns the name of the player.
     * @return The name of the player.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns if this player is an AI or a human player.
     * @return If the player is an AI or a human player.
     */
    public boolean getIsCpu() {
        return this.isCpu;
    }

    /**
     * Adds the passed in card to the correct hand type.
     * @param card The card being added to the hand.
     * @param type The hand type that the card is being added to.
     */
    public void addToHand(Card card, HandType type) {
        switch(type) {
            case Regular -> generalHand.addCard(card);
            case Constrained -> constrainedHand.addCard(card);
            case Hidden -> hiddenHand.addCard(card);
        }
    }

    /**
     * Adds cards to the general hand of the player.
     * @param cards The cards to be added to the general hand.
     */
    public void addToGeneral(ArrayList<Card> cards) {
        generalHand.addCards(cards);
    }

}
