package org.kttp.server;

import lombok.RequiredArgsConstructor;
import org.kttp.parser.HttpParser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public class KttpListener {

    private static final int BUFFER_SIZE = 256;

    private final HttpParser parser;
    private final HandlerDispatcher dispatcher;

    public void boostrap() {
        try {
            var serverChannel = AsynchronousServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(8080));
            while (true) {
                var futureChannel = serverChannel.accept();
                handleRequest(futureChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleRequest(Future<AsynchronousSocketChannel> futureChannel)
            throws InterruptedException, ExecutionException, IOException {
        var buffer = ByteBuffer.allocate(BUFFER_SIZE);
        try (var channel = futureChannel.get()) {
            var request = readRequest(channel, buffer);
            if (request != null && !request.isBlank()) {
                var httpRequest = parser.parseRequest(request);
                var response = dispatcher.handleRequest(httpRequest);
                var responseStr = parser.parseResponse(response);
                channel.write(ByteBuffer.wrap(responseStr.getBytes(StandardCharsets.UTF_8)));
            }
        }
    }

    private String readRequest(AsynchronousSocketChannel channel, ByteBuffer buffer) throws ExecutionException, InterruptedException {
        var endReading = false;
        var builder = new StringBuilder();

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
