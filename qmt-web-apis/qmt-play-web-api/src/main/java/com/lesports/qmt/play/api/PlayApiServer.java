package com.lesports.qmt.play.api;

import com.lesports.jersey.AbstractJerseyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ellios
 * Time: 13-11-28 : 上午11:57
 */

public class PlayApiServer extends AbstractJerseyServer {

    private static final Logger LOG = LoggerFactory.getLogger(PlayApiServer.class);
    private static final int DEFAULT_PORT = 9132;

    public static void main(final String[] args) throws Exception {

        int port = parsePortFromCommandLineArguments(DEFAULT_PORT, args);

        PlayApiApplication app = new PlayApiApplication();

        runServer(port, "/", app);
    }
}
