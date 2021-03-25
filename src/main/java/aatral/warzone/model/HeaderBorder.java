package aatral.warzone.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

@Record


/**
 * <h1>HeaderBorder</h1> The Class holds the header content of border map
 *
 * @author Vikram
 * @version 1.0
 * @since 24-02-2021
 */
public class HeaderBorder {
	
	@Field
	private char space1= '\n';
	

	@Field
	private char space2= '\n';
	
	
	
	@Field
	private String header="[Borders]";

}
