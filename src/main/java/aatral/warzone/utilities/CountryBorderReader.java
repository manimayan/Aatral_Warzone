package aatral.warzone.utilities;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.StreamBuilder;

import aatral.warzone.implementation.ComposeBorders;
import aatral.warzone.model.Borders;
import aatral.warzone.model.Country;

/**
 * <h1>CountryBorderReader Class to read countries border</h1>
 * MapReader implements beanReader methods to reads the input file.
 *
 * @author  Manimaran Palani
 * @version 1.0
 * @since   2021-02-12
 */


public class CountryBorderReader {

	private StreamBuilder borderStream = new StreamBuilder("borders")
			.format("delimited")
			.parser(new DelimitedParserBuilder(' '))
			.addRecord(Borders.class)
			.ignoreUnidentifiedRecords();

	/**
	 * CountryBorderReader constructor is used 
	 * to get countries border coordinates from input file
	 */
	
	public CountryBorderReader(List<Country> countryDataList) {

		StreamFactory factory = StreamFactory.newInstance();
		factory.define(this.borderStream);

		try {
			InputStream input = this.getClass().getResourceAsStream("/canada-borders.txt");
			BeanReader inputReader = factory.createReader("borders", new InputStreamReader(input));
			Object record = null;
			List<Borders> borderInputData = new ArrayList<>();
			while ((record = inputReader.read()) != null) {
				Borders borderData = (Borders) record;
				borderInputData.add(borderData);
			}
			new ComposeBorders(countryDataList, borderInputData);
		} 
		catch (IllegalArgumentException ie) {
			System.out.println("Error in parsing input txt file. Manually check if the file format is correct");
		} catch(Exception ex) {
			System.out.println("Error reading file from input folder");
		}
	}
}