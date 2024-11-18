package src.view.widget;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import src.controller.RecordTableListener;

public class RecordTable extends JDialog {
    private JTable table;
    private JPanel panel;
    private JButton button;
    private GridBagConstraints c = new GridBagConstraints();

    public RecordTable() {
        this.setSize(new Dimension(1000, 1000));
        this.setResizable(true);
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        c.insets = new Insets(16, 8, 16, 8);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(WidgetFactory.createJLabel("Select a record", WidgetFactory.Fonts.SUBTITLE), c);
        c.gridy++;
    }

    public void setTableData(String[][] data, String[] columnNames) {
        table = WidgetFactory.createJTable(data, columnNames);
        JScrollPane scrollPane = WidgetFactory.createJScrollPane(table);

        int prevIpadx = c.ipadx;
        c.ipadx = 400;
        panel.add(scrollPane, c);
        c.gridy++;
        c.ipadx = prevIpadx;

        button = WidgetFactory.createJButton("Select");
        panel.add(button, c);
        c.gridy++;
        this.add(panel);
    }

    public void setListener(RecordTableListener listener) {
        button.addActionListener(listener);
    }

    public int getSelected() {
        return table.getSelectedRow();
    }
}
