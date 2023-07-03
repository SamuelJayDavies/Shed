package shed;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Main class that runs a basic game for general play.
 */
public class Main {

    /**
     * Main method of the class main.
     * @param args Command line arguments that aren't relevant for this case.
     */
    public static void main(String[] args) {
        Player sam = new Player("P1", false);
        Player john = new Player("John", true);
        Shed testGame = new Shed(new ArrayList<Player>(Arrays.asList(sam, john)));
    }
}
