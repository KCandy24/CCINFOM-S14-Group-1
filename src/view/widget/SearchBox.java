package src.view.widget;

import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import src.controller.SearchBoxListener;

/**
 * 
 */
public class SearchBox extends JPanel {
    private JList<String> jList;
    private JTextField searchField;

    public SearchBox() {
        WidgetFactory.styleComponent(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.searchField = WidgetFactory.createJTextField();
        this.jList = WidgetFactory.createJList();
        this.add(searchField);
        this.add(jList);
    }

    public void setListData(String[] listData) {
        this.jList.setListData(listData);
    }

    public void setListener(SearchBoxListener searchBoxListener) {
        this.searchField.getDocument().addDocumentListener(searchBoxListener);
    }
}
