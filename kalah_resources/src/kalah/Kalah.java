package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
    boolean playerTurn = false; // false = player1, true = player2
    boolean gameOver = false;
    boolean quit = false;
    int goAgain = 2; // 0 = empty house, 1 = anotherTurn, 2 = nextPlayerTurn
    
	public static void main(String[] args) {           
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
		// Replace what's below with your implementation
                // Setup the board
                House[] player1Houses = new House[6];
                House[] player2Houses = new House[6];
                for(int i = 0; i < 6; i++){
                    player1Houses[i] = new House(4); // 4 seeds per house
                    player2Houses[i] = new House(4); // 4 seeds per house
                }
                Store player1 = new Store(0);
                Store player2 = new Store(0);
                
                Board game = new Board(player1Houses, player2Houses, player1, player2);
                
                int[] values = new int[14];
                int selectedHouse = 0;
                while(!gameOver){
                        // keep program alive3
                        
                    values = game.updateBoard();
                    gameOver = game.checkGameStatus(playerTurn); // check if board is cleared
                    String keyBoard = "1";
                    
                    if(goAgain == 0){ // selected empty house
                        goAgain = 1; // player goes again
                        io.println("House is empty. Move again."); 
                    } 
                    io.println("+----+-------+-------+-------+-------+-------+-------+----+"); 
                    String player2Line = String.format("| P2 | 6[%2d] | 5[%2d] | 4[%2d] | 3[%2d] | 2[%2d] | 1[%2d] | %2d |", values[13], values[12], values[11], values[10], values[9], values[8], values[0]);
                    io.println(player2Line);
                    io.println("|    |-------+-------+-------+-------+-------+-------|    |");
                    String player1Line = String.format("| %2d | 1[%2d] | 2[%2d] | 3[%2d] | 4[%2d] | 5[%2d] | 6[%2d] | P1 |", values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
                    io.println(player1Line);
                    io.println("+----+-------+-------+-------+-------+-------+-------+----+");
                    if(!gameOver){
                        if(playerTurn){
                            String inputText = "Player P2's turn - Specify house number or 'q' to quit: ";
                            keyBoard = io.readFromKeyboard(inputText);
                        } else {
                            String inputText = "Player P1's turn - Specify house number or 'q' to quit: ";
                            keyBoard = io.readFromKeyboard(inputText);
                        }
                    }
                    
                    if("q".equals(keyBoard)){
                        gameOver = true;
                        quit = true;
                    } else {
                        selectedHouse = Integer.parseInt(keyBoard) - 1;
                    }
                    
                    if(gameOver){
                        io.println("Game over");
                        io.println("+----+-------+-------+-------+-------+-------+-------+----+"); 
                        /*String*/ player2Line = String.format("| P2 | 6[%2d] | 5[%2d] | 4[%2d] | 3[%2d] | 2[%2d] | 1[%2d] | %2d |", values[13], values[12], values[11], values[10], values[9], values[8], values[0]);
                        io.println(player2Line);
                        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
                        /*String*/ player1Line = String.format("| %2d | 1[%2d] | 2[%2d] | 3[%2d] | 4[%2d] | 5[%2d] | 6[%2d] | P1 |", values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
                        io.println(player1Line);
                        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
                        
                        if(!quit){
                            int p1Score = values[0]+values[2]+values[3]+values[4]+values[5]+values[6]+values[7];
                            int p2Score = values[13]+values[12]+values[11]+values[10]+values[9]+values[8]+values[1];
                            io.println("\tplayer 1:" + p1Score ); // add all seeds in house to store
                            io.println("\tplayer 2:" + p2Score );
                            if(p1Score >  p2Score){
                                io.println("Player 1 wins!");
                            } else if (p1Score <  p2Score){
                                io.println("Player 2 wins!");
                            } else {
                                io.println("A tie!");
                            }
                        }
                    }                
                    
                    // move seeds and change user turns
                    if(!gameOver){ 
                        goAgain = game.moveSeeds(selectedHouse, playerTurn);
                    }
                                        
                    if(goAgain == 2){ // if need to change player
                        playerTurn = !playerTurn;
                    }
                    
                }
	}
        

}
