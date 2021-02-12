package aatral.warzone.implementation;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import aatral.warzone.model.Continent;
import aatral.warzone.model.Country;

/**
 * <h1>Compose Continent Graph</h1>
 * ComposeContinentGraph reads the input file to get the continent Details
 *
 * @author  Manimaran Palani
 * @version 1.0
 * @since   2021-02-12
 */
public class ComposeContinentGraph {

	/**
	 * ComposeContinentGraph constructor is used 
	 * to compose continent coordinates from the text file
	 * @param continentData contains continent coordinates fetched from text file
	 * @param continentMap  composed Continent Graph
	 */
	
	public ComposeContinentGraph(Continent continentData, HashMap<String, List<Country>> continentMap) {

		continentMap.put(continentData.getContinentId()+"_"+continentData.getContinentName(), new ArrayList<Country>());
	}
}