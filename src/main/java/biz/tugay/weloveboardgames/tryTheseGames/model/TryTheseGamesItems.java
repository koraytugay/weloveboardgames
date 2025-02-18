package biz.tugay.weloveboardgames.tryTheseGames.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class TryTheseGamesItems
{
  @SerializedName("Items")
  public List<TryTheseGamesItem> items;
}
