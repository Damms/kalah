package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import java.lang.String;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
    boolean playerTurn = false; // false = player1, true = player2
    boolean gameOver = false;
    boolean goAgain = false;
    
	public static void main(String[] args) {
                /*Animal chick1 = new Chicken(1, "M", 7);
                //chick1.move();
                
                Animal sparrow1 = new Sparrow(2, "M", 7); 
                //sparrow1.move();
                
                Animal fish1 = new Fish(2, "M", 7); 
                //fish1.move();
                
                moveAnimal(chick1);
                moveAnimal(sparrow1);
                moveAnimal(fish1);*/
               
                
                
		new Kalah().play(new MockIO());
	}
        
        /*public static void moveAnimal(Animal animal){ // allows for more dynamic function
            animal.move();
        }*/
        
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
                while(true){
                        // keep program alive3
                    String inputText = "Specify house number ";
                    int selectedHouse = io.readInteger(inputText, 1, 6, 0, inputText) - 1;
                    io.println("Selected House: " + selectedHouse);
                    
                    goAgain = game.moveSeeds(selectedHouse, playerTurn);
                    values = game.updateBoard();
                    gameOver = game.checkGameStatus(); // check for game status
                    
                    if(!goAgain){ // if need to change player
                        playerTurn = !playerTurn;
                    }
                    
                    if(gameOver){
                        io.println(" ");
                        io.println("-------------------------------");
                        io.println("GAME OVER");
                        io.println("Player 1 Score: " + values[0]);
                        io.println("Player 2 Score: " + values[1]);
                        io.println("-------------------------------");
                    } else {
                        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
                        io.println("| P2 | 6[ "+values[13]+"] | 5[ "+values[12]+"] | 4[ "+values[11]+"] | 3[ "+values[10]+"] | 2[ "+values[9]+"] | 1[ "+values[8]+"] |  "+values[0]+" |");
                        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
                        io.println("|  "+values[1]+" | 1[ "+values[2]+"] | 2[ "+values[3]+"] | 3[ "+values[4]+"] | 4[ "+values[5]+"] | 5[ "+values[6]+"] | 6[ "+values[7]+"] | P1 |");
                        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
                        if(playerTurn){
                            io.println("Player 2's turn - Specify house number or 'q' to quit: ");
                        } else {
                            io.println("Player 1's turn - Specify house number or 'q' to quit: ");
                        }
                    }
                    
                }
	}
        

}
