package aatral.warzone.adapterPatternTest;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.adapterPattern.DominationMapReader;
import aatral.warzone.model.Continent;

/**
 * <h1>DominationMapReaderTest class is used to test the domination map</h1>
 * @author Vignesh
 * @since 13.04.2021
 *
 */
public class DominationMapReaderTest 
{
	
	/**
	 * loadMapTestDomination method is used to test the domination map
	 */
	@Test
	public void loadMapTestDomination()
	{
int expected=0;
		try {

			File file = new File("src/main/resources/domination/canada/canada-continents.txt");
			/// Aatral-Warzone/src/main/resources/source/canada/canada-continents.txt
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				sc.nextLine();
				expected++;
			}

			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		DominationMapReader dmr=new DominationMapReader();
		Map<String,Continent> actual=dmr.loadMap("domination","canada");
		int size=actual.size();
		assertEquals(size,expected);
	}
	
	/**
	 * loadMapTestDominationNegative method is used to load and test the domination map 
	 */
	@Test
	public void loadMapTestDominationNegative()
	{
int expected=0;
		
		DominationMapReader dmr=new DominationMapReader();
		Map<String,Continent> actual=dmr.loadMap("domination","china");
		int size=actual.size();
		assertEquals(size,expected);
	}
	/**
	 * loadMapTestDominationNegative method is used to load and test the domination map value 
	 */
	@Test
	public void loadMapTestDominationNegativeMap()
	{
int expected=0;
		
		DominationMapReader dmr=new DominationMapReader();
		Map<String,Continent> actual=dmr.loadMap("conquest","canada");
		int size=actual.size();
		assertEquals(size,expected);
	}
	
	

}
