package aatral.warzone.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

@Record
/**
 * <h1>HeaderContinent</h1> The Class holds the header content of continent map
 *
 * @author Manimaran Palani
 * @version 1.0
 * @since 10-04-2021
 */
public class ConquestHeaderContinent {

	@Field
	private char space2= '\n';

	@Field
	private String header="[Continents]";

}
