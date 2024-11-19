package src.controller;

import java.awt.event.*;
import src.model.AnimeSystem;
import src.view.gui.TopView;
/**
 * The controller communicates between the model and the view.
 */
public class Controller {
    AnimeSystem animeSystem; // model
    TopView topView; // view

    // # Listeners

    RecordTableListener animeRecordTableListener;
    RecordTableListener userRecordTableListener;
    RecordTableListener studioRecordTableListener;
    RecordTableListener staffRecordTableListener;

    // Records
    RecordsTabListener recordsTabListener;

    // Transactions
    ActionListener transactionListener;

    // Reports
    ActionListener reportListener;
    
    /**
     * Initializes the listeners to listen to the view.
     */
    public Controller(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;

        // # Initialize and set listeners

        // Records
        recordsTabListener = new RecordsTabListener(animeSystem, topView);
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB,
                recordsTabListener,
                "searchAnime", "addNewAnime", "saveAnime", "deleteAnime");
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB,
                recordsTabListener,
                "searchUser", "addNewUser", "saveUser", "deleteUser");
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.STUDIO_RECORD_SUBTAB,
                recordsTabListener,
                "searchStudio", "addNewStudio", "saveStudio", "deleteStudio");
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB,
                recordsTabListener,
                "searchStaff", "addNewStaff", "saveStaff", "deleteStaff");

        // Record Tables ("Search Pop-up")

        // TODO: Revise RecordTable to make it work in other tabs aside from the Records
        animeRecordTableListener = new RecordTableListener(animeSystem, topView, "animes", "Records", "Anime");
        userRecordTableListener = new RecordTableListener(animeSystem, topView, "users", "Records", "User");
        staffRecordTableListener = new RecordTableListener(animeSystem, topView, "staff", "Records", "Staff");
        studioRecordTableListener = new RecordTableListener(animeSystem, topView, "studios", "Records", "Studio");

        topView.setRecordTableListener("animes", animeRecordTableListener);
        topView.setRecordTableListener("users", userRecordTableListener);
        topView.setRecordTableListener("staff", staffRecordTableListener);
        topView.setRecordTableListener("studios", studioRecordTableListener);

        // Transactions
        transactionListener = new TransactionAListener(animeSystem, topView);
        topView.setActionListeners(
                TopView.TRANSACTIONS_TAB, TopView.WATCH_EPISODE_TRANSACTION_SUBTAB,
                transactionListener,
                "searchUser", "searchAnime", "watchEpisode");

        // Reports
        reportListener = new ReportsAListener(animeSystem, topView);

    }
}
