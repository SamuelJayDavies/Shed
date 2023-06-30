package higherOrLower;

public class HigherOrLowerGame {

    private Deck drawDeck;

    private int currentScore;

    public HigherOrLowerGame() {
        currentScore = 0;
        startGame();
    }

    private void startGame() {
        drawDeck = new Deck(DeckType.DRAW);
        drawDeck.shuffle();
    }

    public Card dealFromDeck() {
        return drawDeck.deal();
    }

    public boolean isCorrect(boolean isHigher, Card lastCard, Card newCard) {
        if(isHigher) {
            return newCard.getValue() >= lastCard.getValue();
        } else
            return newCard.getValue() <= lastCard.getValue();
    }

    public void incrementScore() {
        this.currentScore++;
    }

    public int getCardsPlayed() {
        return Deck.DECK_SIZE - drawDeck.getDeckSize() -1;
    }

    public int getCurrentScore() {
        return this.currentScore;
    }

    public String getCurrentCardMsg(Card lastCard) {
        return "Is the next card higher or lower than " + lastCard.toString();
    }
}
