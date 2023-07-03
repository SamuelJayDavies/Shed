package shed;

/**
 * The hand type for a particular hand. Can either be Regular/Constrained/Hidden.
 */
public enum HandType {

    /**
     * The first hand that cards are taken from. All cards need to be removed from this hand before cards from a
     * constrained hand can be used.
     */
    Regular,

    /**
     * The second hand that cards are taken from. ALl cards need to be removed from this han before cards from a hidden
     * hand can be played.
     */
    Constrained,

    /**
     * The final hand that cards are taken from. All cards need to be removed from this hand for the player to win. Also
     * cards can't be seen in this hand until they are played.
     */
    Hidden,

}
