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
public class Store extends Building {
    
    public Store(int numOfSeeds) {
        super(numOfSeeds);
    }

    public void add(int numToAdd) {
        this.seeds += numToAdd;
    }
    
}
