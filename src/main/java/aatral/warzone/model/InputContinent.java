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

public class InputContinent {

	public InputContinent(String ContinentId, String ContinentValue) {
		this.ContinentId = ContinentId;
		this.ContinentName = "addedContinent";
		this.ContinentValue = ContinentValue;
	}

	public InputContinent(Continent value) {
		this.ContinentId = value.getContinentId();
		this.ContinentName = value.getContinentName();
		this.ContinentValue = value.getContinentValue();
	}

	@Field
	private String ContinentId;

	@Field
	private String ContinentName;

	@Field
	private String ContinentValue;
}
