package src.model;

/**
 * TODO: Made this, but not sure if it's useful; wdygt? - wafl
 */
public enum Records {
    ANIME("animes"),
    USER("users"),
    STAFF("staff"),
    STUDIO("studios");

    private String tableName;

    private Records(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String selectAllQuery() {
        return "SELECT * FROM " + tableName;
    }
}