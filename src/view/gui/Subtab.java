package src.view.gui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import src.util.Tuple;
import src.view.widget.WidgetFactory;

public class Subtab extends JPanel {
    private ArrayList<ArrayList<JComponent>> components;
    private HashMap<String, Tuple> componentLocations;

    public Subtab(String jsonPathString) {
        this.components = new ArrayList<>();
        this.componentLocations = new HashMap<>();
        this.setComponents(jsonPathString);
        this.addComponents();
    }

    /**
     * Sets the components ArrayList and the componentLocations HashMap.
     * 
     * @param jsonPathString path to JSON file from which to add component data
     */
    private void setComponents(String jsonPathString) {
        Path jsonPath = Paths.get(jsonPathString);
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
                    componentLocations.put(name, new Tuple(i, j));
                    rowComponents.add(WidgetFactory.componentFromJSON(cell));
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
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        for (ArrayList<JComponent> row : components) {
            c.gridx = 0;
            for (JComponent component : row) {
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
    private JComponent getComponent(String name) {
        Tuple location = componentLocations.get(name);
        int i = location.getFirst();
        int j = location.getSecond();
        return components.get(i).get(j);
    }

    /**
     * 
     * @param name
     * @param listener
     */
    public void setActionListener(String name, ActionListener listener) {
        ((AbstractButton) getComponent(name)).addActionListener(listener);
    }

    public void setDocumentListener(String name, DocumentListener listener) {
        ((JTextComponent) getComponent(name)).getDocument()
                .addDocumentListener(listener);
    }
}