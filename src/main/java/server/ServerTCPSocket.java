package server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by liyinyong on 2018/7/29.
 */
public class ServerTCPSocket {
    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 10005;
    //请求终结字符串
    public static final char REQUEST_END_CHAR = '#';

    public void startServer(String serverIp, int serverPort) {
        InetAddress serverAddress;
        try {
            serverAddress = InetAddress.getByName(serverIp);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try (ServerSocket serverSocket = new ServerSocket(serverPort, 5, serverAddress)) {
            while (true) {
                StringBuilder recvStrBuilder = new StringBuilder();
                try (Socket connection = serverSocket.accept()) {
                    InputStream in = connection.getInputStream();
                    while (true) {
                        int c = in.read();
                        if (c == REQUEST_END_CHAR) {
                            recvStrBuilder.append('#');
                            break;
                        }
                        recvStrBuilder.append((char)c);
                    }
                    OutputStream out = connection.getOutputStream();
                    out.write(recvStrBuilder.toString().toUpperCase().getBytes());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerTCPSocket server = new ServerTCPSocket();
        server.startServer(SERVER_IP, SERVER_PORT);
    }
}
