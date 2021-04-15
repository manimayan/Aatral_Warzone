package aatral.warzone.model;

import java.util.Comparator;
import java.util.List;

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
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

public class ConquestCountry implements Comparator<ConquestCountry>  {

	private String countryId;

	@Field
	private String countryName;

	@Field
	private String x;

	@Field
	private String y;

	@Field
	private String continentName;

	@Override
	public int compare(ConquestCountry o1, ConquestCountry o2) {
		return Integer.parseInt(o1.getCountryId()) - Integer.parseInt(o2.getCountryId());
	}
/**
 * ConquestCountry method is used to conquest the country
 * @param country country value
 * @param matchingInputContinentId matching input continent id value
 */
	public ConquestCountry(InputCountry country, List<InputContinent> matchingInputContinentId) {
		this.countryName=country.getCountryName();
		this.x="999";
		this.y="999";
		for(InputContinent match :  matchingInputContinentId) {
			if(country.getContinentId()!=null) {
				if(country.getContinentId().equalsIgnoreCase(match.getContinentId())) {
					this.continentName=match.getContinentName();
				}
			}
		}
	}
}
