/*
 * SmarterComputerPlayer.java
 *
 * Created on 19 Νοέμβριος 2007, 10:41 μμ
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
public class SmarterComputerPlayer extends AbstractPlayer {
    
    private Belief[] beliefs = null;
    
    /** Creates a new instance of SmarterComputerPlayer */
    public SmarterComputerPlayer(FiniteStateMachine.State stateType) {
        super(stateType);         
    }
    
    public Action doMove(FiniteStateMachine currentBoard){
        // init my beliefs ... 
        beliefs = new Belief[] { new ICanWinSmart(currentBoard, stateType), new ICanBlockAWinSmart(currentBoard, stateType), new ICanMakeAMove(currentBoard, stateType)};
        for(int i=0; i<beliefs.length; i++){
            beliefs[i].update();
        }
        
        double iCanWin = beliefs[0].isTrue();
        double iCanBlockAWin = beliefs[1].isTrue();
        double iCanMove = beliefs[2].isTrue();
        
        System.out.println("ICanWinSmart: " + iCanWin + " ICanBlockSmart: " + iCanBlockAWin + " ICanMove: " + iCanMove);
        
        
        // if i'm ambivalent ... 0.6 is a certain defeat in the next move ! 
        if(iCanWin == iCanBlockAWin){
            if(iCanBlockAWin > 0.6 ){
                score = beliefs[0].isTrue();
                return beliefs[0].getAction();
            }
            else {
                score = beliefs[1].isTrue();
                return beliefs[1].getAction();
            }
        }
        
        if(iCanWin>iCanBlockAWin){
            score = beliefs[0].isTrue();
            return beliefs[0].getAction();
        }

        if(iCanBlockAWin>iCanWin){
            score = beliefs[1].isTrue();
            return beliefs[1].getAction();
        }
        
        score = beliefs[2].isTrue();
        return beliefs[2].getAction();
    }
    
}
