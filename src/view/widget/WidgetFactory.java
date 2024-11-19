package src.view.widget;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONObject;

import src.model.Genre;
import src.model.UserRegion;
import src.view.gui.NamedPanel;
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
        TITLE(20),
        SUBTITLE(16),
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
     * Apply common stylings to a given component.
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
        Dimension windowSize = new Dimension(800, 600);

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

    /**
     * Add a NamedPanel tab to a JTabbedPane. Formatting is applied to the "tab"
     * name via HTML styling.
     * 
     * @param pane
     * @param tab
     */
    public static void addTab(JTabbedPane pane, NamedPanel tab) {
        String formattedString = """
                <html>
                <head><style>td {text-align: center}</style></head>
                <body>
                <table width=250>
                <td>
                """ + tab.getName() +
                """
                        </td>
                        </table>
                        </body>
                        </html>
                        """;
        pane.addTab(formattedString, tab);
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
        jLabel.setHorizontalAlignment(SwingConstants.LEFT);
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

    public static JTable createJTable(String[][] data, String[] columnNames) {
        JTable table = new JTable(data, columnNames) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
                Component component = super.prepareRenderer(renderer, rowIndex, columnIndex);
                TableColumn tableColumn = getColumnModel().getColumn(columnIndex);
                int componentPreferredWidth = component.getPreferredSize().width;
                int columnPreferredWidth = tableColumn.getPreferredWidth();
                tableColumn.setPreferredWidth(Math.max(componentPreferredWidth, columnPreferredWidth));
                return component;
            }
        };
        WidgetFactory.styleComponent(table);
        table.getTableHeader().setFont(WidgetFactory.Fonts.BODY.getFont());
        return table;
    }

    public static JScrollPane createJScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(
                table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        WidgetFactory.styleComponent(scrollPane);
        return scrollPane;
    }

    // Custom widgets

    public static LabelledField creatLabelledField(String text) {
        return new LabelledField(text);
    }

    public static JComboBox<String> createJComboBox() {
        JComboBox<String> cb = new JComboBox<>();
        WidgetFactory.styleComponent(cb);
        return cb;
    }

    /**
     * Generate a component from JSON data.
     * 
     * @param cell
     * @return
     */
    public static JComponent componentFromJSON(JSONObject cell) {
        JComponent component;
        String type = cell.optString("type", "Label");
        String value = cell.optString("value", "default");

        switch (type) {
            case "ComboBox":
                JComboBox<String> comboBox = WidgetFactory.createJComboBox();
                String comboBoxType = cell.optString("combobox_type", "genre");
                switch (comboBoxType) {
                    case "genre":
                    default:
                        for (Genre genre : Genre.values()) {
                            comboBox.addItem(genre.name);
                        }
                        break;
                    case "region":
                        for (UserRegion region : UserRegion.values()) {
                            comboBox.addItem(region.name);
                        }
                        break;
                    case "period":
                        comboBox.addItem("Overall");
                        comboBox.addItem("Monthly");
                        comboBox.addItem("Seasonal");
                        comboBox.addItem("Yearly");
                        break;
                    case "genre_with_none":
                        comboBox.addItem("None");
                        for (Genre genre : Genre.values()) {
                            comboBox.addItem(genre.name);
                        }
                        break;
                }
                component = comboBox;
                break;
            case "Button":
                component = WidgetFactory.createJButton(value);
                break;
            case "TextField":
                component = WidgetFactory.createJTextField();
                break;
            case "Label":
            default:
                /**
                 * "default:" is technically unnecessary as optString has a
                 * default value of "Label", but this signals it to the compiler
                 */
                component = WidgetFactory.createJLabel(value);
                String align = cell.optString("align", "left");
                int alignment;
                switch (align) {
                    case "left":
                        alignment = SwingConstants.LEFT;
                        break;
                    case "right":
                        alignment = SwingConstants.RIGHT;
                        break;
                    default:
                    case "center":
                        alignment = SwingConstants.CENTER;
                        break;
                }
                ((JLabel) component).setHorizontalAlignment(alignment);
                break;
        }

        String name = cell.optString("name");
        if (!name.isEmpty()) {
            component.setName(name);
        } else {
            component.setName(value);
        }

        return component;
    }
}
