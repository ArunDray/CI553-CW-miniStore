package catalogue;

import java.io.Serializable;
import java.util.Collections;

import java.util.Comparator; // Added Import statement to support comparing

/**
 * Write a description of class BetterBasket here.
 * New branch
 * 
 * @author  Arun_Dray
 * @version 1.1
 */
public class BetterBasket extends Basket implements Serializable
{
  private static final long serialVersionUID = 1L;
  

  // You need to add code here
  // merge the items for same product,
  // or sort the item based on the product number

  public void sortByProductNum() {
	    Collections.sort(this, Comparator.comparing(Product::getProductNum));
	} 
 
  // Feature added so users can merge quantities of the same product in basket
  @Override
  public boolean add(Product pr) {
      for (Product existingProduct : this) {
          if (existingProduct.getProductNum().equals(pr.getProductNum())) {
              existingProduct.setQuantity(existingProduct.getQuantity() + pr.getQuantity());
              return true;
          }
      }
      return super.add(pr);
  }
}
