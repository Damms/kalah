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
    boolean goAgain = false;
    
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
                    gameOver = game.checkGameStatus(); // check for game status
                    String keyBoard = "";
                    if(!gameOver){
                        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
                        io.println("| P2 | 6[ "+values[13]+"] | 5[ "+values[12]+"] | 4[ "+values[11]+"] | 3[ "+values[10]+"] | 2[ "+values[9]+"] | 1[ "+values[8]+"] |  "+values[0]+" |");
                        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
                        io.println("|  "+values[1]+" | 1[ "+values[2]+"] | 2[ "+values[3]+"] | 3[ "+values[4]+"] | 4[ "+values[5]+"] | 5[ "+values[6]+"] | 6[ "+values[7]+"] | P1 |");
                        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
                        if(playerTurn){
                            String inputText = "Player P2's turn - Specify house number or 'q' to quit: ";
                            //selectedHouse = io.readInteger(inputText, 1, 6, 0, inputText) - 1;
                            keyBoard = io.readFromKeyboard(inputText);
                        } else {
                            //io.println("Player 1's turn - Specify house number or 'q' to quit: ");
                            String inputText = "Player P1's turn - Specify house number or 'q' to quit: ";
                            //selectedHouse = io.readInteger(inputText, 1, 6, 0, inputText) - 1;
                            keyBoard = io.readFromKeyboard(inputText);
                        }
                    }
                    
                    if("q".equals(keyBoard)){
                        gameOver = true;
                    } else {
                        selectedHouse = Integer.parseInt(keyBoard) - 1;
                    }
                    
                    if(gameOver){
                        io.println("Game over");
                        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
                        io.println("| P2 | 6[ "+values[13]+"] | 5[ "+values[12]+"] | 4[ "+values[11]+"] | 3[ "+values[10]+"] | 2[ "+values[9]+"] | 1[ "+values[8]+"] |  "+values[0]+" |");
                        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
                        io.println("|  "+values[1]+" | 1[ "+values[2]+"] | 2[ "+values[3]+"] | 3[ "+values[4]+"] | 4[ "+values[5]+"] | 5[ "+values[6]+"] | 6[ "+values[7]+"] | P1 |");
                        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
                        io.println("    player 1:" + values[0]);
                        io.println("    player 2:" + values[1]);
                        if(values[0] > values[1]){
                            io.println("Player 1 wins!");
                        } else {
                            io.println("Player 2 wins!");
                        }
                    }                
                    
                    if(!gameOver){
                        goAgain = game.moveSeeds(selectedHouse, playerTurn);
                    }
                                        
                    if(!goAgain){ // if need to change player
                        playerTurn = !playerTurn;
                    }
                    
                    
                }
	}
        

}
