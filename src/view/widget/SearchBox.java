package src.view.widget;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import src.controller.SearchBoxListener;

/**
 * Note: this search box uses composition instead of inheritance;
 * use its getJPanel method when adding it to a frame/panel!
 * TODO: ^ With that in mind, do we switch to inheritance?
 * Not really sure about the benefits to either TBH -- Justin
 */
public class SearchBox {
    private JPanel jPanel;
    private JList<String> jList;
    private JTextField searchField;

    public SearchBox() {
        jPanel = WidgetFactory.createJPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

        searchField = WidgetFactory.createJTextField();
        jPanel.add(searchField);

        jList = WidgetFactory.createJList();

        jPanel.add(jList);
    }

    public JPanel getJPanel() {
        return this.jPanel;
    }

    public void setListData(String[] listData) {
        jList.setListData(listData);
    }

    public void setListener(SearchBoxListener searchBoxListener) {
        this.searchField.getDocument().addDocumentListener(searchBoxListener);
    }
}
