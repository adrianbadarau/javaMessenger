package server;

import views.Content;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;

/**
 * Created by adrian on 19.02.2015.
 */
public class MyServer implements Runnable {
    public static InetAddress machineIP;
    public static int port = 10101;
    
    public static void getMachineIP(){
        String ip = "";
        InetAddress addr = null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                }
                machineIP = addr;
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

    }
    

    @Override
    public void run() {
        getMachineIP();
        try(
                ServerSocket serverSocket = new ServerSocket(port,30,machineIP);
                Socket con = serverSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
                BufferedOutputStream output = new BufferedOutputStream(con.getOutputStream())
        ) {
            String address = serverSocket.getInetAddress()+":"+port;
            Content.showIP.setText(address);
            String message = input.readLine();
            while(message!=null&&message!=""){
                System.out.println(message);
                message = input.readLine();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
