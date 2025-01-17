package clients;

import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierView;
import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;
import clients.packing.PackingController;
import clients.packing.PackingModel;
import clients.packing.PackingView;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Starts all the clients (user interface) as a single application.
 * Good for testing the system using a single application.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 * @version year-2024
 */
public class Main {

    public static void main(String args[]) {
        // Set the hover effect for buttons to a dark grey
        UIManager.put("Button.select", new Color(105, 105, 105)); // Dark grey colour set for when user clicks / holds downn on the button

        // Set the button outline to dark grey colour on button
        UIManager.put("Button.border", BorderFactory.createLineBorder(new Color(140, 140, 140)));

        new Main().begin();
    }

    /**
     * Starts the system (Non-distributed)
     */
    public void begin() {
        MiddleFactory mlf = new LocalMiddleFactory();  // Direct access
        startCustomerGUI_MVC(mlf);
        startCashierGUI_MVC(mlf);
        startPackingGUI_MVC(mlf);
        startBackDoorGUI_MVC(mlf);
    }

    /**
     * Starts the Customer client GUI
     * @param mlf A factory to create objects to access the stock list
     */
    public void startCustomerGUI_MVC(MiddleFactory mlf) {
        JFrame window = new JFrame();
        window.setTitle("Customer Client MVC");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension pos = PosOnScrn.getPos(); // 

        CustomerModel model = new CustomerModel(mlf);
        CustomerView view = new CustomerView(window, mlf, pos.width, pos.height); // Updated to match new constructor
        CustomerController controller = new CustomerController(model, view);
        view.setController(controller);

        model.addObserver(view);       // Add observer to the model
        window.setVisible(true);       // Start GUI
    }

    /**
     * Starts the Cashier client GUI
     * @param mlf A factory to create objects to access the stock list
     */
    public void startCashierGUI_MVC(MiddleFactory mlf) {
        JFrame window = new JFrame();
        window.setTitle("Cashier Client MVC");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension pos = PosOnScrn.getPos();

        CashierModel model = new CashierModel(mlf);
        CashierView view = new CashierView(window, mlf, pos.width, pos.height);
        CashierController controller = new CashierController(model, view);
        view.setController(controller);

        model.addObserver(view);       // Add observer to the model
        window.setVisible(true);       // Make window visible
        model.askForUpdate();          // Initial display
    }

    /**
     * Starts the Packing client GUI
     * @param mlf A factory to create objects to access the stock list
     */
    public void startPackingGUI_MVC(MiddleFactory mlf) {
        JFrame window = new JFrame();
        window.setTitle("Packing Client MVC");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension pos = PosOnScrn.getPos();

        PackingModel model = new PackingModel(mlf);
        PackingView view = new PackingView(window, mlf, pos.width, pos.height);
        PackingController controller = new PackingController(model, view);
        view.setController(controller);

        model.addObserver(view);       // Add observer to the model
        window.setVisible(true);       // Make window visible
    }

    /**
     * Starts the BackDoor client GUI
     * @param mlf A factory to create objects to access the stock list
     */
    public void startBackDoorGUI_MVC(MiddleFactory mlf) {
        JFrame window = new JFrame();
        window.setTitle("BackDoor Client MVC");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension pos = PosOnScrn.getPos();

        BackDoorModel model = new BackDoorModel(mlf);
        BackDoorView view = new BackDoorView(window, mlf, pos.width, pos.height);
        BackDoorController controller = new BackDoorController(model, view);
        view.setController(controller);

        model.addObserver(view);       // Add observer to the model
        window.setVisible(true);       // Make window visible
    }
}
