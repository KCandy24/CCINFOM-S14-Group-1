package src.model;

import java.util.ArrayList;

/**
 * TODO: Actually use a SQL Database instead of hardcoding these lists
 */
public class AnimeSystem {
    private String[] titles = {
            "Ao no Kanata Four Rhythms Across the Blue",
            "Cory in the House",
            "Dragon Ball Z",
            "Goblin Slayer",
            "Konosuba: God's Gift on this Wonderful World",
            "Love Live School Idol Project",
            "Love Live Sunshine",
            "Mayo Chiki!",
            "Miss Kobayashi's Dragon Maid",
            "Nichijou",
            "Sankarea",
            "Shrek",
            "Tokyo Ghoul",
            "The Helpful Fox Senko-san",
            "Trinity Seven"
    };

    public AnimeSystem() {

    }

    // Titles
    
    public String[] getTitles() {
        return titles;
    }

    public String minimize(String string) {
        string = string.toLowerCase();
        string.replaceAll(" ", "");
        return string;
    }

    public String[] searchTitles(String text) {
        ArrayList<String> results = new ArrayList<>();
        String minimizedSearch = minimize(text);
        String minimizedTitle;
        for (String title : titles) {
            minimizedTitle = minimize(title);
            if (minimizedTitle.contains(minimizedSearch)) {
                results.add(title);
            }
        }
        return results.toArray(new String[0]);
    }
}
