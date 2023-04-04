import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Character;
import org.example.domain.Episode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.RequestUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class EpisodeTests {
    static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturnCharacterAmountInEpisode() throws JsonProcessingException {
        String url = "https://rickandmortyapi.com/api/episode/1";
        HttpResponse<String> response = getResponse(url);
        Episode result = mapper.readValue(response.body(), Episode.class);
        System.out.println(result);
        Assertions.assertEquals(result.getCharacters().size(), 19);
    }

    @Test
    public void shouldTestEpisodeResponse() throws JsonProcessingException {
        String url = "https://rickandmortyapi.com/api/episode/1";
        HttpResponse<String> response = getResponse(url);
        Episode result = mapper.readValue(response.body(), Episode.class);
        Episode expected = Episode.builder()
                .id(1L)
                .name("Pilot")
                .episode("S01E01")
                .air_date("December 2, 2013")
                .characters(List.of("https://rickandmortyapi.com/api/character/1",
                        "https://rickandmortyapi.com/api/character/2",
                        "https://rickandmortyapi.com/api/character/35",
                        "https://rickandmortyapi.com/api/character/38",
                        "https://rickandmortyapi.com/api/character/62",
                        "https://rickandmortyapi.com/api/character/92",
                        "https://rickandmortyapi.com/api/character/127",
                        "https://rickandmortyapi.com/api/character/144",
                        "https://rickandmortyapi.com/api/character/158",
                        "https://rickandmortyapi.com/api/character/175",
                        "https://rickandmortyapi.com/api/character/179",
                        "https://rickandmortyapi.com/api/character/181",
                        "https://rickandmortyapi.com/api/character/239",
                        "https://rickandmortyapi.com/api/character/249",
                        "https://rickandmortyapi.com/api/character/271",
                        "https://rickandmortyapi.com/api/character/338",
                        "https://rickandmortyapi.com/api/character/394",
                        "https://rickandmortyapi.com/api/character/395",
                        "https://rickandmortyapi.com/api/character/435"))
                .build();
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void TestIfEpisodeContainsASpecificCharacterByUrl() throws JsonProcessingException {
        String url = "https://rickandmortyapi.com/api/episode/17";
        HttpResponse<String> response = getResponse(url);
        Episode result = mapper.readValue(response.body(), Episode.class);
        Assertions.assertTrue(result.getCharacters().contains("https://rickandmortyapi.com/api/character/1"));
    }

    @Test
    public void TestIfEpisodeContainsASpecificCharacterByObject() throws JsonProcessingException {
        String url = "https://rickandmortyapi.com/api/episode/17";
        HttpResponse<String> response = getResponse(url);
        Episode result = mapper.readValue(response.body(), Episode.class);
        Character actual = mapper.readValue(RequestUtil.getResponse(result.getCharacters().get(0)).body(), Character.class);
        Character expected = createRickSanchez();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturn404ResponseCode() {
        String url = "https://rickandmortyapi.com/api/episode/9999";
        HttpResponse<String> response = getResponse(url);
        Assertions.assertEquals(response.statusCode(), 404);
    }

    @Test
    public void shouldReturn200ResponseCode() {
        String url = "https://rickandmortyapi.com/api/episode/10";
        HttpResponse<String> response = getResponse(url);
        Assertions.assertEquals(response.statusCode(), 200);
    }

    static Character createRickSanchez() {

        return Character.builder()
                .id(1L)
                .name("Rick Sanchez")
                .gender("Male")
                .type("")
                .species("Human")
                .status("Alive")
                .build();
    }

    static HttpResponse<String> getResponse(String url) {

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException();
        }
    }
}