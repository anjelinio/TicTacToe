/*
 * ComputerPlayer.java
 *
 * Created on 18 Νοέμβριος 2007, 8:00 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package agents;


import agentgame.logic.*;
import agents.beliefs.*;

import java.util.List;
import java.awt.Point;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author anjelinio
 */
public class ComputerPlayer extends AbstractPlayer {
    
    private Belief[] beliefs = null; 
    
    /** Creates a new instance of ComputerPlayer */
    public ComputerPlayer(FiniteStateMachine.State stateType) {
        super(stateType);        
    }
    
    public Action doMove(FiniteStateMachine currentBoard){
        // init my beliefs ... 
        beliefs = new Belief[] { new ICanWin(currentBoard, stateType), new ICanBlockAWin(currentBoard, stateType), new ICanMakeAMove(currentBoard, stateType)};
        for(int i=0; i<beliefs.length; i++){
            beliefs[i].update();
        }
        
        double iCanWin = beliefs[0].isTrue();
        double iCanBlockAWin = beliefs[1].isTrue();
        double iCanMove = beliefs[2].isTrue();
        
        System.out.println("ICanWin: " + iCanWin + " ICanBlock: " + iCanBlockAWin + " ICanMove: " + iCanMove);
        
        // ok, if I can win ... go for it ... 
        if(iCanWin >= iCanBlockAWin && iCanWin > 0.0){
            score = beliefs[0].isTrue();
            return beliefs[0].getAction();
        }
       else if (iCanBlockAWin >= iCanMove){
            score = beliefs[1].isTrue();
            return beliefs[1].getAction();
        }
        
        score = beliefs[2].isTrue();
        return beliefs[2].getAction();
    }
}
