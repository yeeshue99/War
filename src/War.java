/*	
 * 	File:				War.java
 * 	Associated Files:	Main.java, Deck.java
 * 	Packages Needed:	java.util.ArrayList, java.util.HashMap, java.util.Scanner
 * 	Author:            	Michael Ngo (https://github.com/yeeshue99)
 * 	Date Modified:      8/13/2020 by Michael Ngo
 * 	Modified By:        Michael Ngo
 * 
 * 	Purpose:			Underlying structure for War card game
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/*
 * Class:				War
 * Purpose:				Handles War engine and game
 * Methods:				PlayGame, StartWar, WarHelper, TurnCards
 */
public class War {

	
	int numPlayers = 2;
	Deck deck;
	ArrayList<ArrayList<String>> allHands;
	Scanner sc;
	
	/*
	 * Function:			Initialize
	 * Params: 				
	 * Purpose:				Initializes War engine
	 * Returns: 			
	 */
	public War() {
		deck = new Deck();
		System.out.println("Dealing the deck evenly to every player...");
		allHands = deck.DealCards(numPlayers);
	}
	
	/*
	 * Function:			PlayGame
	 * Params: 				Java command line input(Scanner)
	 * Purpose:				Run the game loop and communicate with user
	 * Returns: 			Player who lost(int)
	 */
	public int PlayGame(Scanner sc) {
		System.out.println("Welcome to the game of War!");
		this.sc = sc;
		
		int player = 0;
		int nextPlayer = 1;
		int playerLost = -1;
		int rounds = 0;
		outer:
		while(true) {
			System.out.println("======================================");
            System.out.printf("Player #%d, you have %d cards.%n", (player + 1),
            		allHands.get(player).size());
            System.out.printf("Player #%d, you have %d cards.%n", (nextPlayer + 1),
            		allHands.get(nextPlayer).size());
            System.out.println("Are you both ready?");
            //sc.nextLine();

            StartWar(allHands.get(player), allHands.get(nextPlayer));
            rounds++;
            Collections.shuffle(allHands.get(player));
            Collections.shuffle(allHands.get(nextPlayer));
            for(int i = 0; i < numPlayers; i++) {
            	if(allHands.get(i).size() <= 0) {
            		playerLost = i;
            		break outer;
            	}
            }
		}
		System.out.printf("Number of rounds: %d%n", rounds);
		//return playerLost;
		return rounds;
	}
	
	/*
	 * Function:			StartWar
	 * Params: 				Player1's hand(ArrayList<String>), Player2's hand(ArrayList<String>)
	 * Purpose:				Start recursively calling for War comparisons
	 * Returns: 			
	 */
	public void StartWar(ArrayList<String> playerCards, ArrayList<String> nextPlayerCards) {
		ArrayList<String> playerCardPool = new ArrayList<String>();
		ArrayList<String> nextPlayerCardPool = new ArrayList<String>();
		WarHelper(playerCards, nextPlayerCards, playerCardPool, nextPlayerCardPool, false);
	}
	
	/*
	 * Function:			WarHelper
	 * Params: 				Player1's hand(ArrayList<String>), player2's hand(ArrayList<String>),
	 * 							Player1's pool of drawn cards(ArrayList<String>),
	 * 							Player2's pool of drawn cards(ArrayList<String>),
	 * 							Whether or not the players have drawn matching cards yet(boolean)
	 * Purpose:				Handles logic for War comparison
	 * Returns: 			
	 */
	private void WarHelper(ArrayList<String> playerCards, ArrayList<String> nextPlayerCards,
			ArrayList<String> playerCardPool, ArrayList<String> nextPlayerCardPool, boolean matched){

		TurnCards(playerCards, playerCardPool, matched);
		TurnCards(nextPlayerCards, nextPlayerCardPool, matched);
		
		System.out.printf("Player 1 draws a %s.%n",
				playerCardPool.get(playerCardPool.size() - 1));
		System.out.printf("Player 2 draws a %s.%n",
				nextPlayerCardPool.get(nextPlayerCardPool.size() - 1));
		
		int compare = deck.dblDigitSort.compare(playerCardPool.get(playerCardPool.size() - 1),
				nextPlayerCardPool.get(nextPlayerCardPool.size() - 1));
		if(compare == 0) {
			System.out.println("Both of your cards were the same. Now, we have to do it again!");
			WarHelper(playerCards, nextPlayerCards, playerCardPool, nextPlayerCardPool, true);
		}
		else if(compare > 0) {
			System.out.println("Player 1 won. They take all the cards!");
			playerCards.addAll(playerCardPool);
			playerCards.addAll(nextPlayerCardPool);
		}else {
			System.out.println("Player 2 won. They take all the cards!");
			nextPlayerCards.addAll(playerCardPool);
			nextPlayerCards.addAll(nextPlayerCardPool);
		}
	}
	
	/*
	 * Function:			TurnCards
	 * Params: 				A player's hand(ArrayList<String>),
	 * 							A player's pool of drawn cards(ArrayList<String>),
	 * 							Whether or not the players have drawn matching cards yet(boolean)
	 * Purpose:				Handles logic for War card drawing
	 * Returns: 			
	 */
	private void TurnCards(ArrayList<String> player, ArrayList<String> playerPool, boolean matched) {
		String playerCard;
		int cardsGiven = 0;
		if(matched) {
			while(cardsGiven < 4 && player.size() > 0) {
				playerCard = player.get(0);
				player.remove(0);
				playerPool.add(playerCard);
				cardsGiven++;
			}
		}
		else {
			while(cardsGiven < 2 && player.size() > 0) {
				playerCard = player.get(0);
				player.remove(0);
				playerPool.add(playerCard);
				cardsGiven++;
			}
		}
	}
}
