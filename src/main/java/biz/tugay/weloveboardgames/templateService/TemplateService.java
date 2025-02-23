package biz.tugay.weloveboardgames.templateService;

import biz.tugay.weloveboardgames.BoardGame;
import freemarker.template.*;

import java.nio.file.Paths;
import java.util.*;
import java.io.*;


import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateService
{
  public static void generateIndexHtml(List<BoardGame> boardGames) throws IOException, TemplateException {
    HashMap<String, List<BoardGame>> templateData = new HashMap<>();
    templateData.put("games", boardGames);

    Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
    Template temp = configuration.getTemplate(Paths.get("dist", "mytemplate.ftl").toString());

    temp.process(templateData, new FileWriter(Paths.get("index.html").toString()));
  }
}
