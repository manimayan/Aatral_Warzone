package aatral.warzone.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aatral.warzone.model.InputCountry;

/**
 * <h1>Utility Class to create Graph</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

public class Graph {

	public Map<String, List<InputCountry>> l_adjVertices = new HashMap<>();

	/**
	 * addVertex method is used to create a node/vertex in a graph
	 * 
	 * @param p_countryId country Id
	 * @return adjacent vertices.
	 */

	public Map<String, List<InputCountry>> addVertex(String p_countryId) {
		l_adjVertices.put(p_countryId, new ArrayList<>());
		return l_adjVertices;
	}

	/**
	 * removeVertex method is used to remove a node.vertex from a graph
	 * 
	 * @param p_countryId country Id
	 */

	public void removeVertex(String p_countryId) {
		l_adjVertices.values().stream().forEach(e -> e.remove(p_countryId));
		l_adjVertices.remove(p_countryId);
	}

	/**
	 * addEdge method is used to add an edge to a graph
	 * 
	 * @param p_sourceCountryId    source country
	 * @param p_destinationCountry destination country
	 * @return adjacent vertices.
	 */

	public Map<String, List<InputCountry>> addEdge(String p_sourceCountryId, InputCountry p_destinationCountry) {
		l_adjVertices.get(p_sourceCountryId).add(p_destinationCountry);
		return l_adjVertices;
	}

	/**
	 * removeEdge method is used to remove edges from adjacent countries in a graph
	 * 
	 * @param p_sourceCountryId    source country
	 * @param p_destinationCountryId destination country
	 */

	public void removeEdge(String p_sourceCountryId, String p_destinationCountryId) {
		List<InputCountry> eV1 = l_adjVertices.get(p_sourceCountryId);
		List<InputCountry> eV2 = l_adjVertices.get(p_destinationCountryId);
		if (eV1 != null)
			eV1.remove(p_destinationCountryId);
		if (eV2 != null)
			eV2.remove(p_sourceCountryId);
	}
}
