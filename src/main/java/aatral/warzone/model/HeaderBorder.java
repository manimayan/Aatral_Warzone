package aatral.warzone.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

@Record
public class HeaderBorder {
	
	@Field
	private char space1= '\n';
	

	@Field
	private char space2= '\n';
	
	
	
	@Field
	private String header="[Borders]";

}