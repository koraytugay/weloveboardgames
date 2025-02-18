package biz.tugay.weloveboardgames.recommendation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ctc.wstx.shaded.msv_core.datatype.xsd.datetime.BigDateTimeValueType;

public class RecommendedGame
{
  public String name;

  public String id;

  public int recommendationCount;

  public String yearPublished;

  public String imageUrl;

  public String minimumNumberOfPlayers;

  public String maximumNumberOfPlayers;

  public String bestNumberOfPlayers;

  public List<Integer> recommendingGameIds = new ArrayList<>();

  public List<String> recommendedByGameNames = new ArrayList<>();

  public String minimumAge;

  public String thumbnail;

  private List<String> recommendedByThumbnails = new ArrayList<>();

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecommendedGame that = (RecommendedGame) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public int getRecommendationCount() {
    return recommendationCount;
  }

  public String getYearPublished() {
    return yearPublished;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getBoardGameGeekUrl() {
    return "https://boardgamegeek.com/boardgame/" + id;
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

  public void addRecommendedById(int id) {
    this.recommendingGameIds.add(id);
  }

  public void addRecommendedByName(String recommendedByGameName) {
    this.recommendedByGameNames.add(recommendedByGameName);
  }

  public void addRecommendedByThumbnail(String thumbnail) {
    this.recommendedByThumbnails.add(thumbnail);
  }

  public List<String> getRecommendedByThumbnails() {
    return recommendedByThumbnails;
  }

  public List<String> getRecommendedByGameNames() {
    return recommendedByGameNames.stream().sorted().collect(Collectors.toList());
  }

  public String getMinimumAge() {
    return minimumAge;
  }

  public String getThumbnail() {
    return thumbnail;
  }
}
