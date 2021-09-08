/*
 * ICanBlockAWin.java
 *
 * Created on 18 Νοέμβριος 2007, 7:26 μμ
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

import java.util.List;
import java.util.ArrayList;

import java.awt.Point;

public class ICanBlockAWin extends ICanWin {
    
    /** Creates a new instance of ICanBlockAWin */
    public ICanBlockAWin(FiniteStateMachine fsm, FiniteStateMachine.State myState )
    {
        super( fsm, myState );
    }
    
    public void update( ){
        // if i can find two 'adjacent' - in tic tactoe terms - that are
        // followed by a blank, I'm okay !

        FiniteStateMachine.State lookingFor = (myState.equals(super.CROSSSTATE))? super.NOUGHTSTATE : super.CROSSSTATE;
        ArrayList<Point> adjacentPoints = new ArrayList<Point>();
        
        predicate = 0.0;
        
        int moveX = 0;
        int moveY = 0;
        
        for(int i = 0 ; i < FiniteStateMachine.NUMBEROFROWS && predicate<1.0; i++ )
        {
            for(int j = 0 ; j < FiniteStateMachine.NUMBEROFCOLUMNS && predicate<1.0; j++ )
            {
                FiniteStateMachine.State currState = super.grid[i][j];
                Point newPoint = new Point(i, j);
                if(lookingFor.equals(currState)){
                    // okay, now, I'm on one of three states here ... 
                    // 1. I'm looking for the first cell that matches myState => adjacentFound = 0;
                    if(adjacentPoints.size()==0){
                        // found my first one !!!
                        adjacentPoints.add(new Point(i, j));
                    }
                    else if (adjacentPoints.size()==1){                        
                        // is it a match, position-wise ?
                        if(isAdjacent(adjacentPoints, newPoint)){
                            // found my second one !!!
                            adjacentPoints.add(newPoint);
                            // change the state I'm looking for ...
                            lookingFor = FiniteStateMachine.State.blank;
                            
                            // now, depending on how defensive i wanna be, i could leave this
                            // be 0.66 .. now i won't react, until it is really necessary ...
                            // predicate = 0.66;
                        }
                    }
                    else if (adjacentPoints.size()==2){
                        if(isAdjacent(adjacentPoints, newPoint)){
                            predicate = 1.0;
                            
                            moveX = i;
                            moveY = j;
                            
                            break;
                        }
                    }
                }
            }
        }
        
        action = new Action(moveX, moveY, myState);
    } 
}
