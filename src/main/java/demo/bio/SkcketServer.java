package demo.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author yongxum
 * @date 2021年06月15日 14:09
 */
public class SkcketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true){
            System.out.println("等待连接....");
            Socket clientSocket = serverSocket.accept();
            System.out.println("有客户端连接了..");
            handler(clientSocket);
        }
    }

    private static void handler(Socket clientSocket) throws IOException{
        byte[] bytes = new byte[1024];
        int read = clientSocket.getInputStream().read(bytes);
        System.out.println("read完毕....");
        if(read != -1){
            System.out.println("接收到客户端的数据" + new String(bytes, 0, read));
        }
    }
}
