package src.view.gui;

import java.awt.*;

import javax.swing.*;

import src.controller.TitlesSearchBoxListener;
import src.view.widget.WidgetFactory;

public class TopView {
    private JFrame frame;
    private SearchDemo searchDemo;

    private JTabbedPane tabs;

    private Tab recordsTab;
    private Subtab animeRecords;
    private Subtab userRecords;
    private Subtab staffRecords;
    private Subtab studioRecords;

    private Tab transactionsTab;
    private Subtab watchEpisode;
    private Subtab rateAnime;
    private Subtab editCredits;
    private Subtab followUser;

    private Tab reportsTab;
    private Subtab highestRatedAnime;
    private Subtab topStudios;
    private Subtab userProfile;
    private Subtab recommendAnime;

    /**
     * Initialize the top view.
     */
    public TopView() {
        frame = WidgetFactory.createJFrame("CCINFOM MCO GUI Prototype");
        frame.setLayout(new BorderLayout());
        frame.setBounds(100, 100, 1000, 500);

        instantiateWidgets();
        placeWidgets();
        frame.setVisible(true);
    }

    public void instantiateWidgets() {
        tabs = WidgetFactory.createJTabbedPane();

        // TODO: deprecate
        searchDemo = new SearchDemo();
        tabs.add("Demo", searchDemo);

        // Records
        recordsTab = new Tab("Records");
        animeRecords = new Subtab("Anime", "records/anime.json");
        userRecords = new Subtab("User", "records/user.json");
        staffRecords = new Subtab("Studio", "records/studio.json");
        studioRecords = new Subtab("Staff", "records/staff.json");
        recordsTab.addSubtab(animeRecords);
        recordsTab.addSubtab(userRecords);
        recordsTab.addSubtab(staffRecords);
        recordsTab.addSubtab(studioRecords);
        WidgetFactory.addTab(tabs, recordsTab);

        // Transactions
        transactionsTab = new Tab("Transactions");
        watchEpisode = new Subtab("Anime", "transactions/watch_episode.json");
        rateAnime = new Subtab("User", "transactions/rate_anime.json");
        editCredits = new Subtab("Studio", "transactions/edit_credits.json");
        followUser = new Subtab("Staff", "transactions/follow_user.json");
        transactionsTab.addSubtab(watchEpisode);
        transactionsTab.addSubtab(rateAnime);
        transactionsTab.addSubtab(editCredits);
        transactionsTab.addSubtab(followUser);
        WidgetFactory.addTab(tabs, transactionsTab);

        // Reports
        reportsTab = new Tab("Reports");
        highestRatedAnime = new Subtab("Anime",
                "reports/highest_rated_anime.json");
        topStudios = new Subtab("User", "reports/recommend_anime.json");
        userProfile = new Subtab("Studio", "reports/top_studios.json");
        recommendAnime = new Subtab("Staff", "reports/user_profile.json");
        reportsTab.addSubtab(highestRatedAnime);
        reportsTab.addSubtab(topStudios);
        reportsTab.addSubtab(userProfile);
        reportsTab.addSubtab(recommendAnime);
        WidgetFactory.addTab(tabs, reportsTab);
    }

    public void placeWidgets() {
        this.frame.add(tabs, BorderLayout.CENTER);
    }

    // TODO: Set listeners to everything

    // Search Demo
    public void setSearchBoxListener(
            TitlesSearchBoxListener searchBoxListener) {
        this.searchDemo.setListener(searchBoxListener);
    }

    public void setSearchBoxResults(String[] results) {
        this.searchDemo.setListData(results);
    }

}
