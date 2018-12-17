package com.lesports.qmt.sbd.server;

import com.lesports.qmt.sbd.api.service.TSbdInternalService;
import com.lesports.qmt.sbd.thrift.TSbdInternalServiceAdapter;
import com.lesports.utils.LeProperties;
import me.ellios.hedwig.rpc.core.ServiceConfig;
import me.ellios.hedwig.rpc.core.ServiceSchema;
import me.ellios.hedwig.rpc.core.ServiceType;
import me.ellios.hedwig.rpc.server.HedwigServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TSbdInternalServer {
    private static final Logger LOG = LoggerFactory.getLogger(com.lesports.qmt.sbd.server.TSbdInternalServer.class);

    private static final int DEFAULT_PORT = 9125;

    public static void main(String... args) {
        System.setProperty("spring.profiles.active", LeProperties.getString("spring.profiles.active", "default"));

        int port = parsePortFromCommandLineArguments(DEFAULT_PORT, args);
        LOG.info("Ready to start server on port: {}", port);
        HedwigServer server = HedwigServer.getServer();

        ServiceConfig config = ServiceConfig.newBuilder()
                .serviceFace(TSbdInternalService.Iface.class)
                .serviceImpl(TSbdInternalServiceAdapter.class)
                .port(port)
                .type(ServiceType.THRIFT)
                .build();
        server.registerService(config);

        ServiceConfig http = ServiceConfig.newBuilder()
                .serviceFace(TSbdInternalService.Iface.class)
                .serviceImpl(TSbdInternalServiceAdapter.class)
                .port(port + 1)
                .schema(ServiceSchema.HTTP)
                .type(ServiceType.THRIFT)
                .build();
        server.registerService(http);

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
