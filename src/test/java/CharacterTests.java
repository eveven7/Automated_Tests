import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Character;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class CharacterTests {
    @Test
    public void test1() throws IOException, InterruptedException {
       HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://rickandmortyapi.com/api/character/40"))
                .build();
        System.out.println(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
    }

    @Test
    public void test2() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://rickandmortyapi.com/api/character/40"))
                .build();
        HttpResponse<String> response  = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response code " + response.statusCode());
        Assertions.assertEquals(response.statusCode(), "201");

    }
    @Test
    public void shouldReturnStatusCode200() throws IOException, InterruptedException {
        final Integer expectedCode = 200;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://rickandmortyapi.com/api/character/40"))
                .build();
        HttpResponse<String> response  = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response code " + response.statusCode());
        Assertions.assertEquals(expectedCode, response.statusCode());

    }
    @Test
    public void TestID() throws IOException, InterruptedException {

      final Integer expectedId = 1;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://rickandmortyapi.com/api/character/40"))
                .build();
        HttpResponse<String> response  = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response code " + response.body());
        Assertions.assertEquals(expectedId, response.statusCode());
    }
    @Test
    public void TestContentWithJson() throws IOException, InterruptedException {

        final String expected = Files.readString(Path.of("src/test/resources/expected.json"));
        System.out.println("---------------------");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://rickandmortyapi.com/api/character/1"))
                .build();
        HttpResponse<String> response  = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("---------------------");
        System.out.println("Response code " + response.body());
    }
    private HttpResponse<String> getHttpResponse(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }
    }



    @Test
    public void ContentTestWithObjects() throws JsonProcessingException {
    Character expected = Character.builder()
        .id(1)
        .name("Rick Sanchex")
        .status("Alive")
        .gender("Male")
        .build();
        ObjectMapper mapper = new ObjectMapper();
        HttpResponse<String>response = getHttpResponse("https://rickandmortyapi.com/api/character/1");
        Character actual = mapper.readValue(response.body(), Character.class);
        System.out.println(actual);
    }

}
