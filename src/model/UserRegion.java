package src.model;

/**
 * ("Region" is already taken)
 */
public enum UserRegion {
    JP("JP", "Japan"),
    AM("AM", "Americas"),
    EU("EU", "Europe"),
    AS("AS", "Asia"),
    AU("AU", "Australia"),
    AF("AF", "Africa"),
    AC("AC", "Antarctica");

    public final String code;
    public final String name;

    private UserRegion(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String findName(String code) {
        for (UserRegion genre : UserRegion.values()) {
            if (genre.code.equals(code)) {
                return genre.name;
            }
        }
        return null;
    }
}