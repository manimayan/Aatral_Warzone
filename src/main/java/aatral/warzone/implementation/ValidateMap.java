package aatral.warzone.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import aatral.warzone.model.InputBorders;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
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
	 * 
	 * @param warZoneMap map of warzone.
	 * @param continentID continent id.
	 * 
	 * @return false.
	 */
	public boolean validateContinentID(String warZoneMap, String continentID) {
		List<InputContinent> continentList = new ContinentMapReader().readContinentFile(warZoneMap);
		for (InputContinent continentIDSearch : continentList)
			if (continentIDSearch.getContinentId().equalsIgnoreCase(continentID)) {
				return true;
			}
		return false;
	}

	/**
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * 
	 * @param warZoneMap map of warzone.
	 * @param continentName continentname.
	 * @return false.
	 */
	public boolean validateContinentName(String warZoneMap, String continentName) {
		List<InputContinent> continentList = new ContinentMapReader().readContinentFile(warZoneMap);
		for (InputContinent continentNameSearch : continentList)
			if (continentNameSearch.getContinentName().equalsIgnoreCase(continentName)) {
				return true;
			}
		return false;
	}

	/**
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * 
	 * @param warZoneMap map of warzone.
	 * @param countryId countryid value.
	 * @return true.
	 */
	public boolean validateCountryID(String warZoneMap, String countryId) {
		List<InputCountry> countryList = new CountryMapreader().readCountryMap(warZoneMap);
		for (InputCountry countryIDSearch : countryList)
			if (countryIDSearch.getCountryId().equalsIgnoreCase(countryId)) {
				return true;
			}
		return false;
	}

	/**
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * 
	 * @param warZoneMap map of warzone.
	 * 
	 * @return true.
	 */
	public boolean validateFullMap(String warZoneMap) {
		List<InputContinent> continentList = new ContinentMapReader().readContinentFile(warZoneMap);
		List<InputBorders> bordersList = new CountryBorderReader().mapCountryBorderReader(warZoneMap);
		List<InputCountry> countryList = new CountryMapreader().readCountryMap(warZoneMap);

		List<String> continentID = new ArrayList<>();
		List<String> countryID = new ArrayList<>();
		HashMap<String, Set<String>> borderHashMap = new HashMap<>();
		HashSet<String> validateID = new HashSet<>();
		for (InputContinent continentObject : continentList) {
			if(validateID.contains(continentObject.getContinentId())){
				System.out.println("Duplicate Continent ID - " + continentObject.getContinentId() + " exist in "+ warZoneMap + " continent map");
				return false;
			}else {
				validateID.add(continentObject.getContinentId());
			}
		}
		validateID.clear();
		for (InputCountry countryObject : countryList) {
			if(validateID.contains(countryObject.getCountryId())){
				System.out.println("Duplicate Country ID - " + countryObject.getCountryId() + " exist in "+ warZoneMap + " country map");
				return false;
			}else {
				validateID.add(countryObject.getCountryId());
			}
		}
		validateID.clear();
		for (InputBorders borderObject : bordersList) {
			if(validateID.contains(borderObject.getCountryId())){
				System.out.println("Duplicate Country ID - " + borderObject.getCountryId() + " exist in "+ warZoneMap + " border map");
				return false;
			}else {
				validateID.add(borderObject.getCountryId());
			}
		}
		validateID.clear();
		for (InputContinent continentObject : continentList) {
			continentID.add(continentObject.getContinentId());
		}

		for (InputCountry countryObject : countryList) {
			if (!continentID.contains(countryObject.getContinentId())) {
				System.out.println("Invalid Continent ID - " + countryObject.getContinentId() + " exist in "
						+ warZoneMap + " country map");
				return false;
			}
			countryID.add(countryObject.getCountryId());
		}

		for (InputBorders borderObject : bordersList) {
			borderHashMap.put(borderObject.getCountryId(), borderObject.getAdjacentCountries());
		}

		for (Entry<String, Set<String>> m : borderHashMap.entrySet()) {
			if (!countryID.contains(m.getKey())) {
				System.out.println("Invalid Country ID - " + m.getKey() + " exist in " + warZoneMap + " border map");
				return false;
			}
			for (String adjEdge : (Set<String>) m.getValue()) {
				if (!borderHashMap.containsKey(adjEdge)) {
					System.out.println("Country ID - " + adjEdge + " is not adjacent for the Country ID - " + m.getKey()
							+ " in " + warZoneMap + " border map");
					return false;
				} else if (!borderHashMap.get(adjEdge).contains(m.getKey())) {
					System.out.println("Country ID - " + m.getKey() + " is not adjacent for the Country ID - " + adjEdge
							+ " in " + warZoneMap + " border map");
					return false;
				}
			}
		}
		return true;
	}
}
