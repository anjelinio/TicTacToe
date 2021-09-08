/*
 * Facade.java
 *
 * Created on 17 Νοέμβριος 2007, 1:12 πμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package agentgame.logic;

import java.util.Vector;
import java.util.SortedMap;

/**
 *
 * @author anjelinio
 */
public class Facade {
    
    //<editor-fold desc="event listener interface & methods">
    /**
     *  A simple event listener interface, to listen for changes
     *  in the state of the board, as players make their moves
     */
    public interface IStateChangedListener {
        
        public void stateChanged(FiniteStateMachine currentBoard);
    }
    
    private Vector eventListeners = new Vector();
    
    public void addSelectionListener(IStateChangedListener listener){
        eventListeners.add(listener);
    }
    
    public void removeSelectionListener(IStateChangedListener listener){
        eventListeners.remove(listener);
    }
    
    private void notifyListeners(FiniteStateMachine board){
        java.util.Iterator<Object> listeners = eventListeners.iterator();        
        while(listeners.hasNext()){
            ((IStateChangedListener)listeners.next()).stateChanged(board);
        }
    }
    
    //</editor-fold>
    
    private IPlayer player1 = null;
    
    private IPlayer player2 = null;
    
    private FiniteStateMachine board = new FiniteStateMachine();
    
    /** 
     * Creates a new instance of Facade 
     */
    public Facade(IPlayer player1, IPlayer player2) {
        setPlayer1(player1);
        setPlayer2(player2);
    }
    
    public synchronized void dispose(){
        disposed = true;
    }
    
    private boolean disposed = false;
    
    int i = 0;
    int d = 0;
    
    public boolean disposed(){
        return disposed;
    }
    
    public boolean won(){
        SortedMap<Double, FiniteStateMachine> crossScore = Goals.getBestRanksFor(board, 1.0, FiniteStateMachine.State.cross);
        SortedMap<Double, FiniteStateMachine> noughtScore = Goals.getBestRanksFor(FiniteStateMachineExtensions.inverse(board), 1.0, FiniteStateMachine.State.nought);
        
        return (crossScore.size() > 0 || noughtScore.size() > 0);
    } 
    
    public boolean draw(){
        return (d++) >= 9;
    }
    
    public void begin(){
        IPlayer[] players = new IPlayer[] { getPlayer1(), getPlayer2() };
        IPlayer currentPlayer = null;
        
        // do a first update, to clear the board or something .. 
        this.notifyListeners(board);

        int playerIndex = 1;
        // actually, while none of my rules fire ...
        while (!this.won() && !this.draw() && !disposed()) {  
            // get a move from a player and update the board
            currentPlayer = players[playerIndex-1];                
            // get the move ... 
            Action move = currentPlayer.doMove(board);
            // update the board ...
            
            System.out.println("Player [" + currentPlayer.getClass().getSimpleName() + " ] " + playerIndex + " made move [ " + move.x + ", " + move.y + " ] ranking: " + move.rank);
 
            if(3<=move.x || 3<=move.y){
                System.out.println("INVALID - RETRY :Player [" + currentPlayer.getClass().getSimpleName() + " ] " + playerIndex + " made move [ " + move.x + ", " + move.y + " ] ranking: " + move.rank);
                move = currentPlayer.doMove(board);
            }
            // i'm doind a little trick here ... I've rigged the playerIndex value
            // to return 1 or 2 ...which cast to X & O in CellState. So I'm controlling
            // the index in the players array and the update value for the board using this
            // single variable
            board.updateCell(move.x, move.y, FiniteStateMachine.parseState(playerIndex));
            
            // fire the notifyListeners event ... 
            notifyListeners(board);
 
            // alternate the index, before i forget :P
            playerIndex = (playerIndex > 1) ? 1 : 2;
        }
        
        if(won()){
            javax.swing.JOptionPane.showMessageDialog(null, "We have a winner !!!");
        }
        else if(draw()){
            javax.swing.JOptionPane.showMessageDialog(null, "A draw .. better luck next time");
        }
    }
    

    public IPlayer getPlayer1() {
        return player1;
    }

    public void setPlayer1(IPlayer player1) {
        this.player1 = player1;
    }

    public IPlayer getPlayer2() {
        return player2;
    }

    public void setPlayer2(IPlayer player2) {
        this.player2 = player2;
    }
    
}
