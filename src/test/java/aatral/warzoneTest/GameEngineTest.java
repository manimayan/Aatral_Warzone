package aatral.warzoneTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;

import aatral.warzone.GameEngine;
import aatral.warzone.GamePlayer;

public class GameEngineTest {

	@Test
	public void gameEngineTestValidInputArmiesPositive()
	{
		GameEngine l_vim = new GameEngine("canada");
		boolean actual = l_vim.validateInputArmies(2,4);
		boolean expected=true;
		assertEquals(expected,actual);
	}

	@Test
	public void gameEngineTestValidInputArmiesNegative()
	{
		GameEngine l_vimo = new GameEngine("canada");
		boolean actual = l_vimo.validateInputArmies(4,2);
		boolean expected=false;
		assertEquals(expected,actual);
	}
	@Test
	public void gameEngineTestContinentId() {


		int l_continentsize=0;
		try {

			//	int l_continentsize = 0;
			File file = new File("src/main/resources/map/canada/canada-continents.txt");

			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				sc.nextLine();
				l_continentsize++;
			}

			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}

		GameEngine l_cid=new GameEngine("canada");
		HashMap<Integer,String> l_hm = new HashMap<Integer,String>();
		l_hm.put(1,"Atlantic_Provinces");
		l_hm.put(2,"Ontario_and_Quebec");
		l_hm.put(3,"Western_Provinces-South");
		l_hm.put(4,"Western_Provinces-North");
		l_hm.put(5,"Nunavut");
		l_hm.put(6,"Northwestern_Territories");


		int l_continentid =	l_cid.getContinentID(l_hm);
		boolean actual1=false;

		if(l_continentid <= l_continentsize )
		{
			actual1=true;
		}
		boolean expected = true;
		assertEquals(expected,actual1);
		System.out.println(l_continentid+""+l_continentsize);
	}


	@Test
	public void assignReinforcements()
	{
		GamePlayer l_ar = new GamePlayer();
		l_ar.setArmies(5);
		int l_armies = l_ar.getArmies();
		l_ar.setPlayerName("user");
		l_ar.getPlayerName();

		assertNotNull(l_ar);

	}

	@Test
	public void validateCountryValue()
	{
		GameEngine l_vc = new GameEngine("canada");
		boolean actual = l_vc.validateCountryValue("");
		boolean expected = true;
		assertEquals(expected,actual);


	}
	@Test
	public void validateCountryValueNegative()
	{
		GameEngine l_vc = new GameEngine("canada");
		boolean actual = l_vc.validateCountryValue("carolina");
		boolean expected = false;
		assertEquals(expected,actual);


	}









}
