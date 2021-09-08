/*
 * Action.java
 *
 * Created on 18 Νοέμβριος 2007, 5:59 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package agentgame.logic;

/**
 *
 * @author anjelinio
 */
public class Action extends java.awt.Point {
    
    /** Creates a new instance of Action */
    public Action(int row, int column, FiniteStateMachine.State updateState) {
        super(row, column);
        
        this.updateState = updateState;
    }
    
    public Action(int row, int column, double rank, FiniteStateMachine.State updateState) {
        super(row, column);
        
        this.rank = rank;
        this.updateState = updateState;
    }
    
    public FiniteStateMachine.State updateState; 
    
    public double rank = 0.0;
}
