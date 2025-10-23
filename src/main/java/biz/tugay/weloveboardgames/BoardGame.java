package biz.tugay.weloveboardgames;

import java.util.ArrayList;
import java.util.List;

import biz.tugay.weloveboardgames.boardGameGeek.model.BoardGameGeekItem;

public class BoardGame
{
  public Integer id;

  public String name;

  public String yearPublished;

  public String imageUrl;

  public String thumbnailUrl;

  public String minimumNumberOfPlayers;

  public String maximumNumberOfPlayers;

  public String bestNumberOfPlayers;

  public String minimumAge;

  public List<BoardGame> linkedGames = new ArrayList<>();

  public List<Integer> linkedGamesStrengths = new ArrayList<>();

  public boolean isOwned;

  public boolean isDontBuy;

  public Integer myRating;

  public String averageRating;

  public String complexity;

  public String playingTime;

  public String minPlayTime;

  public String maxPlayTime;

  public static BoardGame fromMyBoardGameGeekItem(BoardGameGeekItem item) {
    BoardGame boardGame = new BoardGame();
    boardGame.id = item.objectId;
    boardGame.name = item.name.get(0).value;
    if (item.yearPublished != null) {
      boardGame.yearPublished = item.yearPublished.value;
    }
    boardGame.imageUrl = item.image;
    boardGame.thumbnailUrl = item.thumbnail;
    boardGame.isOwned = item.status.own == 1;
    boardGame.isDontBuy = item.status.wishlistpriority == 5;
    try {
      boardGame.myRating = Integer.parseInt(item.stats.rating.value);
    }
    catch (Exception ignored) {
    }
    return boardGame;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getYearPublished() {
    return yearPublished;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getThumbnailUrl() {
    return thumbnailUrl;
  }

  public String getMinimumNumberOfPlayers() {
    return minimumNumberOfPlayers;
  }

  public String getMaximumNumberOfPlayers() {
    return maximumNumberOfPlayers;
  }

  public String getBestNumberOfPlayers() {
    return bestNumberOfPlayers;
  }

  public String getMinimumAge() {
    return minimumAge;
  }

  public String getAverageRating() {
    return averageRating;
  }

  public String getComplexity() {
    return complexity;
  }

  public String getPlayingTime() {
    return playingTime;
  }

  public String getMinPlayTime() {
    return minPlayTime;
  }

  public String getMaxPlayTime() {
    return maxPlayTime;
  }

  public List<BoardGame> getLinkedGames() {
    return linkedGames;
  }

  public List<Integer> getLinkedGamesStrengths() {
    return linkedGamesStrengths;
  }

  public boolean isOwned() {
    return isOwned;
  }

  public boolean isDontBuy() {
    return isDontBuy;
  }

  public Integer getMyRating() {
    return myRating;
  }

  public String getBoardGameGeekUrl() {
    return "https://boardgamegeek.com/boardgame/" + id;
  }

  public double getLinkScore() {
    double score = 0;
    for (int i = 0; i < linkedGames.size(); i++) {
      int strength = linkedGamesStrengths.get(i);
      int rating = linkedGames.get(i).myRating;

      double positionMultiplier = 1.5;
      if (strength >= 5 && strength < 15) {
        positionMultiplier = 1.25;
      } else if (strength >= 15 && strength < 30) {
        positionMultiplier = 1.0;
      } else if (strength >= 30) {
        positionMultiplier = 0.6;
      }

      double ratingBonus = 1.0;
      if (rating >= 9) {
        ratingBonus = 1.3;
      } else if (rating >= 8) {
        ratingBonus = 1.15;
      } else if (rating >= 7) {
        ratingBonus = 1.05;
      }

      score = score + (positionMultiplier * rating * ratingBonus);
    }
    return score;
  }
}
