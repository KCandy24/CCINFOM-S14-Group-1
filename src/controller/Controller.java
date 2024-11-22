package src.controller;

import java.awt.event.*;
import src.model.AnimeSystem;
import src.model.Records;
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

    CurrentTabListener currentTabListener;
    CurrentSubtabListener currentSubtabListener;

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

        currentTabListener = new CurrentTabListener(topView);
        currentSubtabListener = new CurrentSubtabListener(topView);
        topView.setTabListeners(currentTabListener, currentSubtabListener);

        for (Records record : Records.values()) {
            System.out.println("Setting record table for " + record.name);
            topView.setRecordTableListener(record.name, new RecordTableListener(animeSystem, topView, record));
        }

        recordsTabListener = new RecordsTabListener(animeSystem, topView);
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB,
                recordsTabListener,
                "searchAnime", "searchStudio", "addNewAnime", "saveAnime", "deleteAnime");
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB,
                recordsTabListener,
                "searchUser", "addNewUser", "saveUser", "deleteUser");
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.STUDIO_RECORD_SUBTAB,
                recordsTabListener,
                "searchStudio", "saveStudio", "deleteStudio", "searchAllAnime", "addNewStudio");
        topView.setActionListeners(
                TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB,
                recordsTabListener,
                "searchStaff", "addNewStaff", "saveStaff", "deleteStaff");

        transactionsTabListener = new TransactionsTabListener(animeSystem, topView);
        topView.setActionListeners(
                TopView.TRANSACTIONS_TAB, TopView.WATCH_EPISODE_TRANSACTION_SUBTAB,
                transactionsTabListener,
                "searchUser", "searchAnime", "watchEpisode");
        topView.setActionListeners(
                TopView.TRANSACTIONS_TAB, TopView.RATE_ANIME_TRANSACTION_SUBTAB,
                transactionsTabListener,
                "searchUser", "searchAnime", "saveRating", "loadRating");
        topView.setActionListeners(
                TopView.TRANSACTIONS_TAB, TopView.EDIT_CREDITS_TRANSACTION_SUBTAB,
                transactionsTabListener,
                "searchAnime", "searchStaff", "saveCredits", "deleteCredits", "loadCredits");
        topView.setActionListeners(
                TopView.TRANSACTIONS_TAB, TopView.FOLLOW_USER_TRANSACTION_SUBTAB,
                transactionsTabListener,
                "searchFollower", "searchFollowed", "follow", "unfollow");

        reportListener = new ReportsTabListener(animeSystem, topView);
        topView.setActionListeners(
                TopView.REPORTS_TAB, TopView.HIGHEST_RATED_ANIME_REPORT_SUBTAB,
                reportListener,
                "checkButton");
        topView.setActionListeners(
                TopView.REPORTS_TAB, TopView.RECOMMEND_ANIME_REPORT_SUBTAB,
                reportListener,
                "searchUser", "checkRecommendedAnime");
        topView.setActionListeners(
                TopView.REPORTS_TAB, TopView.TOP_STUDIOS_REPORT_SUBTAB,
                reportListener, "checkTopStudio");
        topView.setActionListeners(
                TopView.REPORTS_TAB, TopView.USER_PROFILE_REPORT_SUBTAB,
                reportListener,
                "searchUser", "checkUserProfile");
    }
}
