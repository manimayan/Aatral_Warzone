package aatral.warzone.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Record

/**
 * <h1>Continent POJO</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

public class ConquestContinent {

	@Field
	private String ContinentName;

	@Field
	public String ContinentValue;
	
	public ConquestContinent(InputContinent continent) {
		this.ContinentName = continent.getContinentName();
		this.ContinentValue = continent.getContinentValue();
	}
}
