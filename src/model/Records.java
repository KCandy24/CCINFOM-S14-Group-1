package src.model;

/**
 */
public enum Records {
    ANIME("animes", "an anime", "title"),
    USER("users", "a user",
            "user_name"),
    STAFF("staff", "a staff member",
            "first_name", "last_name"),
    STUDIO("studios", "a studio",
            "studio_name");

    public final String name;
    public final String noun;
    public final String[] shownColumnNames;

    private Records(String name, String noun, String... shownColumnNames) {
        this.name = name;
        this.noun = noun;
        this.shownColumnNames = shownColumnNames;
    }
}