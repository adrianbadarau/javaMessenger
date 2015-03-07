package server;

import models.ReceivedMessage;
import views.Content;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.SQLException;
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
                if(line.equals("Success")){
                    Content.showMessage("Message has been sent");
                    Content.showConnection.setText("Idle");
                }else if(line.equals("Error")){
                    Content.showMessage("Error");
                }
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
                ReceivedMessage got = new ReceivedMessage();
                String message = input.readLine();
                while ((message != null)&&(!message.equals(""))) {
                    System.out.println(message);
//                    regex match ([A-Z]+):=([a-zA-Z0-9\.\:]+)
                    String[] received = message.split(":=");
                    got.setAttribute(received[0],received[1]);
                    message = input.readLine();
                }
                if(got.save()) {
                    output.write("Success".concat("\n\n").getBytes());
                    Content.showMessage("You got a message from: "+got.senderIP);
                    final Button msg = new Button(got.title+" from: "+got.senderIP);
                    Content.inboxP.add(msg);
                    msg.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Content.showMessage(got.toString());
                        }
                    });

                }else{
                    output.write("Error".concat("\n\n").getBytes());
                }
                output.flush();
            } catch (IOException | SQLException e) {
                Content.showMessage(e.getMessage());
            }
        }
    }
}
