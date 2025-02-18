package biz.tugay.weloveboardgames.boardGameGeek;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import biz.tugay.weloveboardgames.boardGameGeek.model.BoardGameGeekItem;
import com.google.gson.Gson;

public class BoardGameGeekInMemoryDatabase
{
  public static Set<BoardGameGeekItem> OWNED_GAMES = new HashSet<>();

  public static Set<BoardGameGeekItem> BOARD_GAME_GEEK_ITEMS = new HashSet<>();

  public static void populateOwnedGamesByUsername(String username) {
    System.out.println("Fetching recommended games for: " + username);
    List<BoardGameGeekItem> boardGameGeekItems = BoardGameGeekClient.fetchBoardGamesByUsername(username);
    OWNED_GAMES.addAll(boardGameGeekItems);
    for (BoardGameGeekItem boardGameGeekItem : boardGameGeekItems) {
      boardGameGeekItem.id = String.valueOf(boardGameGeekItem.objectId);
    }

    for (BoardGameGeekItem boardGameGeekItem : boardGameGeekItems) {
      getByIdOrFetch(boardGameGeekItem.objectId);
    }

    System.out.println("Fetched recommended games for: " + username);
  }

  public static List<Integer> getIdsOfOwnedGamesWithMinimumScore(int minimumScore) {
    List<Integer> gameIds = BoardGameGeekInMemoryDatabase.OWNED_GAMES.stream()
        .filter(boardGameGeekItem -> boardGameGeekItem.stats != null)
        .filter(boardGameGeekItem -> boardGameGeekItem.stats.rating != null)
        .filter(boardGameGeekItem -> boardGameGeekItem.stats.rating.value != null)
        .filter(boardGameGeekItem -> !boardGameGeekItem.stats.rating.value.equals("N/A"))
        .filter(boardGameGeekItem -> Integer.parseInt(boardGameGeekItem.stats.rating.value) >= minimumScore)
        .map(boardGameGeekItem -> boardGameGeekItem.objectId)
        .toList();

    System.out.println("Number of games with minimum score " + minimumScore + " or higher: " + gameIds.size());

    return gameIds;
  }

  public static BoardGameGeekItem getByIdOrFetch(int id) {
    System.out.println("Fetching game with id: " + id + " from boardgamegeek.com.");
    Optional<BoardGameGeekItem> boardGameGeekItem = BOARD_GAME_GEEK_ITEMS.stream()
        .filter(item -> item.objectId == id)
        .findAny();

    if (boardGameGeekItem.isEmpty()) {
      System.out.println("Game with id: " + id + " not found in the cache. Fetching..");
      boardGameGeekItem = Optional.ofNullable(BoardGameGeekClient.fetchById(id));
    }
    else {
      System.out.println("Game with id: " + id + " is found in the cache.");
    }

    if (boardGameGeekItem.isEmpty()) {
      System.err.println("Failed to fetch game with id: " + id + " from BoardGameGeek.");
    }
    else {
      BoardGameGeekItem geekItem = boardGameGeekItem.get();
      geekItem.objectId = id;
      System.out.println(new Gson().toJson(geekItem));
      BOARD_GAME_GEEK_ITEMS.add(geekItem);
    }

    return boardGameGeekItem.orElse(null);
  }

  public static boolean isAlreadyOwned(int gameId) {
    return OWNED_GAMES.stream().anyMatch(boardGameGeekItem -> boardGameGeekItem.objectId == gameId);
  }
}
