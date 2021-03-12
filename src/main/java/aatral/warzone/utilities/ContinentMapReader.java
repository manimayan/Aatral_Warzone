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
 * <h1>MapReader Class to read Continents</h1> MapReader implements beanReader
 * methods to reads the input file.
 *
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-12
 */

public class ContinentMapReader {

	private StreamBuilder continentStream = new StreamBuilder("continents").format("delimited")
			.parser(new DelimitedParserBuilder(' ')).addRecord(Continent.class).ignoreUnidentifiedRecords();

	/**
	 * readContinentFile method is used to get continent coordinates from input file
	 * @param p_map map as string.
	 * @return list of continents.
	 */
	public List<Continent> readContinentFile(String p_map) {

		StreamFactory factory = StreamFactory.newInstance();
		factory.define(this.continentStream);

		List<Continent> l_continentDataList = new ArrayList<>();
		String url = "/map/" + p_map + "/" + p_map + "-continents.txt";
		try {
			InputStream l_input = this.getClass().getResourceAsStream(url);
			BeanReader inputReader = factory.createReader("continents", new InputStreamReader(l_input));
			Object l_record = null;

			while ((l_record = inputReader.read()) != null) {
				Continent continentData = (Continent) l_record;
				l_continentDataList.add(continentData);
			}
			l_input.close();

		} catch (IllegalArgumentException ie) {
			System.out.println("Error in parsing input text file. Manually check if the file format is correct");
		} catch (Exception ex) {
			System.out.println("Error reading file from input folder");
		}
		return l_continentDataList;
	}

}
