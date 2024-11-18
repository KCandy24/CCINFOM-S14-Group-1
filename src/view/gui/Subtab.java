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
    private HashMap<String, JComponent> componentLocations;
    private HashMap<String, JComponent> componentColumns;
    private String name;

    private static final String JSON_PATH_PREFIX = "src/view/gui/";

    public Subtab(String name, String jsonPathString) {
        this.name = name;
        this.components = new ArrayList<>();
        this.componentLocations = new HashMap<>();
        this.componentColumns = new HashMap<>();
        this.setComponents(jsonPathString);
        this.addComponents();
    }

    /**
     * Sets the components ArrayList and the componentLocations HashMap.
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
                    String name = cell.optString("name",
                            String.format("%d,%d", i, j));
                    String column = cell.optString("column",
                            String.format("-"));
                    JComponent component = WidgetFactory.componentFromJSON(cell);
                    componentLocations.put(name, component);
                    componentColumns.put(column, component);
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
        return componentLocations.get(name);
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

    public void setData(HashMap<String, String> data) {
        for (String key : data.keySet()) {
            JComponent component = this.getAssociatedComponent(key);
            if (component instanceof AbstractButton) {
                ((AbstractButton) component).setText(data.get(key));
            } else if (component instanceof JLabel) {
                ((JLabel) component).setText(data.get(key));
            } else if (component instanceof JTextField) {
                ((JTextField) component).setText(data.get(key));
            } else if (component instanceof JComboBox) {
                JComboBox<String> comboBox = (JComboBox<String>) component;
                String genreName = Genre.findName(data.get(key));
                if (genreName != null) {
                    comboBox.setSelectedItem(genreName);
                } else {
                    comboBox.setSelectedItem(UserRegion.findName(data.get(key)));
                }
            }
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
