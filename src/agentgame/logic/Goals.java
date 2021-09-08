/*
 * Goals.java
 *
 * Created on 17 Νοέμβριος 2007, 4:59 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package agentgame.logic;

import java.util.ArrayList;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Comparator;

/**
 *
 * @author anjelinio
 */
public class Goals {
    
    // <editor-fold desc="winning boards" >
        static int[][] board0_horiz = new int[][] { 
             new int[]{1, 1, 1},
             new int[]{0, 0, 0},
             new int[]{0, 0, 0}
        };

        static int[][] board1_horiz = new int[][] { 
             new int[]{0, 0, 0},
             new int[]{1, 1, 1},
             new int[]{0, 0, 0}
        };

        static int[][] board2_horiz = new int[][] { 
             new int[]{0, 0, 0},
             new int[]{0, 0, 0},
             new int[]{1, 1, 1}
        };


        static int[][] board0_vert = new int[][] { 
             new int[]{1, 0, 0},
             new int[]{1, 0, 0},
             new int[]{1, 0, 0}
        };

        static int[][] board1_vert = new int[][] { 
             new int[]{0, 1, 0},
             new int[]{0, 1, 0},
             new int[]{0, 1, 0}
        };

        static int[][] board2_vert = new int[][] { 
             new int[]{0, 0, 1},
             new int[]{0, 0, 1},
             new int[]{0, 0, 1}
        };


        static int[][] board0_diag = new int[][] { 
             new int[]{1, 0, 0},
             new int[]{0, 1, 0},
             new int[]{0, 0, 1}
        };

        static int[][] board1_diag = new int[][] { 
             new int[]{0, 0, 1},
             new int[]{0, 1, 0},
             new int[]{1, 0, 0}
        };
        // </editor-fold>
        
    private static ArrayList<FiniteStateMachine> _desiredStates = null;
    
    protected static ArrayList<FiniteStateMachine> getDesiredStates(){
        if(null==_desiredStates){
            
            _desiredStates = new ArrayList<FiniteStateMachine>();
            
            _desiredStates.add(new FiniteStateMachine(board0_horiz));
            _desiredStates.add(new FiniteStateMachine(board1_horiz));
            _desiredStates.add(new FiniteStateMachine(board2_horiz));
            
            _desiredStates.add(new FiniteStateMachine(board0_vert));
            _desiredStates.add(new FiniteStateMachine(board1_vert));
            _desiredStates.add(new FiniteStateMachine(board2_vert));
            
            _desiredStates.add(new FiniteStateMachine(board0_diag));
            _desiredStates.add(new FiniteStateMachine(board1_diag));
        }
        
        return _desiredStates;
    }
    
    /** Creates a new instance of Goals */
    public Goals() {
    }
    
    /**
     *  goes through all the "desired states", and ranks the currentState against them. It returns
     *  all matches that are >= lowerBound similar, having matched on matchOn cell states, and blanks.
     */ 
    public static SortedMap<Double, FiniteStateMachine> getBestRanksFor(FiniteStateMachine currentState, double lowerBound, FiniteStateMachine.State matchOn){
        // oookay .. now here's the tough cookie. 
        // i need to go through all the wining boards, and rank them again the 
        // board i was given. Then, i must return at most the results whose rank is >= 
        // to what i was instructed to ...
        
        // create the "results" map ... 
        SortedMap<Double, FiniteStateMachine> retVal = new TreeMap<Double, FiniteStateMachine>(
                        // this is a 'reverse' Double comparator, i.e. larger to smaller
                        new Comparator<Double>() {
                            public int compare(Double d1, Double d2){
                                return - d1.compareTo(d2);
                            }
                        });
        // get a local reference of the desired states ... for debugging .. 
        ArrayList<FiniteStateMachine> desiredStates = getDesiredStates();
        // and now loop, to create the rankings ...
        for(int i=0; i<desiredStates.size(); i++){
            // compute my ranking ... 
            double key = new Double(FiniteStateMachineExtensions.similarityRank(desiredStates.get(i), currentState, FiniteStateMachine.State.cross));
            // if my similarity is below the treshold given, don't bother with this
            // one any more ... 
            if(lowerBound>key)
                continue;
            // some of these computations are bound to have the same result. 
            // which means that key may already be there. I'm incrementing it very slightly,
            // so that all the matching boards are properly included in the returning
            // list
            while(retVal.containsKey(key))
                key += 0.001;
            // okay, now write my 'safe' key into the map ... 
            retVal.put(key, desiredStates.get(i));
        }
        // and return the map. 
        return retVal;
    }    
}
