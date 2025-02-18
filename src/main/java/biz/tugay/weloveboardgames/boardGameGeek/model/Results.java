package biz.tugay.weloveboardgames.boardGameGeek.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Results
{
  @JacksonXmlProperty(isAttribute = true, localName = "numplayers")
  public String numberOfPlayers;

  @JacksonXmlElementWrapper(useWrapping = false)
  public List<Result> result;
}
