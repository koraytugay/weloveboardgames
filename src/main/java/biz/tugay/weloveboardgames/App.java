package biz.tugay.weloveboardgames;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import biz.tugay.weloveboardgames.boardGameGeek.BoardGameGeekClient;
import biz.tugay.weloveboardgames.boardGameGeek.model.BoardGameGeekItem;
import biz.tugay.weloveboardgames.boardGameGeek.model.PollSummary;
import biz.tugay.weloveboardgames.boardGameGeek.model.Result;
import biz.tugay.weloveboardgames.boardGameGeek.model.Stats;
import biz.tugay.weloveboardgames.recommendation.RecommendedGameService;
import biz.tugay.weloveboardgames.templateService.TemplateService;
import com.google.gson.GsonBuilder;
import freemarker.template.TemplateException;

public class App
{
  public static String USER_NAME = "koraytugay";

  public static int MINIMUM_SCORE = 6;

  public static int RECOMMENDATION_COUNT_THRESHOLD = 2;

  public static int RECOMMENDATION_ADJACENCY_LIMIT = 50;

  public static int MINIMUM_AGE_LOWER_LIMIT = 6;

  public static int MINIMUM_AGE_UPPER_LIMIT = 18;

  public static double MINIMUM_AVERAGE_RATING = 0.0;

  public static double MINIMUM_COMPLEXITY = 0.0;

  public static double MAXIMUM_COMPLEXITY = 5.0;

  public static int MAXIMUM_PLAYING_TIME = Integer.MAX_VALUE;

  public void run() throws TemplateException, IOException {
    FileReader reader = new FileReader("config.properties");
    Properties prop = new Properties();
    prop.load(reader);

    USER_NAME = prop.getProperty("bbg.username");
    MINIMUM_SCORE = Integer.parseInt(prop.getProperty("min.score"));
    RECOMMENDATION_COUNT_THRESHOLD = Integer.parseInt(prop.getProperty("recommendation.count.threshold"));
    RECOMMENDATION_ADJACENCY_LIMIT = Integer.parseInt(prop.getProperty("recommendation.adjacency.limit"));
    MINIMUM_AGE_LOWER_LIMIT = Integer.parseInt(prop.getProperty("minimum.age.lower.limit"));
    MINIMUM_AGE_UPPER_LIMIT = Integer.parseInt(prop.getProperty("minimum.age.upper.limit"));
    MINIMUM_AVERAGE_RATING = Double.parseDouble(prop.getProperty("minimum.average.rating", "0.0"));
    MINIMUM_COMPLEXITY = Double.parseDouble(prop.getProperty("minimum.complexity", "0.0"));
    MAXIMUM_COMPLEXITY = Double.parseDouble(prop.getProperty("maximum.complexity", "5.0"));
    MAXIMUM_PLAYING_TIME = Integer.parseInt(prop.getProperty("maximum.playing.time", String.valueOf(Integer.MAX_VALUE)));

    List<BoardGame> boardGames = BoardGameGeekClient.fetchMyBoardGamesByUsername(USER_NAME);

    boardGames = RecommendedGameService.getRecommendedGamesForMyBoardGames(MINIMUM_SCORE, boardGames);
    boardGames = boardGames.stream().filter(boardGame -> boardGame.linkedGames.size() >= RECOMMENDATION_COUNT_THRESHOLD).collect(Collectors.toList());
    boardGames = boardGames.stream().filter(boardGame -> !boardGame.isDontBuy).collect(Collectors.toList());
    boardGames = boardGames.stream().filter(boardGame -> !boardGame.isOwned).collect(Collectors.toList());

    for (BoardGame boardGame : boardGames) {
      BoardGameGeekItem boardGameGeekItem = BoardGameGeekClient.fetchById(boardGame.id);
      //noinspection DataFlowIssue
      populate(boardGame, boardGameGeekItem);
      for (BoardGame linkedGame : boardGame.linkedGames) {
        boardGameGeekItem = BoardGameGeekClient.fetchById(linkedGame.id);
        //noinspection DataFlowIssue
        populate(linkedGame, boardGameGeekItem);
      }
    }

    boardGames = boardGames.stream().filter(boardGame -> Integer.parseInt(boardGame.minimumAge) >= MINIMUM_AGE_LOWER_LIMIT).collect(Collectors.toList());
    boardGames = boardGames.stream().filter(boardGame -> Integer.parseInt(boardGame.minimumAge) <= MINIMUM_AGE_UPPER_LIMIT).collect(Collectors.toList());
    boardGames.sort((o1, o2) -> Double.compare(o2.getLinkScore(), o1.getLinkScore()));

    for (BoardGame boardGame : boardGames) {
      for (BoardGame linkedGame : boardGame.linkedGames) {
        linkedGame.linkedGames.clear();
      }
    }

    // Sort the linked games based on how strongly they recommend this particular game
    for (BoardGame boardGame : boardGames) {
      List<BoardGame> sortedBoardGames = new ArrayList<>();
      List<Integer> indices = new ArrayList<>();
      for (int i = 0; i < boardGame.linkedGames.size(); i++) {
        indices.add(i);
      }
      indices.sort(Comparator.comparingInt(boardGame.linkedGamesStrengths::get));
      for (int index : indices) {
        sortedBoardGames.add(boardGame.linkedGames.get(index));
      }
      boardGame.linkedGames = sortedBoardGames;
    }

    String jsonOutput = new GsonBuilder().setPrettyPrinting().create().toJson(boardGames);
    Files.writeString(Paths.get("dist/weloveboardgames.json"), jsonOutput);
    TemplateService.generateIndexHtml(boardGames);
  }

  private void populate(BoardGame boardGame, BoardGameGeekItem boardGameGeekItem) {
    boardGame.name = boardGameGeekItem.getPrimaryName();
    boardGame.imageUrl = boardGameGeekItem.image;
    boardGame.minimumAge = boardGameGeekItem.minimumAge.value;
    boardGame.minimumNumberOfPlayers = boardGameGeekItem.minPlayers.value;
    boardGame.maximumNumberOfPlayers = boardGameGeekItem.maxPlayers.value;
    boardGame.yearPublished = boardGameGeekItem.yearPublished.value;

    Stats statsObj = boardGameGeekItem.stats != null ? boardGameGeekItem.stats : boardGameGeekItem.statistics;
    if (statsObj != null && statsObj.ratings != null) {
      if (statsObj.ratings.average != null) {
        boardGame.averageRating = statsObj.ratings.average.value;
      }
      if (statsObj.ratings.averageWeight != null) {
        boardGame.complexity = statsObj.ratings.averageWeight.value;
      }
    }

    if (boardGameGeekItem.playingTime != null) {
      boardGame.playingTime = boardGameGeekItem.playingTime.value;
    }
    if (boardGameGeekItem.minPlayTime != null) {
      boardGame.minPlayTime = boardGameGeekItem.minPlayTime.value;
    }
    if (boardGameGeekItem.maxPlayTime != null) {
      boardGame.maxPlayTime = boardGameGeekItem.maxPlayTime.value;
    }

    for (PollSummary pollSummary : boardGameGeekItem.pollSummary) {
      for (Result result : pollSummary.result) {
        if ("bestwith".equals(result.name)) {
          boardGame.bestNumberOfPlayers = result.value;
        }
      }
    }
  }
}
