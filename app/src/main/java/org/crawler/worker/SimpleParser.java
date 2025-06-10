package org.crawler.worker;

import org.crawler.entity.Article;
import org.crawler.entity.WebUrl;

import org.jsoup.nodes.Document;

/**
 * @author;
 */
public class SimpleParser {

  public boolean shouldVisit(final WebUrl refer, final WebUrl webUrl) {
    return true;
  }

  public Article get(final Document document, final String url) {
    return new Article(document.title(),
        document.body().text().trim(), url);
  }
}
