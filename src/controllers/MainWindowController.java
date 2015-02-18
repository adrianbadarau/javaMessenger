package controllers;

import views.MainWindow;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by adrian on 18.02.2015.
 */
public class MainWindowController {
    public static void populateContent(){
        MainWindow.homePage.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });        
    }
    
    public static void run(){
        populateContent();
        
    }
}
