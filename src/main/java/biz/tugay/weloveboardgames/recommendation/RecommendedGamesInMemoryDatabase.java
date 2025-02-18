package biz.tugay.weloveboardgames.recommendation;

import java.util.ArrayList;
import java.util.List;

import biz.tugay.weloveboardgames.boardGameGeek.BoardGameGeekInMemoryDatabase;
import biz.tugay.weloveboardgames.boardGameGeek.model.BoardGameGeekItem;
import biz.tugay.weloveboardgames.boardGameGeek.model.PollSummary;
import biz.tugay.weloveboardgames.boardGameGeek.model.Result;
import biz.tugay.weloveboardgames.recommendation.model.RecommendedGame;

public class RecommendedGamesInMemoryDatabase
{
  public static List<RecommendedGame> RECOMMENDED_GAMES = new ArrayList<>();

  public static void addOrIncrementRecommendedGame(Integer gameId, List<Integer> recommendedGameIds) {
    ArrayList<RecommendedGame> newGames = new ArrayList<>();

    for (Integer recommendedGameId : recommendedGameIds) {
      RecommendedGame game = new RecommendedGame();
      game.id = Integer.toString(recommendedGameId);
      if (RECOMMENDED_GAMES.contains(game)) {
        int index = RECOMMENDED_GAMES.indexOf(game);
        RECOMMENDED_GAMES.get(index).recommendationCount++;
        RECOMMENDED_GAMES.get(index).addRecommendedById(gameId);
      }
      else {
        RecommendedGame recommendedGame = new RecommendedGame();
        recommendedGame.id = game.id;
        recommendedGame.recommendationCount = 1;
        recommendedGame.addRecommendedById(gameId);
        newGames.add(recommendedGame);
      }
    }

    RECOMMENDED_GAMES.addAll(newGames);
  }

  public static void populateRecommendedGameDetails(String id) {
    BoardGameGeekItem byIdOrFetch = BoardGameGeekInMemoryDatabase.getByIdOrFetch(Integer.valueOf(id));
    for (RecommendedGame recommendedGame : RECOMMENDED_GAMES) {
      if (recommendedGame.id.equals(id)) {
        recommendedGame.name = byIdOrFetch.getPrimaryName();
        recommendedGame.yearPublished = byIdOrFetch.yearPublished.value;
        recommendedGame.imageUrl = byIdOrFetch.image;
        recommendedGame.minimumNumberOfPlayers = byIdOrFetch.minPlayers.value;
        recommendedGame.maximumNumberOfPlayers = byIdOrFetch.maxPlayers.value;
        recommendedGame.minimumAge = byIdOrFetch.minimumAge.value;

        for (PollSummary pollSummary : byIdOrFetch.pollSummary) {
          for (Result result : pollSummary.result) {
            if ("bestwith".equals(result.name)) {
              recommendedGame.bestNumberOfPlayers = result.value;
            }
          }
        }

        for (Integer recommendingGameId : recommendedGame.recommendingGameIds) {
          byIdOrFetch = BoardGameGeekInMemoryDatabase.getByIdOrFetch(recommendingGameId);
          recommendedGame.addRecommendedByName(byIdOrFetch.getPrimaryName());
        }
      }
    }
  }
}
