package websocket;

import http.handler.HttpFileServerHandler;
import http.server.HttpFileServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import websocket.handler.WebSocketServerHandler;

/**
 * @author 71972
 * @date 2018/10/7
 */
public class WebSocketServer {

    public void run(final int port) throws Exception {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //将请求和应答消息编码或者解码为HTTP消息
                            ch.pipeline().addLast(new HttpServerCodec());
                            //将多个消息聚合成一个完整的HTTP消息
                            ch.pipeline().addLast(new HttpObjectAggregator(65536));
                            //想起客户端发送H5文件，主要用于支持浏览器和服务端进行WebSocket通信
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new WebSocketServerHandler());
                        }
                    });
            ChannelFuture f = b.bind("localhost", port).sync();
            f.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception{
        new WebSocketServer().run(8081);
    }
}
