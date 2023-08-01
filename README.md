# Shed
Card game that can be played with 2 or more players including various different game-modes, AI and advanced setup. Basic instructions can be found below on how to play:

# How to Play Shed

Shed is a card game where the aim is to play all of your cards before the other player can. Cards are played onto the discard pile
and drawn from the draw pile. For a card to be suitable it needs to be greater than or equal to the value of the card in the discard pile.
If the player has no eligible cards, they will pick up all the cards in the discard pile.

---------------------------------------------------------------------------------------------------------------------------------------------

Each player has 3 hands of cards at their disposal:

- General Hand
- Constrained Hand
- Hidden Hand

The general hand are the cards in the players hand's. All of these cards need to be played to allow access to the constrained hand.
When the discard pile is picked up, all those cards are placed into the general hand.

The constrained hand are cards that sit on top of the hidden hand. These cards can't be played until the general hand is empty. Once all of
these cards have been played, the player now has access to the hidden hand.

The cards in the hidden hand are facing down where the values of the cards can't be seen. When the player is using cards from the hidden hand
they will have to blindly play cards onto the discard pile to see if they are eligible. However, if a card fails to be suitable it will go into
the general hand.

Once all 3 of these hands have been emptied, the player wins the game.

---------------------------------------------------------------------------------------------------------------------------------------------

There are also 3 special cards, 2s, 10s and 7s:

- A 2 can be played on any card.
- A 10 is the same as a 2 except it will also clear the discard pile and allow another card to be played.
- A 7 when played, will force the other player to play a card that is lower or equal to a 7.

Those are the regular rules of Shed but there are also a variety of game-modes that change the duration and rules of the game:

- Basic Fast Track
    Each player starts with 9 cards, 3 in each hand.
    The draw pile is only used to deal cards at the start, and once the game starts, becomes empty
    The player cannot stack cards, and can only play one card each turn (including 10's)
    Player wins when they no longer have any cards in any hands

- Basic
    Each player starts with 9 cards, 3 in each hand.
    The draw pile is drawn from by each player when they have less than 3 cards.
        The draw pile is drawn from until it is empty.
    The player cannot stack cards, and can only play one card each turn (including 10's)
    Player wins when they no longer have any cards in any hands

- Regular Fast Track
    Each player starts with 9 cards, 3 in each hand.
    The draw pile is drawn from by each player when they have less than 3 cards.
        The draw pile is drawn from until it is empty.
    The player can stack cards of the same value, stacking 4 of the same will result in an extra turn.
    10's now also allow the player to play another card afterwards.
    Player wins when they no longer have any cards in any hands.

- Regular
    Each player starts with 9 cards, 3 in each hand.
    The draw pile is only used to deal cards at the start, and once the game starts, becomes empty
    The player can stack cards of the same value, stacking 4 of the same will result in an extra turn.
    10's now also allow the player to play another card afterwards.
    Player wins when they no longer have any cards in any hands.

- Advanced Setup
    Allows for the amount of players to be changed and selecting if each player is a computer or another person.

Essentially, fast track will be a far shorter game and basic simplifies the rules for new players, by not allowing multiple cards
being played or extra turns.

---------------------------------------------------------------------------------------------------------------------------------------------

# How to Run
Install the zip and navigate to the exe folder. Once there, simply click on the GameOfShed.bat file and the game will start. The game can be played
with only the exe folder, but the help screen requires the helpTxt file.

Hope you enjoy playing, was just a quick side project I made because of how much I played this card game in my first year of university.
