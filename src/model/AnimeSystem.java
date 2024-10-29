package src.model;

import java.util.ArrayList;

/**
 * TODO: Actually use SQL
 */
public class AnimeSystem {
    private String[] titles = {
            "Ao no Kanata Four Rhythms Across the Blue",
            "Dragon Ball Z",
            "Love Live School Idol Project",
            "Love Live Sunshine",
            "Shrek"
    };

    public AnimeSystem() {

    }

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
