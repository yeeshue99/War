/*	
 * 	File:				Main.java
 * 	Associated Files:	Deck.java, War.java, Card.java
 * 	Packages Needed:	
 * 	Author:            	Michael Ngo (https://github.com/yeeshue99)
 * 	Date Modified:      8/13/2020 by Michael Ngo
 * 	Modified By:        Michael Ngo
 * 
 * 	Purpose:			Run a simple War game in console
 */

import java.util.Scanner;

/*
 * Class:				Main
 * Purpose:				Run the code
 * Methods:				main
 */
public class Main {
	
	/*
	 * Function:			main 
	 * Params: 				commandLineArguments(String[]) {Not used}
	 * Purpose:				Handle overhead components to structure War game
	 * Returns: 			
	 */
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Welcome to War. Let's get ready to play!");
        War warGame = new War();
        
        int loser = warGame.PlayGame(sc) + 1;
        int winner;
        if(loser == 1) {
        	winner = 2;
        }
        else {
        	winner = 1;
        }
        System.out.printf("Congratulations, player #%d, you won!", (winner));
    }
}
