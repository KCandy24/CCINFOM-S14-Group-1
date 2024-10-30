package src.controller;

import javax.swing.event.DocumentEvent;

import src.model.AnimeSystem;
import src.view.gui.TopView;

public class TitlesSearchBoxListener extends SearchBoxListener {
    public TitlesSearchBoxListener(AnimeSystem as, TopView tv) {
        super(as, tv);
    }

    public void initialize() {
        this.tv.setSearchBoxResults(as.searchTitles(""));
    }

    public void update(DocumentEvent e) {
        String text;
        try {
            text = e.getDocument().getText(0, e.getDocument().getLength());
            this.tv.setSearchBoxResults(as.searchTitles(text));
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
