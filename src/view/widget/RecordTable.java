package src.view.widget;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;

import src.controller.RecordTableListener;

public class RecordTable extends JDialog {
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JButton button;
    private GridBagConstraints c = new GridBagConstraints();
    private String[] shownColumnNames;
    private String[] columnNames;

    public RecordTable(JFrame frame, String recordName, String noun, String... shownColumnNames) {
        super(frame, "Please select " + noun, true);
        this.shownColumnNames = shownColumnNames;
        this.setSize(new Dimension(800, 600));
        this.setResizable(true);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(WidgetFactory.createJLabel("Please select " + noun + ":",
                WidgetFactory.Fonts.SUBTITLE), c);
        c.gridy++;
    }

    public void hideColumns() {
        // Hide some columns
        TableColumnModel columnModel = table.getColumnModel();
        boolean show;
        for (int i = columnNames.length - 1; i >= 0; i--) {
            show = false;
            for (int j = 0; j < shownColumnNames.length; j++) {
                if (shownColumnNames[j].equals(columnNames[i])) {
                    System.out.println("\tThis column must be shown.");
                    show = true;
                    break;
                }
            }
            if (!show) {
                TableColumn hiddenColumn = columnModel.getColumn(i);
                columnModel.removeColumn(hiddenColumn);
                System.out.println("\tHiding column " + columnNames[i] + " - Index " + i);
            }
        }
    }

    public void initializeData(String[][] data, String[] columnNames) {
        this.columnNames = columnNames;
        this.table = WidgetFactory.createJTable(data, columnNames);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.hideColumns();
        this.scrollPane = WidgetFactory.createJScrollPane(table);

        c.weightx = 1;
        c.weighty = 1;
        panel.add(scrollPane, c);
        c.gridy++;

        c.weightx = 0;
        c.weighty = 0;
        button = WidgetFactory.createJButton("Select");
        panel.add(button, c);
        c.gridy++;

        this.add(panel);
    }

    public void setData(String[][] data, String[] columnNames) {
        TableModel model = table.getModel();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < columnNames.length; j++) {
                model.setValueAt(data[i][j], i, j);
            }
        }
    }

    public void setListener(RecordTableListener listener) {
        button.addActionListener(listener);
    }

    public int getSelected() {
        return table.getSelectedRow();
    }
}
