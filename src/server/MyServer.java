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
public class MyServer {
    public InetAddress machineIP;
    public static int port = 10101;
    public HashMap<Integer,String> errorCodes = new HashMap<>();

    public void setErrorCodes() {
        this.errorCodes.put(200,"200 OK");
        this.errorCodes.put(404,"404 Not Found");
        this.errorCodes.put(500,"500 Internal Server Error");
    }

    public void getMachineIP() throws SocketException {
        String ip = "";
        InetAddress addr = null;
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

    public static String sendMessage(String address, String content) {
        String response = null;
        try(
                Socket conection = new Socket(address,port);
                BufferedReader input = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                BufferedOutputStream out = new BufferedOutputStream(conection.getOutputStream());
                ) {
            out.write(content.concat("\n\n").getBytes());
            out.flush();
            String line;
            input.ready();
            line = input.readLine();
            while ((line != null)&&(!line.equals(""))) {
                System.out.println(line);
                line = input.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.print(response);
        return  response;
    }
    

    public void run() throws SocketException {
        this.getMachineIP();
        setErrorCodes();
        Content.showIP.setText(this.machineIP.getHostAddress()+":"+port);
        while(true) {
            try (
                    ServerSocket serverSocket = new ServerSocket(port, 30, machineIP);
                    Socket con = serverSocket.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    BufferedOutputStream output = new BufferedOutputStream(con.getOutputStream());
            ) {
                String address = serverSocket.getInetAddress() + ":" + port;
                input.ready();
                String message = input.readLine();
                while ((message != null)&&(!message.equals(""))) {
                    System.out.println(message);
//                    regex match ([A-Z]+):=([a-zA-Z0-9\.\:]+)
                    String[] received = message.split(":=");
                    for(int i=0;i<received.length;i++){
                        System.out.println(received[i]);
                    }
                    message = input.readLine();
                }
                output.write("HELLO WORLD".concat("\n\n").getBytes());
                output.flush();
            } catch (IOException e) {
                Content.showMessage(e.getMessage());
            }
        }
    }
}
