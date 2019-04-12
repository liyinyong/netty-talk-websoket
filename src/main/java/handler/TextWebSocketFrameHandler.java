package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * Created by 71972 on 2018/9/27.
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    /*
    * 重写 userEventTriggered() 方法以处理自定义事件
    * */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //如果该事件 表示握手成 功，则从该 Channelipeline 中移除 HttpRequestHandler， 因为将不会 接收到任何 HTTP 消息了
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            ctx.pipeline().remove(HttpRequestHandler.class);
            //通知所有已经连接的 WebSocket 客户端新 的客户端已经连接上了
            group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + "joined"));
            //将新的 WebSocket Channel 添加到 ChannelGroup 中，以 便它可以接收到所有的消息
            group.add(ctx.channel());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //增加消息的引用计数，并将它写到 ChannelGroup 中所有 已经连接的客户端
        group.writeAndFlush(((TextWebSocketFrame)msg).retain());
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

    }
}
