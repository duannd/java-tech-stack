# Jetty Server Tech Stack

##  Server Libraries
    
- allow you to configure and start programmatically an HTTP or WebSocket server from a main class, or embed it in your existing application.
    - A typical example is a HTTP server that needs to expose a REST endpoint.
    - Another example is a proxy application that receives HTTP requests, processes them, and then forwards them to third party services
- The Jetty server-side libraries provide:
  - HTTP support for HTTP/1.0, HTTP/1.1, HTTP/2, clear-text or encrypted, HTTP/3, for applications that want to embed Jetty as a generic HTTP server or proxy, via the HTTP libraries
  - HTTP/2 low-level support, for applications that want to explicitly handle low-level HTTP/2 sessions, streams and frames, via the HTTP/2 libraries
  - HTTP/3 low-level support, for applications that want to explicitly handle low-level HTTP/3 sessions, streams and frames, via the HTTP/3 libraries
  - WebSocket support, for applications that want to embed a WebSocket server, via the WebSocket libraries
  - FCGI support, to delegate requests to python or similar scripting languages.

### Server Compliance Modes

- HTTP Compliance Modes
- URI Compliance Modes
- Cookie Compliance Modes

### HTTP Server Libraries

- allow you to write web applications components using either the Jetty APIs (by writing Jetty Handlers) or using the standard Servlet APIs (by writing Servlets and Servlet Filters).
- The Maven artifact coordinates are:
```xml
<dependency>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-server</artifactId>
    <version>11.0.8</version>
</dependency>
```
- An org.eclipse.jetty.server.Server instance is the central component that links together a collection of Connectors and a collection of Handlers, with threads from a ThreadPool doing the work.
- [org.eclipse.jetty.server.Server](https://www.eclipse.org/jetty/documentation/jetty-11/programming-guide/images/diag-05cfcead1245ff0fbcf3af8873998cee.png)

1. Request Processing
    - Request Processing diagram
      - First, the Jetty I/O layer emits an event that a socket has data to read.
      - This event is converted to a call to AbstractConnection.onFillable(), where the Connection first reads from the EndPoint into a ByteBuffer, and then calls a protocol specific parser to parse the bytes in the ByteBuffer.
      - The parser emit events that are protocol specific; the HTTP/2 parser, for example, emits events for each HTTP/2 frame that has been parsed, and similarly does the HTTP/3 parser. 
      - The parser events are then converted to protocol independent events such as "request start", "request headers", "request content chunk", etc. that in turn are converted into method calls to HttpChannel.
      - When enough of the HTTP request is arrived, the Connection calls HttpChannel.handle() that calls the Handler chain, that eventually calls the server application code.
    
    - HttpChannel Events
      - The central component processing HTTP requests is HttpChannel. 
        - A typical case is to know exactly when the HTTP request/response processing is complete, for example to monitor processing times.
      - HttpChannel notifies HttpChannel.Listeners of the progress of the HTTP request/response handling. Currently, the following events are available:

2. Request Logging

3. Server Connectors
    - A Connector is the component that handles incoming requests from clients, and works in conjunction with ConnectionFactory instances.
    - The available implementations are:
        - org.eclipse.jetty.server.ServerConnector, for TCP/IP sockets.
        - org.eclipse.jetty.unixdomain.server.UnixDomainServerConnector for Unix-Domain sockets (requires Java 16 or later).
    - Both use a java.nio.channels.ServerSocketChannel to listen to a socket address and to accept socket connections.
    
4. Server Handlers
    - org.eclipse.jetty.server.Handler is the component that processes incoming HTTP requests and eventually produces HTTP responses.
    - Handlers can be organized in different ways:
        - in a sequence, where Handlers are invoked one after the other
            - HandlerCollection invokes all Handlers one after the other
            - HandlerList invokes Handlerss until one calls Request.setHandled(true) to indicate that the request has been handled and no further Handler should be invoked
        - nested, where one Handler invokes the next, nested, Handler
    - The HandlerCollection behavior (invoking all handlers) is useful when for example the last Handler is a logging Handler that logs the request (that may have been modified by previous handlers).
    

















