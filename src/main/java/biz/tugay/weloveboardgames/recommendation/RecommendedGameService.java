package biz.tugay.weloveboardgames.recommendation;

import java.util.List;
import java.util.stream.Collectors;

import biz.tugay.weloveboardgames.App;
import biz.tugay.weloveboardgames.boardGameGeek.BoardGameGeekInMemoryDatabase;
import biz.tugay.weloveboardgames.recommendation.model.RecommendedGame;
import biz.tugay.weloveboardgames.tryTheseGames.TryTheseGamesClient;

public class RecommendedGameService
{
  public static void getRecommendedGames(List<Integer> gameIds) {
    for (Integer gameId : gameIds) {
      System.out.println("Fetching recommendations for: " + gameId);
      List<Integer> recommendedGameIdsForGame = TryTheseGamesClient.getRecommendedGameIdsForGame(gameId);
      System.out.println("Fetched " + recommendedGameIdsForGame.size() + " recommended games for: " + gameId);

      if (recommendedGameIdsForGame.size() > App.recommendationAdjacencyLimit) {
        recommendedGameIdsForGame = recommendedGameIdsForGame.subList(0, App.recommendationAdjacencyLimit);
      }

      RecommendedGamesInMemoryDatabase.addOrIncrementRecommendedGame(gameId, recommendedGameIdsForGame);
    }
  }

  public static void removeAlreadyOwnedGames() {
    List<String> ownedGameIds =
        BoardGameGeekInMemoryDatabase.OWNED_GAMES.stream().map(boardGameGeekItem -> boardGameGeekItem.id).toList();

    RecommendedGamesInMemoryDatabase.RECOMMENDED_GAMES.removeIf(
        recommendedGame -> ownedGameIds.contains(recommendedGame.id));
  }

  public static void removeDoNotBuyGames() {
    List<String> ownedGameIds =
        BoardGameGeekInMemoryDatabase.OWNED_GAMES.stream()
            .filter(boardGameGeekItem -> boardGameGeekItem.status.wishlistPriority == 4)
            .map(boardGameGeekItem -> boardGameGeekItem.id)
            .toList();

    RecommendedGamesInMemoryDatabase.RECOMMENDED_GAMES.removeIf(
        recommendedGame -> ownedGameIds.contains(recommendedGame.id));
  }

  public static List<RecommendedGame> getRecommendedGamesWithMinimumRecommendedCount(int recommendationCountThreshold) {
    List<RecommendedGame> collect = RecommendedGamesInMemoryDatabase.RECOMMENDED_GAMES.stream()
        .filter(recommendedGame -> recommendedGame.recommendationCount >= recommendationCountThreshold).collect(
            Collectors.toList());

    return collect;
  }

  public static List<RecommendedGame> getSortedByRecommendedCountLimitBy(int recommendationCountThreshold) {
    List<RecommendedGame> collect = RecommendedGamesInMemoryDatabase.RECOMMENDED_GAMES.stream()
        .filter(recommendedGame -> recommendedGame.recommendationCount >= recommendationCountThreshold)
        .sorted((o1, o2) -> Integer.compare(o2.recommendationCount, o1.recommendationCount))
        .collect(Collectors.toList());

    return collect;
  }

  public static void fetchThumbnails(final List<RecommendedGame> recommendedGames) {
    for (RecommendedGame recommendedGame : recommendedGames) {
      for (Integer recommendingGameId : recommendedGame.recommendingGameIds) {
        recommendedGame.addRecommendedByThumbnail(BoardGameGeekInMemoryDatabase.getByIdOrFetch(recommendingGameId).thumbnail);
      }
    }
  }
}
