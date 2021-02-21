package aatral.warzone.implementationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.*;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import aatral.warzone.implementation.ComposeGraph;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Country;
import aatral.warzone.utilities.ContinentMapReader;
import aatral.warzone.utilities.CountryMapreader;

public class ComposeGraphTest {

	@Ignore
	public HashMap<String, List<Country>> getContinentMap(List<Continent> p_continentData) {

		HashMap<String, List<Country>> l_continentMap = new HashMap<>();
		for (Continent continent : p_continentData) {
			l_continentMap.put(continent.getContinentId() + "_" + continent.getContinentName(), new ArrayList<Country>());
		}

		CountryMapreader l_cmr = new CountryMapreader();
		List<Country> l_countryDataList = new ArrayList<>();
		l_countryDataList = l_cmr.readCountryMap();

		for (String continent : l_continentMap.keySet()) {

			List<Country> l_addAgainstContinent = new ArrayList<>();

			for (Country country : l_countryDataList) {

				String[] continentId = continent.split("_");

				if (continentId[0].equalsIgnoreCase(country.getContinentId())) {
					l_addAgainstContinent.add(country);
				}
			}
			l_continentMap.put(continent, l_addAgainstContinent);
		}
		return l_continentMap;
	}

	@Test
	public void composeGraphtest() {
		ComposeGraph l_cg = new ComposeGraph();

		List<aatral.warzone.model.Continent> l_lc = new ContinentMapReader().readContinentFile();

		HashMap<String, List<Country>> actual = l_cg.getContinentMap(l_lc);
		HashMap<String, List<Country>> expected = getContinentMap(l_lc);

		/*
		 * System.out.println (actual.keySet()); System.out.println (expected.keySet());
		 * if(getContinentMap(lc).equals(cg.getContinentMap(lc))) {
		 * System.out.println("Equals"); }else { System.out.println("Not Equals"); }
		 */
		assertEquals(expected.keySet(), actual.keySet());

	}

}