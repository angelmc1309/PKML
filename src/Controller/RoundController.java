package Controller;

import Model.Board;
import Model.Player;


import java.util.ArrayList;
import java.util.Arrays;

public class RoundController {
    private final float BBsize = 1,SBsize = BBsize/2;

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
    private boolean gameStarted;

    private int handCount = 0;


    public RoundController (Board board, ArrayList<Player> players){
        this.board = board;
        this.players = players;
        this.BB = 4;
        this.SB = 3;
        this.D = 2;
    }

    public void startRound(){
        if(gameStarted) {
            //System.out.println(board);
        }
        handCount++;
        board.startRound();
        for(Player player : players){
            if(player.getChips() < 100){
                player.substractToBankRoll(100 - player.getChips());
                player.setChips(100);
            }
        }

        D  = (D +1) % 6;
        while(players.get(D).isFolded()){
            D = (D + 1) % 6;
        }
        SB = (D+1) % 6;
        while(players.get(SB).isFolded()){
            SB = (SB + 1) % 6;
        }
        BB = (SB + 1) % 6;
        while(players.get(BB).isFolded()){
            BB = (BB + 1) % 6;
        }



        restartBets();
        for(Player player :players){
            player.setTotalAmountBet(0);
            player.setPreflopHistory("");
            player.setFlopHistory("");
            player.setTurnHistory("");
            player.setRiverHistory("");
        }
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
        //System.out.println("Player : "+ players.get(playerTurn).getName() +" turn");
        gameStarted = true;
    }

    public void call() throws Exception{
        if(amountBet - players.get(playerTurn).getAmountBetThisRound() > players.get(playerTurn).getChips()){
            throw new Exception("Not enough chips to CALL");
        }
        if(stage == 0){
            players.get(playerTurn).setPreflopHistory("C");

        }
        else if(stage == 1){
            if(players.get(playerTurn).getFlopHistory().equals("CK")) {
                players.get(playerTurn).setFlopHistory("CK-C");
            }else{
                players.get(playerTurn).setFlopHistory("C");
            }
        }
        else if(stage == 2){
            if(players.get(playerTurn).getTurnHistory().equals("CK")) {
                players.get(playerTurn).setTurnHistory("CK-C");
            }else{
                players.get(playerTurn).setTurnHistory("C");
            }
        }
        else if(stage == 3){
            if(players.get(playerTurn).getRiverHistory().equals("CK")) {
                players.get(playerTurn).setRiverHistory("CK-C");
            }else{
                players.get(playerTurn).setRiverHistory("C");
            }
        }

        players.get(playerTurn).bet(board,amountBet-players.get(playerTurn).getAmountBetThisRound());
        if(players.get(playerTurn).getChips() == 0){
            players.get(playerTurn).allIn(board);
        }
        nextPlayer();
    }
    public void raise(float amount)throws Exception{
        if(amount > players.get(playerTurn).getChips()){
            throw new Exception("Not enough chips to RAISE");
        }
        if(amount + players.get(playerTurn).getAmountBetThisRound() == amountBet ){
            this.call();
            //System.out.println("CALLing a raise");
            return;
        }
        if(amount + players.get(playerTurn).getAmountBetThisRound() + BBsize< amountBet ){
            throw new Exception("This RAISE is too small");

        }
        double potProportion = amount / board.potSize();
        if(stage == 0){
            if(amountBet == BBsize){
                players.get(playerTurn).setPreflopHistory("OPEN");
            }
            if(amountBet >= 2*BBsize){
                players.get(playerTurn).setPreflopHistory("3B");
            }
        }
        else if(stage == 1){
            if(players.get(playerTurn).getFlopHistory().equals("CK")) {
                players.get(playerTurn).setFlopHistory("CK-R "+ potProportion);
            }else{
                players.get(playerTurn).setFlopHistory("R "+ potProportion);
            }
        }
        else if(stage == 2){
            if(players.get(playerTurn).getTurnHistory().equals("CK")) {
                players.get(playerTurn).setTurnHistory("CK-R "+ potProportion);
            }else{
                players.get(playerTurn).setTurnHistory("R "+ potProportion);
            }
        }
        else if(stage == 3){
            if(players.get(playerTurn).getRiverHistory().equals("CK")) {
                players.get(playerTurn).setRiverHistory("CK-R "+ potProportion);
            }else{
                players.get(playerTurn).setRiverHistory("R "+ potProportion);
            }
        }


        preflop = false;
        playerToCall = playerTurn;
        amountBet = amount + players.get(playerTurn).getAmountBetThisRound();
        players.get(playerTurn).bet(board,amount);
        players.get(playerTurn).setAmountBetThisRound(amountBet);
        nextPlayer();

    }
    public void allIn(){
        amountBet = players.get(playerTurn).getChips() + players.get(playerTurn).getAmountBetThisRound();
        double potProportion = amountBet / board.potSize();

        if(stage == 0){

            players.get(playerTurn).setPreflopHistory("A " + potProportion);

        }
        else if(stage == 1){

            players.get(playerTurn).setFlopHistory("A "+ potProportion);

        }
        else if(stage == 2){

            players.get(playerTurn).setTurnHistory("A "+ potProportion);

        }
        else if(stage == 3){

            players.get(playerTurn).setRiverHistory("A "+ potProportion);

        }

        preflop = false;
        playerToCall = playerTurn;
        if(players.get(playerTurn).getChips() + players.get(playerTurn).getAmountBetThisRound() > amountBet){
            amountBet = players.get(playerTurn).getChips() + players.get(playerTurn).getAmountBetThisRound();
        }

        players.get(playerTurn).allIn(board);
        players.get(playerTurn).setAmountBetThisRound(amountBet);
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
        if(stage == 0){
            players.get(playerTurn).setPreflopHistory("CK");
        }
        else if(stage == 1){
            players.get(playerTurn).setFlopHistory("CK");
        }
        else if(stage == 2){
            players.get(playerTurn).setTurnHistory("CK");
        }
        else if(stage == 3){
            players.get(playerTurn).setRiverHistory("CK");
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
            //System.out.println(board);
            startRound();
            return;
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
                return;
            }
            else{
                if(players.get(playerTurn).isAllIn() || players.get(playerTurn).getAmountBetThisRound() >= amountBet){
                    if(board.getCards().size() == 0){
                        board.flop();
                    }
                    if(board.getCards().size() == 3){
                        board.turn();
                    }if(board.getCards().size() == 4){
                        board.river();

                    }
                    showDown();
                    //System.out.println(board);
                    startRound();
                    return;
                }
            }

        }else if(playerTurn == playerToCall && !preflop){
            nextStage();
            return;
        }
        else if(players.get(playerToCall).isAllIn()){
            int previousPlayer = playerToCall > 0 ? (playerToCall-1)%6 : 5;
            while (players.get(previousPlayer).isAllIn() || players.get(previousPlayer).isFolded()){
                previousPlayer =  previousPlayer > 0 ? (previousPlayer-1)%6 : 5;
            }
            if(players.get(previousPlayer).getAmountBetThisRound() >= players.get(playerToCall).getAmountBetThisRound()){
                nextStage();
                return;
            }
        }
       // System.out.println("Player : "+ players.get(playerTurn).getName() +" turn");
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
            //System.out.println("FLOP");
           // System.out.println("Player : "+ players.get(playerTurn).getName() +" turn");
        }else if(stage == 1){
            stage++;
            board.turn();
            //System.out.println("TURN");
            //System.out.println("Player : "+ players.get(playerTurn).getName() +" turn");
        }else if(stage == 2){
            stage++;
            board.river();
            //System.out.println("RIVER");
            //System.out.println("Player : "+ players.get(playerTurn).getName() +" turn");
        }
        else if(stage == 3){
            //System.out.println(board);
            showDown();
            //System.out.println(board);
            startRound();
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
            for(Player player : players){
                if(!player.isAllIn() && !player.isFolded()){
                    allInPlayers.add(player);
                }
            }
            float allInSizes[] = new float[allInPlayers.size()];
            float potSizes[] = new float[allInPlayers.size()];
            float potOffset[] = new float[allInPlayers.size()];
            for(int i = 0;i<allInPlayers.size();i++){
                allInSizes[i] = allInPlayers.get(i).getAllInAmount();
                potSizes[i] = allInSizes[i];
            }
            Arrays.sort(allInSizes);
            Arrays.sort(potSizes);
            for(int i = 1;i<allInPlayers.size();i++){
                for(int j = i-1;j >= 0;j--){
                    potSizes[i] -= potSizes[j];
                }
            }
            for(Player player : players){
                if(player.isFolded()){
                    float amountBet = player.getTotalAmountBet();
                    for(int i = 0;i< allInPlayers.size();i++){
                        if( amountBet < allInSizes[i]){
                            if(i == 0){
                                potOffset[i] += amountBet;
                            }else {
                                potOffset[i] += amountBet - allInSizes[i - 1];
                            }
                            break;
                        }
                    }
                }
            }
            ArrayList<Player> potParticipants = new ArrayList<>();
            for(int i = allInPlayers.size()-1;i >= 0;i--){
                for(Player p : showndowners){
                    if(!p.isAllIn() || p.getAllInAmount() >= allInSizes[i]){
                        potParticipants.add(p);
                    }
                }
                int amountOfOverBets = 0;
                for(Player player : players){
                    if(player.getTotalAmountBet() >= allInSizes[i]){
                        amountOfOverBets++;
                    }
                }
                float desiredPot = amountOfOverBets * potSizes[i] + potOffset[i];

                potParticipants = ShowDownDecider.getRoundWinners(potParticipants,board);
                for(Player player : potParticipants){
                    player.giveAmount(desiredPot/potParticipants.size());
                    board.addToPot(-desiredPot/potParticipants.size());
                }
                potParticipants.clear();
            }
        }
        else {
            showndowners = ShowDownDecider.getRoundWinners(showndowners, board);
            for(Player player : showndowners){
                player.giveAmount(board.potSize()/showndowners.size());
            }
        }
    }
    private void giveAllPot(int playerTurn) {
        players.get(playerTurn).giveAmount(board.potSize());
    }
    private void restartBets(){
        for(Player p : players){
            p.setAmountBetThisRound(0);

        }
    }


    public void setBB(int index) {
        this.BB = index;
    }

    public void setSB(int index) {
        this.SB = index;
    }

    public void setD(int index) {
        this.D = index;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public boolean canCheckPlayer(int playerTurn) {
        return !(amountBet > players.get(playerTurn).getAmountBetThisRound());
    }

    public int getHandCount() {
        return handCount;
    }
    public void resetHandCount(){
        handCount = 0;
    }

    public double getMinRaise() {
        if(amountBet == 0 || amountBet == BBsize){
            return BBsize;
        }

        return amountBet * 2;

    }
}
