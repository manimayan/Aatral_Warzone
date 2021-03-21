package aatral.warzone.utilitiesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.model.InputBorders;
import aatral.warzone.model.InputCountry;
import aatral.warzone.utilities.CountryBorderReader;
import aatral.warzone.utilities.CountryMapreader;

public class CountryBorderReaderTest {
	@Test
	public void countryBorderReader() {

		int l_expected = 0;

		try {

			File file = new File("src/main/resources/source/canada/canada-borders1.txt");

			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				sc.nextLine();
				l_expected++;
			}

			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}

		CountryBorderReader l_cbr = new CountryBorderReader();
		List<InputBorders> ls = l_cbr.mapCountryBorderReader("canada");
		int l_actual = ls.size();
		// assertThat(actual, hasSize(3));
		System.out.println(l_expected + "" + l_actual);
		assertEquals(l_expected, l_actual);
	}

}
