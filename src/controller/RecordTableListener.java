package src.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.model.AnimeSystem;
import src.view.gui.TopView;

/**
 * ? Could be an abstract class?
 */
public class RecordTableListener implements ActionListener {
    AnimeSystem animeSystem;
    TopView topView;
    String recordName;
    String[][] data;
    String[] column;

    public RecordTableListener(AnimeSystem animeSystem, TopView topView, String recordName) {
        this.animeSystem = animeSystem;
        this.topView = topView;
        this.recordName = recordName;
        this.setData();
    }

    public void setData() {
        this.column = animeSystem.getRecordColNames(recordName);
        this.data = animeSystem.query(column, recordName);
        topView.setRecordTableData(recordName, this.data, column);
    }

    // TODO: Implementing actions
    // ? Idea: HashMap<recordName, HashMap<columnName, componentName>>?
    @Override
    public void actionPerformed(ActionEvent e) {
        // int index = topView.getSelected("animes");
        int index = topView.getSelected(recordName);
        System.out.println(e.getActionCommand());
        for (String data : this.data[index]) {
            System.out.print(data + '\t');
        }
        System.out.println();
    }
}
