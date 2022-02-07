package tools.wesley.wpscanner.wp;

import org.springframework.stereotype.Service;
import tools.wesley.wpscanner.controllers.HttpException;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;

@Service
public class CustomHttpClient {
    public HttpResponse<String> getPage(String url) throws HttpException {
        var client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new HttpException("Unable to send GET request to: " + url, e);
        }
    }
}