package src.model;

/**
 */
public enum Records {
    ANIME("animes", "an anime", "animes.title"),
    USER("users", "a user",
            "users.user_name"),
    STAFF("staff", "a staff member",
            "staff.first_name", "staff.last_name"),
    STUDIO("studios", "a studio",
            "studios.studio_name");

    public final String name;
    public final String noun;
    public final String[] shownColumnNames;

    private Records(String name, String noun, String... shownColumnNames) {
        this.name = name;
        this.noun = noun;
        this.shownColumnNames = shownColumnNames;
    }
}