package biz.tugay.weloveboardgames.boardGameGeek.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Poll
{
  public String name;

  @JacksonXmlElementWrapper(useWrapping = false)
  public List<Results> results;
}
