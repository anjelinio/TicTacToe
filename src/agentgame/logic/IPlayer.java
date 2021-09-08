/*
 * IPlayer.java
 *
 * Created on 17 Νοέμβριος 2007, 1:23 πμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package agentgame.logic;

/**
 *
 * @author anjelinio
 */
public interface IPlayer {
  
    /*
     *  Computes how close the player is
     *  at completing a match
     */
    public double getScore();
    
    /*
     *  Returns the action the player wishes to take
     *  given the state of the board
     */
    public Action doMove(FiniteStateMachine state);
}
