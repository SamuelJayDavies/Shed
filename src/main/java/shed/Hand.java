package shed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Models a hand of cards that a particular player would hold. Can add/remove/get cards from their hand.
 */
public class Hand {

    /**
     * An arrayList of cards in the hand.
     */
    private ArrayList<Card> cards;

    /**
     * The hand type of the hand. Either General/Constrained/Hidden.
     */
    private final HandType handType;

    /**
     * Creates a hand of cards initially empty.
     *
     * @param handType The hand type of the hand. Either General/Constrained/Hidden.
     */
    public Hand(HandType handType) {
        this.handType = handType;
        cards = new ArrayList<>();
    }

    /**
     * Returns all the cards in the hand.
     *
     * @return An arrayList of the cards in the hand.
     */
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    /**
     * Returns the card from the hand corresponding to the provided index.
     *
     * @param index The position of the card in the hand. Starts from 0.
     * @return The card found in that position in the hand.
     */
    public Card getCard(int index) {
        try {
            return cards.get(index);
        } catch (ArrayIndexOutOfBoundsException error) {
            return null;
        }
    }

    /**
     * Returns the highest card in the hand.
     *
     * @return The highest card in the hand.
     */
    public Card getLowestRegularCard() {
        Card lowestCard = cards.get(0);
        for (Card card : cards) {
            if(card.getValue() != 2 && card.getValue() != 10) {
                if (card.getValue() < lowestCard.getValue() || lowestCard.getValue() == 2 || lowestCard.getValue() == 10) {
                    lowestCard = card;
                }
            }
        }
        return lowestCard;
    }

    /**
     * Returns the lowest card that is not a 2 or a 10.
     * @return Returns the lowest card that is not a 2 or a 10.
     */
    public Card getLowestSpecialCard() {
        Card specialCard = null;
        for (Card card : cards) {
            if(card.getValue() == 2 || card.getValue() == 10) {
                if(specialCard == null) {
                    specialCard = card;
                } else if(specialCard.getValue() == 10) {
                    specialCard = card;
                }
            }
        }
        return specialCard;
    }

    /**
     * Sorts the hand into ascending numerical order.
     */
    public void sortHand() {
        Collections.sort(this.cards, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getValue() - o2.getValue();
            }
        });
    }

    /**
     * Returns the hand type of the hand.
     *
     * @return The hand type of the hand.
     */
    public HandType getHandType() {
        return this.handType;
    }

    /**
     * Adds a new card to the deck.
     *
     * @param card A new card to the deck.
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Adds multiple new cards to the deck.
     *
     * @param cards An arrayList containing all the new cards.
     */
    public void addCards(ArrayList<Card> cards) {
        for (Card card : cards) {
            this.cards.add(card);
        }
    }

    /**
     * Returns the number of cards in the hand.
     *
     * @return The number of cards in the hand.
     */
    public int getNumOfCards() {
        return cards.size();
    }

    /**
     * Removes the card passed into the method from the hand.
     *
     * @param card The card to be removed.
     */
    public void removeCard(Card card) {
        for (Card currentCard : cards) {
            if (currentCard.equals(card)) {
                cards.remove(currentCard);
                return;
            }
        }
    }

    /**
     * Returns A String representation of the hand.
     *
     * @return A String representation of the hand.
     */
    @Override
    public String toString() {
        String msg = "";
        for (Card card : cards) {
            msg += card + "\n";
        }
        return msg;
    }

}
