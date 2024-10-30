package src.controller;

import src.model.AnimeSystem;
import src.view.gui.TopView;

/**
 * The controller communicates between the model and the view.
 */
public class Controller {
    // Declare listeners
    TitlesSearchBoxListener titlesSearchBoxListener;

    /**
     * Initializes the listeners to listen to the view.
     */
    public Controller(AnimeSystem as, TopView tv) {
        // Initialize listeners
        titlesSearchBoxListener = new TitlesSearchBoxListener(as, tv);

        // Set listeners in topView
        tv.setSearchBoxListener(titlesSearchBoxListener);
    }
}
