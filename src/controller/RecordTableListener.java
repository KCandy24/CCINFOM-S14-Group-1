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

    public RecordTableListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
        this.setData();
    }

    // Todo: Parametrize or extend
    public void setData() {
        String[][] data = animeSystem.getAnimes();
        String[] column = {
                "title", "genre", "air_date"
        };
        topView.setRecordTableData("anime", data, column);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int index = topView.getSelected("anime");
        System.out.println(e.getActionCommand());
        for (String data : animeSystem.getAnimes()[index]) {
            System.out.print(data + '\t');
        }
        System.out.println();
    }
}
