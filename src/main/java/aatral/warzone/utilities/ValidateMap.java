package aatral.warzone.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import aatral.warzone.model.Borders;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Country;

public class ValidateMap {
	
	public boolean validateContinentID(int continentID) {
		List<Continent> continentList = new ContinentMapReader().readContinentFile();
		for(Continent continentIDSearch:continentList)
			if(continentIDSearch.getContinentId().equalsIgnoreCase(continentID+""))
				return false;
		return true;
	}
	public boolean validateContinentName(String continentName) {
		List<Continent> continentList = new ContinentMapReader().readContinentFile();
		for(Continent continentNameSearch:continentList)
			if(continentNameSearch.getContinentName().equalsIgnoreCase(continentName))
				return false;
		return true;
	}
	public boolean validateCountryID(int countryID) {
		List<Country> countryList = new CountryMapreader().readCountryMap();
		for(Country countryIDSearch:countryList)
			if(countryIDSearch.getCountryId().equalsIgnoreCase(countryID+"")) {
				return false;
			}
		return true;
	}
	
	public boolean validateFullMap() {
		List<Continent> continentList = new ContinentMapReader().readContinentFile();
		List<Borders> bordersList = new CountryBorderReader().mapCountryBorderReader();
		List<Country> countryList = new CountryMapreader().readCountryMap();
		
		List<String> continentID = new ArrayList<>();
		List<String> countryID = new ArrayList<>();
		HashMap<String, List<String>> borderHashMap = new HashMap<>();
		
		for(Continent continentObject:continentList) {
			continentID.add(continentObject.getContinentId());
		}
		
		for(Country countryObject:countryList) {
			if(!continentID.contains(countryObject.getContinentId())) {
				return false;
			}
			countryID.add(countryObject.getCountryId());
		}
		
		for(Borders borderObject:bordersList) {
			borderHashMap.put(borderObject.getCountryId(), borderObject.getAdjacentCountries());
		}
		
		for(Entry<String, List<String>> m : borderHashMap.entrySet()) {
			if(!countryID.contains(m.getKey())) {
				return false;
			}
			for(String adjEdge:(List<String>)m.getValue()) {
				if(!borderHashMap.containsKey(adjEdge) || !borderHashMap.get(adjEdge).contains(m.getKey())) {
					return false;
				}
			}
		}
		System.out.println(continentID);
		return true;
	}
}
