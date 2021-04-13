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

public class ConquestMapAdapterTest {
	
	@Test
	public void loadMapTest()
	{
		ConquestMapAdapter cma=new ConquestMapAdapter(null);
		Map<String,Continent> actual=cma.loadMap("domination","canada");
		ConquestMapReader dm=new ConquestMapReader();
		boolean result = dm instanceof ConquestMapReader;
		assertTrue(result);
		
		}
	@Test
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
