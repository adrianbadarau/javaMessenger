import controllers.*;
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
    }
}
