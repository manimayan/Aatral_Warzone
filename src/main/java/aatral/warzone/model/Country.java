package aatral.warzone.model;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Record

/**
 * <h1>Country POJO</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

public class Country {

	@Field
	private String countryId;

	@Field
	private String countryName;

	@Field
	private String continentId;

	private String continentName;

	private int armies;

	public Country(String countryId, String continentId) {
		this.countryId = countryId;
		this.continentId = continentId;
	}

	public Country(String countryId, String countryName, String continentId) {
		this.countryId = countryId;
		this.countryName = countryName;
		this.continentId = continentId;
	}

}
