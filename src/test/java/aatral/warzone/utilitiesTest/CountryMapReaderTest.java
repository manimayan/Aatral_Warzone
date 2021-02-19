package aatral.warzone.utilitiesTest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.model.Continent;
import aatral.warzone.model.Country;
import aatral.warzone.utilities.ContinentMapReader;
import aatral.warzone.utilities.CountryMapreader;

public class CountryMapReaderTest {
	@Test
	public void countryMapReader()
	{

		int actual = 0;

		try {

			File file = new File("src/main/resources/canada-countries.txt");

			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				sc.nextLine();
				actual++;
			}

			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}

		CountryMapreader comr=new CountryMapreader();
		List<Country> ls = comr.readCountryMap();
		int expected = ls.size();
		//assertThat(actual, hasSize(3));
		System.out.println(actual+""+expected);
		assertEquals(expected,actual);
	}

}
