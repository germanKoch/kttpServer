package org.kttp.listener;

import lombok.RequiredArgsConstructor;
import org.kttp.listener.parser.RequestParser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class KttpListener {

    private static final int BUFFER_SIZE = 256;

    private final RequestParser parser;
    private final HandlerDispatcher dispatcher;

    public void boostrap() {
        try {
            var group = AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(5));
            var serverChannel = AsynchronousServerSocketChannel.open(group);
            serverChannel.bind(new InetSocketAddress(8080));
            serverChannel.accept(null, new CompletionHandler<>() {
                @Override
                public void completed(AsynchronousSocketChannel channel, Object attachment) {
                    serverChannel.accept(null, this);
                    try (channel) {
                        var request = readRequest(channel);
                        if (request != null && !request.isBlank()) {
                            var httpRequest = parser.parseRequest(request);
                            var response = dispatcher.handleRequest(httpRequest);
                            var responseStr = parser.parseResponse(response);
                            channel.write(ByteBuffer.wrap(responseStr.getBytes(StandardCharsets.UTF_8)));
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    exc.printStackTrace();
                }
            });
            group.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
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


}
