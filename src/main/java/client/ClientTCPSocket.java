package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import server.ServerTCPSocket;
import server.ServerUDPSocket;


/**
 * Created by liyinyong on 2018/7/29.
 */
public class ClientTCPSocket {
    //客户端使用的TCP Socket
    private Socket clientSocket;

    public String toUpperRemote(String serverIp, int serverPort, String str) {
        StringBuilder recvStrBuilder = new StringBuilder();
        try {
            //创建UDP Socket
            clientSocket = new Socket(serverIp, serverPort);

            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(str.getBytes());

            InputStream inputStream = clientSocket.getInputStream();
            for (int c = inputStream.read(); c != '#'; c = inputStream.read()) {
                recvStrBuilder.append((char)c);
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != clientSocket) {


                try {
                    if (clientSocket != null) {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return recvStrBuilder.toString();
    }

    public static void main(String[] args) {
        ClientTCPSocket client = new ClientTCPSocket();
        String recvStr = client.toUpperRemote(ServerUDPSocket.SERVER_IP, ServerUDPSocket.SERVER_PORT, "hello my friend"+ ServerTCPSocket.REQUEST_END_CHAR);
        System.out.println("收到:" + recvStr);
    }

}
