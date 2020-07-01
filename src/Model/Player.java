package Model;

import java.util.ArrayList;

public class Player {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private float chips;
    private ArrayList<Card> cards;
    private String name;
    private boolean isFolded;
    private float amountBetThisRound;
    private float totalAmountBet;
    private float allInAmount;

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
}
