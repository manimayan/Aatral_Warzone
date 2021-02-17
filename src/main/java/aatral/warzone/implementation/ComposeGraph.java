package aatral.warzone.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import aatral.warzone.model.Borders;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Country;
import aatral.warzone.utilities.ContinentMapReader;
import aatral.warzone.utilities.CountryBorderReader;
import aatral.warzone.utilities.CountryMapreader;
import aatral.warzone.utilities.Graph;

/**
 * <h1>Class to create Graph with inputs</h1>
 * @author  Manimaran Palani
 * @version 1.0
 * @since   2021-02-17
 */

public class ComposeGraph {

	/**
	 * getContinentMap method is used 
	 * to get a map of continent and its respective countries
	 * @param continentData contains continent coordinates fetched from text file
	 * @return constructed continent map
	 */
	public HashMap<String, List<Country>> getContinentMap() {

		//read continent data from text file
		ContinentMapReader comr =  new ContinentMapReader();
		List<Continent> continentData =  new ArrayList<>(); 
		continentData = comr.readContinentFile();
		
		HashMap<String, List<Country>> continentMap =  new HashMap<>();
		for (Continent continent : continentData) {
			continentMap.put(continent.getContinentId()+"_"+continent.getContinentName(), new ArrayList<Country>());
		}

		//read country data from text file
		CountryMapreader cmr =  new CountryMapreader();
		List<Country> countryDataList =  new ArrayList<>(); 
		countryDataList = cmr.readCountryMap();

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
		return continentMap;
	}

	/**
	 * getBorderMap method is used 
	 * to get a map of country and its respective adjacent countries
	 * @return Map of Country Id and its adjacent countries
	 */
	public Map<String, List<Country>> getBorderMap() {

		//compose borders of countries
		Graph borderGraph = new Graph();
		Map<String, List<Country>> mappedBorders =  new HashMap<>();
		HashMap<String, Country> countryMap =  new HashMap<>();

		// get country data
		CountryMapreader cmr =  new CountryMapreader();
		List<Country> countryDataList =  new ArrayList<>(); 
		countryDataList = cmr.readCountryMap();

		//get border data
		CountryBorderReader cbr =  new CountryBorderReader();
		List<Borders> borderDataList =  new ArrayList<>(); 
		borderDataList = cbr.mapCountryBorderReader();


		for (Country country : countryDataList) {
			countryMap.put(country.getCountryId(), country);
		}

		for(Borders borders : borderDataList) {
			borderGraph.addVertex(borders.getCountryId());
			for (String adjacent : borders.getAdjacentCountries()) {
				mappedBorders =	borderGraph.addEdge(borders.getCountryId(), countryMap.get(adjacent));
			}
		}
		return mappedBorders;
	}

	/**
	 * printCountries method is used 
	 * to print list countries in the console
	 * @return
	 */
	public void printCountries() {

		// get country data
		CountryMapreader cmr =  new CountryMapreader();
		List<Country> countryDataList =  new ArrayList<>(); 
		countryDataList = cmr.readCountryMap();

		//print countries in console
		System.out.println("\nCOUNTRIES\n");
		for (Country country : countryDataList) {
			System.out.println("Country_Id:"+country.getCountryId()+" || "+"Country_Name: "+country.getCountryName()
			+" || "+"Continent_Id:"+country.getContinentId());
		}
	}

	/**
	 * printBorders method is used 
	 * to print list of countries and its borders in the console
	 * @return 
	 */
	public void printBorders() {

		//get border data
		CountryBorderReader cbr =  new CountryBorderReader();
		List<Borders> borderDataList =  new ArrayList<>(); 
		borderDataList = cbr.mapCountryBorderReader();

		// getting mappedborders object
		Map<String, List<Country>> countryMap =  getBorderMap();
		
		//print borders in console
		System.out.println("\nBORDERS\n");
		for (Borders borders : borderDataList) {
			List<String> printCountryId  =  new ArrayList<>();
			List<Country> printCountry = countryMap.get(borders.getCountryId());

			System.out.println(borders.getCountryId()+ " => " );
			for (Country count : printCountry) {
				printCountryId.add(count.getCountryName());
			}
			System.out.println(StringUtils.collectionToDelimitedString(printCountryId, ", "));
		}
	}
}
