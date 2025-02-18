package biz.tugay.weloveboardgames.boardGameGeek;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import biz.tugay.weloveboardgames.boardGameGeek.model.BoardGameGeekItem;
import biz.tugay.weloveboardgames.boardGameGeek.model.BoardGameGeekResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

class BoardGameGeekClient
{
  static XmlMapper XML_MAPPER = new XmlMapper();

  static HttpClientResponseHandler<BoardGameGeekResponse> httpClientResponseHandler = response -> {
    int code = response.getCode();
    if (code == 200) {
      String responseBody = EntityUtils.toString(response.getEntity());
      BoardGameGeekResponse boardGameGeekResponse = XML_MAPPER.readValue(responseBody, BoardGameGeekResponse.class);
      return boardGameGeekResponse;
    }
    return null;
  };

  static List<BoardGameGeekItem> fetchBoardGamesByUsername(String username) {
    String url = "https://boardgamegeek.com/xmlapi2/collection?username=" + username + "&stats=1";

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      ClassicHttpRequest request = new HttpGet(url);
      BoardGameGeekResponse response = httpClient.execute(request, httpClientResponseHandler);

      if (response == null || response.items == null) {
        Thread.sleep(2000);
        return fetchBoardGamesByUsername(username);
      }

      return response.items;
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  static BoardGameGeekItem fetchById(int id) {
    Path cachedFilePath = Paths.get("cache", id + ".json");
    if (cachedFilePath.toFile().exists()) {
      System.out.println("Object found in the file cache.");
      try {
        BoardGameGeekItem boardGameGeekItem =
            new Gson().fromJson(Files.readString(cachedFilePath), BoardGameGeekItem.class);
        boardGameGeekItem.objectId = id;
        return boardGameGeekItem;
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    String url = "https://boardgamegeek.com/xmlapi2/thing?id=" + id + "&stats=1";

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      ClassicHttpRequest request = new HttpGet(url);
      HttpClientResponseHandler<BoardGameGeekResponse> responseHandler = httpClientResponseHandler;
      BoardGameGeekResponse response = httpClient.execute(request, responseHandler);

      if (response == null || response.items == null) {
        System.err.println("First attempt to fetch game details for game with id: " + id + " failed.");
        Thread.sleep(2000);
        response = httpClient.execute(request, responseHandler);
        if (response == null || response.items == null) {
          System.err.println("Second attempt to fetch game details for game with id: " + id + " failed.");
          return null;
        }
      }

      BoardGameGeekItem boardGameGeekItem = response.items.get(0);

      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String itemAsJson = gson.toJson(boardGameGeekItem);
      Files.writeString(Paths.get("cache", boardGameGeekItem.id + ".json"), itemAsJson);

      return boardGameGeekItem;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
