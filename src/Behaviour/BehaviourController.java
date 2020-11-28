package Behaviour;

import Controller.Controller;
import Model.Card;
import Model.Player;

import java.util.ArrayList;

public class BehaviourController {
    private Controller controller;
    private PlayerBehaviour[] players;
    private int maxHands = 100;

    public BehaviourController() throws Exception{
        players = new PlayerBehaviour[6];

        controller = new Controller();
        controller.startGame();
        for(int i = 0; i < 6 ; i++){
            players[i] = new PlayerBehaviour();
            players[i].updateActionInputs(getActionInput(i));
            players[i].updateDataInputs(getDataInput(i));
            players[i].updateOtherCardInputs(getOtherCardInput());
            players[i].updateOwnCardInputs(getOwnCardInput(i));
        }
        //startGeneration();
    }

    public void nonStopMode() {
        while(controller.getHandCount() < maxHands){
            decideMovement();
        }
        controller.resetHandCount();

    }
    public void setMaxHands(int h){
        this.maxHands = h;;
    }

    public void decideMovement() {
        int playerTurn = controller.getPlayerTurn();

        try {
            players[playerTurn].updateActionInputs(getActionInput(playerTurn));
            players[playerTurn].updateDataInputs(getDataInput(playerTurn));
            if(controller.boardCardsChanged()) {
                players[playerTurn].updateOtherCardInputs(getOtherCardInput());
            }
            if(controller.ownCardsChanged(playerTurn)) {
                players[playerTurn].updateOwnCardInputs(getOwnCardInput(playerTurn));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        int action = players[playerTurn].getAction();
        System.out.println(controller.getPlayerName(playerTurn)+" is about to move");
        try {

            switch (action) {
                case 0: {
                    if (controller.canCkeckPlayer(playerTurn)) {
                        controller.check();
                        System.out.println("CHECK");
                    } else {
                        controller.fold();
                        System.out.println("FOLD");
                    }
                    break;
                }
                case 1: {
                    if (controller.canCkeckPlayer(playerTurn)) {
                        controller.check();
                        System.out.println("CHECK");
                    } else {
                        try {
                            controller.call();
                        } catch (Exception e) {
                            controller.allIn();
                        }

                        System.out.println("CALL");
                    }
                    break;
                }
                case 2: {
                    double amount = controller.getMinRaise() +
                            controller.getPlayers().get(playerTurn).getChips() * players[playerTurn].getRaisePercent();
                    amount = (Math.round(amount * 2) / 2.0);
                    try {
                        controller.raise((float) amount);
                    } catch (Exception e) {
                        controller.allIn();
                    }
                    System.out.println("RAISE");
                    System.out.println(players[playerTurn].getRaisePercent());
                    break;
                }
            }


        } catch (Exception e) {
        }
        System.out.println(controller.getBoard());
    }

    public double[] getDataInput(int playerIndex){
        double [] returnVal = new double[Network.LAYER_DATA_SIZE];

        ArrayList<Player> players = controller.getPlayers();

        //DATA OF PLAYER CHIPS --->
        returnVal[0] = (players.get(playerIndex).getChips()) / controller.INITIAL_CHIPS;
        returnVal[1] = (players.get((playerIndex+1)%6).getChips()) / controller.INITIAL_CHIPS;
        returnVal[2] = (players.get((playerIndex+2)%6).getChips()) / controller.INITIAL_CHIPS;
        returnVal[3] = (players.get((playerIndex+3)%6).getChips()) / controller.INITIAL_CHIPS;
        returnVal[4] = (players.get((playerIndex+4)%6).getChips()) / controller.INITIAL_CHIPS;
        returnVal[5] = (players.get((playerIndex+5)%6).getChips()) / controller.INITIAL_CHIPS;

        //DATA OF AMOUNT BETS --->

        returnVal[6] = (players.get(playerIndex).getAmountBetThisRound()) / controller.getBoard().potSize();
        returnVal[7] = (players.get((playerIndex+1)%6).getAmountBetThisRound()) / controller.getBoard().potSize();
        returnVal[8] = (players.get((playerIndex+2)%6).getAmountBetThisRound()) / controller.getBoard().potSize();
        returnVal[9] = (players.get((playerIndex+3)%6).getAmountBetThisRound()) / controller.getBoard().potSize();
        returnVal[10] = (players.get((playerIndex+4)%6).getAmountBetThisRound()) / controller.getBoard().potSize();
        returnVal[11] = (players.get((playerIndex+5)%6).getAmountBetThisRound()) / controller.getBoard().potSize();

        //DATA OF ALLIN STATUS --->

        returnVal[12] = players.get( playerIndex     ).isAllIn() ? 1 : 0 ;
        returnVal[13] = players.get((playerIndex+1)%6).isAllIn() ? 1 : 0 ;
        returnVal[14] = players.get((playerIndex+2)%6).isAllIn() ? 1 : 0 ;
        returnVal[15] = players.get((playerIndex+3)%6).isAllIn() ? 1 : 0 ;
        returnVal[16] = players.get((playerIndex+4)%6).isAllIn() ? 1 : 0 ;
        returnVal[17] = players.get((playerIndex+5)%6).isAllIn() ? 1 : 0 ;


        //DATA OF FOLDED STATUS --->

        returnVal[18] = players.get( playerIndex     ).isFolded() ? 1 : 0 ;
        returnVal[19] = players.get((playerIndex+1)%6).isFolded() ? 1 : 0 ;
        returnVal[20] = players.get((playerIndex+2)%6).isFolded() ? 1 : 0 ;
        returnVal[21] = players.get((playerIndex+3)%6).isFolded() ? 1 : 0 ;
        returnVal[22] = players.get((playerIndex+4)%6).isFolded() ? 1 : 0 ;
        returnVal[23] = players.get((playerIndex+5)%6).isFolded() ? 1 : 0 ;


        return returnVal;
    }

    public double[] getOwnCardInput(int index){
        double [] returnVal = new double[Network.LAYER_CARDS_OWN_SIZE];

        Player player = controller.getPlayers().get(index);

        Card first = player.getCards().get(0);
        Card second = player.getCards().get(1);

        returnVal[0] = first.getNumber() == 1  ? 1 : 0;
        returnVal[1] = first.getNumber() == 2  ? 1 : 0;
        returnVal[2] = first.getNumber() == 3  ? 1 : 0;
        returnVal[3] = first.getNumber() == 4  ? 1 : 0;
        returnVal[4] = first.getNumber() == 5  ? 1 : 0;
        returnVal[5] = first.getNumber() == 6  ? 1 : 0;
        returnVal[6] = first.getNumber() == 7  ? 1 : 0;
        returnVal[7] = first.getNumber() == 8  ? 1 : 0;
        returnVal[8] = first.getNumber() == 9  ? 1 : 0;
        returnVal[9] = first.getNumber() == 10  ? 1 : 0;
        returnVal[10] = first.getNumber() == 11  ? 1 : 0;
        returnVal[11] = first.getNumber() == 12  ? 1 : 0;
        returnVal[12] = first.getNumber() == 13  ? 1 : 0;

        returnVal[13] = first.getSuit() == 1  ? 1 : 0;
        returnVal[14] = first.getSuit() == 2  ? 1 : 0;
        returnVal[15] = first.getSuit() == 3  ? 1 : 0;
        returnVal[16] = first.getSuit() == 4  ? 1 : 0;

        returnVal[17] = second.getNumber() == 1  ? 1 : 0;
        returnVal[18] = second.getNumber() == 2  ? 1 : 0;
        returnVal[19] = second.getNumber() == 3  ? 1 : 0;
        returnVal[20] = second.getNumber() == 4  ? 1 : 0;
        returnVal[21] = second.getNumber() == 5  ? 1 : 0;
        returnVal[22] = second.getNumber() == 6  ? 1 : 0;
        returnVal[23] = second.getNumber() == 7  ? 1 : 0;
        returnVal[24] = second.getNumber() == 8  ? 1 : 0;
        returnVal[25] = second.getNumber() == 9  ? 1 : 0;
        returnVal[26] = second.getNumber() == 10  ? 1 : 0;
        returnVal[27] = second.getNumber() == 11  ? 1 : 0;
        returnVal[28] = second.getNumber() == 12  ? 1 : 0;
        returnVal[29] = second.getNumber() == 13  ? 1 : 0;

        returnVal[30] = second.getSuit() == 1  ? 1 : 0;
        returnVal[31] = second.getSuit() == 2  ? 1 : 0;
        returnVal[32] = second.getSuit() == 3  ? 1 : 0;
        returnVal[33] = second.getSuit() == 4  ? 1 : 0;

        return returnVal;
    }

    public double[] getOtherCardInput(){
        double[] returnVal = new double[Network.LAYER_CARDS_OTHER_SIZE];

        ArrayList<Card> cards = controller.getBoard().getCards();
        int i = 0;
        for (Card first: cards){
            returnVal[0 + i] = first.getNumber() == 1  ? 1 : 0;
            returnVal[1 + i] = first.getNumber() == 2  ? 1 : 0;
            returnVal[2 + i] = first.getNumber() == 3  ? 1 : 0;
            returnVal[3 + i] = first.getNumber() == 4  ? 1 : 0;
            returnVal[4 + i] = first.getNumber() == 5  ? 1 : 0;
            returnVal[5 + i] = first.getNumber() == 6  ? 1 : 0;
            returnVal[6 + i] = first.getNumber() == 7  ? 1 : 0;
            returnVal[7 + i] = first.getNumber() == 8  ? 1 : 0;
            returnVal[8 + i] = first.getNumber() == 9  ? 1 : 0;
            returnVal[9 + i] = first.getNumber() == 10  ? 1 : 0;
            returnVal[10 + i] = first.getNumber() == 11  ? 1 : 0;
            returnVal[11 + i] = first.getNumber() == 12  ? 1 : 0;
            returnVal[12 + i] = first.getNumber() == 13  ? 1 : 0;

            returnVal[13 + i] = first.getSuit() == 1  ? 1 : 0;
            returnVal[14 + i] = first.getSuit() == 2  ? 1 : 0;
            returnVal[15 + i] = first.getSuit() == 3  ? 1 : 0;
            returnVal[16 + i] = first.getSuit() == 4  ? 1 : 0;
            i += 17;
        }

        return returnVal;
    }

    public double[] getActionInput(int index){
        double[] returnVal = new double[Network.LAYER_ACTION_SIZE];

        ArrayList<Player> players = controller.getPlayers();

        for(int i = (index + 1) % 6; i != index ; i = (i + 1)%6){
            returnVal[0 + i*21] = players.get(i).getPreflopHistory().equals("OPEN") ? 1 : 0;
            returnVal[1 + i*21] = players.get(i).getPreflopHistory().equals("3B") ? 1 : 0;
            returnVal[2 + i*21] = players.get(i).getPreflopHistory().equals("C") ? 1 : 0;

            returnVal[3 + i*21] = players.get(i).getFlopHistory().equals("CK") ? 1 : 0;
            returnVal[4 + i*21] = players.get(i).getFlopHistory().equals("CK-C") ? 1 : 0;
            returnVal[5 + i*21] = players.get(i).getFlopHistory().equals("C") ? 1 : 0;

            if(players.get(i).getFlopHistory().contains("CK-R")){
                returnVal[6 + i*21] = 1;
                returnVal[8 + i*21] = Double.parseDouble(players.get(i).getFlopHistory().substring(4));
            }
            else if(players.get(i).getFlopHistory().contains("R")){
                returnVal[7 + i*21] = 1;
                returnVal[8 + i*21] = Double.parseDouble(players.get(i).getFlopHistory().substring(1));
            }

            returnVal[9 + i*21] = players.get(i).getTurnHistory().equals("CK") ? 1 : 0;
            returnVal[10 + i*21] = players.get(i).getTurnHistory().equals("CK-C") ? 1 : 0;
            returnVal[11 + i*21] = players.get(i).getTurnHistory().equals("C") ? 1 : 0;

            if(players.get(i).getTurnHistory().contains("CK-R")){
                returnVal[12 + i*21] = 1;
                returnVal[14 + i*21] = Double.parseDouble(players.get(i).getTurnHistory().substring(4));
            }
            else if(players.get(i).getTurnHistory().contains("R")){
                returnVal[13 + i*21] = 1;
                returnVal[14 + i*21] = Double.parseDouble(players.get(i).getTurnHistory().substring(1));
            }

            returnVal[15 + i*21] = players.get(i).getRiverHistory().equals("CK") ? 1 : 0;
            returnVal[16 + i*21] = players.get(i).getRiverHistory().equals("CK-C") ? 1 : 0;
            returnVal[17 + i*21] = players.get(i).getRiverHistory().equals("C") ? 1 : 0;

            if(players.get(i).getRiverHistory().contains("CK-R")){
                returnVal[18 + i*21] = 1;
                returnVal[20 + i*21] = Double.parseDouble(players.get(i).getRiverHistory().substring(4));
            }
            else if(players.get(i).getRiverHistory().contains("R")){
                returnVal[19 + i*21] = 1;
                returnVal[20 + i*21] = Double.parseDouble(players.get(i).getRiverHistory().substring(1));
            }

        }

        return returnVal;
    }


}
