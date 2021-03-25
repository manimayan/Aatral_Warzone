package aatral.warzone.model;

import java.util.HashSet;
import java.util.Set;

import org.beanio.annotation.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Continent {

	public Continent(InputContinent l_continent, Set<Countries> continentOwnedCountries) {

		this.ContinentId = l_continent.getContinentId();
		this.ContinentName = l_continent.getContinentName();
		this.ContinentValue = l_continent.getContinentValue();
		this.continentOwnedCountries = continentOwnedCountries;
	}

	private String ContinentId;

	private String ContinentName;

	private String ContinentValue;

	Set<Countries> continentOwnedCountries = new HashSet<>();
}
