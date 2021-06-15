package demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author yongxum
 * @date 2021年06月15日 13:08
 */
public class NioServer {

    // 保存客户端连接
    static List<SocketChannel> channelList = new ArrayList<SocketChannel>();
    public static void main(String[] args) throws IOException {
        // 创建NIO ServerSocketChannel, 与BIO的serverSocket类似
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        //设置ServerSocketChannel为非阻塞
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务启动成功");

        while (true){
            // 非阻塞模式accept方式不会阻塞, 否则会阻塞
            // NIO的非阻塞是由操作系统内部实现的, 底层调用了linux内核的accept函数
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel != null){
                System.out.println("连接成功");
                socketChannel.configureBlocking(false);
                // 保存客户端连接在list中
                channelList.add(socketChannel);
            }
            // 遍历连接进行数据读取
            Iterator<SocketChannel> iterator = channelList.iterator();
            while(iterator.hasNext()){
                SocketChannel sc = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                // 非阻塞模式read方法不会阻塞, 否则会阻塞
                int len = sc.read(byteBuffer);
                if (len > 0){
                    System.out.println("接收到消息: " + new String(byteBuffer.array()));
                }else if (len == -1){
                    iterator.remove();
                    System.out.println("客户端断开连接");
                }
            }
        }
    }
}
