/*
 * ICanBlockAWinSmart.java
 *
 * Created on 19 Νοέμβριος 2007, 10:56 μμ
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
public class ICanBlockAWinSmart extends ICanWinSmart {
    
    /** Creates a new instance of ICanBlockAWinSmart */
    public ICanBlockAWinSmart(FiniteStateMachine fsm, FiniteStateMachine.State myState )
    {
        super(fsm, myState);
        // now 'inverse' myState ... therefore ... i'm the opponent ;]
        super.myState = (FiniteStateMachine.State.cross.equals(myState))? FiniteStateMachine.State.nought
                                                                  : FiniteStateMachine.State.nought.equals(myState)? FiniteStateMachine.State.cross
                                                                                                                   : FiniteStateMachine.State.blank;
    }
    
}
