package com.example.eindwerkJava2.api.geo;

import com.example.eindwerkJava2.api.geo.json_model.AuthToken_json;
import com.example.eindwerkJava2.api.geo.json_model.City_json;
import com.example.eindwerkJava2.api.geo.json_model.Country_json;
import com.example.eindwerkJava2.api.geo.json_model.State_json;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@NoArgsConstructor
public class ApiCountriesCities {
    //https://www.universal-tutorial.com/rest-apis/free-rest-api-for-country-state-city
    private final String api_key = "FBu65rQEWHRLlg9VdoM0-scy8e6fMKTfW6m42nIObtX9DI4qLb7HOZBRT8ODWNKekeo";
    private final String eMail = "benibouhayie@gmail.com";
    public AuthToken_json getBearerToken() throws IOException, InterruptedException {
        String API_URL_GET_TOKEN = "https://www.universal-tutorial.com/api/getaccesstoken";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestToken = HttpRequest.newBuilder().GET().
                header("accept", "application/json").
                header("api-token",api_key).
                header("user-email",eMail).
                uri(URI.create(API_URL_GET_TOKEN)).build();
        HttpResponse<String> response = client.send(requestToken,HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        AuthToken_json bearerToken = mapper.readValue(response.body(), new TypeReference<AuthToken_json>(){});
        //System.out.println(bearerToken.getAuth_token());

        return bearerToken;

    }

    public List<Country_json> getCountries() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        String API_URL_GET_COUNTRIES = "https://www.universal-tutorial.com/api/countries";

        AuthToken_json bearerToken = getBearerToken();

        HttpRequest requestCountries = HttpRequest.newBuilder().GET().
                header("accept", "application/json").
                header("Authorization",bearerToken.getAuth_token()).
                header("api-token",api_key).
                header("user-email",eMail).
                uri(URI.create(API_URL_GET_COUNTRIES)).build();
        HttpResponse <String> responseCountries = client.send(requestCountries,HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        List<Country_json> countries = mapper.readValue(responseCountries.body(), new TypeReference<List<Country_json>>(){});
        System.out.println(responseCountries.body());
        return countries;
    }

    public List<State_json> getStates(String country) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        String API_URL_GET_CITIES = "https://www.universal-tutorial.com/api/states/"+country.replaceAll(" ","%20");

        AuthToken_json bearerToken = getBearerToken();

        HttpRequest requestCountries = HttpRequest.newBuilder().GET().
                header("accept", "application/json").
                header("Authorization",bearerToken.getAuth_token()).
                header("api-token",api_key).
                header("user-email",eMail).
                uri(URI.create(API_URL_GET_CITIES)).build();
        HttpResponse <String> responseStates = client.send(requestCountries,HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        List<State_json> states = mapper.readValue(responseStates.body(), new TypeReference<List<State_json>>(){});
        System.out.println(responseStates.body());
        return states;
    }
    public List<City_json> getCities(String state) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        String API_URL_GET_CITIES = "https://www.universal-tutorial.com/api/cities/"+state.replaceAll(" ","%20");

        AuthToken_json bearerToken = getBearerToken();

        HttpRequest requestCountries = HttpRequest.newBuilder().GET().
                header("accept", "application/json").
                header("Authorization",bearerToken.getAuth_token()).
                header("api-token",api_key).
                header("user-email",eMail).
                uri(URI.create(API_URL_GET_CITIES)).build();
        HttpResponse <String> responseCities = client.send(requestCountries,HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        List<City_json> cities = mapper.readValue(responseCities.body(), new TypeReference<List<City_json>>(){});
        System.out.println(responseCities.body());
        return cities;
    }
}

