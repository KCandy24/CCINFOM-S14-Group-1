package src.view.gui;

import java.awt.*;
import javax.swing.*;

import src.controller.SearchBoxListener;
import src.view.widget.SearchBox;
import src.view.widget.WidgetFactory;
import src.view.widget.WidgetFactory.Fonts;

public class TopView {
    private JFrame frame;
    private JButton frogButton;
    private SearchBox sampleSearchBox;

    /**
     * Initialize the top view.
     */
    public TopView() {
        this.frame = WidgetFactory.createJFrame("Animo Anime List");
        this.instantiateWidgets();
        this.placeWidgets();
        this.frame.setVisible(true);
    }

    /**
     * Instantiate widgets
     */
    public void instantiateWidgets() {
        frogButton = WidgetFactory.createJButton("Frog button");
        sampleSearchBox = WidgetFactory.createSearchBox();
    }

    /**
     * Place widgets onto the `frame`.
     */
    public void placeWidgets() {
        JPanel center = WidgetFactory.createJPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JPanel hero = WidgetFactory.createJPanel();
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBorder(BorderFactory.createEmptyBorder(16, 64, 16, 16));
        hero.add(WidgetFactory.createJLabel("Anime System", Fonts.TITLE));
        hero.add(WidgetFactory.createJLabel("CCINFOM S14 Group 1", Fonts.SUBTITLE));
        hero.add(WidgetFactory.createJLabel("10/29/2024: Check out this search box - Justin"));
        hero.add(frogButton);

        this.frame.add(hero, BorderLayout.NORTH);

        center.add(this.sampleSearchBox);

        this.frame.add(center, BorderLayout.CENTER);

        this.frame.add(WidgetFactory.createJLabel("South", Fonts.TITLE),
                BorderLayout.SOUTH);
        this.frame.add(WidgetFactory.createJLabel("East", Fonts.TITLE),
                BorderLayout.EAST);
        this.frame.add(WidgetFactory.createJLabel("West", Fonts.TITLE),
                BorderLayout.WEST);
    }

    // Search Box
    public void setSearchBoxListener(SearchBoxListener searchBoxListener) {
        this.sampleSearchBox.setListener(searchBoxListener);
    }

    public void setSearchBoxResults(String[] results) {
        this.sampleSearchBox.setListData(results);
    }
}
