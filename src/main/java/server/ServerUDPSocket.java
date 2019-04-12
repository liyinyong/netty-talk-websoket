package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by liyinyong on 2018/7/29.
 */
public class ServerUDPSocket {
    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 10005;
    public static int MAX_BYTES = 1024;

    private DatagramSocket serverSocket;

    public void startServer(String serverIp, int serverPort) {
        try {
            InetAddress serverAddress = InetAddress.getByName(serverIp);
            serverSocket = new DatagramSocket(serverPort, serverAddress);

            byte[] recvBuf = new byte[MAX_BYTES];
            DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
            while (true) {
                try {
                    serverSocket.receive(recvPacket);
                } catch (IOException e) {
                    e.getStackTrace();
                }
                String recvStr = new String(recvPacket.getData());

                InetAddress clientAddr = recvPacket.getAddress();
                int clientPort = recvPacket.getPort();

                String upperString = recvStr.toUpperCase();
                byte[] sendBuf = upperString.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, clientAddr, clientPort);
                try {
                    serverSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
        } finally {
            //记得关闭Socket
            if (null != serverSocket) {
                serverSocket.close();
                serverSocket = null;
            }
        }
    }
    public static void main(String[] args) {
        ServerUDPSocket server = new ServerUDPSocket();
        server.startServer(SERVER_IP, SERVER_PORT);
    }
}
