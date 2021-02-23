package aatral.warzone.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import aatral.warzone.model.Borders;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Country;
import aatral.warzone.utilities.ContinentMapReader;
import aatral.warzone.utilities.CountryBorderReader;
import aatral.warzone.utilities.CountryMapreader;

/**
 * <h1>ValidateMap is used to validate the selected map</h1>
 * 
 * @author William Moses
 * @version 1.0
 * @since 2021-02-23
 */
public class ValidateMap {

	/**
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * @param map 
	 * 
	 * @return
	 */
	public boolean validateContinentID(String warZoneMap, String continentID) {
		List<Continent> continentList = new ContinentMapReader().readContinentFile(warZoneMap);
		for (Continent continentIDSearch : continentList)
			if (continentIDSearch.getContinentId().equalsIgnoreCase(continentID + "")) {
				return true;
			}
		return false;
	}

	/**
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * @param map 
	 * 
	 * @return
	 */
	public boolean validateContinentName(String warZoneMap, String continentName) {
		List<Continent> continentList = new ContinentMapReader().readContinentFile(warZoneMap);
		for (Continent continentNameSearch : continentList)
			if (continentNameSearch.getContinentName().equalsIgnoreCase(continentName)) {
				return true;
			}
		return false;
	}

	/**
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * @param map 
	 * 
	 * @return
	 */
	public boolean validateCountryID(String warZoneMap, String countryId) {
		List<Country> countryList = new CountryMapreader().readCountryMap(warZoneMap);
		for (Country countryIDSearch : countryList)
			if (countryIDSearch.getCountryId().equalsIgnoreCase(countryId + "")) {
				return true;
			}
		return false;
	}

	/**
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * @param map 
	 * 
	 * @return
	 */
	public boolean validateFullMap(String warZoneMap) {
		List<Continent> continentList = new ContinentMapReader().readContinentFile(warZoneMap);
		List<Borders> bordersList = new CountryBorderReader().mapCountryBorderReader(warZoneMap);
		List<Country> countryList = new CountryMapreader().readCountryMap(warZoneMap);

		List<String> continentID = new ArrayList<>();
		List<String> countryID = new ArrayList<>();
		HashMap<String, List<String>> borderHashMap = new HashMap<>();

		for (Continent continentObject : continentList) {
			continentID.add(continentObject.getContinentId());
		}

		for (Country countryObject : countryList) {
			if (!continentID.contains(countryObject.getContinentId())) {
				return false;
			}
			countryID.add(countryObject.getCountryId());
		}

		for (Borders borderObject : bordersList) {
			borderHashMap.put(borderObject.getCountryId(), borderObject.getAdjacentCountries());
		}

		for (Entry<String, List<String>> m : borderHashMap.entrySet()) {
			if (!countryID.contains(m.getKey())) {
				return false;
			}
			for (String adjEdge : (List<String>) m.getValue()) {
				if (!borderHashMap.containsKey(adjEdge) || !borderHashMap.get(adjEdge).contains(m.getKey())) {
					return false;
				}
			}
		}
		System.out.println(continentID);
		return true;
	}
}
