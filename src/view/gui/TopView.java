package src.view.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;


import src.controller.TitlesSearchBoxListener;
import src.view.widget.TitledTab;
import src.view.widget.WidgetFactory;

public class TopView {
    private JFrame frame;
    private SearchDemo searchDemo;
    private RecordsTab recordsTab;
    private JPanel transactionsTab;
    private JPanel reportsTab;
    private JTabbedPane tabs;

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
        searchDemo = new SearchDemo();
        recordsTab = new RecordsTab();
        transactionsTab = new TransactionsTab();
        reportsTab = new ReportsTab();

        ArrayList<TitledTab> titledTabs = new ArrayList<>();
        Collections.addAll(
                titledTabs,
                new TitledTab("(Demo -- Remove in Final)", searchDemo),
                new TitledTab("Records", recordsTab),
                new TitledTab("Transactions", transactionsTab),
                new TitledTab("Reports", reportsTab));
        tabs = WidgetFactory.createJTabbedPane(titledTabs);
    }

    public void placeWidgets() {
        this.frame.add(tabs, BorderLayout.CENTER);
    }

    // Search Demo
    public void setSearchBoxListener(
            TitlesSearchBoxListener searchBoxListener) {
        this.searchDemo.setListener(searchBoxListener);
    }

    public void setSearchBoxResults(String[] results) {
        this.searchDemo.setListData(results);
    }
}
