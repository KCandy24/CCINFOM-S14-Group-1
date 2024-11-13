package src.controller;

import java.awt.event.*;

public class ReportsAListener implements ActionListener {

    Controller control;

    public ReportsAListener(Controller controller){
        control = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Check":
                break;
            default:
                throw new UnsupportedOperationException(
                        "Unimplemented method 'actionPerformed'");
        }
    }
    
}
