package aatral.warzone.adapterPatternTest;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.adapterPattern.DominationMapReader;
import aatral.warzone.model.Continent;

public class DominationMapReaderTest {
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
	@Test
	public void loadMapTestDominationNegative()
	{
int expected=0;
		
		DominationMapReader dmr=new DominationMapReader();
		Map<String,Continent> actual=dmr.loadMap("domination","china");
		int size=actual.size();
		assertEquals(size,expected);
	}
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
