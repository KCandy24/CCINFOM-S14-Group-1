package src.view.widget;

import java.awt.*;
import javax.swing.*;

/**
 * The WidgetFactory class enables quick creation of styled UI elements such as
 * Labels and Buttons via static methods.
 */
public class WidgetFactory {
    private static final String DEFAULT_FONT_FAMILY = "Segoe UI";

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
        TITLE(28),
        SUBTITLE(20),
        BODY(14);

        private Font font;

        private Fonts(int size) {
            this.font = new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, size);
        }

        public Font getFont() {
            return font;
        }

        public int getSize() {
            return font.getSize();
        }
    }

    /**
     * Apply common stylings to a given component.
     * 
     * @param component
     */
    static void styleComponent(JComponent component) {
        component.setBackground(Color.WHITE);
        component.setFont(Fonts.BODY.getFont());
    }

    /**
     * Create an ordinary label with a given text.
     * 
     * @param text
     * @return
     */
    public static JLabel createJLabel(String text) {
        JLabel jLabel = new JLabel(text);
        WidgetFactory.styleComponent(jLabel);
        return jLabel;
    }

    /**
     * Create an ordinary label with a given text and font.
     * 
     * @param text
     * @param font any of {@link WidgetFactory.Fonts}
     * @return
     */
    public static JLabel createJLabel(String text, Fonts font) {
        JLabel jLabel = createJLabel(text);
        jLabel.setFont(font.getFont());
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return jLabel;
    }

    /**
     * Create an ordinary label with a given text.
     * 
     * @param text
     * @return
     */
    public static JButton createJButton(String text) {
        JButton jButton = new JButton(text);
        WidgetFactory.styleComponent(jButton);
        return jButton;
    }

    public static JPanel createJPanel() {
        JPanel jPanel = new JPanel();
        WidgetFactory.styleComponent(jPanel);
        return jPanel;
    }

    public static JTextField createJTextField() {
        JTextField jTextField = new JTextField();
        WidgetFactory.styleComponent(jTextField);
        jTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                jTextField.getPreferredSize().height));
        return jTextField;
    }

    public static JList<String> createJList() {
        JList<String> jList = new JList<String>();
        WidgetFactory.styleComponent(jList);
        jList.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return jList;
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
        JFrame jFrame = new JFrame(title);
        jFrame.setLocationByPlatform(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jFrame.getContentPane().setBackground(Color.WHITE);
        jFrame.setMinimumSize(screenSize);

        jFrame.setLayout(new BorderLayout());

        return jFrame;
    }

    // Custom widgets

    public static SearchBox createSearchBox() {
        SearchBox searchBox = new SearchBox();
        return searchBox;
    }
}
