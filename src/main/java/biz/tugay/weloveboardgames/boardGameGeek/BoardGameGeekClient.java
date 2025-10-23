package biz.tugay.weloveboardgames.boardGameGeek;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import biz.tugay.weloveboardgames.BoardGame;
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

public class BoardGameGeekClient
{
  static boolean cachingOn = false;

  static XmlMapper XML_MAPPER = new XmlMapper();

  static HttpClientResponseHandler<BoardGameGeekResponse> httpClientResponseHandler = response -> {
    int code = response.getCode();
    if (code == 200) {
      String responseBody = EntityUtils.toString(response.getEntity());
      BoardGameGeekResponse boardGameGeekResponse = XML_MAPPER.readValue(responseBody, BoardGameGeekResponse.class);
      String id = boardGameGeekResponse.items.get(0).id;
      if (id == null) {
        System.err.println("id was null for:" + boardGameGeekResponse.items.get(0).getPrimaryName());
        id = String.valueOf(boardGameGeekResponse.items.get(0).objectId);
        System.err.println("objectId is:" + boardGameGeekResponse.items.get(0).objectId);
      }
      if (cachingOn) {
        Files.writeString(Paths.get("cache", id + ".xml"), responseBody);
      }
      return boardGameGeekResponse;
    }
    return null;
  };

  public static List<BoardGame> fetchMyBoardGamesByUsername(String username) {
    return fetchMyBoardGamesByUsernameWithRetry(username, 0);
  }

  private static List<BoardGame> fetchMyBoardGamesByUsernameWithRetry(String username, int retryCount) {
    cachingOn = false;

    List<BoardGame> boardGames = new ArrayList<>();

    String url = "https://boardgamegeek.com/xmlapi2/collection?username=" + username + "&stats=1";

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      ClassicHttpRequest request = new HttpGet(url);
      BoardGameGeekResponse response = httpClient.execute(request, httpClientResponseHandler);

      if (response == null || response.items == null) {
        if (retryCount < 3) {
          System.err.println("Retry attempt " + (retryCount + 1) + " for fetching collection for user: " + username);
          Thread.sleep(2000);
          return fetchMyBoardGamesByUsernameWithRetry(username, retryCount + 1);
        } else {
          System.err.println("Failed to fetch collection after 3 retries for user: " + username);
          return null;
        }
      }

      List<BoardGameGeekItem> items = response.items;
      for (BoardGameGeekItem item : items) {
        BoardGame boardGame = BoardGame.fromMyBoardGameGeekItem(item);
        boardGames.add(boardGame);
      }

      return boardGames;
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public static BoardGameGeekItem fetchById(int id) {
    return fetchByIdWithRetry(id, 0);
  }

  private static BoardGameGeekItem fetchByIdWithRetry(int id, int retryCount) {
    cachingOn = true;

    Path cachedFilePath = Paths.get("cache", id + ".xml");
    if (cachedFilePath.toFile().exists()) {
      try {
        BoardGameGeekResponse boardGameGeekResponse = XML_MAPPER.readValue(Files.readString(cachedFilePath), BoardGameGeekResponse.class);
        BoardGameGeekItem boardGameGeekItem = boardGameGeekResponse.items.get(0);
        if (boardGameGeekItem.id == null) {
          boardGameGeekItem.id = String.valueOf(boardGameGeekItem.objectId);
        }
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
        if (retryCount < 2) {
          System.err.println("Retry attempt " + (retryCount + 1) + " for fetching game with id: " + id);
          Thread.sleep(2000);
          return fetchByIdWithRetry(id, retryCount + 1);
        } else {
          System.err.println("Failed to fetch game after 3 attempts for id: " + id);
          return null;
        }
      }

      BoardGameGeekItem boardGameGeekItem = response.items.get(0);
      if (boardGameGeekItem.id == null) {
        boardGameGeekItem.id = String.valueOf(boardGameGeekItem.objectId);
      }
      return boardGameGeekItem;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
