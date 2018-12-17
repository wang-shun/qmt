package com.lesports.qmt.cms.server;

import com.lesports.qmt.cms.api.service.TCmsInternalService;
import com.lesports.qmt.cms.thrift.TCmsInternalServiceAdapter;
import com.lesports.utils.LeProperties;
import me.ellios.hedwig.rpc.core.ServiceConfig;
import me.ellios.hedwig.rpc.core.ServiceType;
import me.ellios.hedwig.rpc.server.HedwigServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCmsInternalServer {
    private static final Logger LOG = LoggerFactory.getLogger(TCmsInternalServer.class);

    private static final int DEFAULT_PORT = 9225;

    public static void main(String... args) {
        System.setProperty("spring.profiles.active", LeProperties.getString("spring.profiles.active", "default"));

        int port = parsePortFromCommandLineArguments(DEFAULT_PORT, args);
        LOG.info("Ready to start server on port: {}", port);
        HedwigServer server = HedwigServer.getServer();

        ServiceConfig config = ServiceConfig.newBuilder()
                .serviceFace(TCmsInternalService.Iface.class)
                .serviceImpl(TCmsInternalServiceAdapter.class)
                .port(port)
                .type(ServiceType.THRIFT)
                .build();
        server.registerService(config);

        server.start();
    }


    public static int parsePortFromCommandLineArguments(int defaultPort, String... args) {
        int port = defaultPort;
        if (args.length > 0) {
            port = Integer.valueOf(args[0]);
            if (port <= 0) {
                port = defaultPort;
            }
        }
        return port;
    }
}
