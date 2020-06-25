
import Controller.RoundController;
import Model.Board;
import Model.Player;
import junit.framework.TestCase;

import java.util.ArrayList;

public class RoundTest extends TestCase{
    public void testFolding(){
        ArrayList<Player> p = new ArrayList<>();
        for(int i = 0;i<6;i++){
            p.add(new Player(3000,Integer.toString(i)));
        }
        Board b = new Board(p);
        RoundController roundController = new RoundController(b,p);

        roundController.startRound();
        System.out.println(b);
        roundController.fold();
        roundController.fold();
        roundController.fold();
        roundController.fold();
        roundController.fold();
        System.out.println(b);
    }
    public void testBetingWithFold(){
        ArrayList<Player> p = new ArrayList<>();
        for(int i = 0;i<6;i++){
            p.add(new Player(3000,Integer.toString(i)));
        }
        Board b = new Board(p);
        RoundController roundController = new RoundController(b,p);

        roundController.startRound();
        System.out.println(b);
        roundController.fold();
        roundController.call();
        roundController.call();
        roundController.call();
        roundController.fold();
        roundController.raise(200);
        roundController.fold();
        roundController.fold();
        roundController.raise(400);
        roundController.fold();
        System.out.println(b);


    }

    public void testStages(){
        ArrayList<Player> p = new ArrayList<>();
        for(int i = 0;i<6;i++){
            p.add(new Player(3000,Integer.toString(i)));
        }
        Board b = new Board(p);
        RoundController roundController = new RoundController(b,p);

        roundController.startRound();
        System.out.println(b);
        roundController.fold();
        roundController.fold();
        roundController.fold();
        roundController.fold();
        roundController.call();
        roundController.check();
        System.out.println(b);
        roundController.check();
        roundController.check();
        System.out.println(b);
        roundController.check();
        roundController.check();
        System.out.println(b);
        roundController.check();
        roundController.check();
        System.out.println(b);
    }
}
