package shed;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that models a deck of cards. Has the ability to generate, shuffle and deal cards.
 */
public class Deck {

    /**
     * The size of a deck of cards.
     */
    public static final int DECK_SIZE = 52;

    /**
     * An arrayList containing the Deck cards.
     */
    private ArrayList<Card> cards;

    /**
     * Creates a draw or discard deck. Generates the deck when deckType is draw.
     * @param deckType The deck type of the deck. Either draw or discard.
     */
    public Deck(DeckType deckType) {
        if(deckType.equals(DeckType.DRAW)) {
            cards = generateDeck();
        } else {
            cards = new ArrayList<>();
        }
    }

    /**
     * Generates a traditional deck of 52 cards, 12 for each suit.
     * @return Returns the complete deck.
     */
    public ArrayList<Card> generateDeck() {
        ArrayList<Card> cards = new ArrayList<>();
        for(Suit suit: Suit.values()) {
            for(int i=1; i<=13; i++) {
                cards.add(new Card(i, suit));
            }
        }
        return cards;
    }

    /**
     * Shuffles the deck randomly.
     */
    public void shuffle() {
        Random random = new Random();
        int deckSize = cards.size();
        for(int j=deckSize; j>0; j--) {
            int position = random.nextInt(j);
            cards.add(cards.get(position));
            cards.remove(cards.get(position));
        }
    }

    /**
     * Returns the top card of the deck.
     * @return The top card of the deck.
     */
    public Card peekTop() {
        if(this.cards.size() == 0) {
            return null;
        } else {
            return cards.get(cards.size()-1);
        }
    }

    /**
     * Returns the top card while removing it from the deck.
     * @return The top card of the deck.
     */
    public Card deal() {
        Card currentCard = cards.get(0);
        cards.remove(currentCard);
        return currentCard;
    }

    /**
     * Returns all the cards in the deck.
     * @return ALl the cards in the deck.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Adds a card to the deck.
     * @param card The card being added to the deck.
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Adds multiple cards to the deck.
     * @param cards An arrayList of cards to be added to the deck.
     */
    public void addCards(ArrayList<Card> cards) {
        for(Card card: cards) {
            this.cards.add(card);
        }
    }

    /**
     * Removes all the cards from the deck.
     */
    public void empty() {
        this.cards = new ArrayList<>();
    }

    /**
     * Returns true if there are no cards in the deck, else false.
     * @return If the deck is empty.
     */
    public boolean isEmpty() {
        return this.cards.size() == 0;
    }

    /**
     * Returns the number of cards in the deck.
     * @return The number of cards in the deck.
     */
    public int getDeckSize() {
        return cards.size();
    }

    /**
     * Returns A String representation of the deck.
     * @return A String representation of the deck.
     */
    @Override
    public String toString() {
        String msg = "";
        for (Card card: cards) {
            msg += card + "\n";
        }
        return msg;
    }

}
