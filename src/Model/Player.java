package Model;

import java.util.ArrayList;

public class Player {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private float chips;
    private ArrayList<Card> cards;
    private String name;
    private boolean isFolded;
    private float amountBetThisRound;
    private float totalAmountBet;
    private float allInAmount;

    private String preflopHistory = "";
    private String flopHistory = "";
    private String turnHistory = "";
    private String riverHistory = "";

    private float bankRoll = 0;

    private boolean ownCardsChanged = false;

    public Player(float chips,String name){
        this.chips = chips;
        this.name = name;
        cards = new ArrayList<>();
        allInAmount = 0;
    }

    public void deal(Deck deck){
        cards.clear();
        cards.add(deck.pick());
        cards.add(deck.pick());
        ownCardsChanged = true;
        isFolded = false;
        amountBetThisRound = 0;
        allInAmount = 0;
        totalAmountBet = 0;

    }
    public String getName(){
        return name;
    }
    public void bet(Board board,float amount){
        board.addToPot(amount);
        this.chips -= amount;
        amountBetThisRound += amount;
        totalAmountBet += amount;
    }
    public void allIn(Board board){
        allInAmount = chips+amountBetThisRound;
        this.bet(board,chips);
    }
    public float getAllInAmount(){
        return this.allInAmount;
    }
    public void fold(){
        isFolded = true;
    }
    public boolean isFolded(){
        return isFolded;
    }
    public float getAmountBetThisRound() {
        return amountBetThisRound;
    }

    public void setAmountBetThisRound(float amountBetThisRound) {
        this.amountBetThisRound = amountBetThisRound;
    }

    @Override
    public String toString() {
        if(cards.size() == 0){
            return ANSI_RED+" "+name+": "+chips + "   " +ANSI_RESET;
        }
        if(isAllIn()){
            return ANSI_RED+" "+name+": "+chips + "   " + cards.get(0) + "   "+ cards.get(1)+"   | BET: "
                    +amountBetThisRound+ANSI_RESET;
        }else if(isFolded()) {
            return ANSI_WHITE + " " + name + ": " + chips + "   " + cards.get(0) + "   " + cards.get(1) +"   | BET: "
                    +amountBetThisRound+ANSI_RESET;
        }
        return ANSI_GREEN+" "+name+": "+chips + "   " + cards.get(0) + "   "+ cards.get(1)+"   | BET: "
                +amountBetThisRound+ANSI_RESET;
    }

    public void giveAmount(float potSize) {
        this.chips += potSize;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public float getChips() {
        return chips;
    }

    public boolean isAllIn() {
        return allInAmount > 0;
    }

    public float getTotalAmountBet() {
        return totalAmountBet;
    }

    public void setTotalAmountBet(float i) {
        amountBetThisRound = i;
    }

    public float getBankRoll(){
        return bankRoll;
    }
    public void substractToBankRoll(float amount){
        this.bankRoll -= amount;
    }

    public void setChips(float f) {
        this.chips = f;
    }

    public void setPreflopHistory(String preflopHistory) {
        this.preflopHistory = preflopHistory;
    }

    public void setFlopHistory(String flopHistory) {
        this.flopHistory = flopHistory;
    }

    public void setTurnHistory(String turnHistory) {
        this.turnHistory = turnHistory;
    }

    public void setRiverHistory(String riverHistory) {
        this.riverHistory = riverHistory;
    }

    public String getPreflopHistory() {
        return preflopHistory;
    }

    public String getFlopHistory() {
        return flopHistory;
    }

    public String getTurnHistory() {
        return turnHistory;
    }

    public String getRiverHistory() {
        return riverHistory;
    }

    public boolean ownCardsChanged() {
        if(ownCardsChanged){
            ownCardsChanged = false;
            return true;
        }
        return false;
    }
}
