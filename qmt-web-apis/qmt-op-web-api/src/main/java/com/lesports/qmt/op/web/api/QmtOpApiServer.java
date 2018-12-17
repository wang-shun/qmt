package com.lesports.qmt.op.web.api;


import com.lesports.jersey.AbstractJerseyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * User: ellios
 * Time: 13-11-28 : 上午11:57
 */

public class QmtOpApiServer extends AbstractJerseyServer {

    private static final Logger LOG = LoggerFactory.getLogger(QmtOpApiServer.class);
    private static final int DEFAULT_PORT = 9132;

    public static void main(final String[] args) throws Exception {

        int port = parsePortFromCommandLineArguments(DEFAULT_PORT, args);

        QmtOpApiApplication app = new QmtOpApiApplication();

        runServer(port, "/sms/op/v1/", app);
    }
}
