/*
 * Belief.java
 *
 * Created on 18 Νοέμβριος 2007, 5:56 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package agents.beliefs;

import agentgame.logic.*;

/**
 *
 * @author anjelinio
 */
public abstract class Belief
{
    /* some finals for convenience, to save typing */
    protected static final FiniteStateMachine.State BLANKSTATE = FiniteStateMachine.State.blank;
    protected static final FiniteStateMachine.State NOUGHTSTATE = FiniteStateMachine.State.nought;
    protected static final FiniteStateMachine.State CROSSSTATE = FiniteStateMachine.State.cross;
    
    /** boolean to hold truth of belief */
    protected double predicate;
    /** reference to enviroment i.e. the grid of cells */
    protected FiniteStateMachine.State[ ][ ] grid;
    /* and the actual FiniteStateMachine is also nice ... :) */
    protected FiniteStateMachine state = null;
    /** based on our beliefs, we determine the correct action */
    protected Action action;
    /** the state we're playgin with ... */
    protected FiniteStateMachine.State myState; 
    
    /** Creates a new instance of Belief */
    public Belief( FiniteStateMachine fsm, FiniteStateMachine.State myState )
    {
        predicate = 0.0; // I don't believe it!!
        grid = fsm.getGrid( );
        action = null; // the action is unknown right now
        this.myState = myState;
        this.state = fsm;
    }
    
    /**
    * is the belief true?
    * @return true if belief holds, false otherwise
    */
    public double isTrue( )
    {
        return predicate;
    }
    /**
    * update the belief
    */
    public abstract void update( );
    /**
    * get the action as determined from the belief
    * Note: this is null until the Beliefs are updated
    * @return action
    */
    public Action getAction( )
    {
        return action;
    }
}