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
    int numberOfBuildingsOnBoard = 14;
    int numberOfHouses = 6;                           
    int[] values = new int[numberOfBuildingsOnBoard]; 
    House[] player1Houses = new House[numberOfHouses];
    House[] player2Houses = new House[numberOfHouses];
    Store player1Store = null;
    Store player2Store = null;
    int seedsInHouse = 0;
    int firstHouse = -1;
    int emptyHouse = 10;
    int house;
    boolean turn;
    
    public Board(House[] player1House, House[] player2House, Store p1Store, Store p2Store){
        for(int i = 0; i < numberOfHouses; i++){
            this.player1Houses[i] = player1House[i];
            this.player2Houses[i] = player2House[i];
        }
        this.player1Store = p1Store;
        this.player2Store = p2Store;
    }
    
    // update display values with backend stored values
    public int[] updateValues(){
        
        values[0] = this.player1Store.getSeeds();
        values[1] = this.player2Store.getSeeds();
        
        for(int i = 0; i < numberOfHouses; i++){
            values[2+i] = this.player1Houses[i].getSeeds();
            values[8+i] = this.player2Houses[i].getSeeds();
        }
        
        return values;
        
    }
    
    // check if one side has been cleared - graceful end
    public boolean checkGameStatus(boolean playerTurn){ 
        int count1 = 0, count2 = 0;
        
        for(int i = 0; i < numberOfHouses; i++){
            
            if(playerTurn){
                if(this.player2Houses[i].getSeeds() == 0){
                    count2++;
                }
            } else {
                if(this.player1Houses[i].getSeeds() == 0){
                count1++;
                }
            }
            
        }
        
        if(count1 == numberOfHouses || count2 == numberOfHouses){ 
            return true;
        }
        return false;
        
    }
    
    // Return values : 0 = empty house try again, 1 = anotherTurn, 2 = nextPlayerTurn
    public int moveSeeds(int houseNum, boolean playerTurn){ 
        seedsInHouse = 0;
        house = houseNum;
        turn = playerTurn;
        
        getSeedsInHouse(playerTurn);
        
        if(seedsInHouse == 0){ 
            return 0; 
        }
        
        spreadSeeds(playerTurn);

        return captureCheck(playerTurn);
        
    }
    
    public int captureCheck(boolean playerTurn){
        int seedsToAdd = 0;
        
        if(house == emptyHouse){
            return 1; 
        }
        
        if(turn){ // ends on player 2 side
            
            if(playerTurn){ // was originally player 2 turn
                seedsToAdd = this.player1Houses[0+(5-house)].getSeeds();
                
                if(this.player2Houses[house].getSeeds() == 1 && seedsToAdd > 0){
                    this.player2Store.add(seedsToAdd+1);
                    this.player2Houses[house].setSeeds(0); // sets current house to 0
                    this.player1Houses[0+(5-house)].setSeeds(0); // sets opposite house to 0 seeds
                    return 2; // next players turn
                }
                
            }
            
        } 
        else { // ends on player 1 side
            
            if(!playerTurn){ // was originally player 1 turn
                seedsToAdd = this.player2Houses[0+(5-house)].getSeeds();
                
                if(this.player1Houses[house].getSeeds() == 1 && seedsToAdd > 0){ 
                    
                    this.player1Store.add(seedsToAdd+1);
                    this.player1Houses[house].setSeeds(0); // sets current house to 0
                    this.player2Houses[0+(5-house)].setSeeds(0); // sets opposite house to 0 seeds
                    return 2; // next players turn
                }
                
            }
            
        }
        return 2; 
    }
    
     // update display values with backend stored values
    public void getSeedsInHouse(boolean playerTurn){
        
        if(playerTurn){ // player 2 turn
            seedsInHouse = this.player2Houses[house].getSeeds();
            this.player2Houses[house].setSeeds(0);
        } else { // player 1 turn
            seedsInHouse = this.player1Houses[house].getSeeds();
            this.player1Houses[house].setSeeds(0);
        }
        
    }
    
    public void spreadSeeds(boolean playerTurn){
        // spread seeds
        while(seedsInHouse > 0){
            house++;
            
            if(house == 6){ 
                addSeedToStore(playerTurn);
            } 
            else { 
                addSeedToHouse(playerTurn);
            }            
            
        }
    }
    
    public void addSeedToStore(boolean playerTurn){
        house = firstHouse;
                
        if(turn == playerTurn && seedsInHouse > 0){ 

            if(playerTurn){ // player 2
                this.player2Store.incrementSeeds();
            } else { // player 1 
                this.player1Store.incrementSeeds();
            }
            seedsInHouse--; 

            if(seedsInHouse == 0){ 
                house = emptyHouse;
            }
        }
        
        turn = !turn;
    }
    
    public void addSeedToHouse(boolean playerTurn){
        
        if(turn){ // player 2
            this.player2Houses[house].incrementSeeds();
        } else { // player 1
            this.player1Houses[house].incrementSeeds();
        }
        seedsInHouse--; 
        
    }
  
} 
