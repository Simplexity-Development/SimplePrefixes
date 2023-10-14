package simplexity.simpleprefixes.util;

public enum Permission {

    GUI("simpleprefix.gui"),
    INFO("simpleprefix.info"),
    RELOAD("simpleprefix.reload"),
    RESET("simpleprefix.reset"),
    SET("simpleprefix.set");

    private final String permission;

    Permission(String permission) { this.permission = permission; }
    public String get() { return this.permission; }

}
