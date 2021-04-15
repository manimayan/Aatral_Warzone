package aatral.warzone.utilities;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.StreamBuilder;

import aatral.warzone.model.ConquestBorders;
import aatral.warzone.model.ConquestContinent;
import aatral.warzone.model.ConquestCountry;
import aatral.warzone.model.InputBorders;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
/**
 * <h1>MapReader reads the Map values</h1>
 * @author Tejeswini
 * @since 12.04.2021
 */
public class MapReader {

	/**
	 * readContinentFile method is used to get continent coordinates from input file
	 * 
	 * @param p_typeOfMap  type of map.
	 * @param p_warZoneMap map name
	 * @return list of continents.
	 */
	public List<InputContinent> readContinentFile(String p_typeOfMap, String p_warZoneMap) {

		List<InputContinent> l_continentDataList = new ArrayList<>();

		if (p_typeOfMap.equalsIgnoreCase("domination")) {
			StreamBuilder continentStream = new StreamBuilder("continents").format("delimited")
					.parser(new DelimitedParserBuilder(' ')).addRecord(InputContinent.class)
					.ignoreUnidentifiedRecords();

			StreamFactory factory = StreamFactory.newInstance();
			factory.define(continentStream);

			String url = "/" + p_typeOfMap + "/" + p_warZoneMap + "/" + p_warZoneMap + "-continents.txt";
			try {
				InputStream l_input = this.getClass().getResourceAsStream(url);
				BeanReader inputReader = factory.createReader("continents", new InputStreamReader(l_input));
				Object l_record = null;

				while ((l_record = inputReader.read()) != null) {
					InputContinent continentData = (InputContinent) l_record;
					l_continentDataList.add(continentData);
				}
				l_input.close();

			} catch (IllegalArgumentException ie) {
				System.out.println("Error in parsing input text file. Manually check if the file format is correct");
			} catch (Exception ex) {
				System.out.println("Error reading file from input folder");
			}
		}
		if (p_typeOfMap.equalsIgnoreCase("conquest")) {

			List<ConquestContinent> listConquestContinent = new ArrayList<>();
			StreamBuilder continentStream = new StreamBuilder("continents").format("delimited")
					.parser(new DelimitedParserBuilder('=')).addRecord(ConquestContinent.class)
					.ignoreUnidentifiedRecords();

			StreamFactory factory = StreamFactory.newInstance();
			factory.define(continentStream);

			String url = "/" + p_typeOfMap + "/" + p_warZoneMap + "/" + p_warZoneMap + "-continents.txt";
			try {
				InputStream l_input = this.getClass().getResourceAsStream(url);
				BeanReader inputReader = factory.createReader("continents", new InputStreamReader(l_input));
				Object l_record = null;

				while ((l_record = inputReader.read()) != null) {
					ConquestContinent continentData = (ConquestContinent) l_record;
					listConquestContinent.add(continentData);
				}
				int i = 1;
				for (ConquestContinent convertConquest : listConquestContinent) {
					InputContinent continentData = new InputContinent(convertConquest, i);
					l_continentDataList.add(continentData);
					i++;
				}

				l_input.close();

			} catch (IllegalArgumentException ie) {
				System.out.println("Error in parsing input text file. Manually check if the file format is correct");
			} catch (Exception ex) {
				System.out.println("Error reading file from input folder");
			}
		}
		return l_continentDataList;
	}

	/**
	 * readCountryMap method is used to get country coordinates from input file
	 * 
	 * @param p_typeOfMap  type of map.
	 * @param p_warZoneMap map name
	 * 
	 * @return list of country
	 */

	public List<InputCountry> readCountryMap(String p_typeOfMap, String p_warZoneMap) {

		List<InputCountry> l_countryDataList = new ArrayList<>();

		if (p_typeOfMap.equalsIgnoreCase("domination")) {
			StreamBuilder countryStream = new StreamBuilder("countries").format("delimited")
					.parser(new DelimitedParserBuilder(' ')).addRecord(InputCountry.class).ignoreUnidentifiedRecords();

			StreamFactory l_factory = StreamFactory.newInstance();
			l_factory.define(countryStream);

			String url = "/" + p_typeOfMap + "/" + p_warZoneMap + "/" + p_warZoneMap + "-countries.txt";
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
		}
		if (p_typeOfMap.equalsIgnoreCase("conquest")) {

			List<ConquestCountry> listConquestCountry = new ArrayList<>();
			StreamBuilder countryStream = new StreamBuilder("countries").format("delimited")
					.parser(new DelimitedParserBuilder(',')).addRecord(ConquestCountry.class)
					.ignoreUnidentifiedRecords();

			StreamFactory l_factory = StreamFactory.newInstance();
			l_factory.define(countryStream);

			String url = "/" + p_typeOfMap + "/" + p_warZoneMap + "/" + p_warZoneMap + "-countries.txt";
			try {
				InputStream l_input = this.getClass().getResourceAsStream(url);
				BeanReader l_inputReader = l_factory.createReader("countries", new InputStreamReader(l_input));
				Object record = null;

				while ((record = l_inputReader.read()) != null) {
					ConquestCountry countryData = (ConquestCountry) record;
					listConquestCountry.add(countryData);
				}
				List<InputContinent> matchingInputContinentId = readContinentFile(p_typeOfMap, p_warZoneMap);
				int i = 1;
				for (ConquestCountry convertConquest : listConquestCountry) {
					InputCountry countryData = new InputCountry(matchingInputContinentId, convertConquest, i);
					l_countryDataList.add(countryData);
					i++;
				}
				l_input.close();
			} catch (IllegalArgumentException ie) {
				System.out.println("Error in parsing input txt file. Manually check if the file format is correct");
			} catch (Exception ex) {
				System.out.println("Error reading file from input folder");
			}
		}
		return l_countryDataList;
	}

	/**
	 * mapCountryBorderReader method is used to get countries border coordinates
	 * from input file
	 * 
	 * @param p_typeOfMap  type of map.
	 * @param p_warZoneMap map name
	 * 
	 * @return list of border data
	 */
	public List<InputBorders> mapCountryBorderReader(String p_typeOfMap, String p_warZoneMap) {

		List<InputBorders> l_borderInputData = new ArrayList<>();
		if (p_typeOfMap.equalsIgnoreCase("domination")) {
			StreamBuilder borderStream = new StreamBuilder("borders").format("delimited")
					.parser(new DelimitedParserBuilder(' ')).addRecord(InputBorders.class).ignoreUnidentifiedRecords();

			StreamFactory l_factory = StreamFactory.newInstance();
			l_factory.define(borderStream);

			String url = "/" + p_typeOfMap + "/" + p_warZoneMap + "/" + p_warZoneMap + "-borders1.txt";
			try {
				InputStream l_input = this.getClass().getResourceAsStream(url);
				BeanReader inputReader = l_factory.createReader("borders", new InputStreamReader(l_input));
				Object l_record = null;

				while ((l_record = inputReader.read()) != null) {
					InputBorders borderData = (InputBorders) l_record;
					l_borderInputData.add(borderData);
				}
				l_input.close();
			} catch (IllegalArgumentException ie) {
				System.out.println("Error in parsing input txt file. Manually check if the file format is correct");
			} catch (Exception ex) {
				System.out.println("Error reading file from input folder");
			}
		}
		if (p_typeOfMap.equalsIgnoreCase("conquest")) {
			
			List<ConquestBorders> listConquestBorders = new ArrayList<>();
			StreamBuilder borderStream = new StreamBuilder("borders").format("delimited")
					.parser(new DelimitedParserBuilder(',')).addRecord(ConquestBorders.class).ignoreUnidentifiedRecords();

			StreamFactory l_factory = StreamFactory.newInstance();
			l_factory.define(borderStream);

			String url = "/" + p_typeOfMap + "/" + p_warZoneMap + "/" + p_warZoneMap + "-countries.txt";
			try {
				InputStream l_input = this.getClass().getResourceAsStream(url);
				BeanReader inputReader = l_factory.createReader("borders", new InputStreamReader(l_input));
				Object l_record = null;

				while ((l_record = inputReader.read()) != null) {
					ConquestBorders borderData = (ConquestBorders) l_record;
					listConquestBorders.add(borderData);
				}

				List<InputCountry> matchingInputCountryId = readCountryMap(p_typeOfMap, p_warZoneMap);
				for (ConquestBorders convertConquest : listConquestBorders) {
					InputBorders borderData = new InputBorders(matchingInputCountryId, convertConquest);
					l_borderInputData.add(borderData);
				}
				l_input.close();
			} catch (IllegalArgumentException ie) {
				System.out.println("Error in parsing input txt file. Manually check if the file format is correct");
			} catch (Exception ex) {
				System.out.println("Error reading file from input folder");
			}
		}
		return l_borderInputData;
	}

}
