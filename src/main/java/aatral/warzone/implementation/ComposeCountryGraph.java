package aatral.warzone.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aatral.warzone.model.Country;
import aatral.warzone.utilities.CountryBorderReader;

/**
 * <h1>Compose Country Graph</h1>
 * ComposeCountryGraph reads the input file to get the country Details
 *
 * @author  Manimaran Palani
 * @version 1.0
 * @since   2021-02-12
 */
public class ComposeCountryGraph {
	
	/**
	 * ComposeCountryGraph constructor is used 
	 * to compose Country coordinates from the text file
	 * @param countryDataList contains country coordinates fetched from text file
	 * @param continentMap  composed Continent Graph to map a country to continent
	 */

	public ComposeCountryGraph(List<Country> countryDataList, HashMap<String, List<Country>> continentMap) {

		for(String continent : continentMap.keySet()) {

			List<Country> addAgainstContinent =  new ArrayList<>();

			for (Country country : countryDataList) {

				String[] continentId = continent.split("_");

				if(continentId[0].equalsIgnoreCase(country.getContinentId())){
					addAgainstContinent.add(country);
				}
			}
			continentMap.put(continent, addAgainstContinent);
		}
		
		new CountryBorderReader(countryDataList);
	}
}
