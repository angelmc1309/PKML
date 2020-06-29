package Controller;

import Model.Board;
import Model.Player;


import java.util.ArrayList;
import java.util.Arrays;

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
        for(Player player : players){
            if(player.getChips() <= 0){
                player.fold();
            }
        }
        restartBets();
        board.startRound();
        players.get(BB).bet(board,BBsize);
        players.get(SB).bet(board,SBsize);
        playerTurn = (BB+1)%6;
        amountBet = BBsize;
        playerToCall = BB;
        preflop = true;
        stage = 0;
        for(Player player : players){
            if(player.getChips() <= 0){
                player.fold();
            }
        }

    }

    public void call() throws Exception{
        if(amountBet - players.get(playerTurn).getAmountBetThisRound() > players.get(playerTurn).getChips()){
            throw new Exception("Not enough chips to CALL");
        }
        players.get(playerTurn).bet(board,amountBet-players.get(playerTurn).getAmountBetThisRound());
        nextPlayer();
    }
    public void raise(float amount)throws Exception{
        if(amount > players.get(playerTurn).getChips()){
            throw new Exception("Not enough chips to RAISE");
        }
        preflop = false;
        playerToCall = playerTurn;
        amountBet = amount + players.get(playerTurn).getAmountBetThisRound();
        players.get(playerTurn).bet(board,amount);
        players.get(playerTurn).setAmountBetThisRound(amountBet);
        nextPlayer();

    }
    public void allIn(){
        players.get(playerTurn).allIn(board);
        nextPlayer();
    }
    public void fold(){
        players.get(playerTurn).fold();
        nextPlayer();

    }
    public void check()throws Exception{
        if(amountBet > players.get(playerTurn).getAmountBetThisRound()){
            throw new Exception("Not allowed to CHECK");
        }
        nextPlayer();
    }

    private void nextPlayer() {
        boolean allFolded = true;
        for(int i = 0;i<players.size() && allFolded;i++){
            if(!(players.get(i).isAllIn() || players.get(i).isFolded())){
                allFolded = false;
            }
        }
        if(allFolded){
            if(board.getCards().size() == 0){
                board.flop();
            }
            if(board.getCards().size() == 3){
                board.turn();
            }if(board.getCards().size() == 4){
                board.river();

            }
            showDown();
        }
        if(preflop && playerTurn == playerToCall && playerTurn == BB){
            nextStage();
            return;
        }
        int nextToPlay;
        playerTurn = (playerTurn +1)%6;
        while(players.get(playerTurn).isFolded() || players.get(playerTurn).isAllIn()){
            playerTurn = (playerTurn +1)%6;
        }

        nextToPlay = (playerTurn +1)%6;
        while(players.get(nextToPlay).isFolded() || players.get(nextToPlay).isAllIn()){
            nextToPlay = (nextToPlay +1)%6;
        }

        if(nextToPlay == playerTurn){
            boolean isAnyAllIn = false;
            for(Player p : players){
                if(p.isAllIn()){
                    isAnyAllIn = true;
                }
            }
            if(!isAnyAllIn) {
                giveAllPot(playerTurn);
                startRound();
            }

        }else if(playerTurn == playerToCall && !preflop){
            nextStage();
        }
    }

    private void nextStage() {
        restartBets();
        preflop = false;
        playerTurn = (D+1)%6;
        while (players.get(playerTurn).isFolded() || players.get(playerTurn).isAllIn()){
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
        ArrayList<Player> showndowners = new ArrayList<>();
        for(Player player : players){
            if(!player.isFolded()){
                showndowners.add(player);
            }
        }
        ArrayList<Player> allInPlayers = new ArrayList<>();
        for(Player player : showndowners){
            if(player.isAllIn()){
                allInPlayers.add(player);
            }
        }
        if(allInPlayers.size() > 0){
            float allInSizes[] = new float[allInPlayers.size()];
            for(int i = 0;i<allInPlayers.size();i++){
                allInSizes[i] = allInPlayers.get(i).getAllInAmount();
            }
            Arrays.sort(allInSizes);
            ArrayList<Player> potParticipants = new ArrayList<>();
            for(int i = 0;i<allInPlayers.size();i++){
                for(Player p : showndowners){
                    if(!p.isAllIn() || p.getAllInAmount() >= allInSizes[i]){
                        potParticipants.add(p);
                    }
                }
                float desiredPot = potParticipants.size() * allInSizes[i];
                for(int j = 0;j<allInSizes.length;j++){
                    allInSizes[j] -= allInSizes[i];
                }
                if(desiredPot > board.potSize()){
                    desiredPot = board.potSize();
                }
                potParticipants = ShowDownDecider.getRoundWinners(showndowners,board);
                for(Player player : potParticipants){
                    player.giveAmount(desiredPot/potParticipants.size());
                    board.addToPot(-desiredPot/potParticipants.size());
                }
                potParticipants.clear();
            }
            if(board.potSize() > 0){
                for(Player p: players){
                    if(!p.isFolded() && !p.isAllIn()){
                        potParticipants.add(p);
                    }
                }
                potParticipants = ShowDownDecider.getRoundWinners(showndowners,board);
                for(Player player : potParticipants){
                    player.giveAmount(board.potSize()/potParticipants.size());
                }
            }


        }
        else {
            showndowners = ShowDownDecider.getRoundWinners(showndowners, board);
            for(Player player : showndowners){
                player.giveAmount(board.potSize()/showndowners.size());
            }
        }
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
