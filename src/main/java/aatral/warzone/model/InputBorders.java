package aatral.warzone.model;

import java.util.Comparator;
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

public class InputBorders implements Comparator<InputBorders>{

	public InputBorders(Countries countryEntry) {
		this.countryId = countryEntry.getCountryId();
		this.adjacentCountries = countryEntry.getCountryOwnedBorders();
	}

	@Field
	private String countryId;

	@Field(collection = Set.class, maxOccurs = -1,trim = true)
	 Set<String> adjacentCountries;

	@Override
	public int compare(InputBorders o1, InputBorders o2) {

			return Integer.parseInt(o1.getCountryId()) - Integer.parseInt(o2.getCountryId());
		}
	}
