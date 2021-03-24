package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import deckOfCards.*;

public class BlackjackModel {
	/*an ArrayList of Cards that will be used to store the dealer's cards.*/
	private ArrayList<Card> dealerCards;
	/*an ArrayList of Cards that will be used to store the player's cards.*/
	private ArrayList<Card> playerCards;
	/*a Deck variable, representing the Deck of cards for the game*/
	private Deck deck;
	
	/*A standard getter that does a deep copy of dealerCards to prevent privacy
	 *  leaks*/
	public ArrayList<Card> getDealerCards() {
		ArrayList<Card>dealerCopy = new ArrayList<>();
		for(Card card: dealerCards) {
			dealerCopy.add(card);
		}
		return dealerCopy;
	}
	
	/*A standard getter that does a deep copy of playerCards to prevent privacy
	 *  leaks*/
	public ArrayList<Card> getPlayerCards() {
		ArrayList<Card>playerCopy = new ArrayList<>();
		for(Card card: playerCards) {
			playerCopy.add(card);
		}
		return playerCopy;
	}
	
	/*A standard setter that does a deep copy of dealerCards to prevent privacy 
	 * leaks*/
	public void setDealerCards(ArrayList<Card> dealerCards) {
		ArrayList<Card>dealerCopy = new ArrayList<>();
		for(Card card: dealerCards) {
			dealerCopy.add(card);
		}
		this.dealerCards = dealerCopy;
	}
	
	/*A standard setter that does a deep copy of playerCards to prevent privacy 
	 * leaks*/
	public void setPlayerCards(ArrayList<Card> playerCards) {
		ArrayList<Card>playerCopy = new ArrayList<>();
		for(Card card: playerCards) {
			playerCopy.add(card);
		}
		this.playerCards = playerCards;
	}
	
	/*This method assigns a new instance of the Deck class to the deck variable,
	 *  and will then shuffle the deck, passing the parameter (random) along to
	 *   the deck's shuffle method.*/
	public void createAndShuffleDeck(Random random) {
		deck = new Deck();
		deck.shuffle(random);
	}
	
	/*this method deals the initial two cards to the player. The method 
	 * instantiates the respective list of cards (playerCards) and then will 
	 * deal two cards from the deck and add them to that list */
	public void initialDealerCards() {
		dealerCards = new ArrayList<>();
		for(int index =0; index < 2; index++) {
			dealerCards.add(deck.dealOneCard());
		}
	}
	
	/*this method deals the initial two cards to the dealer. The method 
	 * instantiates the respective list of cards (dealerCards) and then will 
	 * deal two cards from the deck and add them to that list */
	public void initialPlayerCards() {
		playerCards = new ArrayList<>();
		for(int index =0; index < 2; index++) {
			playerCards.add(deck.dealOneCard());
		}
	}
	
	/*this method deals just one card to either the player. it simply deal a 
	 * card from the deck and add it to either the playerCards list*/
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());
	}
	
	/*this method deals just one card to either the dealer. it simply deal a 
	 * card from the deck and add it to either the dealerCards list*/
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());
	}
	
	/*This method will evaluate the hand in question and return a very short 
	 * ArrayList that contains either one or two Integers, representing the 
	 * value(s) that could be assigned to that hand. and in my code the very 
	 * short arrayList would be called *newList*. */
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand){
		ArrayList<Integer> newList = new ArrayList<>();
		int sum = 0;/*this is used to check the sum of the hand values without 
		adding an Ace*/
		
		int check = 0;/*this checker is used to see if an ace is present in the
		hand and if so  it is incremented*/
		
		for(Card card: hand) {
			if(card.getRank()==Rank.ACE) {
				check++;
			}
		}
		
		//checks to see if the value check is still 0. meaning no Ace was found
		if(check==0) {
			for(Card card: hand) {
				sum += card.getRank().getValue();
			}
			newList.add(sum);
			return newList;
		}else {
			for(Card card: hand) {
				if(card.getRank()!=Rank.ACE) {
					sum += card.getRank().getValue();
				}
			}
			
			int sumWithAce1 = sum+(1*check);
			int sumWithAce11 = sum+11+(check-1);
			
			/*checks to see if sumWithAce11 is greater than 21. if so it wont be 
			added to the newList*/
			if(sumWithAce11 > 21) {
				newList.add(sumWithAce1);
				return newList;
			}
			
			/*if not. both values would be added.*/
			newList.add(sumWithAce1);
			newList.add(sumWithAce11);
			return newList;
				
		}
	}
	
	/*This method will assess the hand and return one of the four HandAssessment
	 *  constants*/
	public static HandAssessment assessHand(ArrayList<Card> hand) {
		/*i created an arrayList for the possibleHandValues for the hand and 
		 * called it poss*/
		ArrayList<Integer> poss = possibleHandValues(hand);
		
		if(hand == null || hand.size()<2) {
			return HandAssessment.INSUFFICIENT_CARDS;
		
		}else if( hand.size()==2 && poss.contains(21)) {
				return HandAssessment.NATURAL_BLACKJACK;
				
		}else if(Collections.max(poss) > 21) {
				return HandAssessment.BUST;
				
		}else {
				return HandAssessment.NORMAL;
			}
			
	}
	
	/*This method will look at the playerCards and the dealerCards and determine
	 *  the outcome of the game, returning one of the GameResult constants 
	 *  (PLAYER_WON, PLAYER_LOST, PUSH, or NATURAL_BLACKJACK).*/
	public GameResult gameAssessment() {
		/*i created the variable player to represent the assess hand of the 
		 * playerCards*/
		HandAssessment player = assessHand(playerCards);
		/*i created the variable dealer to represent the assess hand of the 
		 * dealerCards*/
		HandAssessment dealer = assessHand(dealerCards);
		/*i created a variable possPlayer that represents the possible hand 
		 * value for the playerCards*/
		ArrayList <Integer> possPlayer = possibleHandValues(playerCards);
		/*i created a variable possDealer that represents the possible hand 
		 * value for the dealerCards*/
		ArrayList <Integer> possDealer = possibleHandValues(dealerCards);
		
		if(player.equals(HandAssessment.NATURAL_BLACKJACK) && !dealer.equals(HandAssessment.NATURAL_BLACKJACK)) {
			return GameResult.NATURAL_BLACKJACK ;
			 
		}else if (player.equals(HandAssessment.NATURAL_BLACKJACK) && dealer.equals(HandAssessment.NATURAL_BLACKJACK)) {
			return GameResult.PUSH;
			
		}else {
			if(player.equals(HandAssessment.BUST) ) {
				return GameResult.PLAYER_LOST;
				
			}else if(!player.equals(HandAssessment.BUST) && dealer.equals(HandAssessment.BUST)) {
				return GameResult.PLAYER_WON;
				
			}else{
				if(Collections.max(possPlayer) > Collections.max(possDealer)) {
					return GameResult.PLAYER_WON;
					
				}else if (Collections.max(possPlayer) < Collections.max(possDealer)) {
					return GameResult.PLAYER_LOST;
					
				}else {
					return GameResult.PUSH;
				}
			}
		}
	}
	
	/*This method will look at the dealerCards to determine whether or not the 
	 * dealer should take another card during her turn, returning true if the 
	 * dealer should take another card and false otherwise. 
	 * The method has no side effects. */
	public boolean dealerShouldTakeCard() {
		ArrayList <Integer> possDealer = possibleHandValues(dealerCards);
		
		/*this variable is created to check to see if there is an ace in the 
		 * dealerCards. it does so by increment if there is an Ace*/
		int check = 0;
		for(Card card: dealerCards) {
			if(card.getRank()==Rank.ACE) {
				check++;
			}
		}
		if(Collections.max(possibleHandValues(dealerCards)) <= 16) {
			return true;
			
		}else if(Collections.max(possDealer) >= 18) {
			return false;
			
		}else if (check > 0 && (possDealer.contains(7) && possDealer.contains(17))){
			return true;
			
		}else {
			return false;
		}
		
	}
}
