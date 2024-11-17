package src.view.widget;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JButton;

import src.controller.RecordTableListener;

public class RecordTable extends JDialog {
    private JTable table;
    private JPanel panel;
    private JButton button;
    private GridBagConstraints c = new GridBagConstraints();

    public RecordTable() {
        this.setMinimumSize(new Dimension(1000, 1000));
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
        table = new JTable(data, columnNames) {
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

        JScrollPane scrollPane = new JScrollPane(
                table,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        WidgetFactory.styleComponent(scrollPane);

        int prevIpadx = c.ipadx;
        c.ipadx = 400;
        panel.add(scrollPane, c);
        c.gridy++;
        for (String[] row : data) {
            for (String cell : row) {
                System.out.println(cell);
            }
        }
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
