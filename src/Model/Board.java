package Model;

import java.util.ArrayList;

public class Board {
    private float pot;
    private ArrayList<Card> cards;
    private Deck deck;
    private ArrayList<Player> players;

    private boolean boardCardsChanged = false;

    public Board(ArrayList<Player> players){
        pot = 0.0f;
        this.players = players;
        cards = new ArrayList<>();
        this.deck = new Deck();
    }
    public void startRound(){
        pot = 0.0f;
        deck.shuffle();
        cards.clear();
        for(Player player :players){
            player.deal(deck);
        }
        boardCardsChanged = true;

    }
    public void flop(){
        for(Player player :players){
            player.setAmountBetThisRound(0);
        }
        cards.add(deck.pick());
        cards.add(deck.pick());
        cards.add(deck.pick());

        boardCardsChanged = true;
    }
    public void turn(){
        cards.add(deck.pick());

        for(Player player :players){
            player.setAmountBetThisRound(0);
        }
        boardCardsChanged = true;
    }
    public void river(){

        for(Player player :players){
            player.setAmountBetThisRound(0);
        }
        cards.add(deck.pick());

        boardCardsChanged = true;
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
        returnVal += "\nPLAYER CARDS: \n";
        for(Player p : players){
            returnVal += p + "\n";
        }
        returnVal += "\n POT SIZE: "+potSize()+"\n";
        return returnVal;
    }

    public void addToPot(float amount) {
        pot += amount;
    }

    public float potSize() {
        return pot;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public boolean boardCardsChanged() {
        if(boardCardsChanged){
            boardCardsChanged = false;
            return true;
        }
        return false;
    }
}
