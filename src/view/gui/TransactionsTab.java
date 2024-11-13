package src.view.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import src.view.widget.TitledTab;
import src.view.widget.WidgetFactory;

public class TransactionsTab extends JPanel {
    private JTabbedPane tabs;

    // TODO: Implement these tabs
    private JPanel watchNextTab;
    private JPanel rateAnimeTab = WidgetFactory.createJPanel();
    private JPanel addEditAnimeTab = WidgetFactory.createJPanel();
    private JPanel followUserTab = WidgetFactory.createJPanel();

    public TransactionsTab() {
        this.setLayout(new BorderLayout());
        this.instantiateWidgets();
        this.placeWidgets();
    }

    public void instantiateWidgets() {
        watchNextTab = new Subtab("src/view/gui/WatchNextEpisode.json");
        ArrayList<TitledTab> titledTabs = new ArrayList<>();
        Collections.addAll(
                titledTabs,
                new TitledTab("Watch next episode", watchNextTab),
                new TitledTab("Rate an anime", rateAnimeTab),
                new TitledTab("Add/edit an anime", addEditAnimeTab),
                new TitledTab("Follow user", followUserTab));
        tabs = WidgetFactory.createJTabbedPane(titledTabs);
        tabs.setTabPlacement(JTabbedPane.LEFT);
        tabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    }

    public void placeWidgets() {
        this.add(tabs, BorderLayout.CENTER);
    }

    // TODO
    public void setListeners() {

    }
}
