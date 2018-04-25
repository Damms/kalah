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
public class Building {
    int seeds;
    
    public Building(int numOfSeeds){
        this.seeds = numOfSeeds;
    }
    
    public int getSeeds(){
       return this.seeds;
    }
    
    public void incrementSeeds(){
       this.seeds++;
    }
    
}
