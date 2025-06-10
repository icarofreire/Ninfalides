package org.crawler.config;

import org.crawler.JSONUtil;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Loader {
  // public static final Logger logger = LoggerFactory.getLogger(Loader.class);

  public static List<Seed> fromLocal() {
    List<Seed> configs = new ArrayList<>();

    // 1. Check file exist.
    final File confPathFile = new File("sites/");
    if (!confPathFile.exists()) {
      System.out.println("Pasta inserida não existe!");
      return configs;
    }

    // 2. Get task list.
    final File[] taskFiles = confPathFile.listFiles();
    if (taskFiles == null) {
      return configs;
    }

    Arrays.stream(taskFiles)
        .forEach(file -> {
          if (file.getName().endsWith(".json")) {
            Seed seed = JSONUtil.fromFile(file, Seed.class);
            
            /**\/ inserir name padrão ao seed, caso o mesmo não tenha sido inserido; */
            if(seed.getName() == null || seed.getName().isBlank()){
              String alvo1 = "//";
              int ind1 = seed.getUrl().indexOf(alvo1);
              int firstp = (ind1 != -1) ? (ind1+alvo1.length()) : (0);
              int indFinal = seed.getUrl().indexOf(".", firstp+1);
              if(indFinal != -1){
                String name = seed.getUrl().substring(firstp, indFinal);
                seed.setName(name);
              }
            }

            configs.add(seed);
          }
        });
    return configs;
  }
}
