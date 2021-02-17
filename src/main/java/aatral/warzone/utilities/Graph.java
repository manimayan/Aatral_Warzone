package aatral.warzone.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aatral.warzone.model.Country;

/**
 * <h1>Utility Class to create Graph</h1>
 * @author  Manimaran Palani
 * @version 1.0
 * @since   2021-02-12
 */

public class Graph {

	public Map<String, List<Country>> adjVertices =  new HashMap<>();

	/**
	 * addVertex method is used 
	 * to create a node/vertex in a graph
	 * @param countryId country Id
	 * @return 
	 */

	public Map<String, List<Country>> addVertex(String countryId) {
		adjVertices.put(countryId, new ArrayList<>());
		return adjVertices;
	}

	/**
	 * removeVertex method is used 
	 * to remove a node.vertex from a graph
	 * @param countryId country Id
	 */

	public void removeVertex(String countryId) {
		adjVertices.values().stream().forEach(e -> e.remove(countryId));
		adjVertices.remove(countryId);
	}

	/**
	 * addEdge method is used 
	 * to add an edge to a graph
	 * @param sourceCountryId source country
	 * @param destinationCountry  destination country
	 * @return 
	 */

	public Map<String, List<Country>> addEdge(String sourceCountryId, Country destinationCountry) { 
		adjVertices.get(sourceCountryId).add(destinationCountry);
		return adjVertices;
	}

	/**
	 * removeEdge method is used 
	 * to remove edges from adjacent countries in a graph
	 * @param sourceCountryId source country
	 * @param destinationCountry  destination country
	 */

	public void removeEdge(String sourceCountryId, String destinationCountryId) { 
		List<Country> eV1 = adjVertices.get(sourceCountryId); 
		List<Country> eV2 = adjVertices.get(destinationCountryId); 
		if (eV1 != null)
			eV1.remove(destinationCountryId); 
		if (eV2 != null) 
			eV2.remove(sourceCountryId);
	}
}
