package src.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.event.DocumentEvent;

import src.model.AnimeSystem;
import src.model.Records;
import src.view.gui.TopView;

/**
 * ? Could be an abstract class?
 */
public class RecordTableListener extends SearchBoxListener implements ActionListener {
    Records associatedRecord;
    String[][] data;
    String[] columns;

    public RecordTableListener(AnimeSystem animeSystem, TopView topView, Records associatedRecord) {
        super(animeSystem, topView);
        this.associatedRecord = associatedRecord;
        this.setData();
    }

    public void setData() {
        if (associatedRecord != Records.ANIME) {
            this.columns = animeSystem.getRecordColNames(associatedRecord.name);
            this.data = animeSystem.selectColumns(this.columns, associatedRecord.name);
        } else {
            // Anime record table is special since we also want the studio names.
            this.columns = animeSystem.getRecordColNames(Records.ANIME.name, Records.STUDIO.name);
            this.data = animeSystem.selectColumns(this.columns,
                    "animes JOIN studios ON animes.studio_id = studios.studio_id");
        }
        topView.initializeRecordTableData(associatedRecord.name, this.data, this.columns);
    }

    /**
     * Override this method in order to customize what happens when a row is
     * selected.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = topView.getSelected(associatedRecord.name);
        HashMap<String, String> rowData = new HashMap<>();

        for (int i = 0; i < this.data[index].length; i++) {
            rowData.put(this.columns[i], this.data[index][i]);
            System.out.printf("%d\t%s : %s\n", i, this.columns[i], this.data[index][i]);
        }
        topView.setFieldsFromData(rowData);
        topView.setLastRowData(rowData);
        topView.setRecordTableVisible(associatedRecord.name, false);
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
