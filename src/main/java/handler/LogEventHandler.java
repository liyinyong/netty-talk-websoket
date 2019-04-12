package handler;

import logEvent.LogEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author 71972
 * @date 2018/9/29
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LogEvent logEvent = (LogEvent) msg;
        StringBuilder builder = new StringBuilder();
        builder.append(logEvent.getReceived());
        builder.append(" [");
        builder.append(logEvent.getSource().toString());
        builder.append("] [");
        builder.append(logEvent.getLogFile());
        builder.append("] : ");
        builder.append(logEvent.getMsg());
        System.out.println(builder.toString());
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, LogEvent msg) throws Exception {

    }
}
