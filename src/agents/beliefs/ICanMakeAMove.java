/*
 * ICanMakeAMove.java
 *
 * Created on 18 Νοέμβριος 2007, 6:04 μμ
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

public class ICanMakeAMove extends Belief
{
    /**
    * Creates a new instance of ICanMakeAMove
    * @param reference to finite state machine
    */
    public ICanMakeAMove( FiniteStateMachine fsm, FiniteStateMachine.State myState )
    {
        super( fsm, myState );
    }
    
    
   /**
    * update the belief that I can make a move,
    * assuming this is always true!
    */
    public void update( )
    {
        // reset belief to default - "I don't believe it!"
        predicate = 0.0;
        // create a list to hold all potential actions
        // i.e. the current blank cells
        List< Action > blanks = new ArrayList< Action >( );
        // find the blank cells in the grid
        // use the Action class for convenience
        for( int i = 0; i < FiniteStateMachine.NUMBEROFROWS; i++ )
        {
            for( int j = 0; j < FiniteStateMachine.NUMBEROFCOLUMNS; j++ )
            {
                if( grid[ i ][ j ] == BLANKSTATE )
                {
                    blanks.add( new Action( i, j, 1.0, super.myState ) );
                }
            }
        }
        
        if(blanks.size( ) > 1){
            // generate a random number
            final int randomNumber = getRandomInRange( 1, blanks.size( ) );
            // get a blank cell at random
            action = blanks.get( randomNumber - 1 );

            predicate = 1.0; // now I believe it!!
        }
    }
    
    /**
    * get random number in range
    * @param lower bound of range
    * @param upper bound of range
    * @return random number in stated range
    */
    private int getRandomInRange( int lower, int higher )
    {
        return (int) ( Math.floor( Math.random( ) *
                        ( higher - lower - 1 ) ) + lower );
    }
}
