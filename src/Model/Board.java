package Model;

import java.util.ArrayList;

public class Board {
    private float pot;
    private ArrayList<Card> cards;
    private Deck deck;
    private ArrayList<Player> players;

    public Board(ArrayList<Player> players){
        pot = 0.0f;
        this.players = players;
        cards = new ArrayList<Card>();
        this.deck = new Deck();
    }
    public void startRound(){
        pot = 0.0f;
        deck.shuffle();
        cards.clear();
        for(Player player :players){
            player.deal(deck);
        }
    }
    public void flop(){
        for(Player player :players){
            player.setAmountBetThisRound(0);
        }
        cards.add(deck.pick());
        cards.add(deck.pick());
        cards.add(deck.pick());
    }
    public void turn(){
        cards.add(deck.pick());

        for(Player player :players){
            player.setAmountBetThisRound(0);
        }
    }
    public void river(){

        for(Player player :players){
            player.setAmountBetThisRound(0);
        }
        cards.add(deck.pick());
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }


    @Override
    public String toString() {
        String returnVal = "BOARD CARDS: \n";
        for(Card c : cards){
            returnVal += c + "   ";
        }
        returnVal += "\n PLAYER CARDS: \n";
        for(Player p : players){
            returnVal += p + "\n";
        }
        return returnVal;
    }

    public void addToPot(float amount) {
        pot += amount;
    }

    public float potSize() {
        return pot;
    }
}
