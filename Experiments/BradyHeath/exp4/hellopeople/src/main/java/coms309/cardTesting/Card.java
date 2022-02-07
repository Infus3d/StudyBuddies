package coms309.cardTesting;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Card {

    private String number;

    private String suit;

    public Card(){
        
    }

    public Card(String suit, String number){
        this.suit = suit;
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String firstName) {
        this.number = firstName;
    }

    public String getSuit() {
        return this.suit;
    }

    public void setSuit(String lastName) {
        this.suit = lastName;
    }

    @Override
    public String toString() {
        return number + " " 
               + suit + " ";
    }
}
