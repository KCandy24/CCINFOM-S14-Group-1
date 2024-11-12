package src.controller;

import java.awt.event.*;
import src.model.AnimeSystem;
import src.view.gui.TopView;
/**
 * The controller communicates between the model and the view.
 */
public class Controller {
    // Declare listeners
    AnimeSystem animeModel;
    TopView animeGUI;
    TitlesSearchBoxListener titlesSearchBoxListener;

    //TODO: Integrate in GUI
    ActionListener recordListener;
    ActionListener transactionListener;
    ActionListener reportListener;
    
    /**
     * Initializes the listeners to listen to the view.
     */
    public Controller(AnimeSystem animeSystem, TopView topView) {
        animeModel = animeSystem;
        animeGUI = topView;
        // Initialize listeners
        titlesSearchBoxListener = new TitlesSearchBoxListener(animeModel, animeGUI);

        recordListener = new RecordAListener(this);
        transactionListener = new TransactionAListener(this);
        reportListener = new ReportsAListener(this);
        // Set listeners in topView
        animeGUI.setSearchBoxListener(titlesSearchBoxListener);
    }
}
