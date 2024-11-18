package src.controller;

import javax.swing.event.DocumentEvent;

import src.model.AnimeSystem;
import src.view.gui.TopView;

/**
 * 
 */
public abstract class SearchBoxListener extends SimpleDocumentListener {
    AnimeSystem animeSystem;
    TopView topView;

    /**
     * @param animeSystem
     * @param topView
     */
    public SearchBoxListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
    }

    /**
     * This method is called every time the search field is updated.
     */
    public abstract void update(DocumentEvent e);
}
