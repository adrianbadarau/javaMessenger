package controllers;

import views.Content;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by adrian on 18.02.2015.
 */
public class ContentController {
    public static void populateContent(){

        Content.sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Content.showConnection.setText("Sending Message");
            }
        });
    }
    
    public static void run(){
        populateContent();
    }
}
