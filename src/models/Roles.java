package models;


public enum Roles {
    USER("USER"),
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    public static Roles fromValue(String value) {
        if (value != null) {
            for (Roles role : values()) {
                if (role.value.equals(value)) {
                    return role;
                }
            }
        }
        return null;
    }
    public String toValue() {
        return value;
    }
}