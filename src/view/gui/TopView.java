package src.view.gui;

import java.awt.*;
import javax.swing.*;
import src.view.widget.WidgetFactory;
import src.view.widget.WidgetFactory.Fonts;

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

        panel.add(WidgetFactory.createJLabel("Ohayou sekai!", Fonts.TITLE));
        panel.add(WidgetFactory.createJLabel("Template GUI", Fonts.SUBTITLE));
        panel.add(WidgetFactory.createJLabel("Please feel free to edit the code!"));

        test = WidgetFactory.createJButton("Frog button");
        panel.add(test);

        this.frame.add(panel, BorderLayout.CENTER);

        this.frame.add(WidgetFactory.createJLabel("North", Fonts.TITLE), BorderLayout.NORTH);
        this.frame.add(WidgetFactory.createJLabel("South", Fonts.TITLE), BorderLayout.SOUTH);
        this.frame.add(WidgetFactory.createJLabel("East", Fonts.TITLE), BorderLayout.EAST);
        this.frame.add(WidgetFactory.createJLabel("West", Fonts.TITLE), BorderLayout.WEST);
    }
}
