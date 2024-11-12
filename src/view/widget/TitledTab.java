package src.view.widget;

import javax.swing.JPanel;

/**
 * A TitledTab is really just a tuple of <String, JPanel>.
 * 
 * TitledTab
 */
public class TitledTab extends src.util.Pair<String, JPanel> {
    public TitledTab(String title, JPanel tab) {
        super(title, tab);
    }

    public String getTitle() {
        return getFirst();
    }

    public JPanel getTab() {
        return getSecond();
    }
}
