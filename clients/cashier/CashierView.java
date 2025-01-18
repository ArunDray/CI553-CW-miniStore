package clients.cashier;

import middle.MiddleFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Cashier view
 */
public class CashierView implements Observer {

    private static final String VERIFY = "Verify";
    private static final String BUY = "Buy";
    private static final String BOUGHT = "Receipt";

    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;       // Width of window pixels

    private final JLabel pageTitle = new JLabel();
    private final JLabel theAction = new JLabel();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtVerify = new JButton(VERIFY);
    private final JButton theBtBuy = new JButton(BUY);
    private final JButton theBtBought = new JButton(BOUGHT);
    private final JButton theBtVerifiedMessage = new JButton("Product Verified and added to Basket"); // New button
    private final JTextField productInputField = new JTextField(10); // Input field for product number

    private CashierController cont = null;

    /**
     * Construct the view
     *
     * @param rpc Window in which to construct
     * @param mf  Factory to deliver order and stock objects
     * @param x   x-coordinate of position of window on screen
     * @param y   y-coordinate of position of window on screen
     */
    public CashierView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        Container cp = rpc.getContentPane();    // Content Pane
        Container rootWindow = (Container) rpc;         // Root Window
        cp.setLayout(null);                             // No layout manager
        rootWindow.setSize(W, H);                       // Size of Window
        rootWindow.setLocation(x, y);
        cp.setBackground(new Color(102, 102, 102));    // Set a grey background

        Font f = new Font("Monospaced", Font.PLAIN, 12);  // Font f is

        pageTitle.setBounds(110, 0, 270, 20);
        pageTitle.setText("Cashier Client");
        cp.add(pageTitle);

        JLabel productInputLabel = new JLabel("Product Number:");
        productInputLabel.setBounds(16, 25, 120, 20); // Label for product input
        cp.add(productInputLabel);

        productInputField.setBounds(140, 25, 80, 20); // Input field for product number
        cp.add(productInputField);

        theBtVerify.setBounds(16, 60, 80, 40);   // Verify Button
        theBtVerify.addActionListener(                   // Callback code
                e -> {
                    String productNum = productInputField.getText(); // Get product number from input
                    if (productNum.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter a product number.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    cont.doCheck(productNum);            // Perform verify action
                    showVerifiedMessage();               // Display verification message
                });
        cp.add(theBtVerify);                             // Add to canvas

        theBtBuy.setBounds(16, 110, 80, 40);             // Buy Button
        theBtBuy.addActionListener(e -> cont.doBuy());   // Callback code
        cp.add(theBtBuy);                                // Add to canvas

        theBtBought.setBounds(16, 160, 80, 40);          // Bought Button
        theBtBought.addActionListener(e -> cont.doBought()); // Callback code
        cp.add(theBtBought);                             // Add to canvas

        theBtVerifiedMessage.setBounds(110, 220, 270, 40); // Verified message button
        theBtVerifiedMessage.setBackground(new Color(96, 96, 96)); // Button grey background
        theBtVerifiedMessage.setForeground(Color.WHITE);           // Button white text
        theBtVerifiedMessage.setVisible(false);                    // Initially hidden
        cp.add(theBtVerifiedMessage);                              // Add to canvas

        theAction.setBounds(110, 60, 270, 20);           // Message area
        theAction.setText("");                           // Blank
        cp.add(theAction);                               // Add to canvas

        theSP.setBounds(110, 90, 270, 120);              // Scrolling pane
        theOutput.setText("");                           // Blank
        theOutput.setFont(f);                            // Uses font
        cp.add(theSP);                                   // Add to canvas
        theSP.getViewport().add(theOutput);              // In TextArea
        rootWindow.setVisible(true);                     // Make visible
    }

    /**
     * Display the "Product Verified and added to Basket" message
     */
    private void showVerifiedMessage() {
        theBtVerifiedMessage.setVisible(true); // Show the button
        Timer timer = new Timer(3000, e -> theBtVerifiedMessage.setVisible(false)); // Auto-hide after 3 seconds
        timer.setRepeats(false);
        timer.start();
    }

    public void setController(CashierController c) {
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
        CashierModel model = (CashierModel) modelC;
        String message = (String) arg;
        theAction.setText(message);

        if (model.getBasket() != null) {
            theOutput.setText(model.getBasket().getDetails());
        } else {
            theOutput.setText("");
        }
    }
}