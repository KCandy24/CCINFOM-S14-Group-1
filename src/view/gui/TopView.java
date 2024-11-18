package src.view.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.*;

import src.controller.AnimeRecordsListener;
import src.controller.RecordTableListener;
import src.controller.StaffRecordsListener;
import src.controller.StudioRecordsListener;
import src.controller.UserRecordsListener;
import src.model.Records;
import src.view.widget.RecordTable;
import src.view.widget.WidgetFactory;

public class TopView {
    private JFrame frame;
    private JTabbedPane tabs;

    private HashMap<String, Tab> mainTabs;
    private LinkedHashMap<Tab, ArrayList<Subtab>> tabMap; // * LinkedHashMap preserves insertion order
    private HashMap<String, RecordTable> recordTables;

    private final String RECORDS_TAB = "Records";
    private final String ANIME_RECORD_SUBTAB = "Anime";
    private final String USER_RECORD_SUBTAB = "User";
    private final String STAFF_RECORD_SUBTAB = "Staff";
    private final String STUDIO_RECORD_SUBTAB = "Studio";

    private final String TRANSACTIONS_TAB = "Transactions";
    private final String WATCH_EPISODE_TRANSACTION_SUBTAB = "Watch Episode";
    private final String RATE_ANIME_TRANSACTION_SUBTAB = "Rate Anime";
    private final String EDIT_CREDITS_TRANSACTION_SUBTAB = "Edit Credits";
    private final String FOLLOW_USER_TRANSACTION_SUBTAB = "Follow User";

    private final String REPORTS_TAB = "Reports";
    private final String HIGHEST_RATED_ANIME_REPORT_SUBTAB = "Highest Rated Anime";
    private final String RECOMMEND_ANIME_REPORT_SUBTAB = "Recommend Anime";
    private final String TOP_STUDIOS_REPORT_SUBTAB = "Top Studios";
    private final String USER_PROFILE_REPORT_SUBTAB = "User Profile";

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

        recordTables = new HashMap<>();
        mainTabs = new HashMap<>();
        tabMap = new LinkedHashMap<>();

        for (Records record : Records.values()) {
            recordTables.put(record.getTableName(), new RecordTable());
        }

        Tab recordsTab = new Tab(RECORDS_TAB);
        ArrayList<Subtab> recordsTabSubtabs = new ArrayList<>();
        Collections.addAll(recordsTabSubtabs,
                new Subtab(ANIME_RECORD_SUBTAB, "records/anime.json"),
                new Subtab(USER_RECORD_SUBTAB, "records/user.json"),
                new Subtab(STAFF_RECORD_SUBTAB, "records/staff.json"),
                new Subtab(STUDIO_RECORD_SUBTAB, "records/studio.json"));
        tabMap.put(recordsTab, recordsTabSubtabs);

        Tab transactionsTab = new Tab(TRANSACTIONS_TAB);
        ArrayList<Subtab> transactionsTabSubtabs = new ArrayList<>();
        Collections.addAll(transactionsTabSubtabs,
                new Subtab(WATCH_EPISODE_TRANSACTION_SUBTAB, "transactions/watch_episode.json"),
                new Subtab(RATE_ANIME_TRANSACTION_SUBTAB, "transactions/rate_anime.json"),
                new Subtab(EDIT_CREDITS_TRANSACTION_SUBTAB, "transactions/edit_credits.json"),
                new Subtab(FOLLOW_USER_TRANSACTION_SUBTAB, "transactions/follow_user.json"));
        tabMap.put(transactionsTab, transactionsTabSubtabs);

        Tab reportsTab = new Tab(REPORTS_TAB);
        ArrayList<Subtab> reportsTabSubtabs = new ArrayList<>();
        Collections.addAll(reportsTabSubtabs,
                new Subtab(HIGHEST_RATED_ANIME_REPORT_SUBTAB, "reports/highest_rated_anime.json"),
                new Subtab(RECOMMEND_ANIME_REPORT_SUBTAB, "reports/recommend_anime.json"),
                new Subtab(TOP_STUDIOS_REPORT_SUBTAB, "reports/top_studios.json"),
                new Subtab(USER_PROFILE_REPORT_SUBTAB, "reports/user_profile.json"));
        tabMap.put(reportsTab, reportsTabSubtabs);
    }

    public void placeWidgets() {
        for (HashMap.Entry<Tab, ArrayList<Subtab>> tabMapData : this.tabMap.entrySet()) {
            Tab tab = tabMapData.getKey();
            for (Subtab subtab : tabMapData.getValue()) {
                tab.addSubtab(subtab);
                System.out.printf("Subtab added: %s/%s\n", tab.getName(), subtab.getName());
            }
            this.mainTabs.put(tab.getName(), tab);
            WidgetFactory.addTab(tabs, tab);
        }
        this.frame.add(tabs, BorderLayout.CENTER);
    }

    // TODO: Set listeners to everything

    // Records

    // TODO: Refactor... but how?
    // Set ActionListeners for the Anime Records subtab.
    public void setAnimeRecordsListener(AnimeRecordsListener listener) {
        Subtab animeRecords = mainTabs.get(RECORDS_TAB).getSubtab(ANIME_RECORD_SUBTAB);
        animeRecords.setActionListener("selectAnimeId", listener);
        animeRecords.setActionListener("save", listener);
        animeRecords.setActionListener("delete", listener);
    }

    // Set ActionListeners for the User Records subtab.
    public void setUserRecordsListener(UserRecordsListener listener) {
        Subtab userRecords = mainTabs.get(RECORDS_TAB).getSubtab(USER_RECORD_SUBTAB);
        userRecords.setActionListener("searchUser", listener);
        userRecords.setActionListener("save", listener);
        userRecords.setActionListener("delete", listener);
    }

    // Set ActionListeners for the StudioRecordsListener subtab.
    public void setStudioRecordsListener(StudioRecordsListener listener) {
        Subtab studioRecords = mainTabs.get(RECORDS_TAB).getSubtab(STUDIO_RECORD_SUBTAB);
        studioRecords.setActionListener("searchStudio", listener);
        studioRecords.setActionListener("save", listener);
        studioRecords.setActionListener("delete", listener);
    }

    // Set ActionListeners for the StudioRecordsListener subtab.
    public void setStaffRecordsListener(StaffRecordsListener listener) {
        Subtab staffRecords = mainTabs.get(RECORDS_TAB).getSubtab(STAFF_RECORD_SUBTAB);
        staffRecords.setActionListener("searchStaff", listener);
        staffRecords.setActionListener("save", listener);
        staffRecords.setActionListener("delete", listener);
    }

    // Transactions

    // Reports

    // TODO: ^ Instead of defining a fifty functions for setting listeners, use a
    // data structure like a HashMap<Subtab, ArrayList<ActionListener>>
    // or something
    // ! But how?

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

    public void setFieldsFromData(String tabName, String subtabName, HashMap<String, String> data) {
        mainTabs.get(tabName).getSubtab(subtabName).setData(data);
    }

    public void TEMP_FUNC_setTransactionListener(ActionListener listener) {
        mainTabs.get("Transactions").getSubtab(WATCH_EPISODE_TRANSACTION_SUBTAB).setActionListener("watchEpisode",
                listener);
    }

    public JComponent accessComponent(String mainTab, String subTab, String component) {
        return mainTabs.get(mainTab).getSubtab(subTab).getComponent(component);
    }

    public void dialogPopUp(String title, String message) {
        JDialog popup = new JDialog(frame, title, true);
        popup.setSize(400, 160);
        popup.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel(message, UIManager.getIcon("OptionPane.informationIcon"),
                SwingConstants.CENTER);
        messageLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        popup.add(messageLabel, BorderLayout.CENTER);

        popup.setLocationRelativeTo(frame);
        popup.setVisible(true);
    }
}