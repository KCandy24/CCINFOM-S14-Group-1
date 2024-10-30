package src.controller;

import javax.swing.event.DocumentEvent;

import src.model.AnimeSystem;
import src.view.gui.TopView;

/**
 * 
 */
public abstract class SearchBoxListener extends SimpleDocumentListener {
    AnimeSystem as;
    TopView tv;

    /**
     * @param as
     * @param tv
     */
    public SearchBoxListener(AnimeSystem as, TopView tv) {
        this.as = as;
        this.tv = tv;
        initialize();
    }

    /**
     * This method is called once on initialization.
     */
    public abstract void initialize();

    public abstract void update(DocumentEvent e);
}
