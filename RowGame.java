//Michael Dobrzanski

import java.util.Scanner;

public class RowGame {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int[] board = new int[promptNumberReadLine(input, "Please enter the number of spaces for the game (1-40): ", 40 )];
		int player = 1;
		String playerName = "Player 1"; //Initialize our player and our playername with our first player, referred to as X for the tester
		
		while (winner(board) == 0){	//I create a while loop that continues to loop while the game does not have a winner
			
			System.out.println(rowToString(board)); //At the beginning of every turn we print the board
			System.out.print(playerName + ", please enter your move (1-" + board.length + "): "); //After printing our board, ask for the current players move in the board's range
			if (makeMove(board, player, input.nextInt())) {
				if (player == 1) {
					player = 2;
					playerName = "Player 2";
				}
				else {
					player = 1;
					playerName = "Player 1";
				}
			}
		}

		System.out.println(rowToString(board));
		int winner = winner(board); //Get our winner value so we can check what message to print
		if (winner == 1) {
			playerName = "Player 1";
			System.out.println("Game over! " + playerName + " wins.");
		}
		if (winner == 2) {
			playerName ="Player 2";
			System.out.println("Game over! " + playerName + " wins.");
		}
		if (winner == 3)
			System.out.println("Game over! Tie game!");
	}

	public static int promptNumberReadLine(Scanner s, String prompt, int max) {
		int validCheck = 0;

		while (validCheck <= 0 || validCheck > max) {
			System.out.print(prompt);
			if(s.hasNextInt()) {
				validCheck = s.nextInt();
				if (validCheck >0 && validCheck <= max) {
					s.nextLine();
					return validCheck;
				}
			}
			System.out.println("That was not a valid number! Please try again.");
			s.nextLine();
		}
		return validCheck;
	}
	public static String rowToString(int[] game) {
		String board = "";
		for (int i = 0; i <game.length; i++) {
			if (game[i]==0)
				board+= ".";
			if (game[i]==1)
				board+="X";
			if (game[i]==2)
				board+="O";
			if (game[i] > 2)
				board+="-";
		}
		return board;
	}
	public static int winner(int[] game) { //count X's and O's to determine winner
		int player1Score = 0, player2Score = 0;
		for (int i =0; i < game.length; i++) {
			if (game[i] == 0)
				return 0;
			if (game[i] == 1)
				player1Score++;
			if (game[i] == 2)
				player2Score++;
		}
		if (player1Score > player2Score)
			return 1;
		if (player2Score > player1Score)
			return 2;

		return -1;
	}

	public static boolean makeMove(int[] game, int player, int position) {
		
		int opponent = 2;
		if (player == 2)
			opponent = 1;
		position -= 1;     //adjusting position to accomodate player and match indices
		int adjArrLength = game.length -1;

		if (position <= game.length && position >= 0 && game[position] == 0) {
			game[position] = player;
			for (int i = 0; i < game.length; i++) {
				if (game[i] == 3) {
					game[i] = 0;
				}
			}
			if (position > 0 && game[position-1] == opponent) {
				/*We detect an opponent piece to the left of played piece then iterate through.
				If we find an empty space then we're done.
				If we find another space with current players value in it or the end of the board.
				we replace all spaces between with blocked spaces*/
				
				for (int i = position-1; i >= 0; i--) {
					if (game[i] == 0 || game[i] == 3) {
						break;
					}

					if (game[i] == player || i == 0) {
						for (int j = position-1; j >= i; j--) {
							if (game[j] != player)
								game[j] = 3;
						}
						break;
					}
				}
			}

			if (position < adjArrLength && game[position+1] == opponent) { //same as above, but checking to the right
				//	System.out.println("We detect an opponent piece to the right of played piece");
				for (int i = position+1; i <= adjArrLength; i++) {
					if (game[i] == 0 || game[i] == 3) {
						break;
					}
					if (game[i] == player || i == adjArrLength) {
						for (int j = position+1; j <= i; j++) {
							if (game[j] != player)
								game[j] = 3;
						}
						break;
					}
				}
			}
			return true;
		}
		//Handle invalid number errors from inside this function call rather than in the main method
		if (position >= game.length || position < 1)
			System.out.println("That was not a valid number! Please try again.");
		else
			System.out.println("That space is not available! Please try again.");
		return false;
	}
}
