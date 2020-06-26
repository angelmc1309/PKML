package Controller;

import Model.Board;
import Model.Player;

import java.util.ArrayList;

public class RoundController {
    private final float BBsize = 20,SBsize = BBsize/2;

    private Board board;
    private ArrayList<Player> players;
    private int BB,SB,D;
    private int playerTurn;
    private float amountBet;
    private int playerToCall;
    //preflop is true if BB is still able to check
    private boolean preflop;
    //0 for preflop,1 for flop,2 for river,3 for turn
    private int stage;

    public RoundController (Board board, ArrayList<Player> players){
        this.board = board;
        this.players = players;
        this.BB = 4;
        this.SB = 3;
        this.D = 2;
    }

    public void startRound(){
        BB = (BB+1) % 6;
        SB = (SB+1) % 6;
        D  = (D +1) % 6;
        restartBets();
        board.startRound();
        players.get(BB).bet(board,BBsize);
        players.get(SB).bet(board,SBsize);
        playerTurn = (BB+1)%6;
        amountBet = BBsize;
        playerToCall = BB;
        preflop = true;
        stage = 0;
    }

    public void call(){
        players.get(playerTurn).bet(board,amountBet-players.get(playerTurn).getAmountBetThisRound());
        nextPlayer();
    }
    public void raise(float amount){
        preflop = false;
        playerToCall = playerTurn;
        players.get(playerTurn).bet(board,amount);
        amountBet = amount + players.get(playerTurn).getAmountBetThisRound();
        players.get(playerTurn).setAmountBetThisRound(amountBet);
        nextPlayer();

    }
    public void fold(){
        players.get(playerTurn).fold();
        nextPlayer();

    }
    public void check(){
        nextPlayer();
    }

    private void nextPlayer() {
        if(preflop && playerTurn == playerToCall && playerTurn == BB){
            nextStage();
            return;
        }
        int nextToPlay;
        playerTurn = (playerTurn +1)%6;
        while(players.get(playerTurn).isFolded()){
            playerTurn = (playerTurn +1)%6;
        }

        nextToPlay = (playerTurn +1)%6;
        while(players.get(nextToPlay).isFolded()){
            nextToPlay = (nextToPlay +1)%6;
        }

        if(nextToPlay == playerTurn){
            giveAllPot(playerTurn);
            startRound();

        }else if(playerTurn == playerToCall && !preflop){
            nextStage();
        }
    }

    private void nextStage() {
        restartBets();
        preflop = false;
        playerTurn = (D+1)%6;
        while (players.get(playerTurn).isFolded()){
            playerTurn = (playerTurn+1)%6;
        }
        amountBet = 0;
        playerToCall = playerTurn;
        if(stage == 0){
            stage++;
            board.flop();
            System.out.println("FLOP");
        }else if(stage == 1){
            stage++;
            board.turn();
            System.out.println("TURN");
        }else if(stage == 2){
            stage++;
            board.river();
            System.out.println("RIVER");
        }
        else if(stage == 3){
            showDown();
        }
    }
    private void showDown(){
        ArrayList<Player> auxiliar = new ArrayList<>();
        for(Player player : players){
            if(!player.isFolded()){
                auxiliar.add(player);
            }
        }
        auxiliar = ShowDownDecider.getRoundWinners(auxiliar,board);
        startRound();
    }
    private void giveAllPot(int playerTurn) {
        players.get(playerTurn).giveAmount(board.potSize());
    }
    private void restartBets(){
        for(Player p : players){
            p.setAmountBetThisRound(0);
        }
    }


}
