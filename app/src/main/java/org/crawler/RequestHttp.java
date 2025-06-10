package org.crawler;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


/**
 * author ;
 */
public class RequestHttp {

  private final HttpClient httpClient = HttpClient.newBuilder()
          .version(HttpClient.Version.HTTP_1_1)
          .connectTimeout(Duration.ofSeconds(10))
          .build();

  public void main(String url) throws IOException, InterruptedException {

      HttpRequest request = HttpRequest.newBuilder()
              .GET()
              .uri(URI.create(url))
              .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
              .build();

      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      // print response headers
      HttpHeaders headers = response.headers();
      headers.map().forEach((k, v) -> System.out.println(k + ":" + v));

      // print status code
      System.out.println(response.statusCode());

      // print response body
      System.out.println(response.body());

  }

    public void post(String url, Map<Object, Object> data) throws IOException, InterruptedException {

        // form parameters
        // Map<Object, Object> data = new HashMap<>();
        // data.put("username", "abc");
        // data.put("password", "123");
        // data.put("custom", "secret");
        // data.put("ts", System.currentTimeMillis());

        HttpRequest request = HttpRequest.newBuilder()
                .POST(ofFormData(data))
                .uri(URI.create(url))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

    // Sample: 'password=123&custom=secret&username=abc&ts=1570704369823'
    public static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    public void post_json(String url, String json) throws IOException, InterruptedException {

        // json formatted data
        // String json = new StringBuilder()
        //         .append("{")
        //         .append("\"name\":\"mkyong\",")
        //         .append("\"notes\":\"hello\"")
        //         .append("}").toString();

        // add json header
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(url))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());

        // print response body
        System.out.println(response.body());

    }

}
