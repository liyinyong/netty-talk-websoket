package protobuftest.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import javapojo.pojo.SubscribeReq;
import javapojo.pojo.SubscribeResp;

/**
 * @author 71972
 * @date 2018/10/6
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq subscribeReq = (SubscribeReq) msg;
        if("Liyinyong".equals(subscribeReq.getUserName())){
            System.out.println("Server accept client subscribe req :"+ subscribeReq.toString());
            ctx.writeAndFlush(resp(subscribeReq.getSubReqID()));
        }
    }

    private SubscribeResp resp(int subReqID) {
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqID(subReqID);
        resp.setRespCode(0);
        resp.setDesc("Netty book order successed, 3 days later");
        return resp;
    }
}
