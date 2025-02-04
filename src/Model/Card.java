package Model;

public class Card implements Comparable<Card>{
    public static String suitNames[] ={"Spades  ","Clubs   ","Diamonds","Hearts  "};
    public static String cardNames[]={"A ","2 ","3 ","4 ","5 ","6 ","7 ","8 ","9 ","10","J ","Q ","K "};
    // 1 for Ace, 11 for J,12 for Q, 13 for K, equivalent number for the rest.
    private int number;
    // 1 for Spades,2 for Clubs,3 for Diamonds,4 for Hearts
    private int suit;

    public Card(int number, int suit){
            this.number = number;
            this.suit = suit;
    }




    @Override
    public String toString() {
        return cardNames[number-1] +" of "+ suitNames[suit -1];
    }

    public int getNumber() {
        return number;
    }

    public int getSuit() {
        return suit;
    }

    @Override
    public int compareTo(Card other) {
        if(number == 1){
            if(other.number == 1){
                return 0;
            }
            return 1;

        }
        else if(number > other.number){
            if(other.number == 1){
                return -1;
            }
            else{
                return 1;
            }

        }else if(number < other.number){
            return -1;

        }else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return number == ((Card)obj).number && suit == ((Card)obj).suit;
    }
}
