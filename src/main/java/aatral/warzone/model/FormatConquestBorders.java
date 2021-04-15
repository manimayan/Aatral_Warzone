package aatral.warzone.model;

import java.util.HashSet;
import java.util.Set;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * <h1>Borders POJO</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Record
public class FormatConquestBorders {


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

/**
 * FormatConquestBorders method is used to format the conquest border values
 * @param border border values
 */
	public FormatConquestBorders(ConquestBorders border) {
		this.countryName = border.getCountryName()+",";
		this.x=border.getX()+",";
		this.y=border.getY()+",";
		this.continentName = border.getContinentName()+",";
		for(String data : border.getAdjacentCountries()) {
			this.adjacentCountries.add(data+",");
		}
	}
	
}
