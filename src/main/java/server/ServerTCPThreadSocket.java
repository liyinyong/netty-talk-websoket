package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by liyinyong on 2018/7/29.
 */
public class ServerTCPThreadSocket {
    //服务器IP
    public static final String SERVER_IP = "127.0.0.1";

    //服务器端口号
    public static final int SERVER_PORT = 10005;

    //请求终结字符串
    public static final char REQUEST_END_CHAR = '#';

    public void startServer(String serverIP, int serverPort){

        InetAddress serverAddress;
        try {
            serverAddress = InetAddress.getByName(serverIP);
        }catch (UnknownHostException e){
            e.printStackTrace();
            return;
        }

        try(ServerSocket serverSocket =new ServerSocket(SERVER_PORT, 5, serverAddress)){
            Executor executor = Executors.newFixedThreadPool(100);
            while (true){
                final StringBuilder recvStrBuilder = new StringBuilder();

                try{
                    final Socket connection =serverSocket.accept();
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            Socket conn = connection;
                            try {
                                InputStream in = conn.getInputStream();

                                //读取客户端的请求字符串，请求字符串以#终结
                                for (int c = in.read(); c != REQUEST_END_CHAR; c = in.read()) {
                                    recvStrBuilder.append((char)c);
                                }
                                recvStrBuilder.append('#');

                                String recvStr = recvStrBuilder.toString();

                                //向客户端写出处理后的字符串
                                OutputStream out = conn.getOutputStream();
                                out.write(recvStr.toUpperCase().getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (conn != null) {
                                        conn.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
                }catch (Exception e){

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ServerTCPThreadSocket server = new ServerTCPThreadSocket();
        server.startServer(SERVER_IP, SERVER_PORT);
    }

}
