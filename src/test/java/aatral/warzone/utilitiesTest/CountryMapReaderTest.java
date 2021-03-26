package aatral.warzone.utilitiesTest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
import aatral.warzone.utilities.ContinentMapReader;
import aatral.warzone.utilities.CountryMapreader;


/**
 * CountryMapReaderTest class is used to test the methods to 
 * read the input file
 * @author vignesh senthilkumar
 * @since 21.03.2021
 * 
 */

public class CountryMapReaderTest 
{

	/**
	 * countryMapReader method is used to test the country map value
	 */	
	
	@Test
	public void countryMapReader() {

		int l_expected = 0;

		try {

			File file = new File("src/main/resources/source/canada/canada-countries.txt");

			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				sc.nextLine();
				l_expected++;
			}

			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}

		CountryMapreader l_comr = new CountryMapreader();
		List<InputCountry> ls = l_comr.readCountryMap("canada");
		int l_actual = ls.size();
		// assertThat(actual, hasSize(3));
		System.out.println(l_expected + "" + l_actual);
		assertEquals(l_expected, l_actual);
	}

}
