package src.controller;

import java.awt.event.*;

import javax.swing.JComponent;

import src.model.AnimeSystem;
import src.view.gui.TopView;

public class ReportsTabListener implements ActionListener {

    AnimeSystem animeSystem;
    TopView topView;

    public ReportsTabListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JComponent) e.getSource()).getName();
        System.out.println("\nTransactions/?buttonName=" + name);

        switch (name) {
            default:
                System.err.println("No associated action for " + name);
                break;
        }
    }

}
