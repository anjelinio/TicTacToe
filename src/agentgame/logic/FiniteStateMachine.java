/*
 * FiniteStateMachine.java
 *
 * Created on 17 Νοέμβριος 2007, 1:13 πμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package agentgame.logic;

/**
 *
 * @author anjelinio
 */
public class FiniteStateMachine {
    
    /**
    * represents the three possible values of a cell
    */
    public enum State { blank, nought, cross };
    
    public static int parseInt(State state){
        switch(state){
            case cross:
                return 1;
            case nought:
                return 2;
            default:
                return 0;
        }
    }
    
    public static State parseState(int state){
        switch(state){
            case 1:
                return State.cross;
            case 2:
                return State.nought;
            default:
                return State.blank;
        }
    }
    
    public static final int NUMBEROFROWS = 3;
    public static final int NUMBEROFCOLUMNS = 3;
    /**
    * represents the state of cells of game grid
    */
    private State[][] grid;
    
    /** Creates a new instance of FiniteStateMachine */
    public FiniteStateMachine() {
        grid = new State[ NUMBEROFROWS ][ NUMBEROFCOLUMNS ];
        for( int i = 0; i < NUMBEROFROWS; i++ ) {
            for( int j = 0; j < NUMBEROFCOLUMNS; j++ ) {
                grid[ i ][ j ] = State.blank;
            }
        }
    }
    
    /** 
     *  Creates a new instance of FiniteStateMachine, 
     *  with an initial state as indicated by the initialState parameter
     */
    public FiniteStateMachine(int [][] initialState) {
        grid = new State[ NUMBEROFROWS ][ NUMBEROFCOLUMNS ];
        for( int i = 0; i < NUMBEROFROWS; i++ ) {
            for( int j = 0; j < NUMBEROFCOLUMNS; j++ ) {
                grid[ i ][ j ] = parseState(initialState[i][j]);
            }
        }
    }
    
    /**
    * update the state of a cell in the game grid
    * @param row number
    * @param column number
    * @param new cell state
    */
    public void updateCell( int row, int column, State state ) {
        grid[ row ][ column ] = state;
    }
    
    /**
    * get the state of a cell in the game grid
    * @param row number
    * @param column number
    * @teturn state of cell in game grid
    */
    public State getCellState( int row, int column ) {
        return grid[ row ][ column ];
    }
    
    /**
    * get the entire grid
    * @return reference to grid of states
    */
    public State[ ][ ] getGrid( )
    {
        return grid;
    }
    
    /**
     *  Makes a clone of this instance. For "data privacy" ...
     */
    public FiniteStateMachine clone(){
        FiniteStateMachine retVal = new FiniteStateMachine();
        
        for( int i = 0; i < NUMBEROFROWS; i++ ) {
            for( int j = 0; j < NUMBEROFCOLUMNS; j++ ) {
                retVal.updateCell(i,j, getCellState(i, j));
            }
        }
        
        return retVal;
    }
    
}
