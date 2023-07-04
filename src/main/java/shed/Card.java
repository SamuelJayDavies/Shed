package shed;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Class that models a playing card including the value and suit of the card.
 */
public class Card {

    /**
     * The integer value of the card.
     */
    private final int value;

    /**
     * The String equivalent to the int value.
     */
    private final String cardName;

    /**
     * The suit the card belongs to.
     */
    private final Suit suit;

    /**
     * A String array containing all the cardName equivalents for the integer values.
     */
    final static String[] cardNames = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven",
                                       "Eight", "Nine", "Ten", "Jack", "Queen", "King"};

    private final Image image;

    /**
     * Creates a card and assigns its cardName.
     * @param value The integer value of the card.
     * @param suit The suit the card belongs to.
     */
    public Card(int value, Suit suit) {
        this.value = value;
        this.cardName = cardNames[value-1];
        this.suit = suit;

        String fileName = this.value + "_of_" + this.suit.name().toLowerCase() + ".png";
        try {
            FileInputStream stream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\" + fileName);
            this.image = new Image(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the int value of the card.
     * @return Returns the int value of the card.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the String card name of the int value of the card.
     * @return Returns the String name for the card's value.
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * Returns the suit of the card.
     * @return The suit of the card.
     */
    public Suit getSuit() {
        return suit;
    }

    public Image getImage() {
        return this.image;
    }

    public Image getCardBack() {
        try {
            FileInputStream stream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\back_of_card.png");
            return new Image(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a String representation of the card object.
     * @return A String representation of the card object.
     */
    @Override
    public String toString() {
        return cardName + " of " + suit;
    }
}
