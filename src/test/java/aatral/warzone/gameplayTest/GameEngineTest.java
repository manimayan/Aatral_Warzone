package aatral.warzone.gameplayTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.gameplay.GameEngine;


/**
 * GameEngineTest class is the kernel of warzone game and it is used to test the
 * warzone game
 * @author Vignesh
 * @since 22.03.2021
 *
 */
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

	
	/**
	 * listOfCountriesTest method is used to test the list of countries under the continent
	 */
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
