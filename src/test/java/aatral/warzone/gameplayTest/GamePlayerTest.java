package aatral.warzone.gameplayTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import aatral.warzone.gameplay.GamePlayer;

public class GamePlayerTest {
	@Before
	public void constructor()
	{
		try {

			File file = new File("src/main/resources/source/canada/canada-countries.txt");
			//List<>
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				sc.nextLine();

			}

			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	@Test
	public void validateDeployInputTest()
	{
		GamePlayer gp = new GamePlayer();
		String actual = gp.validateDeployInput("deploy 24 4, 23 3");
		//System.out.println(" 24 4, 23 3");
		String expected = " 24 4, 23 3";
		assertTrue(actual.equals(expected));
	}
	public void validateDeployInputTestNegative()
	{
		GamePlayer gp = new GamePlayer();
		String actual = gp.validateDeployInput("deploy 24 4, 23 3");
		//System.out.println(" 24 4, 23 3");
		String expected = " 24 4, 23 3";
		assertTrue(actual.equals(expected));
	}
	@Test
	public void calculateInputArmiesTest()
	{
		GamePlayer gp = new GamePlayer();
		int actual = gp.calculateInputArmies("deploy 23");
		int expected=23;
		assertEquals(expected,actual);
	}

	@Test
	public void validateInputArmies()
	{
		GamePlayer gp = new GamePlayer();
		boolean actual = gp.validateInputArmies(21,24);
		boolean expected = true;
		assertEquals(expected,actual);
	}
	@Test
	public void validateInputArmiesNegative()
	{
		GamePlayer gp = new GamePlayer();
		boolean actual = gp.validateInputArmies(24,21);
		boolean expected = false;
		assertEquals(expected,actual);
	}
	@Test
	public void validateCountryValueTest()
	{
		GamePlayer gp = new GamePlayer();
	boolean actual =	gp.validateCountryValue("india");
		assertEquals(false,actual);
	}
	@Test
	public void validateCountryValueTestNegative()
	{
		GamePlayer gp = new GamePlayer();
	boolean actual =	gp.validateCountryValue("");
		assertEquals(true,actual);
	}
	/*@Test
	public void validateCountryInputTest()
	{
		GamePlayer gp = new GamePlayer();
		GamePlayer gp1 = new GamePlayer();
		String actual=gp.validateCountryInput(" 23 4",gp1);
		
	assertTrue(actual.length()!=0);
		
	}
*/
}

