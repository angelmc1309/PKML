package Controller;

import Model.Board;
import Model.Card;
import Model.Player;


import java.util.ArrayList;
import java.util.Collections;

public class ShowDownDecider {
    public static String[] handNames  = {"","HIGH CARD","PAIR","TWO PAIR","THREE OF A KIND","STRAIGHT","FLUSH","FULL HOUSE"
    ,"FOUR OF A KIND","STRAIGHT FLUSH","ROYAL FLUSH"};
    public static ArrayList<Player> getRoundWinners(ArrayList<Player> players, Board board) {
        int ranks[] = new int[players.size()];
        ArrayList<ArrayList<Card>> hands = new ArrayList<>();
        for(int i = 0; i < players.size();i++){
            ArrayList<Card> cards = new ArrayList<>(players.get(i).getCards());
            cards.addAll(board.getCards());
            ranks[i] = getRank(cards);
            System.out.println("Player "+i+" has:" +handNames[ranks[i]]);
            hands.add(cards);
        }
        int max = 0;
        for(int i = 0;i<ranks.length; i++){
            if(ranks[i]>max){
                max = ranks[i];
            }
        }
        ArrayList<Player> roundWinners = new ArrayList<>();
        ArrayList<ArrayList<Card>> winnerHands = new ArrayList<>();
        for(int i = 0;i<ranks.length; i++){
            if(ranks[i] == max){
                roundWinners.add(players.get(i));
                winnerHands.add(hands.get(i));
            }
        }
        ArrayList<Player> losers = new ArrayList<>();
        for(int i = 0;i<roundWinners.size();i++){
            for(int j = 0;j<roundWinners.size();j++){
                if(compareHand(winnerHands.get(i),winnerHands.get(j)) == -1){
                    losers.add(roundWinners.get(i));
                }
            }
        }
        for(Player loser: losers){
            roundWinners.remove(loser);
        }
        return roundWinners;

    }
    private static int compareHand(ArrayList<Card> cards,ArrayList<Card> other){

        for(int i = 0;i<cards.size();i++){
            if(cards.get(i).compareTo(other.get(i)) == 1){
                return 1;
            }else if(cards.get(i).compareTo(other.get(i)) == -1){
                return -1;
            }
        }
        return 0;

    }

    private static int getRank(ArrayList<Card> cards){
        ArrayList<Card> save = (ArrayList<Card>) cards.clone();
        if(isRoyalFlush(cards)){
            return 10;
        }
        cards.addAll(save);
        if(isStraightFlush(cards)){
            return 9;
        }
        cards.addAll(save);
        if(isFourOfAKind(cards)){
            return 8;
        }
        cards.addAll(save);
        if(isFullHouse(cards)){
            return 7;
        }
        cards.addAll(save);
        if(isFlush(cards)){
            return 6;
        }
        cards.addAll(save);
        if(isStraight(cards)){
            return 5;
        }
        cards.addAll(save);
        if(isThreeOfAKind(cards)){
            return 4;
        }
        cards.addAll(save);
        if(isTwoPair(cards)){
            return 3;
        }
        cards.addAll(save);
        if(isPair(cards)){
            return 2;
        }
        cards.addAll(save);
        isHighCard(cards);
        return 1;
    }


    //Should be private, public for testing
    public static boolean isRoyalFlush(ArrayList<Card> cards){

        Card A = searchByNumber(1,cards);
        while(A != null){
            Card K = searchByNumberAndSuit(13,A.getSuit(),cards),
                    Q = searchByNumberAndSuit(12,A.getSuit(),cards),
                    J = searchByNumberAndSuit(11,A.getSuit(),cards),
                    T = searchByNumberAndSuit(10,A.getSuit(),cards);
            if(K!= null && Q != null && J != null && T != null){
                cards.clear();
                cards.add(A);
                cards.add(K);
                cards.add(Q);
                cards.add(J);
                cards.add(T);
                return true;
            }else{
                cards.remove(A);
                A = searchByNumber(1,cards);

            }
        }
        cards.clear();
        return false;
    }

    public static boolean isStraightFlush(ArrayList<Card> cards){
        Collections.sort(cards);
        Collections.reverse(cards);
        Card highest = cards.get(0);
        while(cards.size() > 4 ){
            if(highest.getNumber() == 1){
                Card second =searchByNumberAndSuit(highest.getNumber()+1,highest.getSuit(),cards)
                        ,third = searchByNumberAndSuit(highest.getNumber()+2,highest.getSuit(),cards)
                        ,fourth = searchByNumberAndSuit(highest.getNumber()+3,highest.getSuit(),cards)
                        ,fifth = searchByNumberAndSuit(highest.getNumber()+4,highest.getSuit(),cards);
                if(second != null && third != null && fourth != null && fifth != null){
                    cards.clear();
                    cards.add(highest);
                    cards.add(second);
                    cards.add(third);
                    cards.add(fourth);
                    cards.add(fifth);
                    return true;
                }
                second =searchByNumberAndSuit(13,highest.getSuit(),cards);
                third = searchByNumberAndSuit(12,highest.getSuit(),cards);
                fourth = searchByNumberAndSuit(11,highest.getSuit(),cards);
                fifth = searchByNumberAndSuit(10,highest.getSuit(),cards);
                if(second != null && third != null && fourth != null && fifth != null){
                    cards.clear();
                    cards.add(highest);
                    cards.add(second);
                    cards.add(third);
                    cards.add(fourth);
                    cards.add(fifth);
                    return true;
                }
                cards.remove(highest);
                highest = cards.get(0);

            }else {
                Card second = searchByNumberAndSuit(highest.getNumber() - 1, highest.getSuit(), cards),
                        third = searchByNumberAndSuit(highest.getNumber() - 2, highest.getSuit(), cards),
                        fourth = searchByNumberAndSuit(highest.getNumber() - 3, highest.getSuit(), cards),
                        fifth = searchByNumberAndSuit(highest.getNumber() - 4, highest.getSuit(), cards);
                if(second != null && third != null && fourth != null && fifth != null){
                    cards.clear();
                    cards.add(highest);
                    cards.add(second);
                    cards.add(third);
                    cards.add(fourth);
                    cards.add(fifth);
                    return true;
                }
                else{
                    cards.remove(highest);
                    highest = cards.get(0);
                }
            }
        }
        cards.clear();
        return false;

    }

    public static boolean isFourOfAKind(ArrayList<Card> cards){

        for(Card card : cards){
            if(countAppareances(card.getNumber(),cards) >= 4){
                Card card1 = card;
                cards.remove(card1);
                Card card2 = searchByNumber(card1.getNumber(),cards);
                cards.remove(card2);
                Card card3 = searchByNumber(card1.getNumber(),cards);
                cards.remove(card3);
                Card card4 = searchByNumber(card1.getNumber(),cards);
                cards.remove(card4);
                Collections.sort(cards);
                Card highCard = cards.get(cards.size()-1);
                cards.clear();
                cards.add(card1);
                cards.add(card2);
                cards.add(card3);
                cards.add(card4);
                cards.add(highCard);
                return true;

            }
        }
        cards.clear();
        return false;

    }

    public static boolean isFullHouse(ArrayList<Card> cards){
        ArrayList<Card> trios = new ArrayList<Card>();
        for(Card c: cards){
            if(countAppareances(c.getNumber(),cards) >= 3){
                trios.add(c);
            }
        }
        if(trios.size() == 6){
            Collections.sort(trios);
            cards.clear();
            cards.add(trios.get(5));
            cards.add(trios.get(4));
            cards.add(trios.get(3));
            cards.add(trios.get(2));
            cards.add(trios.get(1));
            return true;
        }
        if(trios.size() == 3){
            cards.remove(trios.get(0));
            cards.remove(trios.get(1));
            cards.remove(trios.get(1));
            ArrayList<Card> duos = new ArrayList<>();
            for(Card c : cards){
                if(countAppareances(c.getNumber(),cards) >= 2){
                    duos.add(c);
                }
            }
            if(duos.size() != 0){
                Collections.sort(duos);
                cards.clear();
                cards.add(trios.get(0));
                cards.add(trios.get(1));
                cards.add(trios.get(2));
                cards.add(duos.get(duos.size()-1));
                cards.add(duos.get(duos.size()-2));
                return true;
            }

        }
        cards.clear();
        return false;

    }

    public static boolean isFlush(ArrayList<Card> cards){
        ArrayList <Card> flush = new ArrayList<>();
        for(Card c: cards){
            if(countAppareancesSuit(c.getSuit(),cards) >=5){
                flush.add(c);
            }
        }
        if(!flush.isEmpty()){
            Collections.sort(flush);
            cards.clear();
            cards.add(flush.get(flush.size()-1));
            cards.add(flush.get(flush.size()-2));
            cards.add(flush.get(flush.size()-3));
            cards.add(flush.get(flush.size()-4));
            cards.add(flush.get(flush.size()-5));
            return true;
        }
        cards.clear();
        return false;

    }

    public static boolean isStraight(ArrayList<Card> cards){
        Collections.sort(cards);
        Collections.reverse(cards);
        Card highest = cards.get(0);
        while(cards.size() > 4 ){
            if(highest.getNumber() == 1){
                Card second =searchByNumber(highest.getNumber()+1,cards)
                        ,third = searchByNumber(highest.getNumber()+2,cards)
                        ,fourth = searchByNumber(highest.getNumber()+3,cards)
                        ,fifth = searchByNumber(highest.getNumber()+4,cards);
                if(second != null && third != null && fourth != null && fifth != null){
                    cards.clear();
                    cards.add(highest);
                    cards.add(second);
                    cards.add(third);
                    cards.add(fourth);
                    cards.add(fifth);
                    return true;
                }
                second =searchByNumber(13,cards);
                third = searchByNumber(12,cards);
                fourth = searchByNumber(11,cards);
                fifth = searchByNumber(10,cards);
                if(second != null && third != null && fourth != null && fifth != null){
                    cards.clear();
                    cards.add(highest);
                    cards.add(second);
                    cards.add(third);
                    cards.add(fourth);
                    cards.add(fifth);
                    return true;
                }
                cards.remove(highest);
                highest = cards.get(0);

            }else {
                Card second = searchByNumber(highest.getNumber() - 1, cards),
                        third = searchByNumber(highest.getNumber() - 2, cards),
                        fourth = searchByNumber(highest.getNumber() - 3, cards),
                        fifth = searchByNumber(highest.getNumber() - 4, cards);
                if(second != null && third != null && fourth != null && fifth != null){
                    cards.clear();
                    cards.add(highest);
                    cards.add(second);
                    cards.add(third);
                    cards.add(fourth);
                    cards.add(fifth);
                    return true;
                }
                else{
                    cards.remove(highest);
                    highest = cards.get(0);
                }
            }
        }
        cards.clear();
        return false;

    }

    public static boolean isThreeOfAKind(ArrayList<Card> cards){
        ArrayList<Card> trios = new ArrayList<>();
        for(Card card :cards){
            if(countAppareances(card.getNumber(),cards) >= 3){
                trios.add(card);
                cards.remove(card);
                Card card1 = searchByNumber(card.getNumber(),cards);
                trios.add(card1);
                cards.remove(card1);
                Card card2 = searchByNumber(card.getNumber(),cards);
                trios.add(card2);
                cards.remove(card2);
                Collections.sort(cards);
                trios.add(cards.get(cards.size()-1));
                trios.add(cards.get(cards.size()-2));
                cards.clear();
                cards.addAll(trios);
                return true;
            }
        }
        cards.clear();
        return false;

    }

    public static boolean isTwoPair(ArrayList<Card> cards){
        ArrayList<Card> duos = new ArrayList<>();
        for(Card card :cards){
            if(countAppareances(card.getNumber(),cards) >= 2){
                duos.add(card);
            }
        }
        if(duos.size() >= 4){
            Collections.sort(duos);


            cards.remove(duos.get(duos.size()-1));
            cards.remove(duos.get(duos.size()-2));
            cards.remove(duos.get(duos.size()-3));
            cards.remove(duos.get(duos.size()-4));

            Collections.sort(cards);
            Card higher = cards.get(cards.size()-1);
            cards.clear();
            cards.add(duos.get(duos.size()-1));
            cards.add(duos.get(duos.size()-2));
            cards.add(duos.get(duos.size()-3));
            cards.add(duos.get(duos.size()-4));
            cards.add(higher);
            return true;
        }
        cards.clear();
        return false;
    }

    public static boolean isPair(ArrayList<Card> cards){
        ArrayList<Card> duos = new ArrayList<>();
        for(Card card :cards){
            if(countAppareances(card.getNumber(),cards) >= 2){
                duos.add(card);
            }
        }
        if(duos.size()>=2){

            cards.remove(duos.get(duos.size()-1));
            cards.remove(duos.get(duos.size()-2));
            Collections.sort(cards);
            Card higher1 = cards.get(cards.size()-1);
            Card higher2 = cards.get(cards.size()-2);
            Card higher3 = cards.get(cards.size()-3);
            cards.clear();
            cards.add(duos.get(duos.size()-1));
            cards.add(duos.get(duos.size()-2));
            cards.add(higher1);
            cards.add(higher2);
            cards.add(higher3);
            return true;
        }
        cards.clear();
        return false;
    }

    public static boolean isHighCard(ArrayList<Card> cards){
        Collections.sort(cards);
        cards.remove(0);
        cards.remove(0);
        Collections.reverse(cards);
        return true;
    }

    private static Card searchByNumber(int number,ArrayList<Card> cards){
        for(Card card: cards){
            if(number == card.getNumber()){
                return card;
            }
        }
        return null;
    }
    private static Card searchBySuit(int suit,ArrayList<Card> cards){
        for(Card card: cards){
            if(suit == card.getSuit()){
                return card;
            }
        }
        return null;
    }
    private static Card searchByNumberAndSuit(int number,int suit,ArrayList<Card> cards){
        for(Card card: cards){
            if(suit == card.getSuit() && number == card.getNumber()){
                return card;
            }
        }
        return null;
    }

    private static int countAppareances(int number,ArrayList<Card> cards){
        int count = 0;
        for(Card c : cards){
            if(c.getNumber() == number){
                count++;
            }
        }
        return count;

    }

    private static int countAppareancesSuit(int suit,ArrayList<Card> cards){
        int count = 0;
        for(Card c : cards){
            if(c.getSuit() == suit){
                count++;
            }
        }
        return count;
    }


}
