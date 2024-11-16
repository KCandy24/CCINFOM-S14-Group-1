package src.view.gui;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JTabbedPane;

import src.view.widget.WidgetFactory;

public class Tab extends NamedPanel {
    private JTabbedPane tabbedPane;
    private HashMap<String, Subtab> subtabs;
    private String name;

    public Tab(String name) {
        this.name = name;
        this.subtabs = new HashMap<>();
        this.setLayout(new BorderLayout());
        this.tabbedPane = WidgetFactory.createJTabbedPane();
        this.tabbedPane.setTabPlacement(JTabbedPane.LEFT);
        this.tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    public void addSubtab(Subtab subtab) {
        subtabs.put(subtab.getName(), subtab);
        WidgetFactory.addTab(tabbedPane, subtab);
    }

    public String getName() {
        return name;
    }
}