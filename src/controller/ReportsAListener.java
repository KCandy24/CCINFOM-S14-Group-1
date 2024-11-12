package src.controller;

import java.awt.event.*;

public class ReportsAListener implements ActionListener {

    Controller control;

    public ReportsAListener(Controller controller){
        control = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Highest rated anime")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else if (e.getActionCommand().equals("Top Studios")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else if (e.getActionCommand().equals("User Profile")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else if (e.getActionCommand().equals("Recommend an anime")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
