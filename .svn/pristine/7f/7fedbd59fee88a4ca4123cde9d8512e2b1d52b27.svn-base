package com.lesports.msite.server;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.StatisticsServlet;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: ellios
 * Time: 14-6-12 : 下午5:27
 */
public class JettyServerFactory {

    private static final Logger LOG = LoggerFactory.getLogger(JettyServerFactory.class);


    public static Server createServer(final int port,
                                      final Handler handler,
                                      final boolean start) {

        Server server = createServer5ConfigFile(port);
        if (server == null) {
            LOG.info("fail to create server from config file, try to create default server.");
            server = createDefaultServer(port);
        }

        if (server.getHandler() == null) {

            ServletContextHandler backdoorHandler = new ServletContextHandler();
            backdoorHandler.setContextPath("/backdoor");
            ServletHolder holder = new ServletHolder(new StatisticsServlet());
            holder.setInitParameter("restrictToLocalhost", "false");
            backdoorHandler.addServlet(holder, "/stats");

            HandlerCollection handlers = new HandlerCollection();
            handlers.addHandler(handler);
            handlers.addHandler(backdoorHandler);

            StatisticsHandler statisticsHandler = new StatisticsHandler();
            statisticsHandler.setHandler(handlers);

            server.setHandler(statisticsHandler);
        }

        if (start) {
            try {
                // Start the server.
                server.start();
            } catch (Exception e) {
                throw new RuntimeException("Exception thrown when trying to create jetty server", e);
            }
        }
        return server;
    }

    public static Server createDefaultServer(final int port) {
        int defaultPort = 80;

        final int serverPort = (port > 0) ? port : defaultPort;
        ThreadPool threadPool = new LeJettyThreadPool();

        Server server = new Server(threadPool);
        HttpConfiguration config = new HttpConfiguration();
        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(config));
        http.setPort(serverPort);
        server.setConnectors(new Connector[]{http});
        return server;
    }

    public static Server createServer5ConfigFile(final int port) {
        Server server = null;
        try {
            Resource jettyXml = Resource.newSystemResource("com/lesports/msite/jetty.xml");
            if (jettyXml == null) {
                return null;
            }
            XmlConfiguration configuration = new XmlConfiguration(jettyXml.getInputStream());

            server = (Server) configuration.configure();
            if (port > 0) {
                ((ServerConnector) server.getConnectors()[0]).setPort(port);
            }
        } catch (Exception e) {
            LOG.error("fail to create jetty server from config : com/lesports/msite/jetty.xml", e);
            return null;
        }

        return server;
    }
}
