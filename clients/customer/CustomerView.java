package clients.customer;

import catalogue.Basket;
import catalogue.BetterBasket;
import clients.Picture;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

public class CustomerView implements Observer {

    private static final int H = 300; // Updated height of window pixels (same as other screens)
    private static final int W = 400; // Updated width of window pixels (same as other screens)

    private final JLabel pageTitle = new JLabel("");
    private final JTextField searchBar = new JTextField(20); // New search bar
    private final JButton searchButton = new JButton("Search"); // New search button
    private final JLabel feedbackLabel = new JLabel(); // Feedback label
    private final JButton checkButton = new JButton("Check");
    private final JButton clearButton = new JButton("Clear");
    private final JButton guideButton = new JButton("Help Guide"); // Created guide button to help users who are unsure on how the catalogue works

    private JList<String> productList; // Product list to display items
    private DefaultListModel<String> productModel; // Data model for the list

    public CustomerView(JFrame frame, MiddleFactory mf, int width, int height) {
        frame.setSize(W, H); // Set size to match other screens
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color consistentGrey = new Color(102, 102, 102); // Updated grey colour to match CustomerClient screen
        Color buttonGrey = new Color(96, 96, 96); // Button grey theme
        Color textGrey = new Color(69, 69, 69); // Grey background for text components

        // Remove the blue hover effect by setting a custom border for text inputs
        Border noFocusBorder = BorderFactory.createLineBorder(buttonGrey); // Border same as button grey

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(consistentGrey); // Set the main background to grey

        // Top section with title and search
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(pageTitle);
        topPanel.add(new JLabel("Search: "));
        topPanel.add(searchBar);
        topPanel.add(searchButton);
        topPanel.setBackground(consistentGrey); // Set top panel background to grey

        // Style the search bar (JTextField)
        searchBar.setBackground(textGrey);
        searchBar.setForeground(Color.WHITE);
        searchBar.setCaretColor(Color.WHITE); // Ensure the caret is visible
        searchBar.setBorder(noFocusBorder); // Remove default blue hover effect

        // Centre section with product list
        productModel = new DefaultListModel<>();
        productList = new JList<>(productModel);
        productList.setBackground(textGrey); // Set background for the product list
        productList.setForeground(Color.WHITE); // Ensure text is visible
        productList.setBorder(noFocusBorder); // Remove default blue hover effect
        productList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Single click on an item
                    String selectedItem = productList.getSelectedValue();
                    if (selectedItem != null) {
                        showProductInStockMessage(selectedItem);
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(productList);
        scrollPane.getViewport().setBackground(consistentGrey); // Set background for the scrollable product list

        // Bottom section with buttons and feedback
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(checkButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(feedbackLabel);
        bottomPanel.add(guideButton); // Added guide button to the bottom panel
        bottomPanel.setBackground(consistentGrey); // Set bottom panel background grey

        // Apply button colours
        checkButton.setBackground(buttonGrey);
        checkButton.setForeground(Color.WHITE);
        clearButton.setBackground(buttonGrey);
        clearButton.setForeground(Color.WHITE);
        guideButton.setBackground(buttonGrey);
        guideButton.setForeground(Color.WHITE);
        searchButton.setBackground(buttonGrey);
        searchButton.setForeground(Color.WHITE);

        // Added panels to the frame
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);

        // Added listeners
        searchButton.addActionListener(e -> filterProducts());
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterProducts();
            }
        });
        clearButton.addActionListener(e -> {
            productModel.clear();
            feedbackLabel.setText("Cleared product list.");
        });

        // Added action listener to the guide button
        guideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGuide();
            }
        });

        // Added action listener to the check button
        checkButton.addActionListener(e -> handleCheckAction());

        frame.getContentPane().setBackground(consistentGrey); // Ensure the entire frame background is grey
        frame.setVisible(true); // Displaying the frame
    }

    private void filterProducts() {
        String query = searchBar.getText().toLowerCase();
        productModel.clear();
        if (query.isEmpty()) {
            feedbackLabel.setText("Enter a search term.");
            return;
        }
        // Items will appear as displayed below in search bar along with new discounts applied
        boolean found = false;
        if ("0001".contains(query)) { 
            productModel.addElement("40 inch LED TV - £228.65 (15% OFF)"); 
            found = true; 
        }
        if ("0002".contains(query)) { 
            productModel.addElement("DAB Radio - £29.99"); 
            found = true; 
        }
        if ("0003".contains(query)) { 
            productModel.addElement("Toaster - £19.99 (15% OFF)"); 
            found = true; 
        }
        if ("0004".contains(query)) { 
            productModel.addElement("Watch - £29.99 (15% OFF)"); 
            found = true; 
        }
        if ("0005".contains(query)) { 
            productModel.addElement("Digital Camera - £89.99"); 
            found = true; 
        }
        if ("0006".contains(query)) { 
            productModel.addElement("MP3 Player - £7.99"); 
            found = true; 
        }
        if ("0007".contains(query)) { 
            productModel.addElement("USB Drive 32GB - £6.99"); 
            found = true; 
        }

        if (!found) {
            showOutOfStockNotification(query);
        } else {
            feedbackLabel.setText(productModel.isEmpty() ? "No products found! They could be out of stock." : "Products updated.");
        }
    }

    private void showGuide() {
        JOptionPane.showMessageDialog(null, "How to use the catalogue:\n" +
                "1. Use the search bar at the top to find products by code.\n" +
                "2. Products in stock appear at the bottom right of screen and in the search results.\n" +
                "2. Click 'Search' to display matching items.\n" +
                "3. Use 'Clear' to reset the product list.\n" +
                "4. Navigate to the Cashier Client screen and verify your item.\n" +
                "5. Click on BUY then BUY/BOUGHT and our staff will pack your product!.", 
                "Catalogue Guide", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showProductInStockMessage(String product) {
        JOptionPane.showMessageDialog(null, "Product In Stock:\n" + product + "\nPlease continue to Cashier Client to complete purchase.", "Stock Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showOutOfStockNotification(String query) {
        JOptionPane.showMessageDialog(null, "The product code '" + query + "' is out of stock or does not exist.", "Out of Stock Notification", JOptionPane.WARNING_MESSAGE);
    }

    private void handleCheckAction() {
        // Check if a product is selected
        if (productList.getSelectedIndex() != -1) {
            JOptionPane.showMessageDialog(null, "Product is in stock.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please first select a product.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    public static void main(String[] args) {
        new CustomerView();
    }

    public CustomerView() {
        this(new JFrame(), new LocalMiddleFactory(), W, H); // Consistent height so CustomerView will Align with other GUIs
    }

    public void setController(CustomerController controller) {
    }
}
