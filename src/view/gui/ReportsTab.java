package src.view.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import src.view.widget.TitledTab;
import src.view.widget.WidgetFactory;

public class ReportsTab extends JPanel {
    private JTabbedPane tabs;

    private JPanel highestRatedAnimeTab;
    private JPanel topStudiosTab;
    private JPanel userProfileTab;
    private JPanel recommendAnimeTab;

    public ReportsTab() {
        this.setLayout(new BorderLayout());
        this.instantiateWidgets();
        this.placeWidgets();
    }

    public void instantiateWidgets() {
        highestRatedAnimeTab = new Subtab("reports/highest_rated_anime.json");
        topStudiosTab = new Subtab("reports/top_studios.json");
        userProfileTab = new Subtab("reports/user_profile.json");
        recommendAnimeTab = new Subtab("reports/recommend_anime.json");
        ArrayList<TitledTab> titledTabs = new ArrayList<>();
        Collections.addAll(
                titledTabs,
                new TitledTab("Highest rated anime", highestRatedAnimeTab),
                new TitledTab("Top studios", topStudiosTab),
                new TitledTab("User profile", userProfileTab),
                new TitledTab("Recommend anime", recommendAnimeTab));
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
