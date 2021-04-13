package aatral.warzone.adapterPatternTest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.adapterPattern.DominationMapReader;
import aatral.warzone.model.Continent;

public class ConquestMapReaderTest {
	public static int expected=0;
	@Test
	public void loadMapTestConquest()
	{
//int expected=0;
		try {

			File file = new File("src/main/resources/conquest/africa/africa-continents.txt");
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
		Map<String,Continent> actual=dmr.loadMap("conquest","africa");
		int size=actual.size();
		System.out.println(expected);
		assertEquals(size,expected);
	}
	@Test
	public void loadMapTestConquestNegative()
	{
int expected1=0;
		
		DominationMapReader dmr=new DominationMapReader();
		Map<String,Continent> actual=dmr.loadMap("conquest","china");
		int size=actual.size();
		assertEquals(size,expected1);
	}
	@Test
	public void loadMapTestConquestNegativeMap()
	{
int expected2=0;
		
		DominationMapReader dmr=new DominationMapReader();
		Map<String,Continent> actual=dmr.loadMap("domination","africa");
		int size=actual.size();
		assertEquals(size,expected2);
	}
	
}
