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
public class House extends Building {
    
    public House(int numOfSeeds) {
        super(numOfSeeds);
    }
        
    public void setSeeds(int numOfSeeds){
       this.seeds = numOfSeeds;
    }
    
}
