package aatral.warzone.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.util.StringUtils;

import aatral.warzone.model.Borders;
import aatral.warzone.model.Country;
import aatral.warzone.utilities.Graph;

/**
 * <h1>Compose Borders of Countries</h1>
 * ComposeBorders reads the input file to get the border details of countries
 *
 * @author  Manimaran Palani
 * @version 1.0
 * @since   2021-02-12
 */
public class ComposeBorders {

	/**
	 * ComposeBorders constructor is used 
	 * to compose continent coordinates from the text file
	 * @param countryDataList contains country coordinates fetched from text file
	 * @param borderInputData contains border coordinates of countries fetched from text file
	 */
	
	public ComposeBorders(List<Country> countryDataList, List<Borders> borderInputData) {

		HashMap<String, Country> countryMap =  new HashMap<>();

		System.out.println("\nCOUNTRIES\n");
		for (Country country : countryDataList) {
			countryMap.put(country.getCountryId(), country);
			System.out.println("Country_Id:"+country.getCountryId()+" || "+"Country_Name: "+country.getCountryName()
			+" || "+"Continent_Id:"+country.getContinentId());
		}	

		Graph borderGraph = new Graph();

		for(Borders borders : borderInputData) {
			borderGraph.addVertex(borders.getCountryId());
			for (String adjacent : borders.getAdjacentCountries()) {
				borderGraph.addEdge(borders.getCountryId(), countryMap.get(adjacent));
			}
		}

		System.out.println("\nBORDERS\n");
		for (Borders borders : borderInputData) {
			List<String> printCountryId  =  new ArrayList<>();
			List<Country> printCountry = borderGraph.getCountryMap(borders.getCountryId());

			System.out.println(borders.getCountryId()+ " => " );
			for (Country count : printCountry) {
				printCountryId.add(count.getCountryName());
			}
			System.out.println(StringUtils.collectionToDelimitedString(printCountryId, ", "));
		}
	}
}
