package aatral.warzone.utilitiesTest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import aatral.warzone.model.Continent;
import aatral.warzone.utilities.ContinentMapReader;

public class ContinentMapReaderTest {
	@Test	
	public void continentMapReader()
	{
		int actual = 0;

		try {

			File file = new File("src/main/resources/canada-continents.txt");

			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				sc.nextLine();
				actual++;
			}
			
			sc.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
	
	ContinentMapReader cmr=new ContinentMapReader();
	List<Continent> ls = cmr.readContinentFile();
	int expected = ls.size();
	//assertThat(actual, hasSize(3));
	System.out.println(actual+""+expected);
	 assertEquals(expected,actual);
	 

}}