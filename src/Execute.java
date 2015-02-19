import controllers.*;
import server.MyServer;
import views.*;

/**
 * Created by adrian on 18.02.2015.
 */
public class Execute {
    public static void main(String[] args) {
        MainWindow.init();
        MainWindowController.run();
        ContentController.run();
        HeaderController.run();
        MyServer server = new MyServer();
        Thread thread = new Thread(server);
        thread.start();
    }
}
