package src.view.widget;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.JTableHeader;
import javax.swing.JButton;

import src.controller.RecordTableListener;

public class RecordTable extends JDialog {
    private JTable table;
    private JPanel panel;
    private JButton button;
    private GridBagConstraints c = new GridBagConstraints();

    public RecordTable() {
        this.setMinimumSize(new Dimension(800, 800));
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        c.insets = new Insets(16, 16, 16, 16);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(WidgetFactory.createJLabel("Select a record", WidgetFactory.Fonts.SUBTITLE), c);
        c.gridy++;
    }

    public void setTableData(String[][] data, String[] columnNames) {
        table = new JTable(data, columnNames);
        WidgetFactory.styleComponent(table);
        JScrollPane scrollPane = new JScrollPane(table);
        WidgetFactory.styleComponent(scrollPane);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(new JScrollPane(table), c);
        c.gridy++;
        for (String[] row : data) {
            for (String cell : row) {
                System.out.println(cell);
            }
        }
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
