package com.lesports.qmt.sbc.server;

import com.lesports.qmt.sbc.api.service.TSbcEpisodeService;
import com.lesports.qmt.sbc.api.service.TSbcResourceService;
import com.lesports.qmt.sbc.api.service.TSbcService;
import com.lesports.qmt.sbc.thrift.TSbcEpisodeServiceAdapter;
import com.lesports.qmt.sbc.thrift.TSbcResourceServiceAdapter;
import com.lesports.qmt.sbc.thrift.TSbcServiceAdapter;
import com.lesports.utils.LeProperties;
import me.ellios.hedwig.rpc.core.ServiceConfig;
import me.ellios.hedwig.rpc.core.ServiceType;
import me.ellios.hedwig.rpc.server.HedwigServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ellios
 * Time: 16-3-20 : 下午4:36
 */
public class TQmtSbcServer {

    private static final Logger LOG = LoggerFactory.getLogger(TQmtSbcServer.class);

    private static final int DEFAULT_PORT = 8412;

    public static void main(String... args) {
        System.setProperty("spring.profiles.active", LeProperties.getString("spring.profiles.active", "default"));

        int port = parsePortFromCommandLineArguments(DEFAULT_PORT, args);
        LOG.info("Ready to start server on port: {}", port);
        HedwigServer server = HedwigServer.getServer();


        ServiceConfig config = ServiceConfig.newBuilder()
                .serviceFace(TSbcService.Iface.class)
                .serviceImpl(TSbcServiceAdapter.class)
                .port(port)
                .type(ServiceType.THRIFT)
                .build();

        ServiceConfig resourceConfig = ServiceConfig.newBuilder()
                .serviceFace(TSbcResourceService.Iface.class)
                .serviceImpl(TSbcResourceServiceAdapter.class)
                .port(port)
                .type(ServiceType.THRIFT)
                .build();

        ServiceConfig episodeConfig = ServiceConfig.newBuilder()
                .serviceFace(TSbcEpisodeService.Iface.class)
                .serviceImpl(TSbcEpisodeServiceAdapter.class)
                .port(port)
                .type(ServiceType.THRIFT)
                .build();

        server.registerService(config);
        server.registerService(resourceConfig);
        server.registerService(episodeConfig);

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
