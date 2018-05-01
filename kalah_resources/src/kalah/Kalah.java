package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {
    
    public static void main(String[] args) {           
            new Kalah().play(new MockIO());
    }

    public void play(IO io) {
        
        int numberOfHouses = 6;
        int seedsInHouse = 4;
        int initSeedsInStore = 0;
        int numberOfBuildingsOnBoards = 14;
        int[] playerScores = new int[2];

        House[] player1Houses = new House[numberOfHouses];
        House[] player2Houses = new House[numberOfHouses];

        for(int i = 0; i < 6; i++){
            player1Houses[i] = new House(seedsInHouse); 
            player2Houses[i] = new House(seedsInHouse); 
        }

        Store player1 = new Store(initSeedsInStore);
        Store player2 = new Store(initSeedsInStore);

        Board game = new Board(player1Houses, player2Houses, player1, player2); // create new game

        boolean playerTurn = false; // false = player1, true = player2
        boolean gameOver = false;
        boolean quit = false;
        int goAgain = 2; // 0 = empty house, 1 = anotherTurn, 2 = nextPlayerTurn
        int[] values = new int[numberOfBuildingsOnBoards];
        int selectedHouse = 0;

        
        while(!gameOver){

            // update the backend gameboard and check status of the game
            values = game.updateValues();
            gameOver = game.checkGameStatus(playerTurn); 
            String keyBoard = "1";


            // update screen gameplay
            if(goAgain == 0){ // selected empty house
                goAgain = 1; // player goes again
                io.println("House is empty. Move again."); 
            } 
            
            printBoard(io, values);
            
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
                printBoard(io, values);

                if(!quit){ 
                    
                    playerScores = updateScores(values);     
                    io.println("\tplayer 1:" + playerScores[0] );
                    io.println("\tplayer 2:" + playerScores[1] );

                    if(playerScores[0] >  playerScores[1]){
                        io.println("Player 1 wins!");
                    } else if (playerScores[0] <  playerScores[1]){
                        io.println("Player 2 wins!");
                    } else {
                        io.println("A tie!");
                    }
                    
                }
            } else { // move seeds if the game isn't over and change user turns
                
                goAgain = game.moveSeeds(selectedHouse, playerTurn);
                
                if(goAgain == 2){ 
                playerTurn = !playerTurn;
                }
                
            }      

        }
    }
    
    public void printBoard(IO io, int[] values) {
        io.println("+----+-------+-------+-------+-------+-------+-------+----+"); 
        String player2Line = String.format("| P2 | 6[%2d] | 5[%2d] | 4[%2d] | 3[%2d] | 2[%2d] | 1[%2d] | %2d |", values[13], values[12], values[11], values[10], values[9], values[8], values[0]);
        io.println(player2Line);
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        String player1Line = String.format("| %2d | 1[%2d] | 2[%2d] | 3[%2d] | 4[%2d] | 5[%2d] | 6[%2d] | P1 |", values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
        io.println(player1Line);
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }
    
    public int[] updateScores(int[] values) {
        int[] playerScores = new int[2];
        playerScores[0] = values[0]+values[2]+values[3]+values[4]+values[5]+values[6]+values[7]; // store + houses
        playerScores[1] = values[13]+values[12]+values[11]+values[10]+values[9]+values[8]+values[1]; // houses + store
        return playerScores;
    }
        

}
