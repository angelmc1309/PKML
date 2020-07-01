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
            players.add(new Player(1000*i+200,"Player "+ (i+1) ));
        }
        board = new Board(players);
        roundController = new RoundController(board,players);

    }

    public void startGame(){
        roundController.startRound();
    }
    public void check()throws Exception{
        roundController.check();
    }
    public void call()throws Exception{
        roundController.call();
    }
    public void raise(float amount)throws Exception{
        roundController.raise(amount);
    }
    public void allIn(){
        roundController.allIn();
    }

    public void fold(){
        roundController.fold();
    }

    public String boardToString(){
        return board.toString();
    }

    public void setBB(int index){
        roundController.setBB(index);
        roundController.setSB(index);
        roundController.setD(index);
    }
}
