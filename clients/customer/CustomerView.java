package clients.customer;

import catalogue.Basket;
import catalogue.BetterBasket;
import clients.Picture;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class CustomerView implements Observer {

    private static final int H = 400;       // Updated height of window pixels
    private static final int W = 600;       // Updated width of window pixels

    private final JLabel pageTitle = new JLabel("Customer View:");
    private final JTextField searchBar = new JTextField(20); // New search bar
    private final JButton searchButton = new JButton("Search"); // New search button
    private final JLabel feedbackLabel = new JLabel(); // Feedback label
    private final JButton checkButton = new JButton("Check");
    private final JButton clearButton = new JButton("Clear");

    private JList<String> productList; // Product list to display items
    private DefaultListModel<String> productModel; // Data model for the list

    public CustomerView(JFrame frame, MiddleFactory mf, int width, int height) {
        frame.setSize(width > 0 ? width : W, height > 0 ? height : H); // Use provided dimensions or defaults
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.GRAY); // Sets background colour to grey

        // Top section with title and search
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(pageTitle);
        topPanel.add(new JLabel("Search: "));
        topPanel.add(searchBar);
        topPanel.add(searchButton);
        topPanel.setBackground(Color.GRAY); // Set top panel background to grey

        // Center section with product list
        productModel = new DefaultListModel<>();
        productList = new JList<>(productModel);
        JScrollPane scrollPane = new JScrollPane(productList);

        // Bottom section with buttons and feedback
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(checkButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(feedbackLabel);
        bottomPanel.setBackground(Color.GRAY); // Set bottom panel background to grey

        // Adding panels to the frame
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);

        // Add listeners
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

        frame.setVisible(true); // Display the frame
    }

    private void filterProducts() {
        String query = searchBar.getText().toLowerCase();
        productModel.clear();
        if (query.isEmpty()) {
            feedbackLabel.setText("Enter a search term.");
            return;
        }
        // Items will appear as displayed below in search bar
        if ("0001".contains(query)) productModel.addElement("40 inch LED TV - £228.65 (+15% OFF)");
        if ("0002".contains(query)) productModel.addElement("DAB Radio - £29.99 (+15% OFF)");
        if ("0003".contains(query)) productModel.addElement("Toaster - £19.99 (+15% OFF)");
        if ("0004".contains(query)) productModel.addElement("Watch - £29.99 (+15% OFF)");
        if ("0005".contains(query)) productModel.addElement("Digital Camera - £89.99 (+15% OFF)");
        if ("0006".contains(query)) productModel.addElement("MP3 Player - £7.99 (+15% OFF)");
        if ("0007".contains(query)) productModel.addElement("USB Drive 32GB - £6.99 (+15% OFF)");

        feedbackLabel.setText(productModel.isEmpty() ? "No products found! They could be out of stock." : "Products updated.");
    }

    @Override
    public void update(Observable o, Object arg) {
        
    }

    public static void main(String[] args) {
        new CustomerView();
    }

    public CustomerView() {
        this(new JFrame(), new LocalMiddleFactory(), 600, 400);
    }

    public void setController(CustomerController controller) {
        // Storing the controller reference if needed for later use
    }
}
