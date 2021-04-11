package aatral.warzone.model;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

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

public class InputCountry implements Comparator<InputCountry> {



	public InputCountry(Countries countryEntry) {
		this.countryId = countryEntry.getCountryId();
		if (countryEntry.getCountryName() == null) {
			this.countryName = "addCountry";
		} else {
			this.countryName = countryEntry.getCountryName();
		}
		this.continentId = countryEntry.getContinentId();
	}

	@Field
	private String countryId;

	@Field
	private String countryName;

	@Field
	private String continentId;

	@Override
	public int compare(InputCountry o1, InputCountry o2) {
		return Integer.parseInt(o1.getCountryId()) - Integer.parseInt(o2.getCountryId());
	}

	public InputCountry(List<InputContinent> matchingInputContinentId, ConquestCountry convertConquest, int i) {
		this.countryId = Integer.toString(i);
		this.countryName = convertConquest.getCountryName();
		for(InputContinent match : matchingInputContinentId) {
			if(convertConquest.getContinentName().equalsIgnoreCase(match.getContinentName())) {
				this.continentId = match.getContinentId();
			}

		}

	}
}
