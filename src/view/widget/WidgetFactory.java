package src.view.widget;

import java.awt.Font;
import java.awt.*;
import javax.swing.*;

/**
 * The WidgetFactory class enables quick creation of styled UI elements such as
 * Labels and Buttons via static methods.
 */
public class WidgetFactory {
    /**
     * This class is not meant to be instantiated. Use the various static
     * methods to create widgets.
     */
    private WidgetFactory() {

    }

    /**
     * The following fonts were selected based on Microsoft's typography
     * guidelines which were accessed via the following link:
     * https://learn.microsoft.com/en-us/windows/apps/design/style/xaml-theme-resources#the-xaml-type-ramp
     * 
     */
    public enum Fonts {
        BODY("Segoe UI", Font.PLAIN, 14),
        SUBTITLE("Segoe UI", Font.PLAIN, 20),
        TITLE("Segoe UI", Font.PLAIN, 28);

        private Font font;

        private Fonts(String name, int style, int size) {
            this.font = new Font(name, style, size);
        }

        public Font getFont() {
            return font;
        }
    }

    /**
     * Apply common stylings to a given component.
     * 
     * @param component
     */
    private static void styleComponent(JComponent component) {
        component.setFont(Fonts.BODY.getFont());
    }

    /**
     * Create an ordinary label with a given text and font.
     * 
     * @param text
     * @param font any of {@link WidgetFactory.Fonts}
     * @return
     */
    public static JLabel createJLabel(String text, Fonts font) {
        JLabel jlabel = new JLabel(text);
        WidgetFactory.styleComponent(jlabel);
        jlabel.setHorizontalAlignment(JLabel.CENTER);
        jlabel.setFont(font.getFont());
        return jlabel;
    }

    /**
     * Create an ordinary label with a given text.
     * 
     * @param text
     * @return
     */
    public static JLabel createJLabel(String text) {
        JLabel jlabel = new JLabel(text);
        WidgetFactory.styleComponent(jlabel);
        jlabel.setHorizontalAlignment(JLabel.CENTER);
        return jlabel;
    }

    /**
     * Create an ordinary label with a given text.
     * 
     * @param text
     * @return
     */
    public static JButton createJButton(String text) {
        JButton jbutton = new JButton(text);
        WidgetFactory.styleComponent(jbutton);
        return jbutton;
    }

    /**
     * Create a JFrame.
     * 
     * @param title
     * @return
     */
    public static JFrame createJFrame(String title) {
        /**
         * Set the default window size to be in a 16:9 ratio, where the width is
         * 1/2 the width of the viewport.
         */
        Toolkit toolkit = Toolkit.getDefaultToolkit(); // platform-specific info
        Dimension systemResolution = toolkit.getScreenSize();
        int windowWidth = (int) (systemResolution.getWidth() / 2);
        int windowHeight = (int) 9 * windowWidth / 16;
        Dimension screenSize = new Dimension(windowWidth, windowHeight);

        // Attempt to set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set the system look and feel: " + e);
        }

        // Create JFrame and set defaults
        JFrame jframe = new JFrame(title);
        jframe.setLocationByPlatform(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jframe.getContentPane().setBackground(Color.WHITE);
        jframe.setMinimumSize(screenSize);

        jframe.setLayout(new BorderLayout());

        return jframe;
    }

    public static JPanel createJPanel() {
        JPanel jpanel = new JPanel();
        jpanel.setBackground(Color.WHITE);
        return jpanel;
    }
}
