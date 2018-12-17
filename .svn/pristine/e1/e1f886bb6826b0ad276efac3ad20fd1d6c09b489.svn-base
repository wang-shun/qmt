package com.lesports.qmt.ipad.api;

import com.lesports.jersey.AbstractJerseyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ellios
 * Time: 13-11-28 : 上午11:57
 */

public class SmsIpadApiServer extends AbstractJerseyServer {

    private static final Logger LOG = LoggerFactory.getLogger(SmsIpadApiServer.class);
    private static final int DEFAULT_PORT = 9162;

    public static void main(final String[] args) throws Exception {

        int port = parsePortFromCommandLineArguments(DEFAULT_PORT, args);

        SmsIpadApiApplication app = new SmsIpadApiApplication();

        runServer(port, "/sms/ipad/v1/", app);
    }
}
