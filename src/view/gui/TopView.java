package src.view.gui;

import java.awt.*;
import javax.swing.*;

import src.controller.TitlesSearchBoxListener;
import src.view.widget.ButtonSet;
import src.view.widget.WidgetFactory;

public class TopView {
    private JFrame frame;
    private SearchDemo searchDemo;
    private JTabbedPane tabs;
    private ButtonSet buttonSet;

    /**
     * Initialize the top view.
     */
    public TopView() {
        this.frame = WidgetFactory.createJFrame("Program Design is my passion");
        this.frame.setLayout(new BorderLayout());
        this.instantiateWidgets();
        this.placeWidgets();
        this.frame.setVisible(true);
    }

    public void instantiateWidgets() {
        buttonSet = WidgetFactory.createButtonSet("Select", new String[] {
                "Anime Record", "User Record", "Studio Record",
                "Prod Staff Record"
        });
        searchDemo = new SearchDemo();
        tabs = WidgetFactory.createJTabbedPane();
    }

    public void placeWidgets() {
        this.frame.add(this.buttonSet, BorderLayout.WEST);

        // TODO: Revise!!!
        for (String s : new String[] {
                "Search Demo", "Records", "Transactions", "Reports"
        }) {
            String formattedString = """
                    <html>
                    <head><style>td {text-align: center}</style></head>
                    <body>
                    <table width=150>
                    <td>
                    """ + s +
                    """
                            </td>
                            </table>
                            </body>
                            </html>
                            """;
            if (s == "Search Demo") {
                tabs.addTab(formattedString, this.searchDemo);
            } else {
                tabs.addTab(formattedString, WidgetFactory.createJPanel());
            }
        }
        // --

        this.frame.add(tabs, BorderLayout.CENTER);
    }

    // Search Demo
    public void setSearchBoxListener(TitlesSearchBoxListener searchBoxListener) {
        this.searchDemo.setListener(searchBoxListener);
    }

    public void setSearchBoxResults(String[] results) {
        this.searchDemo.setListData(results);
    }
}
