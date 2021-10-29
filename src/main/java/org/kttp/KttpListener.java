package org.kttp;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public class KttpListener {

    private static final int BUFFER_SIZE = 256;
    private static final String REQUEST_END_TRIGGER = "\r\n\r\n";

    private final KttpParser adapter;

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
            var endReading = false;
            var builder = new StringBuilder();

            while (channel.isOpen() && !endReading) {
                channel.read(buffer).get();
                var position = buffer.position();
                var array = position == BUFFER_SIZE - 1 ? buffer.array() : Arrays.copyOf(buffer.array(), position);
                var fragment = new String(array);

                builder.append(fragment);
                buffer.clear();

                endReading = checkEnding(fragment);
            }
            var response = adapter.handleRequest(builder.toString());
            channel.write(ByteBuffer.wrap(response.getBytes()));
        }
    }

    private boolean checkEnding(String fragment) {
        return fragment.endsWith(REQUEST_END_TRIGGER);
    }

}
