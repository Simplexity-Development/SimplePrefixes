import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class TestTwitchAPI {

    static HttpClient client = HttpClient.newHttpClient();
    static Gson gson;
    static String apiSecret;
    static String apiClient;
    static String apiToken;

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        apiSecret = System.getenv("TwitchAPI");
        apiClient = System.getenv("TwitchClientID");
        apiToken = getApiToken();
        System.out.println(apiSecret + " // " + apiClient + " // " + apiToken);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();

        System.out.println("Broadcaster ID of Peashooter101: " + getBroadcasterId("Peashooter101"));
    }

    public static String getApiToken()  throws URISyntaxException {
        File file = new File("apiToken.txt");
        if (file.exists()) {
            Scanner scanner = null;
            try { scanner = new Scanner(file); }
            catch (FileNotFoundException ignored) { }
            if (scanner != null && scanner.hasNextLine()) {
                String token = scanner.nextLine();
                if (validateToken(token)) { return token; }
            }
        }

        String body = String.format("client_id=%s&client_secret=%s&grant_type=client_credentials", apiClient, apiSecret);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://id.twitch.tv/oauth2/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println(request);
        System.out.println(request.headers());
        System.out.println(response);
        System.out.println(response.body());
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        String token = json.get("access_token").getAsString();
        try {
            if (file.exists()) file.delete();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(token);
            writer.close();
        }
        catch (IOException e) { e.printStackTrace(); }

        return token;
    }

    public static boolean validateToken(String token) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://id.twitch.tv/oauth2/validate"))
                .header("Authorization", "Bearer " + token)
                .header("Client-Id", apiClient)
                .GET().build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println(response.statusCode());
        return response.statusCode() == 200;
    }

    public static String getBroadcasterId(String channel) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.twitch.tv/helix/users" + "?login=" + channel))
                .header("Authorization", "Bearer " + apiToken)
                .header("Client-Id", apiClient)
                .GET().build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println(request);
        System.out.println(request.headers());
        System.out.println(response);
        System.out.println(response.body());
        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        return json.getAsJsonArray("data").get(0).getAsJsonObject().get("id").getAsString();
    }

    public static String getChannelInfo(String broadcasterId) throws URISyntaxException {
        HttpRequest.newBuilder()
                .uri(new URI("https://api.twitch.tv/helix/channels"))
                .header("Authorization", "Bearer " + apiToken)
                .header("Client-Id", apiClient);
        return null;
    }

}
