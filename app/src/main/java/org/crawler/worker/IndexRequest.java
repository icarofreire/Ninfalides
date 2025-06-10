package org.crawler.worker;

import org.crawler.config.Configurate;
import org.crawler.entity.Article;
import org.crawler.StoreField;
import org.crawler.StoredDocument;
import org.crawler.JSONUtil;
import org.crawler.RequestHttp;

// import org.apache.http.client.HttpClient;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.entity.StringEntity;
// import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author;
 */
public class IndexRequest {

  private static Logger logger = LoggerFactory.getLogger(IndexRequest.class);

  public static void post(final Article article) {
    if (article == null
        || article.getTitle() == null
        || article.getContent() == null
        || article.getUrl() == null) {
      return;
    }
    try {
      List<StoreField> storeFields = new ArrayList<>();
      storeFields.add(new StoreField<>(
          StoreField.Type.TEXT,
          "title",
          article.getTitle()));
      storeFields.add(new StoreField<>(
          StoreField.Type.STRING,
          "url",
          article.getUrl()));
      storeFields.add(new StoreField<>(
          StoreField.Type.TEXT,
          "content",
          article.getContent()
      ));
      storeFields.add(new StoreField<>(
          StoreField.Type.LONG,
          "timestamp",
          article.getCrawlTimestamp()
      ));
      String text = JSONUtil.toString(new StoredDocument(storeFields));
      // HttpClient httpclient = new DefaultHttpClient();
      // HttpPost httpPost = new HttpPost(Configurate.get().getIndexUrl());
      // httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
      // httpPost.setEntity(new StringEntity(text, "UTF-8"));
      // httpclient.execute(httpPost);
      // httpclient.getConnectionManager().shutdown();

      RequestHttp res = new RequestHttp();
      res.post_json(Configurate.get().getIndexUrl(), text);

    } catch (Exception ex) {
      logger.warn("Post exception.", ex);
    }
  }
}
