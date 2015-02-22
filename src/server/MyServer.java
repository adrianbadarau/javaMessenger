package server;

import views.Content;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by adrian on 19.02.2015.
 */
public class MyServer implements Runnable {
    public static InetAddress machineIP;
    public static int port = 10101;
    public HashMap<Integer,String> errorCodes = new HashMap<>();

    public void setErrorCodes() {
        this.errorCodes.put(200,"200 OK");
        this.errorCodes.put(404,"404 Not Found");
        this.errorCodes.put(500,"500 Internal Server Error");
    }

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

    public String createHeader(Integer errorCode){
        StringBuilder headerStringBuilder = new StringBuilder();
        headerStringBuilder.append("HTTP/1.0 ");
        headerStringBuilder.append(errorCodes.get(errorCode));
        headerStringBuilder.append("\r\n");
        headerStringBuilder.append("Connection: keep-alive\r\n");
//        headerStringBuilder.append("Content-Type: text/plain");
        headerStringBuilder.append("\r\n");
        return headerStringBuilder.toString();
    }

    public static String sendMessage(String address, String content) throws IOException {
        Socket conection = new Socket(address,port);
        BufferedReader input = new BufferedReader(new InputStreamReader(conection.getInputStream()));
        BufferedOutputStream out = new BufferedOutputStream(conection.getOutputStream());
        out.write("POST message HTTP/1.1\r\n\r\n".getBytes());
        out.write(content.getBytes());
        out.flush();
        String response = null;
        String line;
        while ((line=input.readLine())!=null){
            response+=line;
        }
        return  response;
    }
    

    @Override
    public void run() {
        getMachineIP();
        setErrorCodes();
        Content.showIP.setText(machineIP.getHostAddress()+":"+port);
        while(true) {
            try (
                    ServerSocket serverSocket = new ServerSocket(port, 30, machineIP);
                    Socket con = serverSocket.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    BufferedOutputStream output = new BufferedOutputStream(con.getOutputStream());
            ) {
                String address = serverSocket.getInetAddress() + ":" + port;
                String message = input.readLine();
                while (message != null && !message.equals("")) {
                    System.out.println(message);
                    message = input.readLine();
                }
                output.write(createHeader(200).getBytes());
                output.write("HELLO WORLD".getBytes());
            } catch (IOException e) {
                Content.showMessage(e.getMessage());
            }
        }
    }
}
