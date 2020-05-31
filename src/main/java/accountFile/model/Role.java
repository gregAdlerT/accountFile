package main.java.accountFile.model;


public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public static Role fromValue(String value) {
        if (value != null) {
            for (Role role : values()) {
                if (role.toValue().equalsIgnoreCase(value)) {
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