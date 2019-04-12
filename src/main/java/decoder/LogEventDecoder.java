package decoder;

import java.util.List;

import logEvent.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

/**
 *
 * @author 71972
 * @date 2018/9/29
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket>{

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> out) throws Exception {
        ByteBuf data =datagramPacket.content();
        int idx = data.indexOf(0, data.readableBytes(), LogEvent.SEPARATOR);
        String fileName = data.slice(0, idx).toString(CharsetUtil.UTF_8);
        String logMsg = data.slice(idx+1, data.readableBytes()).toString(CharsetUtil.UTF_8);
        LogEvent event = new LogEvent(datagramPacket.sender(), System.currentTimeMillis(), fileName, logMsg);
        out.add(event);
    }
}
