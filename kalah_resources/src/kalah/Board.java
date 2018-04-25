/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kalah;

/**
 *
 * @author jdamm
 */
public class Board {
    int[] values = new int[14]; // 0 is p1 store value, 1 is p2 store value //  2-7 is p1 house values, 8-13 is p2 house values
    House[] player1Houses = new House[6];
    House[] player2Houses = new House[6];
    Store player1Store = null;
    Store player2Store = null;
    
    public Board(House[] player1House, House[] player2House, Store p1Store, Store p2Store){
        for(int i = 0; i < 6; i++){
            this.player1Houses[i] = player1House[i];
            this.player2Houses[i] = player2House[i];
        }
        this.player1Store = p1Store;
        this.player2Store = p2Store;
    }
    
    public int[] updateBoard(){
        //
        values[0] = this.player1Store.getSeeds(); // player 1 store
        values[1] = this.player2Store.getSeeds(); // player 2 store
        for(int i = 0; i < 6; i++){
            values[2+i] = this.player1Houses[i].getSeeds(); // player 1 houses
            values[8+i] = this.player2Houses[i].getSeeds(); // player 2 houses
        }
        return values;
    }
    
    public boolean checkGameStatus(boolean playerTurn){ // check if house has been cleared
        //
        int count1 = 0, count2 = 0;
        for(int i = 0; i < 6; i++){
            if(playerTurn){
                if(this.player2Houses[i].getSeeds() == 0){
                    count2++;
                }
            } else {
                if(this.player1Houses[i].getSeeds() == 0){
                count1++;
                }
            }
            
            if(count1 == 6 || count2 == 6){
                return true;
            }
        }
        return false;
    }
    // 0 = empty house, 1 = anotherTurn, 2 = nextPlayerTurn
    public int moveSeeds(int houseNum, boolean playerTurn){ // check if house has been cleared
        // test
        int seedsInHouse = 0;
        int house = houseNum;
        boolean turn = playerTurn;
        if(playerTurn){
            seedsInHouse = this.player2Houses[house].getSeeds();
            this.player2Houses[house].setSeeds(0);
        } else {
            seedsInHouse = this.player1Houses[house].getSeeds();
            this.player1Houses[house].setSeeds(0);
        }     
        
        if(seedsInHouse == 0){ // empty house
            return 0;
        }
        
        while(seedsInHouse > 0){
            house++; // next house
            if(house == 6){ // add seed into store if it's your turn
                
                house = -1;
                if(turn == playerTurn && seedsInHouse > 0){ // add to players store
                    if(playerTurn){ // player 2 store
                        this.player2Store.incrementSeeds();
                    } else { // player 1 store
                        this.player1Store.incrementSeeds();
                    }
                    seedsInHouse--; // used a seed
                    
                    if(seedsInHouse == 0){ // last seed was in player store
                        house = 10;
                    }
                }
                turn = !turn;
              
            } 
            else { // add to houses
                
                if(turn){ // player 2
                    this.player2Houses[house].incrementSeeds();
                } else { // player 1
                    this.player1Houses[house].incrementSeeds();
                }
                seedsInHouse--; // used a seed
            }            
        }
        
        // check if current house had 0 seeds if so add pebble and opposite to player store
        return checkEnding(turn, playerTurn, house);
    }
    
    public int checkEnding(boolean turn, boolean playerTurn, int house){ // check if house has been cleared
        int seedsToAdd = 0;
        if(house == 10){ // ended on store
            return 1;
        }
        if(turn){ // ends on player 2 side
            if(playerTurn){ // was originally player 2 turn
                seedsToAdd = this.player1Houses[0+(5-house)].getSeeds();
                if(this.player2Houses[house].getSeeds() == 1 && seedsToAdd > 0){ // capture check
                    this.player2Store.add(seedsToAdd+1);
                    this.player2Houses[house].setSeeds(0); // sets current house to 0
                    this.player1Houses[0+(5-house)].setSeeds(0); // sets opposite house to 0 seeds
                    return 2;
                }
            }
        } 
        else { // ends on player 1 side
            if(!playerTurn){ // was originally player 2 turn
                seedsToAdd = this.player2Houses[0+(5-house)].getSeeds();
                if(this.player1Houses[house].getSeeds() == 1 && seedsToAdd > 0){ // capture check
                    this.player1Store.add(seedsToAdd+1);
                    this.player1Houses[house].setSeeds(0); // sets current house to 0
                    this.player2Houses[0+(5-house)].setSeeds(0); // sets opposite house to 0 seeds
                    return 2;
                }
            }
        }
        return 2;
    }
  
} // end of board
