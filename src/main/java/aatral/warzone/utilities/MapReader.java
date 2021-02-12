package aatral.warzone.utilities;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.StreamBuilder;

import aatral.warzone.implementation.ComposeContinentGraph;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Country;

/**
 * <h1>MapReader Class to read Continents</h1>
 * MapReader implements beanReader methods to reads the input file.
 *
 * @author  Manimaran Palani
 * @version 1.0
 * @since   2021-02-12
 */

public class MapReader {

	private StreamBuilder continentStream = new StreamBuilder("continents")
			.format("delimited")
			.parser(new DelimitedParserBuilder(' '))
			.addRecord(Continent.class)
			.ignoreUnidentifiedRecords();

	/**
	 * readContinentFile method is used 
	 * to get continent coordinates from input file
	 */
	public void readContinentFile() {

		StreamFactory factory = StreamFactory.newInstance();
		factory.define(this.continentStream);

		try {
			InputStream input = this.getClass().getResourceAsStream("/canada-continents.txt");
			BeanReader inputReader = factory.createReader("continents", new InputStreamReader(input));
			Object record = null;	
			HashMap<String, List<Country>> continentMap =  new HashMap<>();
			while ((record = inputReader.read()) != null) {
				Continent continentData = (Continent) record;
				new ComposeContinentGraph(continentData, continentMap);
			}
			new CountryMapreader(continentMap);
		} 
		catch (IllegalArgumentException ie) {
			System.out.println("Error in parsing input txt file. Manually check if the file format is correct");
		} catch(Exception ex) {
			System.out.println("Error reading file from input folder");
		}
	}
}
