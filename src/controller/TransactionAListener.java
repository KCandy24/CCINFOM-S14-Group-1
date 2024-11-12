package src.controller;

import java.awt.event.*;

public class TransactionAListener implements ActionListener {

    Controller control;

    public TransactionAListener(Controller controller){
        control = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Watch next episode")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else if (e.getActionCommand().equals("Rate an anime")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else if (e.getActionCommand().equals("Add/edit an anime")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else if (e.getActionCommand().equals("Follow user")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
