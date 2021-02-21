package aatral.warzone.utilities;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.StreamBuilder;

import aatral.warzone.model.Borders;

/**
 * <h1>CountryBorderReader Class to read countries border</h1> MapReader
 * implements beanReader methods to reads the input file.
 *
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

public class CountryBorderReader {

	private StreamBuilder borderStream = new StreamBuilder("borders").format("delimited")
			.parser(new DelimitedParserBuilder(' ')).addRecord(Borders.class).ignoreUnidentifiedRecords();

	/**
	 * mapCountryBorderReader method is used to get countries border coordinates
	 * from input file
	 * 
	 * @return list of border data
	 */
	public List<Borders> mapCountryBorderReader() {

		StreamFactory l_factory = StreamFactory.newInstance();
		l_factory.define(this.borderStream);
		List<Borders> l_borderInputData = new ArrayList<>();

		try {
			InputStream l_input = this.getClass().getResourceAsStream("/canada-borders.txt");
			BeanReader inputReader = l_factory.createReader("borders", new InputStreamReader(l_input));
			Object l_record = null;

			while ((l_record = inputReader.read()) != null) {
				Borders borderData = (Borders) l_record;
				l_borderInputData.add(borderData);
			}

		} catch (IllegalArgumentException ie) {
			System.out.println("Error in parsing input txt file. Manually check if the file format is correct");
		} catch (Exception ex) {
			System.out.println("Error reading file from input folder");
		}
		return l_borderInputData;
	}
}