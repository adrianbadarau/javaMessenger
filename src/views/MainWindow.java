package views;

import controllers.ContentController;

import java.awt.*;

/**
 * Created by adrian on 18.02.2015.
 */
public class MainWindow {
    public static Frame homePage;
    public static void init(){
        homePage = new Frame();
        homePage.add(Header.createHeader(), BorderLayout.PAGE_START);
        homePage.add(Content.createContent(), BorderLayout.CENTER);
        homePage.setSize(500, 500);
        homePage.setVisible(true);
    }
}
