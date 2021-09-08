/*
 * ICanWinSmart.java
 *
 * Created on 19 Νοέμβριος 2007, 10:42 μμ
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

import java.util.SortedMap;
import java.util.TreeMap;

import java.util.Comparator;

import java.awt.Point;

public class ICanWinSmart extends Belief {
    
    /** Creates a new instance of ICanWinSmart */
    public ICanWinSmart(FiniteStateMachine fsm, FiniteStateMachine.State myState )
    {
        super( fsm, myState );
    }
    
    public void update( ){ 
        // okay, first things first. What moves can I do at this state ?
        List<Point> possibleMoves = FiniteStateMachineExtensions.getBlankCells(super.state);
        
        // now what i need to do, is check these moves out, one by one:
        
        // for each possibleMove, i need to rank it, through the Goals class, to the all winning boards.
        // I'm planning to sum up those numbers later, to get an average feel of how good that move actually is.
        // Right now, I'm only taking the best ranking under consideration, so at the end of this process, I'm 
        // left with a sorted list of all my possible moves, from best to worse ;]
        SortedMap<Double, Point> sortedMoves = new TreeMap<Double, Point>(
                        // this is a 'reverse' Double comparator, i.e. larger to smaller
                        new Comparator<Double>() {
                            public int compare(Double d1, Double d2){
                                return - d1.compareTo(d2);
                            }
                        });
                        
        for(int i=0; i<possibleMoves.size(); i++){
            // rank it ... 
            Point move = possibleMoves.get(i);
            Double ranking = rankMove(move, super.state.clone());
            // and add it ...
            sortedMoves.put(ranking, move);
        }  
        
        // check !
        if(0==sortedMoves.size()){
            predicate = 0.0;
            action = new Action(0,0, myState);
            
            return;
        }
        
        predicate = sortedMoves.firstKey().doubleValue();
        Point move = sortedMoves.get(sortedMoves.firstKey());
        
        action = new Action(move.x, move.y, predicate, myState);
    } 
    
    protected double rankMove(Point moveToMake, FiniteStateMachine currentState){
        // hmmm .. okay. .Let's make our move on that state thing ... 
        currentState.updateCell(moveToMake.x, moveToMake.y, super.myState);
        
        // now because I was lazy ... i didn't bother mak wining boards in the Goals class
        // for both noughts and crosses. So If I'm a noughts player, i'll 'inverse' this
        // current board before ranking, therfore immitating a 'crosses' player ... if that
        // all makes sence ?
        FiniteStateMachine evalAgainst = (FiniteStateMachine.State.cross.equals(super.myState))? 
                                           currentState : FiniteStateMachineExtensions.inverse(currentState);
        
        // I need to get it ranked, through the Goals little helper class
        SortedMap<Double, FiniteStateMachine> rankings = Goals.getBestRanksFor(evalAgainst, 0.0, FiniteStateMachine.State.cross);
        
        if(rankings.size()>0)
           return rankings.firstKey().doubleValue(); 
        else
            return 0.0;
    }
}
