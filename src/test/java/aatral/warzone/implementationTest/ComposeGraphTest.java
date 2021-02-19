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
	public HashMap<String, List<Country>> getContinentMap(List<Continent> continentData) {

		HashMap<String, List<Country>> continentMap =  new HashMap<>();
		for (Continent continent : continentData) {
			continentMap.put(continent.getContinentId()+"_"+continent.getContinentName(), new ArrayList<Country>());
		}

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
	
	@Test
	public void composeGraphtest()
	{
		ComposeGraph cg=new ComposeGraph();

		List<aatral.warzone.model.Continent> lc = new ContinentMapReader().readContinentFile();
		
		HashMap<String,List<Country>> actual = cg.getContinentMap(lc);	
		HashMap<String,List<Country>> expected = getContinentMap(lc);
		
	/*	System.out.println (actual.keySet());
		System.out.println (expected.keySet());
		if(getContinentMap(lc).equals(cg.getContinentMap(lc))) {
			System.out.println("Equals");
		}else {
			System.out.println("Not Equals");
		}*/
		assertEquals(expected.keySet(),actual.keySet());

	}
	

}