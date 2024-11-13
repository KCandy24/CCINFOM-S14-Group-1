package src.controller;

import java.awt.event.*;

public class RecordAListener implements ActionListener {

    Controller control;

    public RecordAListener(Controller controller){
        control = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Check":
                break;
            case "Save":
                break;
            case "Delete":
                break;
            default:
                throw new UnsupportedOperationException(
                        "Unimplemented method 'actionPerformed'");
        }
    }
    
}
