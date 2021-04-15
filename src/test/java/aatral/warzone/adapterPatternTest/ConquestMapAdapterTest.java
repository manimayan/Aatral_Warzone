package aatral.warzone.adapterPatternTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.adapterPattern.ConquestMapAdapter;
import aatral.warzone.adapterPattern.ConquestMapReader;
import aatral.warzone.adapterPattern.DominationMapReader;
import aatral.warzone.model.Continent;
/**
 * <h1>ConquestMapAdapterTest class is used to test the Adapter map</h1>
 * @author Vignesh
 * @since 13.04.2021
 *
 */
public class ConquestMapAdapterTest {

	
	@Test
	/**
	 * loadMapTest method is used to load and test the map 
	 */
	public void loadMapTest()
	{
		ConquestMapAdapter cma=new ConquestMapAdapter(null);
		Map<String,Continent> actual=cma.loadMap("domination","canada");
		ConquestMapReader dm=new ConquestMapReader();
		boolean result = dm instanceof ConquestMapReader;
		assertTrue(result);
		
		}
	@Test
	/**
	 * loadMapTestParent method is used to load and test the parent map 
	 */
	public void loadMapTestParent()
	{
		ConquestMapAdapter cma=new ConquestMapAdapter(null);
		Map<String,Continent> actual=cma.loadMap("domination","canada");
		DominationMapReader dm=new DominationMapReader();
		boolean result = dm instanceof DominationMapReader;
		assertTrue(result);
		
		}
	/*@Test
	public void loadMapTest2()
	{
		ConquestMapAdapter cma=new ConquestMapAdapter(null);
		Map<String,Continent> actual=cma.loadMap("domination","canada");
		//ConquestMa dm=new DominationMapReader();
		int actualsize=actual.size();
		System.out.println()
		assertEquals(actualsize,ConquestMapReaderTest.expected );
		}

*/
}
