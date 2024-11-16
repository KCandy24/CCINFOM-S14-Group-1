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

    // Listeners

    // TODO: Deprecate
    // TitlesSearchBoxListener titlesSearchBoxListener;
    // --

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
        // Initialize and set listeners

        // TODO: Deprecate
        // titlesSearchBoxListener = new TitlesSearchBoxListener(animeSystem, topView);
        // topView.setSearchBoxListener(titlesSearchBoxListener);
        // --

        // Records
        animeRecordsListener = new AnimeRecordsListener(animeSystem, topView);
        userRecordsListener = new UserRecordsListener(animeSystem, topView);
        studioRecordsListener = new StudioRecordsListener(animeSystem, topView);
        staffRecordsListener = new StaffRecordsListener(animeSystem, topView);
        topView.setAnimeRecordsListener(animeRecordsListener);
        topView.setUserRecordsListener(userRecordsListener);
        topView.setStudioRecordsListener(studioRecordsListener);
        topView.setStaffRecordsListener(staffRecordsListener);

        // Record Tables
        animeRecordTableListener = new RecordTableListener(animeSystem, topView, "animes");
        userRecordTableListener = new RecordTableListener(animeSystem, topView, "users");
        studioRecordTableListener = new RecordTableListener(animeSystem, topView, "studios");
        staffRecordTableListener = new RecordTableListener(animeSystem, topView, "staff");
        topView.setRecordTableListener("animes", animeRecordTableListener);
        topView.setRecordTableListener("users", userRecordTableListener);
        topView.setRecordTableListener("studios", studioRecordTableListener);
        topView.setRecordTableListener("staff", staffRecordTableListener);

        // Transactions
        transactionListener = new TransactionAListener(animeSystem, topView);

        // Reports
        reportListener = new ReportsAListener(animeSystem, topView);

    }
}
