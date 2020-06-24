package Controller;

import Model.Board;
import Model.Player;

import java.util.ArrayList;


public class Controller {
    public final int numPlayers = 6;
    private Board board;
    private ArrayList<Player> players;
    private RoundController roundController;
    public Controller(){
        players = new ArrayList<Player>();
        for(int i=0;i<numPlayers;i++){
            players.add(new Player(3000,"Player "+i));
        }
        board = new Board(players);
        roundController = new RoundController(board,players);

    }

    public void startGame(){
        


    }
}
