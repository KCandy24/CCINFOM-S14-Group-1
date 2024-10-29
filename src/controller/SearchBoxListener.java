package src.controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import src.model.AnimeSystem;
import src.view.gui.TopView;

/**
 * 
 */
public class SearchBoxListener implements DocumentListener {
    AnimeSystem as;
    TopView tv;

    /**
     * TODO: Modularize so we aren't restricted to searching through titles
     * @param as
     * @param tv
     */
    public SearchBoxListener(AnimeSystem as, TopView tv) {
        this.as = as;
        this.tv = tv;
        this.tv.setSearchBoxResults(as.searchTitles(""));
    }

    /**
     * TODO: Modularize so we aren't restricted to updating a specific list
     * @param e
     */
    public void update(DocumentEvent e) {
        String text;
        try {
            text = e.getDocument().getText(0, e.getDocument().getLength());
            this.tv.setSearchBoxResults(as.searchTitles(text));
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * TODO: Make a simplified DocumentListener interface that does the below
     * for us
     */

    @Override
    public void insertUpdate(DocumentEvent e) {
        update(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        update(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        update(e);
    }
}
