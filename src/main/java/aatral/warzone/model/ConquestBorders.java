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
@AllArgsConstructor
@NoArgsConstructor
@Record

/**
 * <h1>Border POJO</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

public class ConquestBorders implements Comparator<ConquestBorders>{

	public ConquestBorders(Countries countryEntry) {
		this.countryId = countryEntry.getCountryId();
		this.adjacentCountries = countryEntry.getCountryOwnedBorders();
	}

	private String countryId;

	@Field
	private String countryName;

	@Field
	private String x;

	@Field
	private String y;

	@Field
	private String continentName;

	@Field(collection = Set.class, maxOccurs = -1,trim = true)
	Set<String> adjacentCountries =  new HashSet<>();

	
	@Override
	public int compare(ConquestBorders o1, ConquestBorders o2) {

		return Integer.parseInt(o1.getCountryId()) - Integer.parseInt(o2.getCountryId());
	}

	public ConquestBorders(InputBorders borders, List<InputContinent> matchingInputContinentId,
			List<InputCountry> matchingInputCountryId) {
		if(borders.getCountryName()==null) {
		for(InputCountry matchCountry : matchingInputCountryId) {
			if(borders.getCountryId().equalsIgnoreCase(matchCountry.getCountryId())) {
				this.countryName=matchCountry.getCountryName();
				for(InputContinent match :  matchingInputContinentId) {
					if(matchCountry.getContinentId().equalsIgnoreCase(match.getContinentId())) {
						this.continentName=match.getContinentName();
					}	
				}
			}
		}
		} else {
			this.countryName=borders.getCountryName();
			for(InputContinent match :  matchingInputContinentId) {
				if(borders.getContinentId().equalsIgnoreCase(match.getContinentId())) {
					this.continentName=match.getContinentName();
				}	
			}
		}
		this.x="999";
		this.y="999";
		for(String matchingBorder : borders.getAdjacentCountries()) {
			for(InputCountry matchCountry : matchingInputCountryId) {
				if(matchingBorder.equalsIgnoreCase(matchCountry.getCountryId())) {
					this.adjacentCountries.add(matchCountry.getCountryName());
				}
			}
		}
	}
}
