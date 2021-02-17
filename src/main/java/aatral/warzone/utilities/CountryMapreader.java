package aatral.warzone.utilities;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.StreamBuilder;

import aatral.warzone.model.Country;

/**
 * <h1>CountryMapreader Class to read Countries</h1>
 * MapReader implements beanReader methods to reads the input file.
 *
 * @author  Manimaran Palani
 * @version 1.0
 * @since   2021-02-12
 */

public class CountryMapreader {

	private StreamBuilder countryStream = new StreamBuilder("countries")
			.format("delimited")
			.parser(new DelimitedParserBuilder(' '))
			.addRecord(Country.class)
			.ignoreUnidentifiedRecords();

	/**
	 * readCountryMap method is used 
	 * to get country coordinates from input file
	 * @return list of country
	 */

	public List<Country> readCountryMap() {

		StreamFactory factory = StreamFactory.newInstance();
		factory.define(this.countryStream);
		List<Country> countryDataList =  new ArrayList<>(); 

		try {
			InputStream input = this.getClass().getResourceAsStream("/canada-countries.txt");
			BeanReader inputReader = factory.createReader("countries", new InputStreamReader(input));
			Object record = null;	

			while ((record = inputReader.read()) != null) {
				Country countryData = (Country) record;
				countryDataList.add(countryData);
			}
		} 
		catch (IllegalArgumentException ie) {
			System.out.println("Error in parsing input txt file. Manually check if the file format is correct");
		} catch(Exception ex) {
			System.out.println("Error reading file from input folder");
		}
		return countryDataList;
	}
}
