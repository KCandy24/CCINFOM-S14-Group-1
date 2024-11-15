package src.view.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import src.view.widget.TitledTab;
import src.view.widget.WidgetFactory;

public class RecordsTab extends JPanel {
    private JTabbedPane tabs;

    private JPanel recordsAnimeTab;
    private JPanel recordsUserTab;
    private JPanel recordsStudioTab;
    private JPanel recordsStaffTab;

    public RecordsTab() {
        this.setLayout(new BorderLayout());
        this.instantiateWidgets();
        this.placeWidgets();
    }

    public void instantiateWidgets() {
        recordsAnimeTab = new Subtab("records/anime.json");
        recordsUserTab = new Subtab("records/user.json");
        recordsStudioTab = new Subtab("records/studio.json");
        recordsStaffTab = new Subtab("records/staff.json");
        ArrayList<TitledTab> titledTabs = new ArrayList<>();
        Collections.addAll(
                titledTabs,
                new TitledTab("Anime", recordsAnimeTab),
                new TitledTab("User", recordsUserTab),
                new TitledTab("Studio", recordsStudioTab),
                new TitledTab("Staff", recordsStaffTab));
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
