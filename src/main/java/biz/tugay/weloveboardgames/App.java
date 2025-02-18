package biz.tugay.weloveboardgames;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import biz.tugay.weloveboardgames.boardGameGeek.BoardGameGeekInMemoryDatabase;
import biz.tugay.weloveboardgames.recommendation.RecommendedGameService;
import biz.tugay.weloveboardgames.recommendation.RecommendedGamesInMemoryDatabase;
import biz.tugay.weloveboardgames.recommendation.model.RecommendedGame;
import biz.tugay.weloveboardgames.templateService.TemplateService;
import com.google.gson.Gson;
import freemarker.template.TemplateException;

public class App
{

  public static int minimumScore = 6;

  public static int recommendationCountThreshold = 2;

  public static int recommendationAdjacencyLimit = 35;

  public static int MINIMUM_AGE_UPPER_LIMIT = 10;

  public static int MINIMUM_AGE_LOWER_LIMIT = 8;

  public void run() throws TemplateException, IOException {
    BoardGameGeekInMemoryDatabase.populateOwnedGamesByUsername("koraytugay");
    List<Integer> gameIdsOwnedWithMinimumScore = BoardGameGeekInMemoryDatabase.getIdsOfOwnedGamesWithMinimumScore(
        minimumScore);

    RecommendedGameService.getRecommendedGames(gameIdsOwnedWithMinimumScore);
    RecommendedGameService.removeAlreadyOwnedGames();
    RecommendedGameService.removeDoNotBuyGames();

    List<RecommendedGame> recommendedGames = RecommendedGameService.getRecommendedGamesWithMinimumRecommendedCount(
        recommendationCountThreshold);
    for (RecommendedGame recommendedGame : recommendedGames) {
      RecommendedGamesInMemoryDatabase.populateRecommendedGameDetails(recommendedGame.id);
    }

    recommendedGames = RecommendedGameService.getSortedByRecommendedCountLimitBy(recommendationCountThreshold);

    recommendedGames = recommendedGames.stream()
        .filter(recommendedGame -> Integer.parseInt(recommendedGame.minimumAge) <= MINIMUM_AGE_UPPER_LIMIT)
        .filter(recommendedGame -> Integer.parseInt(recommendedGame.minimumAge) >= MINIMUM_AGE_LOWER_LIMIT)
        .collect(Collectors.toList());

    RecommendedGameService.fetchThumbnails(recommendedGames);

    String json = new Gson().toJson(recommendedGames);
    System.out.println(json);
    TemplateService.foo(recommendedGames);
  }
}
