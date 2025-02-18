package biz.tugay.weloveboardgames.boardGameGeek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result
{
  @JacksonXmlProperty(isAttribute = true)
  public String value;

  @JacksonXmlProperty(isAttribute = true, localName = "numvotes")
  public String numberOfVotes;

  @JacksonXmlProperty(isAttribute = true)
  public String name;
}
