package src.view.gui;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import src.view.widget.WidgetFactory;

public class WatchNextEpisodeTab extends JPanel {
    private ArrayList<ArrayList<JComponent>> components;

    /**
     * Passed into `WidgetFactory.setComponentsFromMatrix()` to generate
     * `components`
     */
    private String[][] componentMatrix = {
            {
                    "Select user ID", "<TextField", "<Button"
            },
            {
                    "Last watched anime", "<TextField"
            },
            {
                    "Watch episode", "<TextField"
            },
            {
                    "<Button"
            }
    };

    public WatchNextEpisodeTab() {
        components = WidgetFactory.setComponentsFromMatrix(this,
                componentMatrix);
    }

    public void setListeners() {
        // TODO: Access specific indices of `components` to add listeners
    }
}
