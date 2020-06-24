import Model.Deck;
import junit.framework.TestCase;

public class DeckTest extends TestCase {

    public void testShuffling(){

        Deck d = new Deck();
        for(int i = 0;i<100;i++) {
            System.out.println(d.pick());
        }
    }
}
