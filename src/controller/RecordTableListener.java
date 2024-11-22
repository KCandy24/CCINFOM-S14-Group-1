package src.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;

import src.model.AnimeSystem;
import src.model.Records;
import src.view.gui.Subtab;
import src.view.gui.TopView;

/**
 * Listener for the RecordTable widget.
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
        this.attemptRefreshLastWatched();
        topView.setRecordTableVisible(associatedRecord.name, false);
    }

    /**
     * Attempt to refresh any "last watched episode" labels, if any.
     */
    private void attemptRefreshLastWatched() {
        Subtab subtab = topView.getCurrentSubtab();
        try {
            String userId = subtab.getComponentText("userId");
            String animeId = subtab.getComponentText("animeId");
            try {
                Integer.parseInt(userId);
                Integer.parseInt(animeId);
            } catch (NumberFormatException e) {
                // Subtab does not have either userId or animeId.
                return;
            }
            subtab.setComponentText("episode",
                    animeSystem.getProcedureSingleResult(
                            String.format(
                                    "GetLastWatchedQ(%s, %s)",
                                    userId, animeId)));
        } catch (Exception e) {
            // Subtab does not have a last episode watched label.
            return;
        }

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
