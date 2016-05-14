

import java.awt.*;
import javax.swing.*;


public class EmployeeMSApp {
    boolean packFrame = false;

    /**
     * Construct and show the application.
     */
    public EmployeeMSApp() {
        new EmpLogin().setVisible(true);
 
    }

    /**
     * Application entry point.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.
                                             getSystemLookAndFeelClassName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                new EmployeeMSApp();
            }
        });
    }
}
