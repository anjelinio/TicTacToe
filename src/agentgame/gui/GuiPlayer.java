/*
 * GuiPlayer.java
 *
 * Created on 25 Νοέμβριος 2007, 12:15 πμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package agentgame.gui;

import agents.AbstractPlayer;

import agentgame.logic.FiniteStateMachine;
import agentgame.logic.Action;


/**
 *
 * @author anjelinio
 */
public class GuiPlayer extends AbstractPlayer implements ILabelClickListener {
    
    /** Creates a new instance of GuiPlayer 
     *
     *  I need the uiInstance, to register for the event listeners of the labels in
     *  the gui. Somehow, I need to start listening for a click when my doMove() gets
     *  called, so I can have the player actually pick a move by clicking on a cell.
     *
     *  The hard part will be to block, and wait for that click actually, I got around that
     *  with the players selection panel, where i had to wait for the user to select the types
     *  pf players before continuing to start a new game. There, I just continued and started 
     *  the game in the event handler for the selectionMade event of the dialog. 
     *
     *  But here, perhaps I'm not so lucky. I need to return the value for doMove(), so I have to
     *  block the current thread and wait for the click, before continuing on the same method ! :(
     */
    
    TicTacToeForm ui = null;
    
    public GuiPlayer(TicTacToeForm uiInstance, FiniteStateMachine.State playerState) {
        super(playerState);
        // get the reference to the parent form ... 
        ui = uiInstance;
        // and that's it. I'll register and unregister the event handlers as needed, when my
        // doMove gets called. 
    }
    
    private boolean listening = false;
    /**
     *  Start / stop listening for clicks ... 
     */
    protected boolean listen(boolean listening){        
        if(listening){
            ui.addSelectionListener(this);
        }else {
            ui.removeSelectionListener(this);
        }
        
        return listening;
    }
    
    private Action currentAction = null;
    
    private java.lang.Thread callingThread = null;
    
    public Action doMove(FiniteStateMachine board){
        // okay, start listening ... 
        listen(true);
        
        javax.swing.JOptionPane.showMessageDialog(ui, "Make a move");
        callingThread = java.lang.Thread.currentThread();
        // somehow block ... 
        /*try {
            wait();
        }catch(java.lang.InterruptedException e_int){
            // swallow ... for now
            javax.swing.JOptionPane.showMessageDialog(ui, "interrupted");
        }*/
        currentAction = null;
        
        while(currentAction==null){            
            try {
                callingThread.sleep(1000);
            }catch(java.lang.InterruptedException e_int){
                // swallow ... for now
            }
        }
        
        // stop listening .. 
        listen(false);
        
        return currentAction;
    }
    
    public void selectionMade(int row, int column){
        currentAction = new Action(row, column, super.stateType);
    }
    
}
