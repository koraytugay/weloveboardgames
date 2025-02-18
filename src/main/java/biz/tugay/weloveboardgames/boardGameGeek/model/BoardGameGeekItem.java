package biz.tugay.weloveboardgames.boardGameGeek.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "item")
public class BoardGameGeekItem
{
  @JacksonXmlProperty(isAttribute = true, localName = "type")
  public String type;

  @JacksonXmlProperty(isAttribute = true, localName = "id")
  public String id;

  @JacksonXmlProperty(isAttribute = true, localName = "objecttype")
  public String objectType;

  @JacksonXmlProperty(isAttribute = true, localName = "objectid")
  public int objectId;

  @JacksonXmlProperty(isAttribute = true)
  public String subType;

  @JacksonXmlProperty(localName = "yearpublished")
  public YearPublished yearPublished;

  public String image;

  public String thumbnail;

  @JacksonXmlProperty(localName = "status")
  public Status status;

  @JacksonXmlProperty(localName = "stats")
  public Stats stats;

  @JacksonXmlProperty(localName = "name")
  @JacksonXmlElementWrapper(useWrapping = false)
  public List<Name> name;

  @JacksonXmlProperty(localName = "minplayers")
  @JacksonXmlElementWrapper(useWrapping = false)
  public MinPlayers minPlayers;

  @JacksonXmlProperty(localName = "maxplayers")
  @JacksonXmlElementWrapper(useWrapping = false)
  public MaxPlayers maxPlayers;

  @JacksonXmlProperty(localName = "minage")
  @JacksonXmlElementWrapper(useWrapping = false)
  public MinimumAge minimumAge;

  @JacksonXmlProperty(localName = "poll")
  @JacksonXmlElementWrapper(useWrapping = false)
  public List<Poll> poll;

  @JacksonXmlProperty(localName = "poll-summary")
  @JacksonXmlElementWrapper(useWrapping = false)
  public List<PollSummary> pollSummary;

  public String getPrimaryName() {
    return name.get(0).value;
  }
}
