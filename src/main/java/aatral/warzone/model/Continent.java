package aatral.warzone.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


/**
 * <h1>Continent</h1> The Class has continent values of dependency map
 *
 * @author Vikram
 * @version 1.0
 * @since 24-02-2021
 */
public class Continent {

	public Continent(InputContinent l_continent, List<Countries> continentOwnedCountries) {

		this.ContinentId = l_continent.getContinentId();
		this.ContinentName = l_continent.getContinentName();
		this.ContinentValue = l_continent.getContinentValue();
		this.continentOwnedCountries = continentOwnedCountries;
	}

	private String ContinentId;

	private String ContinentName;

	private String ContinentValue;

	List<Countries> continentOwnedCountries = new ArrayList<>();
}
