package simplexity.simpleprefixes.util;

public enum Permission {

    GUI("simpleprefix.gui"),
    INFO("simpleprefix.info"),
    RELOAD("simpleprefix.reload"),
    RESET("simpleprefix.reset"),
    RESET_OTHER("simpleprefix.reset.other"),
    SET("simpleprefix.set"),
    SET_OTHER("simpleprefix.set.other");

    private final String permission;

    Permission(String permission) { this.permission = permission; }
    public String get() { return this.permission; }

}
