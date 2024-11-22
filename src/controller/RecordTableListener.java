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

    public RecordTableListener(AnimeSystem animeSystem, TopView topView, Records associatedRecord) {
        super(animeSystem, topView);
        this.associatedRecord = associatedRecord;
        this.setData();
    }

    public void setData() {
        String[] columns;
        String[][] data;
        if (associatedRecord != Records.ANIME) {
            columns = animeSystem.getRecordColNames(associatedRecord.name);
            data = animeSystem.selectColumns(columns, associatedRecord.name);
        } else {
            // Anime record table is special since we also want the studio names.
            columns = animeSystem.getRecordColNames(Records.ANIME.name, Records.STUDIO.name);
            data = animeSystem.selectColumns(columns,
                    "animes JOIN studios ON animes.studio_id = studios.studio_id");
        }
        topView.initializeRecordTableData(associatedRecord.name, data, columns);
    }

    /**
     * Override this method in order to customize what happens when a row is
     * selected.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        HashMap<String, String> rowData = topView.getSelectedRowData(associatedRecord);
        topView.setFieldsFromData(rowData);
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
