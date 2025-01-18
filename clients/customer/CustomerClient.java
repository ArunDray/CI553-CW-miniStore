package clients.customer;

import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

import javax.swing.*;
import java.awt.*; // Import for Color class

/**
 * The standalone Customer Client
 */
public class CustomerClient {

    public static void main(String args[]) {
        String stockURL = args.length < 1         // URL of stock R
                        ? Names.STOCK_R           // default location
                        : args[0];                // supplied location

        RemoteMiddleFactory mrf = new RemoteMiddleFactory();
        mrf.setStockRInfo(stockURL);
        displayGUI(mrf);                          // Create GUI
    }

    private static void displayGUI(MiddleFactory mf) {
        JFrame window = new JFrame();
        window.setTitle("Customer Client (MVC RMI)");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background colour of the taskbar tab to grey
        window.getContentPane().setBackground(Color.GRAY);

        CustomerModel model = new CustomerModel(mf);
        CustomerView view = new CustomerView(window, mf, 600, 400); // Match new constructor
        CustomerController cont = new CustomerController(model, view);

        window.setSize(800, 600);
        window.setVisible(true);
    }
}
