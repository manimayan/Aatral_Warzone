package aatral.warzone.utilitiesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.model.Borders;
import aatral.warzone.model.Country;
import aatral.warzone.utilities.CountryBorderReader;
import aatral.warzone.utilities.CountryMapreader;

public class CountryBorderReaderTest {
	@Test
	public void countryBorderReader()
	{

		int actual = 0;

		try {

			File file = new File("src/main/resources/canada-borders.txt");

			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				sc.nextLine();
				actual++;
			}

			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}

		CountryBorderReader cbr=new CountryBorderReader();
		List<Borders> ls = cbr.mapCountryBorderReader();
		int expected = ls.size();
		//assertThat(actual, hasSize(3));
		System.out.println(actual+""+expected);
		assertEquals(expected,actual);
	}

}
