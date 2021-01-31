package com.homework.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.homework.exceptions.CountryResolverException;
import com.homework.models.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@Configurable
@PropertySource("classpath:application.properties")
public class CountryResolver {

    @Value("${api.notASecret}")
    private String accessToken;

    private ObjectMapper mapper = new ObjectMapper();

    public Country resolveCountry(String ip) throws CountryResolverException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(String.format("http://api.ipstack.com/%s?access_key=%s", ip, accessToken))).GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                    HttpResponse.BodyHandlers.ofString());

            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
            };
            Map<String, Object> resp = mapper.readValue(response.body(), typeRef);

            Country resolvedCountry = new Country();
            resolvedCountry.setCode((String) resp.get("country_code"));
            resolvedCountry.setName((String) resp.get("country_name"));

            return resolvedCountry;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new CountryResolverException("Couldn't resolve country", e);
        }
    }
}
