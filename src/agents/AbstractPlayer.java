/*
 * AbstractPlayer.java
 *
 * Created on 18 Νοέμβριος 2007, 5:48 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package agents;

import agentgame.logic.*;

import java.util.List;
import java.awt.Point;

import java.util.SortedMap;
import java.util.TreeMap; 

/**
 * @author anjelinio
 */
public abstract class AbstractPlayer implements IPlayer {
    
    protected FiniteStateMachine.State stateType;
    
    /** Creates a new instance of AbstractPlayer */
    public AbstractPlayer(FiniteStateMachine.State stateType) {
        this.stateType = stateType;
    }
    
    public abstract Action doMove(FiniteStateMachine currentBoard);
    
    protected double score = 0.0;
    
     public double getScore(){
        return score;
    }
}
