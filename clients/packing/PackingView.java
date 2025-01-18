package clients.packing;

import catalogue.Basket;
import middle.MiddleFactory;
import middle.OrderProcessing;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Packing view.
 */
public class PackingView implements Observer {

    private static final int H = 300;       // Height of window pixels (consistent with other screens)
    private static final int W = 400;       // Width of window pixels (consistent with other screens)

    private final JLabel theAction = new JLabel();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtPack = new JButton("Packed");
    private final JLabel titleLabel = new JLabel("Checkout & Packing", SwingConstants.CENTER); // Title label

    private PackingController cont = null;
    private PackingModel model = null;

    /**
     * Construct the view.
     *
     * @param rpc Window in which to construct
     * @param mf  Factory to deliver order and stock objects
     * @param x   x-coordinate of position of window on screen
     * @param y   y-coordinate of position of window on screen
     */
    public PackingView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try {
            OrderProcessing theOrder = mf.makeOrderProcessing(); // Obtain order processing
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        Container cp = rpc.getContentPane();    // Content Pane
        Container rootWindow = (Container) rpc;         // Root Window
        cp.setLayout(null);                             // No layout manager
        rootWindow.setSize(W, H);                       // Size of Window
        rootWindow.setLocation(x, y);
        cp.setBackground(new Color(102, 102, 102));    // Use dark grey background

        if (rpc instanceof JFrame) { // Check JFrame to set the title
            JFrame frame = (JFrame) rpc;
            frame.setTitle("Packing Client");  // Set the title of the GUI window
        }

        Font f = new Font("Segoe UI", Font.PLAIN, 14);  // Consistent font

        titleLabel.setBounds(20, 10, 360, 30);          // Title label placement
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Bold font for the title
        titleLabel.setForeground(Color.BLACK);         // White text for visibility
        cp.add(titleLabel);

        theAction.setBounds(20, 50, 360, 20);           // Action feedback label
        theAction.setForeground(Color.WHITE);          // White text
        cp.add(theAction);

        theBtPack.setBounds(20, 90, 80, 40);            // Button alignment placement
        theBtPack.setBackground(new Color(96, 96, 96)); // Grey theme
        theBtPack.setForeground(Color.WHITE);          // White text
        theBtPack.addActionListener(e -> showCompletionPopup()); // Action listener for Packed button
        cp.add(theBtPack);

        theSP.setBounds(110, 90, 270, 180);             // Scrolling pane
        theOutput.setText("");                          // Blank
        theOutput.setFont(f);                           // Consistent font
        theOutput.setBackground(new Color(69, 69, 69)); // Dark grey background for text area
        theOutput.setForeground(Color.WHITE);           // White text
        theSP.getViewport().add(theOutput);             // Add text area to scroll pane
        cp.add(theSP);

        rootWindow.setVisible(true);                    // Make visible
    }

    /**
     * Displays a pop-up dialog after the order is packed.
     */
    private void showCompletionPopup() {
        if (model == null) {
            JOptionPane.showMessageDialog(
                null,
                "Error: Packing model is not available.",
                "Model Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Basket basket = model.getBasket();

        if (basket != null && !basket.getDetails().isEmpty()) {
            // Generate a random estimated delivery time between 12 and 72 hours
            int estimatedDeliveryTime = (int) (Math.random() * 61) + 12; // 12 to 72 hours

            // Display the pop-up with the updated message
            JOptionPane.showMessageDialog(
                null,
                "Order Complete\nEstimated Delivery Time: " + estimatedDeliveryTime + " hours",
                "Order Packed",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            // Display a message indicating no current orders
            JOptionPane.showMessageDialog(
                null,
                "You have no current orders.",
                "No Orders",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void setController(PackingController c) {
        cont = c;
        if (c instanceof PackingController) {
            model = c.getPackingModel(); // Ensure model is set correctly
        }
    }

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
