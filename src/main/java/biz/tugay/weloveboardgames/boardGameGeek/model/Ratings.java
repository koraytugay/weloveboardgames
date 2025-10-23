package biz.tugay.weloveboardgames.boardGameGeek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ratings
{
  @JacksonXmlProperty(localName = "average")
  public Average average;

  @JacksonXmlProperty(localName = "averageweight")
  public AverageWeight averageWeight;
}
