package src.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.event.DocumentEvent;

import src.model.AnimeSystem;
import src.view.gui.TopView;

/**
 * ? Could be an abstract class?
 */
public class RecordTableListener extends SearchBoxListener implements ActionListener {
    String recordName;
    String subtabName;
    String[][] data;
    String[] column;

    public RecordTableListener(AnimeSystem animeSystem, TopView topView, String recordName, String subtabName) {
        super(animeSystem, topView);
        this.recordName = recordName;
        this.subtabName = subtabName;
        this.setData();
    }

    public void setData() {
        this.column = animeSystem.getRecordColNames(recordName);
        this.data = animeSystem.query(column, recordName);
        topView.setRecordTableData(recordName, this.data, column);
    }

    /**
     * Override this method in order to customize what happens when a row is
     * selected.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = topView.getSelected(recordName);
        HashMap<String, String> rowData = new HashMap<>();

        for (int i = 0; i < this.data[index].length; i++) {
            rowData.put(this.column[i], this.data[index][i]);
            System.out.printf("%d\t%s : %s\n", i, this.column[i], this.data[index][i]);
        }
        topView.setSubtabFieldFromData(subtabName, rowData);
    }

    /**
     * TODO: Filter out results. Use the name of the text field being updated in
     * order to figure out what exactly is being filtered.
     * 
     * @param e
     */
    @Override
    public void update(DocumentEvent e) {

    }
}
