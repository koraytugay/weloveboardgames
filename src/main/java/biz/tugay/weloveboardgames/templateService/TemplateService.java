package biz.tugay.weloveboardgames.templateService;

import freemarker.template.*;

import java.nio.file.Paths;
import java.util.*;
import java.io.*;


import biz.tugay.weloveboardgames.recommendation.model.RecommendedGame;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateService
{
  public static void foo(List<RecommendedGame> recommendedGames) throws IOException, TemplateException {
    HashMap<String, List<RecommendedGame> > templateData = new HashMap<>();
    templateData.put("games", recommendedGames);

    Configuration configuration = new Configuration();
    Template temp = configuration.getTemplate(Paths.get("dist", "template.ftl").toString());
    temp.toString();

    //Writer out = new OutputStreamWriter(System.out);
    temp.process(templateData, new FileWriter(Paths.get("dist", "index.html").toString()));
  }
}
