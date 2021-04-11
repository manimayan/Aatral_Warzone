package aatral.warzone.mapeditorTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import aatral.warzone.adapterPattern.DominationMapReader;
import aatral.warzone.mapeditor.ComposeGraphImpl;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
import aatral.warzone.utilities.MapReader;

/**
 * ComposeGraphImplTest class is used to test the graph with inputs
 * @author Vignesh
 * @since 21.03.2021
 */
public class ComposeGraphImplTest {

	@Ignore
	public HashMap<String, List<InputCountry>> getContinentMap(List<InputContinent> p_continentData) {

		HashMap<String, List<InputCountry>> l_continentMap = new HashMap<>();
		for (InputContinent continent : p_continentData) {
			l_continentMap.put(continent.getContinentId() + "_" + continent.getContinentName(), new ArrayList<InputCountry>());
		}

		MapReader l_cmr = new MapReader();
		List<InputCountry> l_countryDataList = new ArrayList<>();
		l_countryDataList = l_cmr.readCountryMap("domination","canada");

		for (String continent : l_continentMap.keySet()) {

			List<InputCountry> l_addAgainstContinent = new ArrayList<>();

			for (InputCountry country : l_countryDataList) {

				String[] continentId = continent.split("_");

				if (continentId[0].equalsIgnoreCase(country.getContinentId())) {
					l_addAgainstContinent.add(country);
				}
			}
			l_continentMap.put(continent, l_addAgainstContinent);
		}
		return l_continentMap;
	}
/**
 *  composeGraphgetContinentMap method is used to test the map of continent
 *  values and its respective
 * 
 */
	@Test
	public void composeGraphgetContinentMap() {
		ComposeGraphImpl l_cg = new ComposeGraphImpl();

		MapReader l_cmr = new MapReader();
		List<aatral.warzone.model.InputContinent> l_lc = l_cmr.readContinentFile("domination","canada");
		HashMap<String, List<InputCountry>> actual = l_cg.getContinentMap("canada");
		HashMap<String, List<InputCountry>> expected = getContinentMap(l_lc);
		assertEquals(expected.keySet(), actual.keySet());

	}
	/*@Test
	public void composeGraphgetBorderMap() {
		ComposeGraph l_cgbm = new ComposeGraph();

		List<aatral.warzone.model.Borders> l_lc = new CountryBorderReader().mapCountryBorderReader("canada");


		HashMap<String, List<Country>> actual = (HashMap<String, List<Country>>) l_cgbm.getBorderMap("canada");
		HashMap<String, List<Country>> expected = getBorderMap(l_lc);


	 * System.out.println (actual.keySet()); System.out.println (expected.keySet());
	 * if(getContinentMap(lc).equals(cg.getContinentMap(lc))) {
	 * System.out.println("Equals"); }else { System.out.println("Not Equals"); }

		assertEquals(expected.keySet(), actual.keySet());*/

}
