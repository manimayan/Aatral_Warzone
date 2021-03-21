package aatral.warzone.utilities;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.StreamBuilder;

import aatral.warzone.model.InputCountry;

/**
 * <h1>CountryMapreader Class to read Countries</h1> MapReader implements
 * beanReader methods to reads the input file.
 *
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

public class CountryMapreader {

	private StreamBuilder countryStream = new StreamBuilder("countries").format("delimited")
			.parser(new DelimitedParserBuilder(' ')).addRecord(InputCountry.class).ignoreUnidentifiedRecords();

	/**
	 * readCountryMap method is used to get country coordinates from input file
	 * 
	 * @param p_map String format map.
	 * 
	 * @return list of country
	 */

	public List<InputCountry> readCountryMap(String p_map) {

		StreamFactory l_factory = StreamFactory.newInstance();
		l_factory.define(this.countryStream);
		List<InputCountry> l_countryDataList = new ArrayList<>();
		String url = "/source/" + p_map + "/" + p_map + "-countries.txt";
		try {
			InputStream l_input = this.getClass().getResourceAsStream(url);
			BeanReader l_inputReader = l_factory.createReader("countries", new InputStreamReader(l_input));
			Object record = null;

			while ((record = l_inputReader.read()) != null) {
				InputCountry countryData = (InputCountry) record;
				l_countryDataList.add(countryData);
			}
			l_input.close();
		} catch (IllegalArgumentException ie) {
			System.out.println("Error in parsing input txt file. Manually check if the file format is correct");
		} catch (Exception ex) {
			System.out.println("Error reading file from input folder");
		}
		return l_countryDataList;
	}
}
