package com.duannd.jetty;

import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TimingHttpChannelListener implements HttpChannel.Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimingHttpChannelListener.class);

    private final Map<Request, Long> TIMES = new ConcurrentHashMap<>();

    @Override
    public void onRequestBegin(Request request) {
        TIMES.put(request, System.nanoTime());
    }

    @Override
    public void onComplete(Request request) {
        long begin = TIMES.remove(request);
        long elapsed = System.nanoTime() - begin;
        LOGGER.info("Request {} took {} ns", request, elapsed);
    }
}
