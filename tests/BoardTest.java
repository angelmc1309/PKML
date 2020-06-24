

import Model.Board;
import Model.Player;
import junit.framework.TestCase;

import java.util.ArrayList;

public class BoardTest extends TestCase {
    ArrayList<Player> p = new ArrayList<>();


    public void testBoard(){
        for(int i = 0;i<6;i++){
            p.add(new Player(3000,"1"));
        }
        Board b = new Board(p);
        b.startRound();
        System.out.println(b);

        b.flop();
        System.out.println(b);

        b.turn();
        System.out.println(b);

        b.river();
        System.out.println(b);

        b.startRound();
        System.out.println(b);


    }

    public void testBoardPlayers(){
        for(int i = 0;i<6;i++){
            p.add(new Player(3000,"1"));
        }
        Board b = new Board(p);
        b.startRound();
        for(Player p: b.getPlayers()){
            System.out.println(p);
        }
    }
}
