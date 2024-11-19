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
import javax.swing.table.AbstractTableModel;
import javax.swing.JButton;

import src.controller.RecordTableListener;

public class RecordTable extends JDialog {
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JButton button;
    private GridBagConstraints c = new GridBagConstraints();

    public RecordTable(JFrame frame, String recordName) {
        super(frame, "Please select a row from " + recordName, true);
        this.setSize(new Dimension(800, 600));
        this.setResizable(true);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(WidgetFactory.createJLabel("Please select a row from " + recordName + ":",
                WidgetFactory.Fonts.SUBTITLE), c);
        c.gridy++;
    }

    public void initializeData(String[][] data, String[] columnNames) {
        table = WidgetFactory.createJTable(data, columnNames);
        scrollPane = WidgetFactory.createJScrollPane(table);

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
        table.setModel(new AbstractTableModel() {

            public int getRowCount() {
                return data.length;
            }

            public int getColumnCount() {
                return data[0].length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return data[rowIndex][columnIndex];
            }

            public String getColumnName(int columnIndex) {
                return columnNames[columnIndex];
            }
        });
    }

    public void setListener(RecordTableListener listener) {
        button.addActionListener(listener);
    }

    public int getSelected() {
        return table.getSelectedRow();
    }
}
