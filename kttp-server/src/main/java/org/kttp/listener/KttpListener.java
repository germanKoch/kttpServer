package org.kttp.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kttp.listener.parser.RequestParser;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
public class KttpListener {

    private static final int BUFFER_SIZE = 256;

    private final RequestParser parser;
    private final HandlerDispatcher dispatcher;

    public void boostrap() {
        try {
            var threadPool = new ThreadPoolExecutor(
                    5, 20, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                    defaultThreadFactory());
            var group = AsynchronousChannelGroup.withThreadPool(threadPool);
            var serverChannel = AsynchronousServerSocketChannel.open(group);
            serverChannel.bind(new InetSocketAddress(8080));
            serverChannel.accept("Client Request", new CompletionHandler<>() {
                @Override
                public void completed(AsynchronousSocketChannel channel, String attachment) {
                    serverChannel.accept(attachment, this);
                    log.debug(attachment);
                    try (channel) {
                        var request = readRequest(channel);
                        if (request != null && !request.isBlank()) {
                            var httpRequest = parser.parseRequest(request);
                            var response = dispatcher.handleRequest(httpRequest);
                            var responseStr = parser.parseResponse(response);
                            channel.write(ByteBuffer.wrap(responseStr.getBytes(StandardCharsets.UTF_8)));
                        }
                    } catch (Exception e) {
                        log.error("Error while handling request", e);
                    }
                }

                @Override
                public void failed(Throwable e, String attachment) {
                    log.error("Error while handling request", e);
                }
            });
            log.info("Kttp-server started");
            group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Server Error", e);
        }
    }

    private String readRequest(AsynchronousSocketChannel channel) throws ExecutionException, InterruptedException {
        var endReading = false;
        var builder = new StringBuilder();
        var buffer = ByteBuffer.allocate(BUFFER_SIZE);

        while (channel.isOpen() && !endReading) {
            channel.read(buffer).get();
            var position = buffer.position();
            var array = position == BUFFER_SIZE - 1 ? buffer.array() : Arrays.copyOf(buffer.array(), position);
            var fragment = new String(array);
            //TODO: Избавится от fragment
            builder.append(fragment);
            buffer.clear();
            //TODO: сделать умнее.
            endReading = position < BUFFER_SIZE;
        }
        if (builder.length() > 0) {
            return builder.toString();
        }
        return null;
    }

    private ThreadFactory defaultThreadFactory() {
        return new ThreadFactory() {
            final AtomicInteger integer = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable task) {
                return new Thread(task, "kttp-thread-" + integer.getAndIncrement());
            }
        };
    }


}
