package Model;

import java.util.ArrayList;

public class Player {
    private float chips;
    private ArrayList<Card> cards;
    private String name;
    private boolean isFolded;
    private float amountBetThisRound;

    public Player(float chips,String name){
        this.chips = chips;
        this.name = name;
        cards = new ArrayList<>();
    }

    public void deal(Deck deck){
        cards.clear();
        cards.add(deck.pick());
        cards.add(deck.pick());
        isFolded = false;
        amountBetThisRound = 0;
    }
    public String getName(){
        return name;
    }
    public void bet(Board board,float amount){
        board.addToPot(amount);
        this.chips -= amount;
        amountBetThisRound += amount;
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
}
