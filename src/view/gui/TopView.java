package src.view.gui;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import src.controller.AnimeRecordsListener;
import src.controller.RecordTableListener;
import src.controller.StaffRecordsListener;
import src.controller.StudioRecordsListener;
import src.controller.UserRecordsListener;
import src.view.widget.RecordTable;
import src.view.widget.WidgetFactory;

public class TopView {
    private JFrame frame;

    private JTabbedPane tabs;

    private Tab recordsTab;
    private Subtab animeRecords;
    private Subtab userRecords;
    private Subtab staffRecords;
    private Subtab studioRecords;
    private final String ANIME_RECORD_SUBTAB = "Anime";
    private final String USER_RECORD_SUBTAB = "User";
    private final String STAFF_RECORD_SUBTAB = "Staff";
    private final String STUDIO_RECORD_SUBTAB = "Studio";

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

    private HashMap<String, RecordTable> recordTables;

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

        // TODO: The ff. initializations would definitely be better off in some data
        // TODO: structure that we iterate through.

        recordTables = new HashMap<>();
        recordTables.put("animes", new RecordTable());
        recordTables.put("users", new RecordTable());
        recordTables.put("staff", new RecordTable());
        recordTables.put("studios", new RecordTable());

        // Records
        recordsTab = new Tab("Records");
        animeRecords = new Subtab(ANIME_RECORD_SUBTAB, "records/anime.json");
        userRecords = new Subtab(USER_RECORD_SUBTAB, "records/user.json");
        staffRecords = new Subtab(STAFF_RECORD_SUBTAB, "records/staff.json");
        studioRecords = new Subtab(STUDIO_RECORD_SUBTAB, "records/studio.json");

        // Transactions
        transactionsTab = new Tab("Transactions");
        watchEpisode = new Subtab("Watch Episode", "transactions/watch_episode.json");
        rateAnime = new Subtab("Rate Anime", "transactions/rate_anime.json");
        editCredits = new Subtab("Edit Credits", "transactions/edit_credits.json");
        followUser = new Subtab("Follow User", "transactions/follow_user.json");


        // Reports
        reportsTab = new Tab("Reports");
        highestRatedAnime = new Subtab("Highest Rated Anime", "reports/highest_rated_anime.json");
        topStudios = new Subtab("Recommend Anime", "reports/recommend_anime.json");
        userProfile = new Subtab("Top Studios", "reports/top_studios.json");
        recommendAnime = new Subtab("User Profile", "reports/user_profile.json");

    }

    public void placeWidgets() {
        recordsTab.addSubtab(animeRecords);
        recordsTab.addSubtab(userRecords);
        recordsTab.addSubtab(staffRecords);
        recordsTab.addSubtab(studioRecords);
        WidgetFactory.addTab(tabs, recordsTab);

        transactionsTab.addSubtab(watchEpisode);
        transactionsTab.addSubtab(rateAnime);
        transactionsTab.addSubtab(editCredits);
        transactionsTab.addSubtab(followUser);
        WidgetFactory.addTab(tabs, transactionsTab);

        reportsTab.addSubtab(highestRatedAnime);
        reportsTab.addSubtab(topStudios);
        reportsTab.addSubtab(userProfile);
        reportsTab.addSubtab(recommendAnime);
        WidgetFactory.addTab(tabs, reportsTab);

        this.frame.add(tabs, BorderLayout.CENTER);
    }

    // TODO: Set listeners to everything

    // Records

    // Set ActionListeners for the Anime Records subtab.
    public void setAnimeRecordsListener(AnimeRecordsListener listener) {
        this.animeRecords.setActionListener("selectAnimeId", listener);
        this.animeRecords.setActionListener("save", listener);
        this.animeRecords.setActionListener("delete", listener);
    }

    // Set ActionListeners for the User Records subtab.
    public void setUserRecordsListener(UserRecordsListener listener) {
        this.userRecords.setActionListener("searchUser", listener);
        this.userRecords.setActionListener("save", listener);
        this.userRecords.setActionListener("delete", listener);
    }

    // Set ActionListeners for the StudioRecordsListener subtab.
    public void setStudioRecordsListener(StudioRecordsListener listener) {
        this.studioRecords.setActionListener("searchStudio", listener);
        this.studioRecords.setActionListener("save", listener);
        this.studioRecords.setActionListener("delete", listener);
    }

    // Set ActionListeners for the StudioRecordsListener subtab.
    public void setStaffRecordsListener(StaffRecordsListener listener) {
        this.staffRecords.setActionListener("searchStaff", listener);
        this.staffRecords.setActionListener("save", listener);
        this.staffRecords.setActionListener("delete", listener);
    }

    // Transactions

    // Reports

    // TODO: ^ Instead of defining a fifty functions for setting listeners, use a
    // data structure like a HashMap<Subtab, ArrayList<ActionListener>>
    // or something

    // Record Tables
    public void setRecordTableData(String recordName, String[][] data, String[] column) {
        recordTables.get(recordName).setTableData(data, column);
    }

    public void setRecordTableListener(String recordName, RecordTableListener listener) {
        recordTables.get(recordName).setListener(listener);
    }

    public void selectFromTable(String recordName) {
        recordTables.get(recordName).setVisible(true);
    }

    public int getSelected(String recordName) {
        return recordTables.get(recordName).getSelected();
    }

    public void setSubtabFieldFromData(String subtabName, HashMap<String, String> data) {
        // TODO: Use HashMap<String, Subtab> instead of switch case
        // ! TODO: Replace this thing with a HashMap.
        // ? this.getSubtab(name).setData(data); ?
        // or
        // ? this.getTab(name).getSubtab(name).setData(data); ?
        switch (subtabName) {
            // Records
            case ANIME_RECORD_SUBTAB:
                animeRecords.setData(data);
                break;
            case USER_RECORD_SUBTAB:
                userRecords.setData(data);
                break;
            case STAFF_RECORD_SUBTAB:
                staffRecords.setData(data);
                break;
            case STUDIO_RECORD_SUBTAB:
                studioRecords.setData(data);
                break;
            // TODO: Transactions

            // TODO: Reports

        }
    }

}
