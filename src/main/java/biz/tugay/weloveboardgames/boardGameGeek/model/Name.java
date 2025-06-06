package biz.tugay.weloveboardgames.boardGameGeek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Name
{
  @JacksonXmlProperty(isAttribute = true)
  public String type;

  @JacksonXmlProperty(isAttribute = true)
  public String value;
}
