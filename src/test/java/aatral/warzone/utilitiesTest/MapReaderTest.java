package aatral.warzone.utilitiesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.adapterPattern.DominationMapReader;
import aatral.warzone.model.InputBorders;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
import aatral.warzone.utilities.MapReader;

/**
 * ContinentMapReaderTest class is used to test the methods to read the input
 * file
 * 
 * @author vignesh senthilkumar
 * @since 21.03.2021
 * 
 */
public class MapReaderTest {

	/**
	 * continentMapReader method is used to test the positive case of continent map
	 * reader value
	 */
	@Test
	public void continentMapReader() {
		int l_expected = 0;

		try {

			File file = new File("src/main/resources/source/canada/canada-continents.txt");
			/// Aatral-Warzone/src/main/resources/source/canada/canada-continents.txt
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				sc.nextLine();
				l_expected++;
			}

			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}

		MapReader mapReader = new MapReader();
		List<InputContinent> ls = mapReader.readContinentFile("domination", "canada");
		int l_actual = ls.size();
		// assertThat(actual, hasSize(3));
		assertEquals(l_expected, l_actual);

	}

	/**
	 * continentMapReader1 method is used to test the negative case of continent map
	 * reader value
	 */
	@Test
	public void continentMapReader1() {
		int l_expected = 0;
		try {
			File file = new File("src/main/resources/source/india/testfile.txt");
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				sc.nextLine();
				l_expected++;
			}
			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}

		MapReader mapReader = new MapReader();
		List<InputContinent> ls = mapReader.readContinentFile("domination", "canada");
		int l_actual = ls.size();
		assertNotNull(l_actual);
	}

	/**
	 * countryBorderReader method is used to test the country border value
	 */

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

		MapReader mapReader = new MapReader();
		List<InputBorders> ls = mapReader.mapCountryBorderReader("domination", "canada");
		int l_actual = ls.size();
		// assertThat(actual, hasSize(3));
		assertEquals(l_expected, l_actual);
	}

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

		MapReader mapReader = new MapReader();
		List<InputCountry> ls = mapReader.readCountryMap("domination", "canada");
		int l_actual = ls.size();
		// assertThat(actual, hasSize(3));
		System.out.println(l_expected + "" + l_actual);
		assertEquals(l_expected, l_actual);
	}
}
