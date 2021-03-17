package aatral.warzone.utilitiesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.model.InputContinent;
import aatral.warzone.utilities.ContinentMapReader;

public class ContinentMapReaderTest {
	@Test
	public void continentMapReader() {
		int l_expected = 0;

		try {

			File file = new File("src/main/resources/map/canada/canada-continents.txt");

			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				sc.nextLine();
				l_expected++;
			}

			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}

		ContinentMapReader l_cmr = new ContinentMapReader();
		List<InputContinent> ls = l_cmr.readContinentFile("canada");
		int l_actual = ls.size();
		// assertThat(actual, hasSize(3));
		System.out.println(l_expected + "" + l_actual);
		assertEquals(l_expected, l_actual);

	}
	@Test
	public void continentMapReader1() {
		int l_expected = 0;

		try {

			File file = new File("src/main/resources/map/india/testfile.txt");

			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				sc.nextLine();
				l_expected++;
			}

			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}

		ContinentMapReader l_cmr = new ContinentMapReader();
		List<InputContinent> ls = l_cmr.readContinentFile("india");
		int l_actual = ls.size();
		// assertThat(actual, hasSize(3));
		System.out.println(l_expected + "" + l_actual);
		assertEquals(l_expected, l_actual);
	}
}
