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

    // Records
    AnimeRecordsListener animeRecordsListener;
    UserRecordsListener userRecordsListener;
    StudioRecordsListener studioRecordsListener;
    StaffRecordsListener staffRecordsListener;

    RecordTableListener animeRecordTableListener;
    RecordTableListener userRecordTableListener;
    RecordTableListener studioRecordTableListener;
    RecordTableListener staffRecordTableListener;

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
        // TODO: Consolidate to one RecordsTabListener
        animeRecordsListener = new AnimeRecordsListener(animeSystem, topView);
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB,
                animeRecordsListener,
                "searchAnime", "addNew", "save", "delete");

        userRecordsListener = new UserRecordsListener(animeSystem, topView);
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB,
                userRecordsListener,
                "searchUser", "addNew", "save", "delete");

        studioRecordsListener = new StudioRecordsListener(animeSystem, topView);
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.STUDIO_RECORD_SUBTAB,
                studioRecordsListener,
                "searchStudio", "addNew", "save", "delete");

        staffRecordsListener = new StaffRecordsListener(animeSystem, topView);
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB,
                staffRecordsListener,
                "searchStaff", "addNew", "save", "delete");


        // Record Tables ("Search Pop-up")
        // TODO: RecordTableListeners for Transaction subtabs
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
