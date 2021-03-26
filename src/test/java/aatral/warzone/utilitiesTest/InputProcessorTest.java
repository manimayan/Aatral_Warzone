package aatral.warzone.utilitiesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import aatral.warzone.model.Countries;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
import aatral.warzone.utilities.InputProcessor;

/**
 * InputProcessorTest class is used to test the process command line inputs
 * @author vignesh senthilkumar
 * @since 24.03.2021
 *
 */
public class InputProcessorTest {

	
	/**
	 * getAddContinentInputTest method is used to test the continent given by user's
	 * input into continent list to add it in the list
	 */
	@Test
	public void getAddContinentInputTest() 
	{
		//boolean actual = false;
		boolean expected = true;
		String arr[];
		InputProcessor ip= new InputProcessor();
		InputContinent actualobj =	ip.getAddContinentInput("add europe 12");
		InputContinent expectedobj=new InputContinent();
		/*expectedobj.ContinentId = "europe";
	expectedobj.ContinentValue = "12";*/

		/*if((actualobj.getContinentId()=="europe")&&(actualobj.getContinentValue()=="12"))

	{
		boolean actual = true;
	}*/
		System.out.println("helo");
		System.out.println("hii"+actualobj.getContinentValue());
		assertEquals("12",actualobj.getContinentValue());



	}
	
	
	/**
	 * getAddContinentInputTest method is used to test the negative continent given by user's
	 * input into continent list to add it in the list
	 */
	@Test
	public void getAddContinentInputInstanceTest() 
	{
		boolean expected = true;
		boolean actual = false;
		InputProcessor ip= new InputProcessor();
		InputContinent actualobj =	ip.getAddContinentInput("add europe 12");
		if(actualobj instanceof InputContinent)
		{
			actual = true;	
		}
		assertEquals(expected,actual);
	}

	/**
	 * getAddCountryInputTest method is used to test the country given by user's
	 * input into country list to add it in the list
	 */
	@Test
	public void getAddCountryInputTest()
	{
		InputProcessor ip= new InputProcessor();
		Countries actual=ip.getAddCountryInput("add 4 32");
		System.out.println(actual.getContinentId());
		assertEquals("32",actual.getContinentId());
		assertEquals("4",actual.getCountryId());
	}
	
	
	/**
	 * getAddCountryInputTest method is used to test the negative country given by user's
	 * input into country list to add it in the list
	 */
	@Test
	public void getAddCountryInputInstanceTest() 
	{
		boolean expected = true;
		boolean actual = false;
		InputProcessor ip= new InputProcessor();
		Countries actualobj =	ip.getAddCountryInput("add 5 14");
		if(actualobj instanceof Countries)
		{
			actual = true;	
		}
		assertEquals(expected,actual);
	}

/**
 * getStringTest method is used to test the input string value matches or not
 */
	@Test
	public void getStringTest()
	{
		InputProcessor ip= new InputProcessor();

		String actualobj=	ip.getString("edit america 11");
		//String expected="america 11";
		//boolean actual=false;
		System.out.println(actualobj);


		//System.out.println(expected);

		assertTrue(actualobj.equals("america 11 "));
		//assertEquals("america 11",actualobj);

	}
	
	

/**
 * getStringTestNull method is used to test the input string value is null or not
 */
	@Test
	public void getStringTestNull()
	{
		InputProcessor ip= new InputProcessor();

		String actual =	ip.getString("edit ");

		System.out.println(actual);
		assertNotNull(actual);

	}

	/*@Test
	public void getstartupPhaseTest()
	{
		InputProcessor ip= new InputProcessor();
		List<String> expected = new ArrayList<>();
		expected.add("canada");
		List<String> actual=ip.getstartupPhase();
		System.out.println(expected+""+actual);
		assertEquals(expected,actual);

	}*/
	
	
	/**
	 * getstartupPhaseNegativeTest method is used to test and identify the correct 
	 * map which is loaded in the application
	 * 
	 */
	@Test
	public void getstartupPhaseNegativeTest()
	{
		InputProcessor ip= new InputProcessor();
		List<String> expected = new ArrayList<>();
		expected.add("india");
		List<String> actual=ip.getstartupPhase();
		System.out.println(expected+""+actual);
		assertNotEquals(expected,actual);

	}
	

}
