package com.duannd.jetty;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettySimplestServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JettySimplestServer.class);

    public void start() throws Exception {
        // Create and configure a ThreadPool.
        QueuedThreadPool threadPool = new QueuedThreadPool(32, 4);

        // Create a Server instance.
        Server server = new Server(threadPool);

        // Create a ServerConnector to accept connections from clients.
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);

        // Add the HttpChannel.Listener as bean to the connector.
        connector.addBean(new TimingHttpChannelListener());

        // Add the Connector to the Server
        server.addConnector(connector);

        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest,
                               HttpServletRequest request, HttpServletResponse response) {
                LOGGER.info("Simplest Handler target: {}, request: {}", target, baseRequest);
                baseRequest.setHandled(true);
            }
        });

        // Start the Server so it starts accepting connections from clients.
        server.start();
    }

}
