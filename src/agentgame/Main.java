/*
 * Main.java
 *
 * Created on 16 Νοέμβριος 2007, 10:44 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package agentgame;

import agentgame.gui.*;
import agentgame.logic.*;
import agents.*;

/**
 *
 * @author anjelinio
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws java.io.IOException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeForm().setVisible(true);
            }
        });
        
        /*Facade game = new Facade(new ComputerPlayer(FiniteStateMachine.State.cross), new SmarterComputerPlayer(FiniteStateMachine.State.nought));
        game.begin();
        
        System.in.read();*/
    }
    
}
