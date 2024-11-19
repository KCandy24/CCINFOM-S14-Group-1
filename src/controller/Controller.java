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

    // Per-Tab Listeners
    RecordsTabListener recordsTabListener;
    TransactionsTabListener transactionsTabListener;
    ActionListener reportListener;
    
    /**
     * Initializes the listeners to listen to the view.
     */
    public Controller(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;

        // # Initialize and set listeners

        // ## RecordTableListeners ("search pop-up")
        // TODO: Revise RecordTable to make it work in other tabs aside from the Records
        // ? Solution: Make TopView store the current tab and subtab?
        // ? + Set listeners to the JTabbedPanes to update the current tab, subtab
        animeRecordTableListener = new RecordTableListener(animeSystem, topView, "animes", "Records", "Anime");
        userRecordTableListener = new RecordTableListener(animeSystem, topView, "users", "Records", "User");
        staffRecordTableListener = new RecordTableListener(animeSystem, topView, "staff", "Records", "Staff");
        studioRecordTableListener = new RecordTableListener(animeSystem, topView, "studios", "Records", "Studio");

        topView.setRecordTableListener("animes", animeRecordTableListener);
        topView.setRecordTableListener("users", userRecordTableListener);
        topView.setRecordTableListener("staff", staffRecordTableListener);
        topView.setRecordTableListener("studios", studioRecordTableListener);

        // ## Records Tab
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

        // ## Transactions Tab
        transactionsTabListener = new TransactionsTabListener(animeSystem, topView);
        topView.setActionListeners(
                TopView.TRANSACTIONS_TAB, TopView.WATCH_EPISODE_TRANSACTION_SUBTAB,
                transactionsTabListener,
                "searchUserWatchEpisode", "searchAnimeWatchEpisode", "watchEpisode");
        topView.setActionListeners(
                TopView.TRANSACTIONS_TAB, TopView.RATE_ANIME_TRANSACTION_SUBTAB,
                transactionsTabListener,
                "searchUserRateAnime", "searchAnimeRateAnime", "rateAnime");
        // TODO: Set listeners for edit credits, follow user

        // ## Reports Tab
        reportListener = new ReportsTabListener(animeSystem, topView);
        // TODO: Set report listeners

    }
}
