/*	
 * 	File:				Deck.java
 * 	Associated Files:	Main.java, War.java
 * 	Packages Needed:	java.util.ArrayList, java.util.Collections, java.util.Comparator;
 * 	Author:            	Michael Ngo (https://github.com/yeeshue99)
 * 	Date Modified:      8/12/2020 by Michael Ngo
 * 	Modified By:        Michael Ngo
 * 
 * 	Purpose:			Setup card structure to be used in card games
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/*
 * Class:				Deck
 * Purpose:				Handle all card operations
 * Methods:				MakeDeck, DealCards, RemovePairs, DisplayCards,
 * 							dblDigitSort, isNumeric
 */
public class Deck {

	// Different combinations for cards
	String[] suits = { "Clubs", "Hearts", "Spades", "Diamonds" };
	String[] values = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
	HashMap<String, Integer> letterValues = new HashMap<String, Integer>();

	public Deck() {
		MakeDeck();
		letterValues.put("J", 11);
		letterValues.put("Q", 12);
		letterValues.put("K", 13);
		letterValues.put("A", 14);
	}

	// Container to hold all cards
	public ArrayList<String> deck = new ArrayList<String>();

	/*
	 * Function:			MakeDeck 
	 * Params: 
	 * Purpose:				Initialize central deck to hold all cards but the Queen of Hearts
	 * Returns: 			
	 */
	public void MakeDeck() {
		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < values.length; j++) {
				deck.add(values[j] + " of " + suits[i]);
			}
		}

		Collections.shuffle(deck);
	}

	/*
	 * Function:			DealCards 
	 * Params:				Number of hands(int)
	 * Purpose: 			Evenly split every card from the center deck to each hand 
	 * Returns:				Each hand
	 */
	public ArrayList<ArrayList<String>> DealCards(int numPlayers) {
		ArrayList<ArrayList<String>> hands = new ArrayList<ArrayList<String>>();
		if (numPlayers <= 1) {
			numPlayers = 2;
			System.out.println("There has to be at least two players. I assume that's what you meant!");
		}
		
		for (int i = 0; i < numPlayers; i++) {
			hands.add(new ArrayList<String>());
		}

		int player = 0;
		while (!deck.isEmpty()) {
			hands.get(player).add(deck.get(0));
			deck.remove(0);
			player++;
			if (player >= numPlayers) {
				player = 0;
			}

		}
		/*
		for (int i = 0; i < numPlayers; i++) {
			Collections.sort(hands.get(i), dblDigitSort);
		}
		*/
		return hands;
	}

	/*
	 * Function:			RemovePairs
	 * Params: 				player's hand(ArrayList<String>)
	 * Purpose:				Removes every pair of cards from the hand
	 * Returns: 			New hand with all pairs removed
	 */
	public Boolean RemovePairs(ArrayList<String> hand) {
		Collections.sort(hand, dblDigitSort);
		int i = 0;
		while (i < hand.size() - 1) {
			if (hand.get(i).charAt(0) == (hand.get(i + 1).charAt(0)) && hand.get(i).charAt(1) == (hand.get(i + 1).charAt(1))) {
				hand.remove(i);
				hand.remove(i);
				i = Math.max(0, (i - 1));
			}
			else if (hand.get(i).charAt(0) == (hand.get(i + 1).charAt(0))) {
				hand.remove(i);
				hand.remove(i);
				i = Math.max(0, (i - 1));
			} 
			else {
				i++;
			}
		}
		Collections.shuffle(hand);
		if(hand.size() > 0) {
			return false;
		}
		else {
			return true;
		}
	}

	/*
	 * Function:			DisplayCards
	 * Params: 				A certain player's hand(ArrayList<String>)
	 * Purpose:				Prints out a hand to the console to be viewed
	 * Returns: 			
	 */
	public void DisplayCards(ArrayList<String> hand) {
		Collections.sort(hand, dblDigitSort);
		for (int i = 0; i < hand.size(); i++) {
			System.out.print(hand.get(i) + ", ");
		}
		System.out.println();
	}
	
	/*
	 * Function:			dblDigitSort
	 * Params: 				
	 * Purpose:				Sorts based off of Card values: A is low, K is high.
	 * Returns: 			Comparator of two values
	 */
	public Comparator<String> dblDigitSort = new Comparator<String>()
    {
        @Override
        public int compare(String s1, String s2)
        {
        	String[] str1 = s1.split(" ");
        	String[] str2 = s2.split(" ");
        	Integer val1;
        	Integer val2;
        	if(isNumeric(str1[0])) {
                val1 = Integer.parseInt(str1[0]);
        	}
        	else {
        		val1 = letterValues.get(str1[0]);
        	}
        	if(isNumeric(str2[0])) {
                val2 = Integer.parseInt(str2[0]);
        	}
        	else {
        		val2 = letterValues.get(str2[0]);
        	}
            return val1.compareTo(val2);
        }
    };
    
	/*
	 * Function:			isNumeric
	 * Params: 				Card Value(String)
	 * Purpose:				Determines whether or not a string is wholly a number
	 * Returns: 			Whether or not the given string can be parsed as a number
	 */
	private static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Integer __ = Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
}
