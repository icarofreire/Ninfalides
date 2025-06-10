package org.crawler;


import org.crawler.worker.Worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CrawlerApplication {

  private static Worker worker;

  public static void main(String[] args) throws IOException {
    worker = new Worker();
    worker.run();
  }
}
