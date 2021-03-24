package aatral.warzone.gameplayTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.gameplay.GameEngine;

public class GameEngineTest {
	/*@Test
	public void totalCountriesTest()
	{
		int expected=0;
		try {

			File file = new File("src/main/resources/source/canada/canada-countries.txt");

			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				sc.nextLine();
				expected++;
			}

			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		GameEngine ge=new GameEngine();
		//Object l_expected = null;
		assertEquals(expected,ge.totalCountries());
	}*/
	@Test
	public void listOfCountriesTest()
	{
		GameEngine ge=new GameEngine();
		assertNotNull(ge.listOfCountries());

	}
	/*@Test
	public void listOfCountriesTestNegative()
	{
		GameEngine ge=new GameEngine("india");
		assertNull(ge.listOfCountries());

	}*/
	
}
