package clients.packing;

import catalogue.Basket;
import middle.MiddleFactory;
import middle.OrderProcessing;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implements the Packing view.
 */
public class PackingView implements Observer {
    private static final String PACKED = "Packed";

    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;       // Width of window pixels

    private final JLabel pageTitle = new JLabel();
    private final JLabel theAction = new JLabel();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtPack = new JButton(PACKED);

    private OrderProcessing theOrder = null;

    private PackingController cont = null;

    /**
     * Construct the view
     *
     * @param rpc Window in which to construct
     * @param mf  Factory to deliver order and stock objects
     * @param x   x-coordinate of position of window on screen
     * @param y   y-coordinate of position of window on screen
     */
    public PackingView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try {
            theOrder = mf.makeOrderProcessing();        // Process order
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        Container cp = rpc.getContentPane();    // Content Pane
        Container rootWindow = (Container) rpc;         // Root Window
        cp.setLayout(null);                             // No layout manager
        rootWindow.setSize(W, H);                       // Size of Window
        rootWindow.setLocation(x, y);
        cp.setBackground(new Color(102, 102, 102));    // Setting a dark grey background

        Font f = new Font("Monospaced", Font.PLAIN, 12);  // Font f is

        pageTitle.setBounds(110, 0, 270, 20);
        pageTitle.setText("Packing Bought Order");
        cp.add(pageTitle);

        // Apply button styling
        Color buttonGrey = new Color(96, 96, 96); // Button grey theme
        theBtPack.setBounds(16, 25 + 60 * 0, 80, 40);   // Check Button
        theBtPack.setBackground(buttonGrey);
        theBtPack.setForeground(Color.WHITE);
        theBtPack.addActionListener(                   // Call back code
                e -> {
                    cont.doPacked();                   // Perform the packed action
                    showOrderCompletionMessage();      // Display confirmation pop-up
                });
        cp.add(theBtPack);                             // Add to canvas

        theAction.setBounds(110, 25, 270, 20);          // Message area
        theAction.setText("");                          // Blank
        cp.add(theAction);                              // Add to canvas

        theSP.setBounds(110, 55, 270, 205);             // Scrolling pane
        theOutput.setText("");                          // Blank
        theOutput.setFont(f);                           // Uses font
        cp.add(theSP);                                  // Add to canvas
        theSP.getViewport().add(theOutput);             // In TextArea
        rootWindow.setVisible(true);                    // Make visible
    }

    /**
     * Displays a message confirming the order has been completed.
     * Includes an estimated delivery time between 24-48 hours.
     */
    private void showOrderCompletionMessage() {
        int deliveryTime = ThreadLocalRandom.current().nextInt(24, 49); // Random time between 24-48 hours that appears when user/staff have packed order
        JOptionPane.showMessageDialog(
                null,
                "Order has been successfully packed and is ready for delivery!\n" +
                        "Estimated Delivery Time: " + deliveryTime + " hours",
                "Order Complete", // Title of the message box
                JOptionPane.INFORMATION_MESSAGE // Information message type
        );
    }

    public void setController(PackingController c) {
        cont = c;
    }

    /**
     * Update the view
     *
     * @param modelC The observed model
     * @param arg    Specific args
     */
    @Override
    public void update(Observable modelC, Object arg) {
        PackingModel model = (PackingModel) modelC;
        String message = (String) arg;
        theAction.setText(message);

        Basket basket = model.getBasket();
        if (basket != null) {
            theOutput.setText(basket.getDetails());
        } else {
            theOutput.setText("");
        }
    }
}
