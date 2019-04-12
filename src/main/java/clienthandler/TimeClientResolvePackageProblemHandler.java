package clienthandler;

import java.util.Date;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import serverhandler.TimeServerHandler;

/**
 * @author 71972
 * @date 2018/10/5
 */
public class TimeClientResolvePackageProblemHandler extends ChannelHandlerAdapter {
    private static final Logger LOGGER = Logger.getLogger(TimeClientResolvePackageProblemHandler.class.getName());

    private int counter;
    private byte[] req;

    public TimeClientResolvePackageProblemHandler() {
        req = (TimeServerHandler.queryTimeOrder + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("Now is : " + body + "; the counter is :" + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.warning("" + cause.getMessage());
        ctx.close();
    }
}

