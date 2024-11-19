package src.view.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.swing.*;

import src.controller.CurrentSubtabListener;
import src.controller.CurrentTabListener;
import src.controller.RecordTableListener;
import src.model.Records;
import src.view.widget.RecordTable;
import src.view.widget.WidgetFactory;

public class TopView {
    private JFrame frame;
    private JTabbedPane tabs;

    private HashMap<String, Tab> mainTabs;
    private LinkedHashMap<Tab, ArrayList<Subtab>> tabMap; // * LinkedHashMap preserves insertion order

    private HashMap<String, RecordTable> recordTables;

    private ArrayList<String> tabNames;
    private ArrayList<ArrayList<String>> subtabNames;

    public static final String RECORDS_TAB = "Records",
            ANIME_RECORD_SUBTAB = "Anime",
            USER_RECORD_SUBTAB = "User",
            STAFF_RECORD_SUBTAB = "Staff",
            STUDIO_RECORD_SUBTAB = "Studio",

            TRANSACTIONS_TAB = "Transactions",
            WATCH_EPISODE_TRANSACTION_SUBTAB = "Watch Episode",
            RATE_ANIME_TRANSACTION_SUBTAB = "Rate Anime",
            EDIT_CREDITS_TRANSACTION_SUBTAB = "Edit Credits",
            FOLLOW_USER_TRANSACTION_SUBTAB = "Follow User",

            REPORTS_TAB = "Reports",
            HIGHEST_RATED_ANIME_REPORT_SUBTAB = "Highest Rated Anime",
            RECOMMEND_ANIME_REPORT_SUBTAB = "Recommend Anime",
            TOP_STUDIOS_REPORT_SUBTAB = "Top Studios",
            USER_PROFILE_REPORT_SUBTAB = "User Profile";

    // Stores the last selected row on a RecordTable.
    private HashMap<String, String> lastRowData;

    // Stores the current tab and subtab name.
    private int currentTabIndex = 0;
    private int[] currentSubtabIndex = {
            0, 0, 0
    };
    /**
     * Initialize the top view.
     */
    public TopView() {
        frame = WidgetFactory.createJFrame("CCINFOM MCO GUI Prototype");
        this.frame.setLayout(new BorderLayout());
        this.frame.setBounds(100, 100, 1000, 500);

        this.instantiateWidgets();
        this.placeWidgets();
        this.frame.setVisible(true);
    }

    /**
     * Instantiate the widgets of the TopView.
     */
    public void instantiateWidgets() {
        tabs = WidgetFactory.createJTabbedPane();

        recordTables = new HashMap<>();
        mainTabs = new HashMap<>();
        tabMap = new LinkedHashMap<>();
        tabNames = new ArrayList<>();
        subtabNames = new ArrayList<>();

        for (Records record : Records.values()) {
            recordTables.put(record.name,
                    new RecordTable(frame, record.name, record.noun, record.shownColumnNames));
        }

        Collections.addAll(tabNames, RECORDS_TAB, TRANSACTIONS_TAB, REPORTS_TAB);

        Tab recordsTab = new Tab(RECORDS_TAB);
        ArrayList<Subtab> recordsTabSubtabs = new ArrayList<>();
        Collections.addAll(recordsTabSubtabs,
                new Subtab(ANIME_RECORD_SUBTAB, "records/anime.json"),
                new Subtab(USER_RECORD_SUBTAB, "records/user.json"),
                new Subtab(STAFF_RECORD_SUBTAB, "records/staff.json"),
                new Subtab(STUDIO_RECORD_SUBTAB, "records/studio.json"));
        tabMap.put(recordsTab, recordsTabSubtabs);
        ArrayList<String> recordsTabSubtabNames = new ArrayList<>();
        Collections.addAll(recordsTabSubtabNames, ANIME_RECORD_SUBTAB,
                USER_RECORD_SUBTAB, STAFF_RECORD_SUBTAB,
                STUDIO_RECORD_SUBTAB);
        subtabNames.add(recordsTabSubtabNames);

        Tab transactionsTab = new Tab(TRANSACTIONS_TAB);
        ArrayList<Subtab> transactionsTabSubtabs = new ArrayList<>();
        Collections.addAll(transactionsTabSubtabs,
                new Subtab(WATCH_EPISODE_TRANSACTION_SUBTAB, "transactions/watch_episode.json"),
                new Subtab(RATE_ANIME_TRANSACTION_SUBTAB, "transactions/rate_anime.json"),
                new Subtab(EDIT_CREDITS_TRANSACTION_SUBTAB, "transactions/edit_credits.json"),
                new Subtab(FOLLOW_USER_TRANSACTION_SUBTAB, "transactions/follow_user.json"));
        tabMap.put(transactionsTab, transactionsTabSubtabs);
        ArrayList<String> transactionsTabSubtabsNames = new ArrayList<>();
        Collections.addAll(transactionsTabSubtabsNames, WATCH_EPISODE_TRANSACTION_SUBTAB,
                RATE_ANIME_TRANSACTION_SUBTAB, EDIT_CREDITS_TRANSACTION_SUBTAB,
                FOLLOW_USER_TRANSACTION_SUBTAB);
        subtabNames.add(transactionsTabSubtabsNames);

        Tab reportsTab = new Tab(REPORTS_TAB);
        ArrayList<Subtab> reportsTabSubtabs = new ArrayList<>();
        Collections.addAll(reportsTabSubtabs,
                new Subtab(HIGHEST_RATED_ANIME_REPORT_SUBTAB, "reports/highest_rated_anime.json"),
                new Subtab(RECOMMEND_ANIME_REPORT_SUBTAB, "reports/recommend_anime.json"),
                new Subtab(TOP_STUDIOS_REPORT_SUBTAB, "reports/top_studios.json"),
                new Subtab(USER_PROFILE_REPORT_SUBTAB, "reports/user_profile.json"));
        tabMap.put(reportsTab, reportsTabSubtabs);
        ArrayList<String> reportsTabSubtabsNames = new ArrayList<>();
        Collections.addAll(reportsTabSubtabsNames, HIGHEST_RATED_ANIME_REPORT_SUBTAB,
                RECOMMEND_ANIME_REPORT_SUBTAB, TOP_STUDIOS_REPORT_SUBTAB,
                USER_PROFILE_REPORT_SUBTAB);
        subtabNames.add(reportsTabSubtabsNames);
    }

    /**
     * Place the widgets on the TopView.
     */
    public void placeWidgets() {
        for (HashMap.Entry<Tab, ArrayList<Subtab>> tabMapData : this.tabMap.entrySet()) {
            Tab tab = tabMapData.getKey();
            for (Subtab subtab : tabMapData.getValue()) {
                tab.addSubtab(subtab);
            }
            this.mainTabs.put(tab.getName(), tab);
            WidgetFactory.addTab(tabs, tab);
        }
        this.frame.add(tabs, BorderLayout.CENTER);
    }

    // Listeners

    /**
     * Set an action listener to the buttons of asubtab.
     * 
     * @param tabName     The name of the (primary) tab, e.g.,
     *                    {@link TopView#RECORDS_TAB}.
     * @param subtabName  The name of a subtab in the tab, e.g.,
     *                    {@link TopView#ANIME_RECORD_SUBTAB}.
     * @param listener    The action listener to set.
     * @param buttonNames The names of the buttons being set to; check the
     *                    associated subtab JSON file to inspect these.
     */
    public void setActionListeners(
            String tabName, String subtabName,
            ActionListener listener,
            String... buttonNames) {
        Subtab subtab = mainTabs.get(tabName).getSubtab(subtabName);
        for (String name : buttonNames) {
            subtab.setActionListener(name, listener);
        }
    }

    // Record Tables

    /**
     * This method is called once for each record table in order to initialize its
     * data. Unlike {@link #setRecordTableData()}, this also "adds" the JTable
     * itself into the RecordTable widget, hence the distinction.
     * 
     * @param recordName
     * @param data
     * @param column
     */
    public void initializeRecordTableData(String recordName, String[][] data, String[] column) {
        recordTables.get(recordName).initializeData(data, column);
    }

    /**
     * This method is called whenever a record table's data is updated and needs to
     * be set again.
     * 
     * @param recordName
     * @param data
     * @param column
     */
    public void setRecordTableData(String recordName, String[][] data, String[] column) {
        recordTables.get(recordName).setData(data, column);
    }

    /**
     * Set the record table listener for a certain record table.
     * 
     * @param recordName
     * @param listener
     */
    public void setRecordTableListener(String recordName, RecordTableListener listener) {
        recordTables.get(recordName).setListener(listener);
    }

    /**
     * "Pop-up" a record table, prompting the user to select from it.
     * 
     * @param recordName the name of the record (table) to display.
     */
    public void selectFromTable(String recordName) {
        recordTables.get(recordName).setVisible(true);
    }

    /**
     * Get the selected row index of a record table.
     * 
     * @param recordName
     * @return the row index of the selected entry.
     */
    public int getSelected(String recordName) {
        return recordTables.get(recordName).getSelected();
    }

    /**
     * Set the last row data selected.
     * 
     * @param rowData
     */
    public void setLastRowData(HashMap<String, String> rowData) {
        this.lastRowData = new HashMap<String, String>();
        lastRowData.putAll(rowData);
    }

    /**
     * Return the last row data selected.
     * 
     * @return
     */
    public HashMap<String, String> getLastRowData() {
        return lastRowData;
    }

    /**
     * Set the visibility of a record table.
     * 
     * @param recordName The name of the record table
     * @param b          Boolean -- true for visible, false otherwise
     */
    public void setRecordTableVisible(String recordName, boolean b) {
        this.recordTables.get(recordName).setVisible(b);
    }

    // Subtab

    public void setTabListeners(CurrentTabListener currentTabListener, CurrentSubtabListener currentSubtabListener) {
        tabs.getModel().addChangeListener(currentTabListener);
        for (Tab tab : tabMap.keySet()) {
            tab.addChangeListener(currentSubtabListener);
        }
    }

    /**
     * Get a subtab.
     * 
     * @param mainTab The name of the tab containing the subtab.
     * @param subTab  The name of the subtab itself.
     * @return
     */
    public Subtab getSubtab(String mainTab, String subTab) {
        return mainTabs.get(mainTab).getSubtab(subTab);
    }

    /**
     * Reset the fields of a subtab.
     * 
     * @param tabName    The name of the tab containing the subtab.
     * @param subtabName The name of the subtab itself.
     */
    public void resetFields(String tabName, String subtabName) {
        this.getSubtab(tabName, subtabName).resetFields();
    }

    /**
     * 
     * @param mainTab   The name of the tab containing the subtab.
     * @param subTab    The name of the subtab itself.
     * @param component The name of the component.
     * @return
     */
    public JComponent getComponent(String mainTab, String subTab, String component) {
        return this.getSubtab(mainTab, subTab).getComponent(component);
    }

    public String getCurrentTabName() {
        return tabNames.get(currentTabIndex);
    }

    public String getCurrentSubtabName() {
        return subtabNames.get(currentTabIndex).get(currentSubtabIndex[currentTabIndex]);
    }

    public void setCurrentTabName(int tabIndex) {
        this.currentTabIndex = tabIndex;
        System.out.println(getCurrentTabName());
    }

    public void setCurrentSubtabName(int subtabIndex) {
        this.currentSubtabIndex[currentTabIndex] = subtabIndex;
    }

    /**
     * TODO: Store current tabName, subtabName in TopView; make tab pane listeners
     * 
     * @param data A HashMap mapping column names of a record to their corresponding
     *             data values.
     */
    public void setFieldsFromData(HashMap<String, String> data) {
        System.out.printf("Setting fields in %s/%s\n", getCurrentTabName(), getCurrentSubtabName());
        Subtab subtab = mainTabs.get(getCurrentTabName()).getSubtab(getCurrentSubtabName());
        subtab.setFields(data);
    }

    public void displayHighestRatedAnimes(String[] headers, HashMap<String, String[][]> data){
        
    }

    /**
     * Alert the user with a dialog pop-up.
     * 
     * @param title
     * @param message
     */
    public void dialogPopUp(String title, String message) {
        JDialog popup = new JDialog(frame, title, true);
        popup.setSize(400, 160);
        popup.setLayout(new BorderLayout());
        popup.setLocationRelativeTo(frame);

        JLabel messageLabel = WidgetFactory.createJLabel(message);
        messageLabel.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        popup.add(messageLabel, BorderLayout.CENTER);

        popup.setVisible(true);
    }



}