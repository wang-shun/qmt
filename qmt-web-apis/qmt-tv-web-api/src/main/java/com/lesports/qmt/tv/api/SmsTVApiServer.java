package com.lesports.qmt.tv.api;

import com.lesports.jersey.AbstractJerseyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ellios
 * Time: 13-11-28 : 上午11:57
 */

public class SmsTVApiServer extends AbstractJerseyServer {

    private static final Logger LOG = LoggerFactory.getLogger(SmsTVApiServer.class);
    private static final int DEFAULT_PORT = 9152;

    public static void main(final String[] args) throws Exception {

        int port = parsePortFromCommandLineArguments(DEFAULT_PORT, args);

        SmsTVApiApplication app = new SmsTVApiApplication();

        runServer(port, "/sms/tv/v1/", app);
    }
}
