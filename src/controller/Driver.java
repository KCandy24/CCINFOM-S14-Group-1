package src.controller;

import src.model.AnimeSystem;
import src.view.gui.TopView;

/**
 * The Driver contains the entrypoint of the program.
 */
public class Driver {
    public static void main(String[] args) {
        AnimeSystem animeSystem = new AnimeSystem();
        TopView topView = new TopView();
        new Controller(animeSystem, topView);
    }
}
