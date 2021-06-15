package demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yongxum
 * @date 2021年06月15日 13:09
 */
public class NioSelectorServer {

    public static void main(String[] args) throws IOException {

        // 创建NIO ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        // 设置ServerSocketChannel为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 打开Selector处理Channel, 即创建epoll
        Selector selector = Selector.open();
        // 把ServerSocketChannel注册到selector上, 并且selector对客户端accept连接操作感兴趣
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功");

        while(true){
            // 阻塞等待需要处理的事件发生
            selector.select();

            // 获取selector中注册的全部事件的selectionKey实例
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();

            // 遍历SelectionKey对事件进行处理
            while(true){
                SelectionKey key = iterator.next();
                // 如果是OP_ACCEPT事件, 则进行连接获取和事件注册
                if(key.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();
                    // 设置SocketChannel为非阻塞
                    socketChannel.configureBlocking(false);
                    // 这里只注册了读事件, 如果需要给客户端发送数据可以注册写事件
                    SelectionKey selKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端连接成功");
                }else if(key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    // 如果有数据, 把数据打印出来
                    int len = socketChannel.read(byteBuffer);
                    if(len > 0){
                        System.out.println("接收到消息: " + new String(byteBuffer.array()));
                    }else if(len == -1){ //如果客户端断开连接, 关闭Socket
                        System.out.println("客户端断开连接");
                        socketChannel.close();
                    }
                }
                // 从事件集合里删除本次处理的key, 防止下次select重复处理
                iterator.remove();
            }
        }
    }
}
