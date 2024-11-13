package src.view.gui;

import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;

import src.view.widget.WidgetFactory;

public class HighestRatedAnimeTab extends JPanel {
    private ArrayList<ArrayList<JComponent>> components;

    /**
     * Passed into `WidgetFactory.setComponentsFromMatrix()` to generate
     * `components`
     */
    private String[][] componentMatrix = {
            {
                    "Select period", "<ComboBox"
            },
            {
                    "Select genre", "<ComboBox"
            },
            {
                    "<Button"
            }
    };

    private String jsonPathString = "src\\view\\gui\\test.json";

    public HighestRatedAnimeTab() {
        // components = WidgetFactory.setComponentsFromMatrix(this,
        //         componentMatrix);

        components = WidgetFactory.setComponents(this, jsonPathString);
    }

    public void setListeners() {
        // TODO: Access specific indices of `components` to add listeners
    }
}