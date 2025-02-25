package biz.tugay.weloveboardgames.recommendation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biz.tugay.weloveboardgames.BoardGame;
import biz.tugay.weloveboardgames.tryTheseGames.TryTheseGamesClient;

import static biz.tugay.weloveboardgames.App.RECOMMENDATION_ADJACENCY_LIMIT;

public class RecommendedGameService
{
  public static List<BoardGame> getRecommendedGamesForMyBoardGames(int minimumScore, List<BoardGame> boardGames) {
    ArrayList<BoardGame> boardGamesCopy = new ArrayList<>(boardGames);

    Map<Integer, BoardGame> allBoardGames = new HashMap<>();
    for (BoardGame boardGame : boardGames) {
      allBoardGames.put(boardGame.id, boardGame);
    }

    for (BoardGame boardGame : boardGames) {
      if (boardGame.myRating != null && boardGame.myRating >= minimumScore) {
        List<Integer> recommendedGameIdsForGame = TryTheseGamesClient.getRecommendedGameIdsForGame(boardGame.id);
        if (recommendedGameIdsForGame != null) {
          if (recommendedGameIdsForGame.size() > RECOMMENDATION_ADJACENCY_LIMIT) {
            recommendedGameIdsForGame = recommendedGameIdsForGame.subList(0, RECOMMENDATION_ADJACENCY_LIMIT);
          }
          for (int i = 0; i < recommendedGameIdsForGame.size(); i++) {
            BoardGame bg = allBoardGames.get(recommendedGameIdsForGame.get(i));
            if (bg == null) {
              BoardGame newBoardGame = new BoardGame();
              newBoardGame.id = recommendedGameIdsForGame.get(i);
              newBoardGame.linkedGames.add(boardGame);
              newBoardGame.linkedGamesStrengths.add(i);
              boardGamesCopy.add(newBoardGame);
              allBoardGames.put(recommendedGameIdsForGame.get(i), newBoardGame);
            }
            else {
              bg.linkedGames.add(boardGame);
              bg.linkedGamesStrengths.add(i);
            }
          }
        }
      }
    }

    return boardGamesCopy;
  }
}
