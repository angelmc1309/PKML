package Model;

import java.util.ArrayList;

public class Player {
    private float chips;
    private ArrayList<Card> cards;
    private String name;
    private boolean isFolded;
    private float amountBetThisRound;
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
    }
    public String getName(){
        return name;
    }
    public void bet(Board board,float amount){
        board.addToPot(amount);
        this.chips -= amount;
        amountBetThisRound += amount;
    }
    public void allIn(Board board){
        allInAmount = chips;
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
        return "Player "+name+": "+chips + "   " + cards.get(0) + "   "+ cards.get(1);
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
}
