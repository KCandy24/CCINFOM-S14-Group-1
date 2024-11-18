package src.view.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import src.model.Genre;
import src.model.UserRegion;
import src.view.widget.WidgetFactory;

public class Subtab extends NamedPanel {
    private ArrayList<ArrayList<JComponent>> components;
    private HashMap<String, JComponent> componentName;
    private HashMap<String, JComponent> componentColumns;
    private String name;

    private static final String JSON_PATH_PREFIX = "src/view/gui/";

    public Subtab(String name, String jsonPathString) {
        this.name = name;
        this.components = new ArrayList<>();
        this.componentName = new HashMap<>();
        this.componentColumns = new HashMap<>();
        this.setComponents(jsonPathString);
        this.addComponents();
    }

    /**
     * Sets the components ArrayList and the componentName HashMap.
     * 
     * @param jsonPathString path to JSON file from which to add component data
     */
    private void setComponents(String jsonFileName) {
        Path jsonPath = Paths.get(JSON_PATH_PREFIX + jsonFileName);
        try {
            String content = new String(Files.readAllBytes(jsonPath));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                ArrayList<JComponent> rowComponents = new ArrayList<>();
                JSONArray row = jsonArray.getJSONArray(i);

                for (int j = 0; j < row.length(); j++) {
                    JSONObject cell = row.getJSONObject(j);
                    JComponent component = WidgetFactory.componentFromJSON(cell);

                    String name = cell.optString("name");
                    if (!name.isEmpty()) {
                        componentName.put(name, component);
                    }

                    String column = cell.optString("column");
                    if (!column.isEmpty()) {
                        componentColumns.put(column, component);
                    }

                    rowComponents.add(component);
                }

                components.add(rowComponents);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actually adds the components to the Subtab.
     */
    private void addComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(16, 16, 16, 16);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridy = 0;
        for (ArrayList<JComponent> row : components) {
            c.gridx = 0;
            for (JComponent component : row) {
                if (component instanceof JButton) {
                    c.fill = GridBagConstraints.NONE;
                } else {
                    c.fill = GridBagConstraints.HORIZONTAL;
                }
                this.add(component, c);
                c.gridx++;
            }
            c.gridy++;
        }
    }

    /**
     * 
     * @param name
     * @return the component with a matching name
     */
    public JComponent getComponent(String name) {
        return componentName.get(name);
    }

    /**
     * Get the component associated with a column of a dataset.
     * 
     * @param columnName
     * @return
     */
    private JComponent getAssociatedComponent(String columnName) {
        return componentColumns.get(columnName);
    }

    public void setComponentText(JComponent component, String text) {
        if (component instanceof AbstractButton) {
            ((AbstractButton) component).setText(text);
        } else if (component instanceof JLabel) {
            ((JLabel) component).setText(text);
        } else if (component instanceof JTextField) {
            ((JTextField) component).setText(text);
        } else if (component instanceof JComboBox) {
            JComboBox<String> comboBox = (JComboBox<String>) component;
            String genreName = Genre.findName(text);
            String regionName = UserRegion.findName(text);
            if (genreName != null) {
                comboBox.setSelectedItem(genreName);
            } else if (regionName != null) {
                comboBox.setSelectedItem(regionName);
            } else {
                comboBox.setSelectedIndex(0);
            }
        }
    }

    public void setFields(HashMap<String, String> data) {
        for (String key : data.keySet()) {
            JComponent component = this.getAssociatedComponent(key);
            this.setComponentText(component, data.get(key));
        }
    }

    /**
     * Reset the values of all components in this Subtab that are associated with a
     * column.
     */
    public void resetFields() {
        for (JComponent component : componentColumns.values()) {
            setComponentText(component, "");
        }
    }

    /**
     * Set an ActionListener for a component in this Subtab.
     * 
     * @param name
     * @param listener
     */
    public void setActionListener(String name, ActionListener listener) {
        System.out.println(this.getName() + "/" + name + "<-ActionListener(" + listener + ")");
        ((AbstractButton) getComponent(name)).addActionListener(listener);
    }

    public void setDocumentListener(String name, DocumentListener listener) {
        System.out.println(this.getName() + "/" + name + "<-DocumentListener(" + listener + ")");
        ((JTextComponent) getComponent(name)).getDocument().addDocumentListener(listener);
    }

    public String getName() {
        return name;
    }

}
