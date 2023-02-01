package adhdmc.simpleprefixes.prefix.livestream;

public enum Platform {

    TWITCH("https://twitch.tv/<id>", "<white>[<#6441a5>Live Now</#6441a5>]</white>");

    public final String url;
    public String prefix;

    Platform(String url, String prefix) {
        this.url = url;
        this.prefix = prefix;
    }

    public void setPrefix(String prefix) { this.prefix = prefix; }
    public String getPrefix() { return this.prefix; }

}
