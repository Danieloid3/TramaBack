package taller2.tramaback.Services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TMDbService {
    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public TMDbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }
    public String buscarPeliculas(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl + "/search/movie")
                .queryParam("api_key", apiKey)
                .queryParam("query", query)
                .build()
                .toUriString();

        return restTemplate.getForObject(url, String.class);
    }
}
