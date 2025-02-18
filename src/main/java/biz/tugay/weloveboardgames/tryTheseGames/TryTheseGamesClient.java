package biz.tugay.weloveboardgames.tryTheseGames;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class TryTheseGamesClient
{
  static HttpClientResponseHandler<String> responseHandler = response -> {
    if (response.getCode() == 200) {
      return EntityUtils.toString(response.getEntity());
    }
    return null;
  };

  public static List<Integer> getRecommendedGameIdsForGame(int id) {
    Path cachedFilePath = Paths.get("recommendations-cache", id + ".json");
    if (cachedFilePath.toFile().exists()) {
      System.out.println("Object found in the file cache.");
      try {
        Integer[] integers = new Gson().fromJson(Files.readString(cachedFilePath), Integer[].class);
        return Arrays.asList(integers);
      }
      catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    String url = "https://trythesegames.com/api/games/" + id + "/recommendations?GameId=" + id +
        "&HideLinkedItems=true&HideMatchingDesigners=false&Skip=0";

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      String responseBody = httpClient.execute(new HttpGet(url), responseHandler);
      List<Integer> recommendedGameIds = JsonPath.read(responseBody, "$.Items[*].Id");

      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String itemAsJson = gson.toJson(recommendedGameIds);
      Files.writeString(Paths.get("recommendations-cache", id + ".json"), itemAsJson);

      return recommendedGameIds;
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
