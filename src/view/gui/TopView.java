package src.view.gui;

import java.awt.*;
import javax.swing.*;
import src.view.widget.WidgetFactory;
import src.view.widget.WidgetFactory.FontCollection;

public class TopView {
    private JFrame frame;
    JButton test;

    /**
     * Initialize the top view.
     */
    public TopView() {
        this.frame = WidgetFactory.createJFrame("Animo Anime List");
        this.placeWidgets();
        this.frame.setVisible(true);
    }

    /**
     * Place widgets onto the `frame`.
     */
    public void placeWidgets() {
        JPanel panel = WidgetFactory.createJPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(WidgetFactory.createJLabel("Ohayou sekai!",
                FontCollection.TITLE));
        panel.add(WidgetFactory.createJLabel("Template GUI",
                FontCollection.SUBTITLE));
        panel.add(WidgetFactory
                .createJLabel("Please feel free to edit the code!"));

        test = WidgetFactory.createJButton("Frog button");
        panel.add(test);

        this.frame.add(panel, BorderLayout.CENTER);

        this.frame.add(
                WidgetFactory.createJLabel("North", FontCollection.TITLE),
                BorderLayout.NORTH);
        this.frame.add(
                WidgetFactory.createJLabel("South", FontCollection.TITLE),
                BorderLayout.SOUTH);
        this.frame.add(WidgetFactory.createJLabel("East", FontCollection.TITLE),
                BorderLayout.EAST);
        this.frame.add(WidgetFactory.createJLabel("West", FontCollection.TITLE),
                BorderLayout.WEST);
                
    }
}
