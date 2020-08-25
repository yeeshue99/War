/*	
 * 	File:				War.java
 * 	Associated Files:	Main.java, Deck.java, Card.java
 * 	Packages Needed:	java.util.ArrayList, java.util.HashMap, java.util.Scanner
 * 	Author:            	Michael Ngo (https://github.com/yeeshue99)
 * 	Date Modified:      8/13/2020 by Michael Ngo
 * 	Modified By:        Michael Ngo
 * 
 * 	Purpose:			Underlying structure for War card game
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
 * Class:				War
 * Purpose:				Handles War engine and game
 * Methods:				PlayGame, StartWar, WarHelper, TurnCards
 */
public class War {

	
	int numPlayers = 2;
	Deck deck;
	ArrayList<ArrayList<Card>> allHands;
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
		allHands = Deck.DealCards(numPlayers);
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
			//Player printing and round setup
			System.out.println("======================================");
            System.out.printf("Player #%d, you have %d cards.%n", (player + 1),
            		allHands.get(player).size());
            System.out.printf("Player #%d, you have %d cards.%n", (nextPlayer + 1),
            		allHands.get(nextPlayer).size());
            System.out.println("Are you both ready?");
            sc.nextLine();

            //Turn cards and handles everything involving a round
            StartWar(allHands.get(player), allHands.get(nextPlayer));
            rounds++;
            
            //Shuffles decks and moves on
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
		return playerLost;
		//return rounds;
	}
	
	/*
	 * Function:			StartWar
	 * Params: 				Player1's hand(ArrayList<Card>), Player2's hand(ArrayList<Card>)
	 * Purpose:				Start recursively calling for War comparisons
	 * Returns: 			
	 */
	public void StartWar(ArrayList<Card> playerCards, ArrayList<Card> nextPlayerCards) {
		ArrayList<Card> playerCardPool = new ArrayList<Card>();
		ArrayList<Card> nextPlayerCardPool = new ArrayList<Card>();
		WarHelper(playerCards, nextPlayerCards, playerCardPool, nextPlayerCardPool, false);
	}
	
	/*
	 * Function:			WarHelper
	 * Params: 				Player1's hand(ArrayList<Card>), player2's hand(ArrayList<Card>),
	 * 							Player1's pool of drawn cards(ArrayList<Card>),
	 * 							Player2's pool of drawn cards(ArrayList<Card>),
	 * 							Whether or not the players have drawn matching cards yet(boolean)
	 * Purpose:				Handles logic for War comparison
	 * Returns: 			
	 */
	private void WarHelper(ArrayList<Card> playerCards, ArrayList<Card> nextPlayerCards,
			ArrayList<Card> playerCardPool, ArrayList<Card> nextPlayerCardPool, boolean matched){

		TurnCards(playerCards, playerCardPool, matched);
		System.out.printf("Player 1 draws a %s.%n",
				playerCardPool.get(playerCardPool.size() - 1).GetLabel());
		
		TurnCards(nextPlayerCards, nextPlayerCardPool, matched);
		System.out.printf("Player 2 draws a %s.%n",
				nextPlayerCardPool.get(nextPlayerCardPool.size() - 1).GetLabel());
		
		int compare = Deck.dblDigitSort.compare(playerCardPool.get(playerCardPool.size() - 1),
				nextPlayerCardPool.get(nextPlayerCardPool.size() - 1));
		
		//Checks cards. If they are the same, recursively calls to redo same operations
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
	 * Params: 				A player's hand(ArrayList<Card>),
	 * 							A player's pool of drawn cards(ArrayList<Card>),
	 * 							Whether or not the players have drawn matching cards yet(boolean)
	 * Purpose:				Handles logic for War card drawing
	 * Returns: 			
	 */
	private void TurnCards(ArrayList<Card> player, ArrayList<Card> playerPool, boolean matched) {
		int cardsGiven = 0;
		if(matched) {
			System.out.println("Turning over 4 cards...");
			while(cardsGiven < 4 && player.size() > 0) {
				playerPool.add(player.remove(0));
				cardsGiven++;
			}
		}
		else {
			System.out.println("Turning over 2 cards...");
			while(cardsGiven < 2 && player.size() > 0) {
				playerPool.add(player.remove(0));
				cardsGiven++;
			}
		}
	}
}
