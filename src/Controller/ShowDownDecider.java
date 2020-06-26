package Controller;

import Model.Board;
import Model.Card;
import Model.Player;

import java.util.ArrayList;
import java.util.Collections;

public class ShowDownDecider {

    public static ArrayList<Player> getRoundWinners(ArrayList<Player> auxiliar, Board board) {
        int ranks[];
        ranks = getRanks(auxiliar,board);
        return auxiliar;

    }

    private static int[] getRanks(ArrayList<Player> auxiliar, Board board) {
        int ranks[] = new int[auxiliar.size()];
        for(Player player : auxiliar){
            ArrayList<Card> cards = player.getCards();
            cards.addAll(board.getCards());


            /*Check if royal flush*/
            //TODO


        }
        return ranks;
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
        while(cards.size() > 4 || highest.getNumber() < 6){
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
