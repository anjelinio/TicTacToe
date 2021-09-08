/*
 * SillyComputerPlayer.java
 *
 * Created on 18 Νοέμβριος 2007, 4:59 μμ
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
 *
 * @author anjelinio
 */
public class SillyComputerPlayer implements IPlayer {
    
    private FiniteStateMachine.State stateType;
    
    /** Creates a new instance of SillyComputerPlayer */
    public SillyComputerPlayer(FiniteStateMachine.State stateType) {
        this.stateType = stateType;
    }
    
    public Action doMove(FiniteStateMachine currentBoard){
        // okay, first things first. What moves can I do at this state ?
        List<Point> possibleMoves = FiniteStateMachineExtensions.getBlankCells(currentBoard);
        
        // now what i need to do, is check these moves out, one by one:
        
        // for each possibleMove, i need to rank it, through the Goals class, to the all winning boards.
        // I'm planning to sum up those numbers later, to get an average feel of how good that move actually is.
        // Right now, I'm only taking the best ranking under consideration, so at the end of this process, I'm 
        // left with a sorted list of all my possible moves, from best to worse ;]
        SortedMap<Double, Point> sortedMoves = new TreeMap<Double, Point>();
        for(int i=0; i<possibleMoves.size(); i++){
            // rank it ... 
            Point move = possibleMoves.get(i);
            Double ranking = rankMove(move, currentBoard);
            // and add it ...
            sortedMoves.put(ranking, move);
        }       
        
        double predicate = sortedMoves.firstKey().doubleValue();
        Point move = sortedMoves.get(sortedMoves.firstKey());
        
        return new Action(move.x, move.y, stateType);
    }
    
    protected double rankMove(Point moveToMake, FiniteStateMachine currentState){
        // hmmm .. okay. .Let's make our move on that state thing ... 
        currentState.updateCell(moveToMake.x, moveToMake.y, this.stateType);
        
        // now because I was lazy ... i didn't bother mak wining boards in the Goals class
        // for both noughts and crosses. So If I'm a noughts player, i'll 'inverse' this
        // current board before ranking, therfore immitating a 'crosses' player ... if that
        // all makes sence ?
        FiniteStateMachine evalAgainst = (FiniteStateMachine.State.cross.equals(this.stateType))? 
                                           currentState : FiniteStateMachineExtensions.inverse(currentState);
        
        // I need to get it ranked, through the Goals little helper class
        SortedMap<Double, FiniteStateMachine> rankings = Goals.getBestRanksFor(evalAgainst, 0.0, FiniteStateMachine.State.cross);
        
        if(rankings.size()>0)
           return rankings.firstKey().doubleValue(); 
        else
            return 0.0;
    }
    
    public double getScore(){
        return 0.0;
    }
}
