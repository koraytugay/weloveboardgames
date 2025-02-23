package biz.tugay.weloveboardgames.boardGameGeek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YearPublished
{
  @JacksonXmlProperty(isAttribute = true)
  public String value;

  public YearPublished() {
  }

  public YearPublished(String value) {
    this.value = value;
  }
}
