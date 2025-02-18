package biz.tugay.weloveboardgames.boardGameGeek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Status
{
  @JacksonXmlProperty(isAttribute = true)
  public int own;

  @JacksonXmlProperty(localName = "prevowned", isAttribute = true)
  public int prevowned;

  @JacksonXmlProperty(isAttribute = true)
  public int fortrade;

  @JacksonXmlProperty(isAttribute = true)
  public int want;

  @JacksonXmlProperty(isAttribute = true)
  public int wanttoplay;

  @JacksonXmlProperty(isAttribute = true)
  public int wanttobuy;

  @JacksonXmlProperty(isAttribute = true)
  public int wishlist;

  @JacksonXmlProperty(isAttribute = true)
  public int wishlistPriority;  // 0 is must have, 4 means do not buy

  @JacksonXmlProperty(isAttribute = true)
  public int preordered;

  @JacksonXmlProperty(isAttribute = true)
  public String lastmodified;

}
