package com.duannd.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyServletServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JettyServletServer.class);

    public void start() throws Exception {
        QueuedThreadPool threadPool = new QueuedThreadPool(32, 4);

        Server server = new Server(threadPool);

        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setPort(8090);
        server.addConnector(serverConnector);

        // Create a ServletContextHandler with contextPath.
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/api");

        // Add the Servlet implementing the user functionality to the context.
        ServletHolder servletHolder = context.addServlet(UserServlet.class, "/users/*");
        servletHolder.setInitParameter("maxItems", "128");

        // Link the context to the server.
        server.setHandler(context);

        // Start the Server so it starts accepting connections from clients.
        server.start();
    }

}
