package org.crawler.master;

import org.crawler.config.Loader;
import org.crawler.config.Seed;
import org.crawler.config.Task;
// import com.iveely.framework.net.AsynServer;
import org.crawler.ThreadUtil;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author;
 */
public class Master implements Runnable {

  // private final static Logger logger = LoggerFactory.getLogger(Master.class);

  // private final AsynServer server;

  private final MasterHandler handler;

  private final Integer port;

  private boolean isStarted;

  public Master(final Integer port) {
    this.port = port;
    this.handler = new MasterHandler();
    // this.server = new AsynServer(this.port, this.handler);
    this.isStarted = false;
  }

  @Override
  public void run() {
    // 1. Start server.
    // try {
    //   if (!this.server.open()) {
    //     logger.error("Cannot start master server.");
    //     return;
    //   }
    // } catch (IOException ex) {
    //   logger.error("When start master,IOException happend.", ex);
    //   return;
    // }
    this.isStarted = true;
    // logger.info("Master server started,at port {}", this.port);

    // 2. Get tasks and push into list
    while (true) {
      // logger.info("Start to check configurations.");
      List<Seed> configs = Loader.fromLocal();
      if (configs.size() > 0) {
        this.handler.addTasks(configs);
        //TODO: record as many is much better.
      }
      ThreadUtil.sleep(12000);
    }
  }

  public boolean isStarted() {
    return isStarted;
  }

  public void close() {
    // if (this.server != null) {
    //   this.server.close();
    // }
  }

}
