package aatral.warzone.utilities;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.StreamBuilder;

import aatral.warzone.model.Continent;

/**
 * <h1>MapReader Class to read Continents</h1>
 * MapReader implements beanReader methods to reads the input file.
 *
 * @author  Manimaran Palani
 * @version 1.0
 * @since   2021-02-12
 */

public class ContinentMapReader {

	private StreamBuilder continentStream = new StreamBuilder("continents")
			.format("delimited")
			.parser(new DelimitedParserBuilder(' '))
			.addRecord(Continent.class)
			.ignoreUnidentifiedRecords();

	/**
	 * readContinentFile method is used 
	 * to get continent coordinates from input file
	 * @return list of continents
	 */
	public List<Continent> readContinentFile() {

		StreamFactory factory = StreamFactory.newInstance();
		factory.define(this.continentStream);

      List<Continent> continentDataList =  new ArrayList<>();

		try {
			InputStream input = this.getClass().getResourceAsStream("/canada-continents.txt");
			BeanReader inputReader = factory.createReader("continents", new InputStreamReader(input));
			Object record = null;	

			while ((record = inputReader.read()) != null) {
				Continent continentData = (Continent) record;
				continentDataList.add(continentData);
			}

		} 
		catch (IllegalArgumentException ie) {
			System.out.println("Error in parsing input txt file. Manually check if the file format is correct");
		} catch(Exception ex) {
			System.out.println("Error reading file from input folder");
		}
		return continentDataList;
	}

}
