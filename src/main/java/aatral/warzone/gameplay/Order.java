package aatral.warzone.gameplay;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
/**
 * <h1>Order</h1> The Class is the parent class of all the orders
 *
 * @author Vignesh
 * @version 1.0
 * @since 24-02-2021
 */
public class Order  implements Serializable{
	/**
	 * execute method is used to execute the orders in order class
	 */
	public void execute() {
    	System.out.println("Execute Orders in Order Class");
    }
}
