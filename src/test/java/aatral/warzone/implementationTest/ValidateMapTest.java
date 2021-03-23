package aatral.warzone.implementationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import aatral.warzone.mapeditor.ValidateMapImpl;

/**
 * Test class name: <b><u>ValidateMapTest</u></b>
 * <b>The ValidateMapTest class is used to check the validity of the continents, countries and whole graph.</b>
 * 
 * It used to test the validity of the continent id and continent name.
 * 
 * @author vignesh senthilkumar

 *
 */
public class ValidateMapTest {
	/**
	 * The test method used to test the positive test case.
	 * It is hardcore test method.
	 * Used to test the continent id.
	 * 
	 */
	@Test
	public void validateContinentIDTestHardcore()
	{
		boolean expected=true;

		ValidateMapImpl l_vci=new ValidateMapImpl();
		boolean actual = l_vci.validateContinentID("canada","1");
		assertEquals(expected,actual);

	}
	/**
	 * The test method used to test the negative test case.
	 * Used to test the Continent id.
	 */
	@Test
	public void validateContinentIDTestNegative()
	{
		boolean expected=true;

		ValidateMapImpl l_vci=new ValidateMapImpl();
		boolean actual = l_vci.validateContinentID("canada","88");

		assertNotEquals(expected,actual);
	}
	/**
	 * The test method used to test the positive test case.
	 * Used to test the continent id.
	 */
	@Test
	public void validateContinentIDTestPositive()
	{ 
		boolean expected = false;
		String l_cid="1";
		File file = new File("src/main/resources/source/canada/canada-continents.txt");
		List<String> l_continentid = new  ArrayList<String>();
		//List<String> l_continentname = new  ArrayList<String>();

		List<String> list = new  ArrayList<String>();
		try { 
			list = Files.readAllLines(file.toPath(),Charset.defaultCharset());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if(list.isEmpty())
			return;

		for(String line : list){
			String [] res = line.split(" ");

			l_continentid.add(res[0]);
			//l_continentname.add(res[1]);

			for(String i : l_continentid )
			{
				if(i.equals(l_cid))
				{
					expected = true;
				}

			}


		}
		/*System.out.println(l_continentid);

		System.out.println(l_continentname);*/




		ValidateMapImpl l_vci=new ValidateMapImpl();
		boolean actual = l_vci.validateContinentID("canada",l_cid);
		assertEquals(expected,actual);

	}

	/**
	 * The test method used to test the continent name.
	 */
	@Test
	public void validateContinentNameTest()
	{
		boolean expected=false;

		String l_cid="Atlantic_Provinces";
		File file = new File("src/main/resources/source/canada/canada-continents.txt");
		//List<String> l_continentid = new  ArrayList<String>();
		List<String> l_continentname = new  ArrayList<String>();

		List<String> list = new  ArrayList<String>();
		try { 
			list = Files.readAllLines(file.toPath(),Charset.defaultCharset());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if(list.isEmpty())
			return;

		for(String line : list){
			String [] res = line.split(" ");

			//l_continentid.add(res[0]);
			l_continentname.add(res[1]);

			for(String i : l_continentname )
			{
				if(i.equals(l_cid))
				{
					expected = true;
				}

			}


		}

		ValidateMapImpl l_vcn = new ValidateMapImpl();
		boolean actual = l_vcn.validateContinentName("canada",l_cid);
		assertEquals(expected,actual);


	}
	/**
	 * The test method used to test the country id.
	 */
	@Test
	public void validateCountryIDTest()
	{
		boolean expected=false;

		String l_coid="29";
		File file = new File("src/main/resources/source/canada/canada-countries.txt");
		List<String> l_countryid = new  ArrayList<String>();

		List<String> list = new  ArrayList<String>();
		try { 
			list = Files.readAllLines(file.toPath(),Charset.defaultCharset());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if(list.isEmpty())
			return;

		for(String line : list){
			String [] res = line.split(" ");

			l_countryid.add(res[0]);


			for(String i : l_countryid )
			{
				if(i.equals(l_coid))
				{
					expected = true;
				}

			}


		}

		ValidateMapImpl l_vcoi=new ValidateMapImpl();
		boolean actual = l_vcoi.validateCountryID("canada",l_coid);
		assertEquals(expected,actual);

	}
	/**
	 * The test method used to test the  entire map.
	 */
	@Test
	public void validateFullMapTest()
	{
		boolean expected =true;
		ValidateMapImpl l_vfm=new ValidateMapImpl();
		boolean actual = l_vfm.validateFullMap("canada");
		assertEquals(expected,actual);

	}


	/*@Test
	public void validateFullMapTest1()
	{
		boolean expected =true;
		ValidateMap l_vfm1=new ValidateMap();
		boolean actual = l_vfm1.validateFullMap("australia");
		assertEquals(expected,actual);

		}*/





}
