package server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import serverhandler.EchoServerHandler;

/**
 * Created by 71972 on 2018/9/15.
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.out.println("Usage:" + EchoServer.class.getSimpleName() + "<port>");
//        }
        new EchoServer(9999).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            //异步地绑定服务器； 调用 sync()方法阻塞 等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            //获取 Channel 的 CloseFuture，并 且阻塞当前线， 程直到它完成
            f.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            group.shutdownGracefully().sync();
        }

    }
}

