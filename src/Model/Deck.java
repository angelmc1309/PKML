package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

public class Deck {
    private ArrayList<Card> deck;
    private int randomIndex;
    public Deck(){

        randomIndex = 0;

        this.deck = new ArrayList<Card>(52);
        for(int i = 1;i<=13;i++){
            for(int j=1;j<=4;j++){
                deck.add(new Card(i,j));
            }
        }
        Collections.shuffle(deck,new Random(currentTimeMillis()));
    }

    public Card pick(){
        if(randomIndex == 51){

            Collections.shuffle(deck,new Random(currentTimeMillis()));
            randomIndex = 0;
        }
        randomIndex++;
        return deck.get(randomIndex-1);

    }

    public void shuffle(){
        Collections.shuffle(deck,new Random(currentTimeMillis()));
        randomIndex = 0;
    }

}
