package src.controller;

import java.awt.event.*;

public class RecordAListener implements ActionListener {

    Controller control;

    public RecordAListener(Controller controller){
        control = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Anime Record")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else if (e.getActionCommand().equals("User Record")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else if (e.getActionCommand().equals("Studio Record")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else if (e.getActionCommand().equals("Prod Staff Record")) {
            //Data = control.model.getData
            //control.gui.displayStuff
        }
        else
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
