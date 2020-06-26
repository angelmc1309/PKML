import Controller.ShowDownDecider;
import Model.Card;
import junit.framework.TestCase;

import java.util.ArrayList;

public class HandRankingTest extends TestCase {
    public void testRoyalFlush(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(1,2));
        cards.add(new Card(1,1));
        cards.add(new Card(13,1));
        cards.add(new Card(12,1));
        cards.add(new Card(11,1));
        cards.add(new Card(10,1));
        cards.add(new Card(8,3));
        ArrayList<Card> output = (ArrayList<Card>) cards.clone();
        assert (ShowDownDecider.isRoyalFlush(output));
        System.out.println(output);
        cards.clear();
        cards.add(new Card(1,2));
        cards.add(new Card(1,1));
        cards.add(new Card(13,1));
        cards.add(new Card(12,1));
        cards.add(new Card(11,1));
        cards.add(new Card(10,2));
        cards.add(new Card(8,3));
        output = (ArrayList<Card>) cards.clone();
        assert (!ShowDownDecider.isRoyalFlush(output));
        System.out.println(output);
    }

    public void testStraightFlush(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(1,2));
        cards.add(new Card(1,1));
        cards.add(new Card(13,1));
        cards.add(new Card(12,1));
        cards.add(new Card(11,1));
        cards.add(new Card(10,1));
        cards.add(new Card(8,3));
        ArrayList<Card> output = (ArrayList<Card>) cards.clone();
        assert (ShowDownDecider.isStraightFlush(output));
        System.out.println(output);
        cards.clear();
        cards.add(new Card(1,2));
        cards.add(new Card(1,1));
        cards.add(new Card(3,1));
        cards.add(new Card(2,1));
        cards.add(new Card(4,1));
        cards.add(new Card(5,1));
        cards.add(new Card(8,3));
        output = (ArrayList<Card>) cards.clone();
        assert (ShowDownDecider.isStraightFlush(output));
        System.out.println(output);
        cards.clear();
        cards.add(new Card(1,2));
        cards.add(new Card(1,1));
        cards.add(new Card(5,3));
        cards.add(new Card(6,3));
        cards.add(new Card(7,3));
        cards.add(new Card(8,3));
        cards.add(new Card(9,3));
        output = (ArrayList<Card>) cards.clone();
        assert (ShowDownDecider.isStraightFlush(output));
        System.out.println(output);

        cards.clear();
        cards.add(new Card(1,2));
        cards.add(new Card(1,1));
        cards.add(new Card(5,3));
        cards.add(new Card(6,1));
        cards.add(new Card(7,3));
        cards.add(new Card(8,3));
        cards.add(new Card(9,3));
        output = (ArrayList<Card>) cards.clone();
        assert (!ShowDownDecider.isStraightFlush(output));
        System.out.println(output);

    }

    public void testFourOfAKind(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(1,2));
        cards.add(new Card(1,1));
        cards.add(new Card(13,1));
        cards.add(new Card(12,1));
        cards.add(new Card(11,1));
        cards.add(new Card(10,1));
        cards.add(new Card(8,3));
        ArrayList<Card> output = (ArrayList<Card>) cards.clone();
        assert (!ShowDownDecider.isFourOfAKind(output));
        System.out.println(output);

        cards.clear();
        cards.add(new Card(1,2));
        cards.add(new Card(1,1));
        cards.add(new Card(1,3));
        cards.add(new Card(6,1));
        cards.add(new Card(7,3));
        cards.add(new Card(1,4));
        cards.add(new Card(11,3));
        output = (ArrayList<Card>) cards.clone();
        assert (ShowDownDecider.isFourOfAKind(output));
        System.out.println(output);

        cards.clear();
        cards.add(new Card(2,2));
        cards.add(new Card(2,1));
        cards.add(new Card(2,3));
        cards.add(new Card(6,1));
        cards.add(new Card(6,2));
        cards.add(new Card(6,4));
        cards.add(new Card(6,3));
        output = (ArrayList<Card>) cards.clone();
        assert (ShowDownDecider.isFourOfAKind(output));
        System.out.println(output);

    }

    public void testFullHouse(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(1,2));
        cards.add(new Card(1,1));
        cards.add(new Card(13,1));
        cards.add(new Card(12,1));
        cards.add(new Card(11,1));
        cards.add(new Card(10,1));
        cards.add(new Card(8,3));
        ArrayList<Card> output = (ArrayList<Card>) cards.clone();
        assert (!ShowDownDecider.isFullHouse(output));
        System.out.println(output);

        cards.clear();
        cards.add(new Card(2,2));
        cards.add(new Card(2,1));
        cards.add(new Card(2,3));
        cards.add(new Card(6,1));
        cards.add(new Card(6,2));
        cards.add(new Card(6,4));
        cards.add(new Card(7,3));
        output = (ArrayList<Card>) cards.clone();
        assert (ShowDownDecider.isFullHouse(output));
        System.out.println(output);

        cards.clear();
        cards.add(new Card(2,2));
        cards.add(new Card(2,1));
        cards.add(new Card(7,3));
        cards.add(new Card(6,1));
        cards.add(new Card(6,2));
        cards.add(new Card(6,4));
        cards.add(new Card(7,3));
        output = (ArrayList<Card>) cards.clone();
        assert (ShowDownDecider.isFullHouse(output));
        System.out.println(output);

        cards.clear();
        cards.add(new Card(2,2));
        cards.add(new Card(2,1));
        cards.add(new Card(7,3));
        cards.add(new Card(1,1));
        cards.add(new Card(1,2));
        cards.add(new Card(1,4));
        cards.add(new Card(7,3));
        output = (ArrayList<Card>) cards.clone();
        assert (ShowDownDecider.isFullHouse(output));
        System.out.println(output);

    }

    public void testFlush(){
        
    }
}
