package biz.tugay.weloveboardgames.boardGameGeek.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "items")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardGameGeekResponse
{
  @JacksonXmlProperty(localName = "totalitems", isAttribute = true)
  public int totalItems;

  @JacksonXmlProperty(localName = "pubdate", isAttribute = true)
  public String publicationDate;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "item")
  public List<BoardGameGeekItem> items;
}
