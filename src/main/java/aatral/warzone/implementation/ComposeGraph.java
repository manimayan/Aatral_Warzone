package aatral.warzone.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import aatral.warzone.model.InputBorders;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
import aatral.warzone.utilities.ContinentMapReader;
import aatral.warzone.utilities.CountryBorderReader;
import aatral.warzone.utilities.CountryMapreader;
import aatral.warzone.utilities.Graph;

/**
 * <h1>Class to create Graph with inputs</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-17
 */

public class ComposeGraph {
	/**
	 * getContinentMap method is used to get a map of continent and its respective
	 * countries
	 * 
	 * @param p_map contains continent coordinates fetched from text file
	 * @return constructed continent map
	 */
	public HashMap<String, List<InputCountry>> getContinentMap(String p_map) {

		// read continent data from text file
		ContinentMapReader l_comr = new ContinentMapReader();
		List<InputContinent> l_continentData = new ArrayList<>();
		l_continentData = l_comr.readContinentFile(p_map);

		HashMap<String, List<InputCountry>> l_continentMap = new HashMap<>();
		for (InputContinent continent : l_continentData) {
			l_continentMap.put(continent.getContinentId() + "_" + continent.getContinentName(),
					new ArrayList<InputCountry>());
		}

		// read country data from text file
		CountryMapreader l_cmr = new CountryMapreader();
		List<InputCountry> l_countryDataList = new ArrayList<>();
		l_countryDataList = l_cmr.readCountryMap(p_map);

		for (String l_continent : l_continentMap.keySet()) {

			List<InputCountry> l_addAgainstContinent = new ArrayList<>();

			for (InputCountry country : l_countryDataList) {

				String[] continentId = l_continent.split("_");

				if (continentId[0].equalsIgnoreCase(country.getContinentId())) {
					l_addAgainstContinent.add(country);
				}
			}
			l_continentMap.put(l_continent, l_addAgainstContinent);
		}
		return l_continentMap;
	}

	/**
	 * getBorderMap method is used to get a map of country and its respective
	 * adjacent countries
	 * 
	 * @param p_map contains continent coordinates fetched from text file
	 * 
	 * @return Map of Country Id and its adjacent countries
	 */
	public Map<String, List<InputCountry>> getBorderMap(String p_map) {

		// compose borders of countries
		Graph borderGraph = new Graph();
		Map<String, List<InputCountry>> l_mappedBorders = new HashMap<>();
		HashMap<String, InputCountry> l_countryMap = new HashMap<>();

		// get country data
		CountryMapreader l_cmr = new CountryMapreader();
		List<InputCountry> l_countryDataList = new ArrayList<>();
		l_countryDataList = l_cmr.readCountryMap(p_map);

		// get border data
		CountryBorderReader l_cbr = new CountryBorderReader();
		List<InputBorders> l_borderDataList = new ArrayList<>();
		l_borderDataList = l_cbr.mapCountryBorderReader(p_map);

		for (InputCountry l_country : l_countryDataList) {
			l_countryMap.put(l_country.getCountryId(), l_country);
		}

		for (InputBorders l_borders : l_borderDataList) {
			borderGraph.addVertex(l_borders.getCountryId());
			for (String adjacent : l_borders.getAdjacentCountries()) {
				l_mappedBorders = borderGraph.addEdge(l_borders.getCountryId(), l_countryMap.get(adjacent));
			}
		}
		return l_mappedBorders;
	}

	/**
	 * printCountries method is used to print list countries in the console
	 * 
	 * @param p_map contains continent coordinates fetched from text file
	 * 
	
	 */
	public void printCountries(String p_map) {

		// get country data
		CountryMapreader l_cmr = new CountryMapreader();
		List<InputCountry> l_countryDataList = new ArrayList<>();
		l_countryDataList = l_cmr.readCountryMap(p_map);

		// print countries in console
		System.out.println("\nCOUNTRIES\n");
		for (InputCountry l_country : l_countryDataList) {
			System.out.println("Country_Id:" + l_country.getCountryId() + " || " + "Country_Name: "
					+ l_country.getCountryName() + " || " + "Continent_Id:" + l_country.getContinentId());
		}
	}

	/**
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * 
	 * @param p_map contains continent coordinates fetched from text file
	 * 
	
	 */
	public void printBorders(String p_map) {

		// get border data
		CountryBorderReader l_cbr = new CountryBorderReader();
		List<InputBorders> l_borderDataList = new ArrayList<>();
		l_borderDataList = l_cbr.mapCountryBorderReader(p_map);

		// getting mappedborders object
		Map<String, List<InputCountry>> l_countryMap = getBorderMap(p_map);

		// print borders in console
		System.out.println("\nBORDERS\n");
		for (InputBorders borders : l_borderDataList) {
			List<String> l_printCountryId = new ArrayList<>();
			List<InputCountry> l_printCountry = l_countryMap.get(borders.getCountryId());

			System.out.println(borders.getCountryId() + " => ");
			for (InputCountry l_count : l_printCountry) {
				l_printCountryId.add(l_count.getCountryName());
			}
			System.out.println(StringUtils.collectionToDelimitedString(l_printCountryId, ", "));
		}
	}
}
