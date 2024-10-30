package src.view.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import src.controller.TitlesSearchBoxListener;
import src.view.widget.SearchBox;
import src.view.widget.WidgetFactory;
import src.view.widget.WidgetFactory.Fonts;

public class SearchDemo extends JPanel {
    private SearchBox searchBox;

    public SearchDemo() {
        WidgetFactory.styleComponent(this);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        this.instantiateWidgets();
        this.placeWidgets();
    }

    public void instantiateWidgets() {
        searchBox = WidgetFactory.createSearchBox();
    }

    public void placeWidgets() {
        JPanel hero = WidgetFactory.createJPanel();
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.add(WidgetFactory.createJLabel("Search Demo", Fonts.TITLE));
        hero.add(Box.createVerticalStrut(16));
        hero.add(WidgetFactory.createJLabel("Search"));
        this.add(hero, BorderLayout.NORTH);
        this.add(searchBox, BorderLayout.CENTER);
    }

    public void setListener(TitlesSearchBoxListener l) {
        searchBox.setListener(l);
    }

    public void setListData(String[] results) {
        this.searchBox.setListData(results);
    }
}
