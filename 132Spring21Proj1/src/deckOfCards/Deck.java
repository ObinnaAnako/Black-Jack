package deckOfCards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
	/*this is a  private instance variable representing an ArrayList of Cards.*/
	private ArrayList<Card> cards;
	
	/*this is a constructor that takes no parameters. it instantiates the list 
	 * of Cards, and populate it with the usual 52 cards found in a deck*/
	public Deck() {	
		cards = new ArrayList<>();
		/*i created a for each loop to go through value of the suit Enumerator*/
		for(Suit suit: Suit.values()) {
			for(Rank rank: Rank.values()) {
				cards.add(new Card(rank, suit));
			}
		}
	}
	
	/*this method's implementation consists of just one statement, which is a 
	 * call to the version of Collections.shuffle that takes two arguments. 
	 * the method does a good job in shuffling the deck randomly. a random 
	 * number generator is included as a parameter so that while testing the 
	 * project we can reliably generate the exact same shuffles over and over.*/
	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(cards, randomNumberGenerator);
	}
	
	/*This method will remove one card from the front of the list (index 0) and 
	 * return it.*/
	public Card dealOneCard() {
		Card removed = cards.get(0);
		cards.remove(0);
		return removed;
	}
	
	
}
