package com.lesports.msite.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * User: ellios
 * Time: 15-4-1 : 下午9:12
 */
public class MSiteWebServer {

    private static final Logger LOG = LoggerFactory.getLogger(MSiteWebServer.class);

    public static void main(String[] args) throws Exception {

        String contextPath = getContextPath("");
        int port = getPort(9850, args);

        Server server = createServer(contextPath, port);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            System.exit(100);
        }
    }

    private static int getPort(int defaultPort, String... args) {
        int port = defaultPort;
        if (args.length > 0) {
            port = Integer.valueOf(args[0]);
            if (port <= 0) {
                port = defaultPort;
            }
        }
        return port;
    }

    private static String getContextPath(String defaultContext) {
        String context = System.getProperty("app.context");
        if (context == null || context.isEmpty()) {
            context = defaultContext;
        }
        if (!context.startsWith("/")) {
            context = "/" + context;
        }
        return context;
    }

    private static Server createServer(String contextPath, int port) {

        File wFile = new File(System.getProperty("war.path"));
        if (!wFile.exists()) {
            throw new RuntimeException("Unable to find WAR File: "
                    + wFile.getAbsolutePath());
        }
        LOG.info("wFile : {}", wFile);
        WebAppContext context = new WebAppContext(wFile.getAbsolutePath(), contextPath);

        Server server = JettyServerFactory.createServer(port, context, false);

        context.setServer(server);
        // 设置work dir,jsp编译后的文件也将放入其中。
        File workDir = new File(new File(wFile.getPath()).getParent(), "work");
        LOG.info("workDir : {}", workDir);
        context.setTempDirectory(workDir);

        return server;
    }
}
