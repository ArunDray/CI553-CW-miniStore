package catalogue;

import java.io.Serializable;

/**
 * Used to hold the following information about
 * a product: Product number, Description, Price, Stock level.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

// Modernised Variables
public class Product implements Serializable
{
  private static final long serialVersionUID = 20092506;
  private String ProductNum;       // Product number
  private String Description;      // Description of product
  private double Price;            // Price of product
  private int    Quantity;         // Quantity involved

  /**
   * Construct a product details
   * @param aProductNum Product number
   * @param aDescription Description of product
   * @param aPrice The price of the product
   * @param aQuantity The Quantity of the product involved
   */
  public Product( String aProductNum, String aDescription,
                  double aPrice, int aQuantity )
  {
    ProductNum  = aProductNum;     // Product number
    Description = aDescription;    // Description of product
    Price       = aPrice;          // Price of product
    Quantity    = aQuantity;       // Quantity involved
  }
  
  public String getProductNum()  { return ProductNum; }
  public String getDescription() { return Description; }
  public double getPrice()       { return Price; }
  public int    getQuantity()    { return Quantity; }
  
  public void setProductNum( String aProductNum )
  { 
    ProductNum = aProductNum;
  }
  
  public void setDescription( String aDescription )
  { 
    Description = aDescription;
  }
  
  public void setPrice( double aPrice )
  { 
    Price = aPrice;
  }
  
  public void setQuantity( int aQuantity )
  { 
    Quantity = aQuantity;
  }

}
