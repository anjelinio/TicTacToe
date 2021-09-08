/*
 * ICanWin.java
 *
 * Created on 18 Νοέμβριος 2007, 6:40 μμ
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

public class ICanWin extends Belief {
    
    /** Creates a new instance of ICanWin */
    public ICanWin(FiniteStateMachine fsm, FiniteStateMachine.State myState )
    {
        super( fsm, myState );
    }

    
    /*      0,0 1,1 2,2 => x:3   y:3
     *      0,2 1,1 2,0 => x:3  y:3
     *
     */
    
    protected boolean isAdjacent(List<Point> points, Point newPoint){
        // now ... for those points to 'adjacent' in a tic tac toe terms ..
        // they either all share the same row
        // or the same column
        // or ... their corresponding sums or row and column is equal to 3 :P 

        List<Point> searchList = new ArrayList<Point>();
        searchList.addAll(points);
        searchList.add(newPoint);
        
        // <editor-fold desc="do they share the same x dimension ?">
        boolean shareX = true;
        int x = -1;
        for(int i=0; i<searchList.size() && shareX; i++){
            Point toExamineX = searchList.get(i);
            // if this is my first run ... 
            if(x<0)
                x = toExamineX.x;
            
            shareX &= (x == toExamineX.x);
        }
        
        if(shareX)
            return true;
        // </editor-fold>
        
        // <editor-fold desc="do they share the same y dimension ?">
        boolean shareY = true;
        int y = -1;
        for(int c=0; c<searchList.size() && shareY; c++){
            Point toExamineY = searchList.get(c);
            // if this is my first run ... 
            if(y<0)
                y = toExamineY.y;
            
            shareY &= (y == toExamineY.y);
        }
        
        if(shareY)
            return true;
        // </editor-fold>
        
        // <editor-fold desc="x and y all sum up to three ?">
        int totalX = 0;
        int totalY = 0;
        for(int p=0; p<searchList.size(); p++){
            totalX += searchList.get(p).x;
            totalY += searchList.get(p).y;
        }
        
        if(totalX==3 && totalY==3)
            return true;
        
        // </editor-fold>
        
        return false;
    }
    
    public void update( ){
        // if i can find two 'adjacent' - in tic tactoe terms - that are
        // followed by a blank, I'm okay !

        FiniteStateMachine.State lookingFor = myState;
        ArrayList<Point> adjacentPoints = new ArrayList<Point>();
        
        predicate = 0.0;
        
        int moveX = 0;
        int moveY = 0;
        
        for(int i = 0; i < FiniteStateMachine.NUMBEROFROWS && predicate<1.0; i++ )
        {
            for(int j = 0; j < FiniteStateMachine.NUMBEROFCOLUMNS && predicate<1.0; j++ )
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
                            
                            predicate = 0.66;
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
