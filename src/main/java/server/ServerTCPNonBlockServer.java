package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by liyinyong on 2018/7/29.
 */
public class ServerTCPNonBlockServer {
    //服务器IP
    public static final String SERVER_IP = "127.0.0.1";

    //服务器端口号
    public static final int SERVER_PORT = 10005;

    //请求终结字符串
    public static final char REQUEST_END_CHAR = '#';

    public void startServer(String serverIP, int serverPort) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress localAddress = new InetSocketAddress(serverIP, serverPort);

        serverSocketChannel.bind(localAddress);

        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while(keys.hasNext()){
                SelectionKey key = keys.next();
                keys.remove();
                try {
                    if(key.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel)key.channel();
                        SocketChannel channel = server.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                    }else if(key.isReadable()){
                        SocketChannel channel = (SocketChannel)key.channel();

                        ByteBuffer buffer =ByteBuffer.allocate(1024);
                        channel.read(buffer);

                        buffer.flip();
                        key.attach(buffer);
                        key.interestOps(SelectionKey.OP_WRITE);
                    }else if(key.isWritable()){
                        SocketChannel channel =(SocketChannel)key.channel();
                        ByteBuffer buffer = (ByteBuffer)key.attachment();
                        buffer.rewind();

                        String recv = new String(buffer.array());
                        buffer.clear();
                        buffer.flip();

                        byte[] sendBytes = recv.toUpperCase().getBytes();
                        channel.write(ByteBuffer.wrap(sendBytes));

                        key.interestOps(SelectionKey.OP_READ);
                    }
                }catch (Exception e){
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            }
        }
    }
    public static void main(String[] args) {
        ServerTCPNonBlockServer server = new ServerTCPNonBlockServer();
        try {
            server.startServer(SERVER_IP, SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
