package aatral.warzone.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Record

/**
 * <h1>Border POJO</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

public class InputBorders implements Comparator<InputBorders>{

	public InputBorders(Countries countryEntry) {
		this.countryId = countryEntry.getCountryId();
		this.adjacentCountries = countryEntry.getCountryOwnedBorders();
	}

	@Field
	private String countryId;

	@Field(collection = Set.class, maxOccurs = -1,trim = true)
	Set<String> adjacentCountries = new HashSet<>();

	private String countryName;

	private String continentId;

	@Override
	public int compare(InputBorders o1, InputBorders o2) {

		return Integer.parseInt(o1.getCountryId()) - Integer.parseInt(o2.getCountryId());
	}

	public InputBorders(List<InputCountry> matchingInputCountryId, ConquestBorders convertConquest) {
		for(InputCountry match :matchingInputCountryId) {
			if(convertConquest.getCountryName().equalsIgnoreCase(match.getCountryName())) {
				this.countryId= match.getCountryId();
			}
			for(String matchingCountry : convertConquest.getAdjacentCountries()) {
				if(match.getCountryName().equalsIgnoreCase(matchingCountry)) {
					this.adjacentCountries.add(match.getCountryId());
				}
			}
		}
	}

	public InputBorders(String countryId, HashSet<String> hashSet) {
		this.countryId=countryId;
		this.adjacentCountries=hashSet;
	}

	public InputBorders(String countryId, Set<String> countryOwnedBorders, String continentId, String countryName) {
		this.countryId=countryId;
		this.adjacentCountries = countryOwnedBorders;
		this.continentId = continentId;
		this.countryName = countryName;
	}
}
