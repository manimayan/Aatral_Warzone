package aatral.warzone.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

@Record


/**
 * <h1>HeaderCountry</h1> The Class holds the header content of country map
 *
 * @author William
 * @version 1.0
 * @since 24-02-2021
 */
public class HeaderCountry {

	@Field
	private char space1= '\n';
	

	@Field
	private char space2= '\n';
	
	
	@Field
	private String header="[Countries]";


}
