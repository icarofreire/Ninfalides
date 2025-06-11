package org.crawler.config;

import org.crawler.JSONUtil;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Loader {
  // public static final Logger logger = LoggerFactory.getLogger(Loader.class);
  private static final String fileSites = "sites.txt";

  public static List<Seed> fromLocal() {
    List<Seed> configs = new ArrayList<>();

    // 1. Check file exist.
    final File confPathFile = new File("sites/");
    if (!confPathFile.exists() && !confPathFile.isDirectory()) {
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

  public static List<Seed> fromLocalFile() {
    List<Seed> configs = new ArrayList<>();

    // 1. Check file exist.
    final File confPathFile = new File(fileSites);
    if (!confPathFile.exists() && !confPathFile.isFile()) {
      System.out.println("Arquivo de sites não existe!");
      // System.out.println("Crie um arquivo '" + fileSites + "', contendo as urls a serem analisados.");
      createNewFile();
      return configs;
    }

    final List<String> sites = linesFile(fileSites);
    if (sites == null) {
      return configs;
    }

    sites.forEach(site -> {
          if (!site.isBlank()) {
            Seed seed = new Seed();
            seed.setUrl(site);
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

  private static List<String> linesFile(String fileName) {
    List<String> result = null;
    try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
        result = lines.collect(Collectors.toList());
    }catch(IOException e){}
    return result;
  }

  private static void createNewFile(){
    File file = new File(fileSites);
     try {
        if (file.createNewFile()) {
            System.out.println("Insira as urls no arquivo criado: " + fileSites);
        } else {
            // System.out.println("File already exists.");
        }
    } catch (IOException e) {
        // System.out.println("Error creating file: " + e.getMessage());
    }
  }
}
