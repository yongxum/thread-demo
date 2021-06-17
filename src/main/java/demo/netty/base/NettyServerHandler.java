package demo.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author yongxum
 * @date 2021年06月17日 11:28
 * 自定义Handler需要继承entty规定好的某个HandlerAdapter(规范)
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端连接服务器完成就会触发改方法
     * @author yongxum
     * @date 2021/6/17 11:30
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("客户端连接通道建立完成");
    }

    /**
     * 读取客户端发送的数据
     * @author yongxum
     * @date 2021/6/17 11:33
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("接收客户端的消息: " + buf.toString(CharsetUtil.UTF_8));
    }
}
