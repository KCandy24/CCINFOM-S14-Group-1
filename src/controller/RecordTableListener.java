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

    public RecordTableListener(AnimeSystem animeSystem, TopView topView, String recordName) {
        this.animeSystem = animeSystem;
        this.topView = topView;
        this.recordName = recordName;
        this.setData();
    }

    public void setData() {
        String[][] data;
        String[] column;

        // TODO: This can be made more concise
        // Change the cases, and use Kurt's procedure to get all column names.
        switch (recordName) {
            default:
                System.out.println(recordName + " is not a record.");
            case "animes":
                column = new String[] {
                        "studio_id", "title", "genre", "air_date", "num_of_episodes", "available_from_date",
                        "available_to_date"
                };
                break;
            case "users":
                column = new String[] {
                        "user_id", "user_name", "region", "join_date"
                };
                break;
            case "staff":
                column = new String[] {
                        "staff_id", "first_name", "last_name",
                        "occupation", "birthday"
                };
                break;
            case "studios":
                column = new String[] {
                        "studio_id", "studio_name"
                };
                break;
        }
        data = animeSystem.query(column, recordName);
        topView.setRecordTableData(recordName, data, column);
    }

    // TODO: Use arbitrary tables
    @Override
    public void actionPerformed(ActionEvent e) {
        // int index = topView.getSelected("animes");
        int index = topView.getSelected("animes");
        System.out.println(e.getActionCommand());
        for (String data : animeSystem.getAnimes()[index]) {
            System.out.print(data + '\t');
        }
        System.out.println();
    }
}
