package aatral.warzone.implementation;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.StreamBuilder;

import aatral.warzone.model.Borders;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Country;
import aatral.warzone.utilities.ContinentMapReader;
import aatral.warzone.utilities.CountryBorderReader;
import aatral.warzone.utilities.CountryMapreader;
import aatral.warzone.utilities.InputProcessor;

/**
 * <h1>EditMap</h1> The Class edits the selected map
 * 
 * @author Tejaswini Devireddy
 * @version 1.0
 * @since 2021-02-23
 */
public class EditMap {

	private ValidateMap validateOb = new ValidateMap();
	InputProcessor inputProcessor = new InputProcessor();

	List<Continent> continentList;
	List<Country> countryList;
	List<Borders> bordersList;

	public EditMap(String p_warZoneMap) {
		this.continentList = new ContinentMapReader().readContinentFile(p_warZoneMap);
		this.countryList = new CountryMapreader().readCountryMap(p_warZoneMap);
		this.bordersList = new CountryBorderReader().mapCountryBorderReader(p_warZoneMap);
	}

	/**
	 * startEditMap method is used to get the user's input for editing the map
	 * options
	 * 
	 * @param p_warZoneMap map of warzone.
	 * 
	 */
	public void startEditMap(String p_warZoneMap) {
		boolean l_flag = true;
		while (l_flag) {
			System.out.println("\nEdit Map Format: \neditcontinent -add continentID continentvalue -remove continentID "
					+ "\neditcountry -add countryID continentID -remove countryID "
					+ "\neditneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
			System.out.println("enter 1 to return");

			Scanner l_ob = new Scanner(System.in);
			String editCommand = l_ob.nextLine().trim();
			if (editCommand.startsWith("editcontinent")) {
				String l_continentString = inputProcessor.getString(editCommand);
				editContinentMap(p_warZoneMap, l_continentString);
			} else if (editCommand.startsWith("editcountry")) {
				String l_countryString = inputProcessor.getString(editCommand);
				editCountryMap(p_warZoneMap, l_countryString);
			} else if (editCommand.startsWith("editneighbor")) {
				String l_neighborString = inputProcessor.getString(editCommand);
				editNeighbourMap(p_warZoneMap, l_neighborString);
			} else if (editCommand.startsWith("1")) {
				System.out.println("Map Editor Aborted");
				l_flag = false;
			} else {
				System.out.println("Invalid commmand");
				System.out.println("Map Editor Aborted");
				l_flag = false;
			}

			writeContinentFile(p_warZoneMap, this.continentList);
			writeCountryFile(p_warZoneMap, this.countryList);
			writeBordersFile(p_warZoneMap, this.bordersList);
			System.out.println("Files updated");
		}
	}

	/**
	 * editContinentMap method is used to add or delete the continent
	 * 
	 * @param p_warZoneMap map of warzone.
	 * @param p_continentCommand command for continent.
	 * 
	 */
	public void editContinentMap(String p_warZoneMap, String p_continentCommand) {
		String l_listContinentCommand[] = p_continentCommand.split("-");
		for (String l_editContinentCommand : l_listContinentCommand) {
			if ((l_editContinentCommand).isEmpty()) {
			} else {
				if (l_editContinentCommand.startsWith("add")) {
					Continent l_addContinentList = inputProcessor.getAddContinentInput(l_editContinentCommand);
					addContinent(p_warZoneMap, l_addContinentList);
				} else if (l_editContinentCommand.startsWith("remove")) {
					String removeContinentID = l_editContinentCommand.split(" ")[1];
					removeContinent(p_warZoneMap, removeContinentID);
				} else {
					System.out.println(l_editContinentCommand + " is not a valid command");
				}
			}
		}
	}

	/**
	 * addContinent is used to add the continent based on user preference
	 * 
	 * @param p_warZoneMap map of warzone.
	 * @param l_addContinent added continent object.
	 */
	public void addContinent(String p_warZoneMap, Continent l_addContinent) {

		if (validateOb.validateContinentID(p_warZoneMap, l_addContinent.getContinentId())
				|| validateOb.validateContinentName(p_warZoneMap, l_addContinent.getContinentName())) {

			System.out.println("The Entered continent " + l_addContinent.getContinentId() + " "
					+ l_addContinent.getContinentName() + " is already present");
		} else {
			this.continentList.add(l_addContinent);
		}
	}

	/**
	 * removeContinent method is used to remove the continent based on user
	 * preference
	 * 
	 * @param warZoneMap map of warzone.
	 * @param deleteContinent delete continents object.
	 */
	public void removeContinent(String warZoneMap, String deleteContinent) {

		if (!validateOb.validateContinentID(warZoneMap, deleteContinent)) {
			System.out.println(deleteContinent + " is not available in the map to delete");
		} else {
		
			for (Iterator<Continent> continentIterator = this.continentList.iterator(); continentIterator.hasNext();) {
				Continent continentObject = continentIterator.next();
				if (continentObject.getContinentId().equalsIgnoreCase(deleteContinent)) {
					List<Country> tempCountryObject = new ArrayList<Country>();
					tempCountryObject.addAll(this.countryList);
					for (Country countryObject : tempCountryObject) {
						if (countryObject.getContinentId().equalsIgnoreCase(deleteContinent)) {
							removeAllCountry(warZoneMap, countryObject.getCountryId());
						}
					}
					continentIterator.remove();
				}
			}
			System.out.println("Successfully removed the Continent ID - " + deleteContinent);
		}
	}

	/**
	 * editCountryMap method is edit the country like add or remove the country
	 * 
	 * @param warZoneMap map of warzone.
	 * @param countryString country
	 */
	public void editCountryMap(String warZoneMap, String countryString) {
		String listCountryCommand[] = countryString.split("-");

		for (String editCountryCommand : listCountryCommand) {
			if ((editCountryCommand).isEmpty()) {
			} else {
				if (editCountryCommand.startsWith("add")) {
					Country addCountryList = inputProcessor.getAddCountryInput(editCountryCommand);
					addCountry(warZoneMap, addCountryList);
				} else if (editCountryCommand.startsWith("remove")) {
					String countryRemove = editCountryCommand.split(" ")[1];
					removeCountry(warZoneMap, countryRemove);
				} else {
					System.out.println(editCountryCommand + " is not a valid command");
				}
			}
		}
	}


	/**
	 * addCountry method is used to add the country if the country not listed in
	 * input file
	 * 
	 * @param warZoneMap map of warzone.
	 * @param p_addCountry add country object.
	 */
	public void addCountry(String warZoneMap, Country p_addCountry) {

		if (validateOb.validateCountryID(warZoneMap, p_addCountry.getCountryId())
				|| !validateOb.validateContinentID(warZoneMap, p_addCountry.getContinentId())) {

			System.out.println("The Entered country " + p_addCountry.getCountryId()
					+ " is already present or continent Id " + p_addCountry.getContinentId() + " is not present");
		} else {

			this.countryList.add(new Country(p_addCountry.getCountryId(), "addedCountry", p_addCountry.getContinentId()));
			this.bordersList.add(new Borders(p_addCountry.getCountryId(), new HashSet<String>()));

		}

	}

	/**
	 * removeCountry method is used remove the country using country ID
	 * 
	 * @param warZoneMap map of warzone.
	 * @param removeCountryList list of remove country.
	 */
	public void removeCountry(String warZoneMap, String removeCountryList) {
		boolean flag = true;
		String removeName = "";

		String countryID = removeCountryList.trim();
		if (!validateOb.validateCountryID(warZoneMap, countryID)) {
			flag = false;
			removeName = countryID;
		}

		if (flag) {

			if (validateOb.validateCountryID(warZoneMap, countryID)) {
				removeAllCountry(warZoneMap, countryID);
			}

		} else {
			System.out.println("The Entered country ID -" + removeName + " is not present, kindly enter an existing ID");
		}
	}

	/**
	 * editNeighboutMap method is used to add or remove the neighbour
	 * 
	 * @param warZoneMap map of warzone.
	 * @param neighborString neighbour.
	 */
	public void editNeighbourMap(String warZoneMap, String neighborString) {
		String listNeighborCommand[] = neighborString.split("-");

		for (String editNeighborCommand : listNeighborCommand) {
			if ((editNeighborCommand).isEmpty()) {

			} else {
				if (editNeighborCommand.startsWith("add")) {
					String countryID = editNeighborCommand.split(" ")[1];
					String neighborID = editNeighborCommand.split(" ")[2];

					addNeighbours(warZoneMap, countryID, neighborID);
				} else if (editNeighborCommand.startsWith("remove")) {
					String countryID = editNeighborCommand.split(" ")[1];
					String removeNeighborID = editNeighborCommand.split(" ")[2];
					removeNeighbour(warZoneMap, countryID, removeNeighborID);
				} else {
					System.out.println(editNeighborCommand + " is not a valid command");
				}
			}
		}
	}

	/**
	 * addNeighbours method is used for adding neighbours based on countryId and
	 * neighbourCountryID
	 * 
	 * @param warZoneMap map of warzone.
	 * @param countryId country id.
	 * @param neighborCountryID neighbour id.
	 */
	public void addNeighbours(String warZoneMap, String countryId, String neighborCountryID) {

		if (validateOb.validateCountryID(warZoneMap, countryId)
				&& (validateOb.validateCountryID(warZoneMap, neighborCountryID))) {
			int count = 0;
			List<Borders> tempBorderObject = new ArrayList<Borders>();
			tempBorderObject.addAll(this.bordersList);
			for (Borders borderObject : this.bordersList) {
				if (borderObject.getCountryId().equalsIgnoreCase(countryId)) {
					this.bordersList.get(this.bordersList.indexOf(borderObject)).getAdjacentCountries()
							.add(neighborCountryID.trim());
					count++;
				} else if (borderObject.getCountryId().equalsIgnoreCase(neighborCountryID)) {
					this.bordersList.get(this.bordersList.indexOf(borderObject)).getAdjacentCountries()
							.add(countryId.trim());
					count++;
				}
				if (count == 2)
					break;
			}
			this.bordersList=tempBorderObject;

		} else {
			System.out.println("The countryId or Neighboring countryId is invalid");
		}
	}

/**
 * removeNeighbours is used to remove the neighbour from warzone map
 * @param warZoneMap map of warzone.
 * @param countryID country id value.
 * @param adjacentCountryID adjacent country id value.
 */
	public void removeNeighbour(String warZoneMap, String countryID, String adjacentCountryID) {

		countryID = countryID.trim();
		adjacentCountryID = adjacentCountryID.trim();
		int count = 0;
		if (validateOb.validateCountryID(warZoneMap, countryID)	&& validateOb.validateCountryID(warZoneMap, adjacentCountryID)) {
			List<Borders> tempBorderList = new ArrayList<Borders>();
			tempBorderList.addAll(this.bordersList);
			for (Borders borderObject : this.bordersList) {
				if (borderObject.getCountryId().equalsIgnoreCase(countryID)) {
					tempBorderList.get(tempBorderList.indexOf(borderObject)).getAdjacentCountries()
							.remove(adjacentCountryID);
					count++;
				} else if (borderObject.getCountryId().equalsIgnoreCase(adjacentCountryID)) {
					tempBorderList.get(tempBorderList.indexOf(borderObject)).getAdjacentCountries()
							.remove(countryID);
					count++;
				}
				if (count == 2)
					break;
			}
			if (count == 2) {
				this.bordersList=tempBorderList;
			} else {
				System.out.println("The countryId or Neighboring countryId is invalid");
			}
		} else {
			System.out.println("The countryId or Neighboring countryId is invalid");
		}
	}

	/**
	 * removeAllCountry method is used to remove all countries in warzone map based
	 * on countryID
	 * 
	 * @param warZoneMap map of warzone.
	 * @param countryID country id.
	 */
	public void removeAllCountry(String warZoneMap, String countryID) {
		List<Borders> toRemove = new ArrayList<Borders>();
		Iterator<Borders> borderLineObject = this.bordersList.iterator();

		while (borderLineObject.hasNext()) {
			Borders borderObject = borderLineObject.next();
			if (borderObject.getCountryId().equalsIgnoreCase(countryID)) {
				toRemove.add(borderObject);
			} else {
				Set<String> borderList = borderObject.getAdjacentCountries();
				if (borderList.contains(countryID)) {
					borderList.remove(countryID);
					borderObject.setAdjacentCountries(borderList);
				}
			}
		}

		this.bordersList.removeAll(toRemove);


		Iterator<Country> countryObject = this.countryList.iterator();
		Country countryRemoveObject = null;
		while (countryObject.hasNext()) {
			Country couObj = countryObject.next();
			if (couObj.getCountryId().equals(countryID)) {
				countryRemoveObject = couObj;
				break;
			}
		}
		if (countryRemoveObject != null)
			this.countryList.remove(countryRemoveObject);

	}
	/**
	 * writeCountryFile is used to update the country details in warzone map
	 * 
	 * @param warZoneMap map of warzone.
	 * @param updateCountry list of update country.
	 */
	public void writeCountryFile(String warZoneMap, List<Country> updateCountry) {
		String FILE_NAME = "src/main/resources/map/" + warZoneMap + "/" + warZoneMap + "-countries.txt";
		StreamFactory factory = StreamFactory.newInstance();
		StreamBuilder builderCSV = new StreamBuilder("countryWrite").format("delimited")
				.parser(new DelimitedParserBuilder(' ')).addRecord(Country.class);
		factory.define(builderCSV);

		File deleteFile = new File(FILE_NAME);
		deleteFile.delete();

		BeanWriter out = factory.createWriter("countryWrite", new File(FILE_NAME));

		for (Country country : updateCountry) {
			out.write(country);
		}
		out.flush();
		out.close();

	}

	/**
	 * writeContinentFile is used to update the list of continents in continent file
	 * 
	 * @param warZoneMap map of warzone.
	 * @param updateContinent list of update continent.
	 */
	public void writeContinentFile(String warZoneMap, List<Continent> updateContinent) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String FILE_NAME = "src/main/resources/map/" + warZoneMap + "/" + warZoneMap + "-continents.txt";
		String FILE_NAME1 = "src/main/resources/" + warZoneMap + formatter.format(date) + "-continents.txt";
		StreamFactory factory = StreamFactory.newInstance();
		StreamBuilder builderCSV = new StreamBuilder("continentWrite").format("delimited")
				.parser(new DelimitedParserBuilder(' ')).addRecord(Continent.class);
		factory.define(builderCSV);

		File deleteFile = new File(FILE_NAME);
		deleteFile.delete();

		BeanWriter out = factory.createWriter("continentWrite", new File(FILE_NAME));

		for (Continent continent : updateContinent) {
			out.write(continent);
		}
		out.flush();
		out.close();

	}

	/**
	 * writeBordersFile method is used to write the updated borders text file
	 * 
	 * @param warZoneMap map of warzone.
	 * @param updateBorder list of update border.
	 */
	public void writeBordersFile(String warZoneMap, List<Borders> updateBorder) {
		String FILE_NAME = "src/main/resources/map/" + warZoneMap + "/" + warZoneMap + "-borders1.txt";
		StreamFactory factory = StreamFactory.newInstance();
		StreamBuilder builderCSV = new StreamBuilder("continentWrite").format("delimited")
				.parser(new DelimitedParserBuilder(' ')).addRecord(Borders.class);
		factory.define(builderCSV);

		File deleteFile = new File(FILE_NAME);
		deleteFile.delete();

		BeanWriter out = factory.createWriter("continentWrite", new File(FILE_NAME));

		for (Borders borders : updateBorder) {
			Set<String> ignoreNull = borders.getAdjacentCountries();
			for (Iterator<String> iterator = ignoreNull.iterator(); iterator.hasNext();) {
				if (iterator.next().toString().isEmpty()) {
					iterator.remove();
				}
			}
			borders.setAdjacentCountries(ignoreNull);
			out.write(borders);
		}
		out.flush();
		out.close();

	}

}