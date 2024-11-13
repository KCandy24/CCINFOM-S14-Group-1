package src.view.widget;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

// json dependencies
import org.json.JSONObject;

import com.mysql.cj.xdevapi.JsonArray;

import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * The WidgetFactory class enables quick creation of styled UI elements such as
 * Labels and Buttons via static methods.
 */
public class WidgetFactory {
    private static final String DEFAULT_FONT_FAMILY = "Inter";

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
        BODY(14),
        BODY_BOLD(Font.BOLD, 14);

        private Font font;

        private Fonts(int size) {
            this.font = new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, size);
        }

        private Fonts(int style, int size) {
            this.font = new Font(DEFAULT_FONT_FAMILY, style, size);
        }

        public Font getFont() {
            return font;
        }

        public int getSize() {
            return font.getSize();
        }
    }

    /**
     * Apply common stylings to a given component. TODO: A little concerned
     * about this being public; it's used in SearchDemo and maybe other tabs
     * outside `widget` which extend JPanel - Justin
     * 
     * @param component
     */
    public static void styleComponent(JComponent component) {
        component.setBackground(Color.WHITE);
        component.setFont(Fonts.BODY.getFont());
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
        Dimension windowSize = new Dimension(windowWidth, windowHeight);

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
        jFrame.setMinimumSize(windowSize);

        return jFrame;
    }

    public static JTabbedPane createJTabbedPane() {
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.setFont(Fonts.SUBTITLE.getFont());
        return jTabbedPane;
    }

    public static JTabbedPane createJTabbedPane(ArrayList<TitledTab> tabs) {
        JTabbedPane jTabbedPane = createJTabbedPane();
        for (src.util.Pair<String, JPanel> pair : tabs) {
            String formattedString = """
                    <html>
                    <head><style>td {text-align: center}</style></head>
                    <body>
                    <table width=250>
                    <td>
                    """ + pair.getFirst() +
                    """
                            </td>
                            </table>
                            </body>
                            </html>
                            """;
            jTabbedPane.addTab(formattedString, pair.getSecond());
        }
        return jTabbedPane;
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
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
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

    public static JButton createJButton(String text, Fonts font) {
        JButton jButton = new JButton(text);
        WidgetFactory.styleComponent(jButton);
        jButton.setFont(font.getFont());
        return jButton;
    }

    public static JPanel createJPanel() {
        JPanel jPanel = new JPanel();
        WidgetFactory.styleComponent(jPanel);
        return jPanel;
    }

    public static JTextField createJTextField() {
        JTextField jTextField = new JTextField(16);
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

    // Custom widgets

    public static SearchBox createSearchBox() {
        SearchBox searchBox = new SearchBox();
        return searchBox;
    }

    public static ButtonSet createButtonSet(String header, String[] labels) {
        ButtonSet buttonSet = new ButtonSet(header, labels);
        return buttonSet;
    }

    public static LabelledField creatLabelledField(String text) {
        return new LabelledField(text);
    }

    /**
     * TODO: Parameters (Button labels, ComboBox options) ? Label text ?
     * <Button:Text ? <ComboBox:Option 1, Option 2, Option 3 ? See
     * RecordsAnimeTab for a demo
     * 
     * @param componentMatrix
     * @return
     */
    public static ArrayList<ArrayList<JComponent>> setComponentsFromMatrix(
            JPanel panel, String[][] componentMatrix) {
        ArrayList<ArrayList<JComponent>> components = new ArrayList<>();
        for (String[] row : componentMatrix) {
            ArrayList<JComponent> rowComponents = new ArrayList<>();
            for (String componentString : row) {
                switch (componentString) {
                    case "<TextField":
                        rowComponents.add(createJTextField());
                        break;
                    case "<Button":
                        rowComponents.add(createJButton("TODO: Set this text"));
                        break;
                    case "<ComboBox":
                        // TODO
                        JComboBox<String> cb = new JComboBox<>(new String[] {
                                "TODO: Set this list"
                        });
                        styleComponent(cb);
                        rowComponents.add(cb);
                        break;
                    default:
                        rowComponents.add(createJLabel(componentString));
                        break;
                }
                components.add(rowComponents);
            }
        }

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        int i = 0, j;
        for (ArrayList<JComponent> row : components) {
            j = 0;
            for (JComponent component : row) {
                c.gridy = i;
                c.gridx = j;
                panel.add(component, c);
                j++;
            }
            i++;
        }

        return components;
    }

    public static ArrayList<ArrayList<JComponent>> setComponents(JPanel panel, String jsonPathString) {
        ArrayList<ArrayList<JComponent>> components = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(jsonPathString)));

            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                ArrayList<JComponent> rowComponents = new ArrayList<>();
                JSONArray row = jsonArray.getJSONArray(i);
                
                for (int j = 0; j < row.length(); j++) {
                    JSONObject cell = row.getJSONObject(j);
                    
                    switch (cell.getString("data_type")) {
                        case "Label":
                            rowComponents.add(createJLabel(cell.optString("value", "default")));
                            break;
                        case "ComboBox":
                            // TODO
                            JComboBox<String> cb = new JComboBox<>(new String[] {
                                "TODO: Set this list"
                            });
                            styleComponent(cb);
                            rowComponents.add(cb);
                            break;
                        case "Button":
                            rowComponents.add(createJButton(cell.getString("value")));
                            break;
                        case "TextField":
                            rowComponents.add(createJTextField());
                            break;
                    }
                }
                components.add(rowComponents);
            }

            panel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            int i = 0, j;
            for (ArrayList<JComponent> row : components) {
                j = 0;
                for (JComponent component : row) {
                    c.gridy = i;
                    c.gridx = j;
                    panel.add(component, c);
                    j++;
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return components;
    }

    public void placeWidgets(JPanel panel,
            ArrayList<ArrayList<JComponent>> components) {

    }

}
