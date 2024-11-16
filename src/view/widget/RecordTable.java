package src.view.widget;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;

import src.controller.RecordTableListener;

public class RecordTable extends JDialog {
    private JTable table;
    private JPanel panel;
    private JButton button;

    public RecordTable() {
        this.setMinimumSize(new Dimension(400, 400));
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(WidgetFactory.createJLabel("Select a record", WidgetFactory.Fonts.SUBTITLE));
    }

    public void setTableData(String[][] data, String[] columnNames) {
        table = new JTable(data, columnNames);
        panel.add(table);
        for (String[] row : data) {
            for (String cell : row) {
                System.out.println(cell);
            }
        }
        button = WidgetFactory.createJButton("Select");
        panel.add(button);
        this.add(panel);
    }

    public void setListener(RecordTableListener listener) {
        button.addActionListener(listener);
    }

    public int getSelected() {
        return table.getSelectedRow();
    }
}
