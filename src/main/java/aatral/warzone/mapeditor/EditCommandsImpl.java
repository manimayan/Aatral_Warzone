package aatral.warzone.mapeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.beanio.BeanWriter;
import org.beanio.StreamFactory;
import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.builder.StreamBuilder;

import aatral.warzone.model.ConquestBorders;
import aatral.warzone.model.ConquestContinent;
import aatral.warzone.model.ConquestCountry;
import aatral.warzone.model.ConquestHeaderContinent;
import aatral.warzone.model.ConquestHeaderCountry;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;
import aatral.warzone.model.FormatConquestBorders;
import aatral.warzone.model.FormatConquestContinent;
import aatral.warzone.model.HeaderBorder;
import aatral.warzone.model.HeaderContinent;
import aatral.warzone.model.HeaderCountry;
import aatral.warzone.model.InputBorders;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
import aatral.warzone.model.NewLine;
import aatral.warzone.observerPattern.LogEntryBuffer;
import aatral.warzone.observerPattern.LogWriter;
import aatral.warzone.statePattern.MasterMapEditor;
import aatral.warzone.utilities.InputProcessor;
import aatral.warzone.utilities.MapReader;

/**
 * <h1>EditMap</h1> The Class edits the selected map
 * 
 * @author Tejaswini Devireddy
 * @version 1.0
 * @since 2021-02-23
 */
public class EditCommandsImpl {

	LogEntryBuffer log = new LogEntryBuffer();
	LogWriter logWriter = new LogWriter(log);

	private ValidateMapImpl validateOb = new ValidateMapImpl();
	InputProcessor inputProcessor = new InputProcessor();

	List<InputContinent> continentList;
	List<InputCountry> countryList;
	List<InputBorders> bordersList;

	public EditCommandsImpl(String p_typeOfMap, String p_warZoneMap) {
		MapReader mapReader = new MapReader();
		this.continentList = mapReader.readContinentFile(p_typeOfMap, p_warZoneMap);
		this.countryList = mapReader.readCountryMap(p_typeOfMap, p_warZoneMap);
		this.bordersList = mapReader.mapCountryBorderReader(p_typeOfMap, p_warZoneMap);
	}

	/**
	 * startEditMap method is used to get the user's input for editing the map
	 * options
	 * 
	 * @param p_warZoneMap : map of warzone.
	 * @param p_typeOfMap : type of the map
	 * 
	 */
	public void startEditMap(String p_typeOfMap, String p_warZoneMap) {
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
				editContinentMap(p_typeOfMap, p_warZoneMap, l_continentString);
			} else if (editCommand.startsWith("editcountry")) {
				String l_countryString = inputProcessor.getString(editCommand);
				editCountryMap(p_typeOfMap, p_warZoneMap, l_countryString);
			} else if (editCommand.startsWith("editneighbor")) {
				String l_neighborString = inputProcessor.getString(editCommand);
				editNeighbourMap(p_typeOfMap, p_warZoneMap, l_neighborString);
			} else if (editCommand.startsWith("1")) {
				System.out.println("Map Editor Aborted");
				l_flag = false;
			} else {
				System.out.println("Invalid commmand");
				System.out.println("Map Editor Aborted");
				l_flag = false;
			}

			writeContinentFile(p_typeOfMap, p_warZoneMap, this.continentList);
			Map<String, Continent> getMasterMap = new MasterMapEditor().loadMap(p_typeOfMap, p_warZoneMap);
			for (InputContinent inputContinent : this.continentList) {
				Continent addToMaster = new Continent(inputContinent, null);
				getMasterMap.put(inputContinent.getContinentId(), addToMaster);
			}

			writeCountryFile(p_typeOfMap, p_warZoneMap, this.countryList);
			for (Entry<String, Continent> countryEntry : getMasterMap.entrySet()) {
				for (InputCountry inputCountry : this.countryList) {
					if (countryEntry.getKey().equals(inputCountry.getContinentId())) {
						Countries addToMaster = new Countries(inputCountry.getCountryId(),
								inputCountry.getCountryName(), inputCountry.getContinentId());
						if (countryEntry.getValue().getContinentOwnedCountries() != null) {
							countryEntry.getValue().getContinentOwnedCountries().add(addToMaster);
						} else {
							List<Countries> continentOwnedCountries = new ArrayList<Countries>();
							continentOwnedCountries.add(addToMaster);
							countryEntry.getValue().setContinentOwnedCountries(continentOwnedCountries);
						}
					}
				}
			}

			writeBordersFile(p_typeOfMap, p_warZoneMap, this.bordersList);
			for (Entry<String, Continent> ContinentEntry : getMasterMap.entrySet()) {
				for (InputBorders inputBorders : this.bordersList) {
					if (ContinentEntry.getValue().getContinentOwnedCountries() != null) {
						for (Countries CountryEntry : ContinentEntry.getValue().getContinentOwnedCountries()) {
							if (CountryEntry.getCountryId().equals(inputBorders.getCountryId())) {
								CountryEntry.getCountryOwnedBorders().addAll(inputBorders.getAdjacentCountries());
							}
						}
					}
				}
			}
			writeMasterFile(p_typeOfMap, p_warZoneMap, getMasterMap);
			System.out.println("Files updated");
		}
	}

	/**
	 * editContinentMap method is used to add or delete the continent
	 * 
	 * @param p_typeOfMap         : type of map
	 * @param p_warZoneMap:       map of warzone.
	 * @param p_continentCommand: command for continent.
	 * 
	 */
	public void editContinentMap(String p_typeOfMap, String p_warZoneMap, String p_continentCommand) {
		String l_listContinentCommand[] = p_continentCommand.split("-");
		for (String l_editContinentCommand : l_listContinentCommand) {
			if ((l_editContinentCommand).isEmpty()) {
			} else {
				if (l_editContinentCommand.startsWith("add")) {
					InputContinent l_addContinentList = inputProcessor.getAddContinentInput(l_editContinentCommand);
					addContinent(p_typeOfMap, p_warZoneMap, l_addContinentList, p_continentCommand);
				} else if (l_editContinentCommand.startsWith("remove")) {
					String removeContinentID = l_editContinentCommand.split(" ")[1];
					removeContinent(p_typeOfMap, p_warZoneMap, removeContinentID, p_continentCommand);
				} else {
					log.info("MapEditor", p_continentCommand, l_editContinentCommand + " is not a valid command");
					System.out.println(l_editContinentCommand + " is not a valid command");
				}
			}
		}
	}

	/**
	 * addContinent is used to add the continent based on user preference
	 *
	 * @param p_typeOfMap         : type of map
	 * @param p_warZoneMap        : map of warzone.
	 * @param l_addContinent:     added continent object.
	 * @param p_continentCommand: continentCommand
	 */
	public void addContinent(String p_typeOfMap, String p_warZoneMap, InputContinent l_addContinent,
			String p_continentCommand) {

		if (validateOb.validateContinentID(p_typeOfMap, p_warZoneMap, l_addContinent.getContinentId())) {

			System.out.println("The Entered continent " + l_addContinent.getContinentId() + " "
					+ l_addContinent.getContinentName() + " is already present");
			log.info("MapEditor", "editcontinent " + p_continentCommand, l_addContinent.getContinentId() + " "
					+ l_addContinent.getContinentName() + " is already present\"");
		} else {
			log.info("MapEditor", "editcontinent " + p_continentCommand,
					l_addContinent.getContinentId() + " " + l_addContinent.getContinentName() + " added successfully");
			this.continentList.add(l_addContinent);

		}
	}

	/**
	 * removeContinent method is used to remove the continent based on user
	 * preference
	 * 
	 * @param p_typeOfMap         : type of map
	 * @param warZoneMap:         map of warzone.
	 * @param deleteContinent:    delete continents object.
	 * @param p_continentCommand: continentCommand
	 */
	public void removeContinent(String p_typeOfMap, String warZoneMap, String deleteContinent,
			String p_continentCommand) {

		if (!validateOb.validateContinentID(p_typeOfMap, warZoneMap, deleteContinent)) {
			System.out.println(deleteContinent + " is not available in the map to delete");
			log.info("MapEditor", "editcontinent " + p_continentCommand,
					deleteContinent + " is not available in the map to delete");
		} else {

			for (Iterator<InputContinent> continentIterator = this.continentList.iterator(); continentIterator
					.hasNext();) {
				InputContinent continentObject = continentIterator.next();
				if (continentObject.getContinentId().equalsIgnoreCase(deleteContinent)) {
					List<InputCountry> tempCountryObject = new ArrayList<InputCountry>();
					tempCountryObject.addAll(this.countryList);
					for (InputCountry countryObject : tempCountryObject) {
						if (countryObject.getContinentId().equalsIgnoreCase(deleteContinent)) {
							removeAllCountry(warZoneMap, countryObject.getCountryId());
						}
					}
					continentIterator.remove();
				}
			}
			log.info("MapEditor", "editcontinent " + p_continentCommand,
					"Successfully removed the Continent ID - " + deleteContinent);
			System.out.println("Successfully removed the Continent ID - " + deleteContinent);
		}
	}

	/**
	 * editCountryMap method is edit the country like add or remove the country
	 * 
	 * @param p_typeOfMap     : type of map
	 * @param warZoneMap      map of warzone.
	 * @param countryString   country
	 */
	public void editCountryMap(String p_typeOfMap, String warZoneMap, String countryString) {
		String listCountryCommand[] = countryString.split("-");

		for (String editCountryCommand : listCountryCommand) {
			if ((editCountryCommand).isEmpty()) {
			} else {
				if (editCountryCommand.startsWith("add")) {
					Countries addCountryList = inputProcessor.getAddCountryInput(editCountryCommand);
					addCountry(p_typeOfMap, warZoneMap, addCountryList, editCountryCommand);
				} else if (editCountryCommand.startsWith("remove")) {
					String countryRemove = editCountryCommand.split(" ")[1];
					removeCountry(p_typeOfMap, warZoneMap, countryRemove, editCountryCommand);
				} else {
					log.info("MapEditor", "editcountry " + editCountryCommand,
							editCountryCommand + " is not a valid command");
					System.out.println(editCountryCommand + " is not a valid command");
				}
			}
		}
	}
	
	/**
	 * generate method is used to generate random string
	 * 
	 * @return randomString
	 */
	String generate() {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 4;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();

		return generatedString;
	}

	/**
	 * addCountry method is used to add the country if the country not listed in
	 * input file
	 * 
	 * @param p_typeOfMap         : type of map
	 * @param p_warZoneMap:       map of warzone.
	 * @param p_addCountry:       add country object.
	 * @param editCountryCommand: editCountryCommand
	 */
	public void addCountry(String p_typeOfMap, String p_warZoneMap, Countries p_addCountry, String editCountryCommand) {

		if (validateOb.validateCountryID(p_typeOfMap, p_warZoneMap, p_addCountry.getCountryId())
				|| !validateOb.validateContinentID(p_typeOfMap, p_warZoneMap, p_addCountry.getContinentId())) {

			System.out.println("The Entered country " + p_addCountry.getCountryId()
			+ " is already present or continent Id " + p_addCountry.getContinentId() + " is not present");
			log.info("MapEditor", "editcountry " + editCountryCommand,
					"The Entered country " + p_addCountry.getCountryId() + " is already present or continent Id "
							+ p_addCountry.getContinentId() + " is not present");
		} else {
			
			String random = generate();
			this.countryList
			.add(new InputCountry(p_addCountry.getCountryId(), "addCountry"+random, p_addCountry.getContinentId()));

			if (p_typeOfMap.equalsIgnoreCase("conquest")) {
				this.bordersList.add(new InputBorders(p_addCountry.getCountryId(), new HashSet<String>(),
						p_addCountry.getContinentId(), "addCountry"+random));
			} if (p_typeOfMap.equalsIgnoreCase("domination"))  {
				this.bordersList.add(new InputBorders(p_addCountry.getCountryId(), new HashSet<String>()));
			}
			log.info("MapEditor", "editcountry " + editCountryCommand,
					p_addCountry.getCountryId() + " addedd successfully");
		}
	}

	/**
	 * removeCountry method is used remove the country using country ID
	 * 
	 * @param p_typeOfMap         : type of map
	 * @param warZoneMap          : map of warzone.
	 * @param removeCountryList:  list of remove country.
	 * @param editCountryCommand: editCountryCommand
	 */
	public void removeCountry(String p_typeOfMap, String warZoneMap, String removeCountryList,
			String editCountryCommand) {
		boolean flag = true;
		String removeName = "";

		String countryID = removeCountryList.trim();
		if (!validateOb.validateCountryID(p_typeOfMap, warZoneMap, countryID)) {
			flag = false;
			removeName = countryID;
		}

		if (flag) {

			if (validateOb.validateCountryID(p_typeOfMap, warZoneMap, countryID)) {
				removeAllCountry(warZoneMap, countryID);
			}
			log.info("MapEditor", "editcountry " + editCountryCommand, countryID + " deleted successfully");

		} else {
			log.info("MapEditor", "editcountry " + editCountryCommand,
					"The Entered country ID -" + removeName + " is not present, kindly enter an existing ID");
			System.out
			.println("The Entered country ID -" + removeName + " is not present, kindly enter an existing ID");
		}
	}

	/**
	 * editNeighbourMap method is used to add or remove the neighbor
	 *
	 * @param p_typeOfMap     : type of map
	 * @param warZoneMap:     map of warzone.
	 * @param neighborString: neighbour.
	 */
	public void editNeighbourMap(String p_typeOfMap, String warZoneMap, String neighborString) {
		String listNeighborCommand[] = neighborString.split("-");

		for (String editNeighborCommand : listNeighborCommand) {
			if ((editNeighborCommand).isEmpty()) {

			} else {
				if (editNeighborCommand.startsWith("add")) {
					String countryID = editNeighborCommand.split(" ")[1];
					String neighborID = editNeighborCommand.split(" ")[2];

					addNeighbours(p_typeOfMap, warZoneMap, countryID, neighborID, editNeighborCommand);
				} else if (editNeighborCommand.startsWith("remove")) {
					String countryID = editNeighborCommand.split(" ")[1];
					String removeNeighborID = editNeighborCommand.split(" ")[2];
					removeNeighbour(p_typeOfMap, warZoneMap, countryID, removeNeighborID, editNeighborCommand);
				} else {
					log.info("MapEditor", "editNeighbor " + editNeighborCommand,
							editNeighborCommand + " is not a valid command");
					System.out.println(editNeighborCommand + " is not a valid command");
				}
			}
		}
	}

	/**
	 * addNeighbours method is used for adding neighbors based on countryId and
	 * neighbourCountryID
	 * 
	 * @param p_warZoneMap        map of warzone.
	 * @param p_countryId         country id.
	 * @param p_neighborCountryID neighbour id.
	 * @param editNeighborCommand editNeighborCommand
	 * @param p_typeOfMap         type of map
	 */
	public void addNeighbours(String p_typeOfMap, String p_warZoneMap, String p_countryId, String p_neighborCountryID,
			String editNeighborCommand) {

		if (validateOb.validateCountryID(p_typeOfMap, p_warZoneMap, p_countryId)
				&& (validateOb.validateCountryID(p_typeOfMap, p_warZoneMap, p_neighborCountryID))) {
			int count = 0;
			List<InputBorders> tempBorderObject = new ArrayList<InputBorders>();
			tempBorderObject.addAll(this.bordersList);
			for (InputBorders borderObject : this.bordersList) {
				if (borderObject.getCountryId().equalsIgnoreCase(p_countryId)) {
					this.bordersList.get(this.bordersList.indexOf(borderObject)).getAdjacentCountries()
					.add(p_neighborCountryID.trim());
					count++;
				} else if (borderObject.getCountryId().equalsIgnoreCase(p_neighborCountryID)) {
					this.bordersList.get(this.bordersList.indexOf(borderObject)).getAdjacentCountries()
					.add(p_countryId.trim());
					count++;
				}
				if (count == 2)
					break;
			}
			this.bordersList = tempBorderObject;
			log.info("MapEditor", "editNeighbor " + editNeighborCommand, "borders added successfully");

		} else {
			log.info("MapEditor", "editNeighbor " + editNeighborCommand,
					"The countryId or Neighboring countryId is invalid");
			System.out.println("The countryId or Neighboring countryId is invalid");
		}
	}

	/**
	 * removeNeighbours is used to remove the neighbor from warzone map
	 * 
	 * @param warZoneMap          map of warzone.
	 * @param countryID           country id value.
	 * @param adjacentCountryID   adjacent country id value.
	 * @param editNeighborCommand editNeighborCommand
	 * @param p_typeOfMap         type of map
	 */
	public void removeNeighbour(String p_typeOfMap, String warZoneMap, String countryID, String adjacentCountryID,
			String editNeighborCommand) {

		countryID = countryID.trim();
		adjacentCountryID = adjacentCountryID.trim();
		int count = 0;
		if (validateOb.validateCountryID(p_typeOfMap, warZoneMap, countryID)
				&& validateOb.validateCountryID(p_typeOfMap, warZoneMap, adjacentCountryID)) {
			List<InputBorders> tempBorderList = new ArrayList<InputBorders>();
			tempBorderList.addAll(this.bordersList);
			for (InputBorders borderObject : this.bordersList) {
				if (borderObject.getCountryId().equalsIgnoreCase(countryID)) {
					tempBorderList.get(tempBorderList.indexOf(borderObject)).getAdjacentCountries()
					.remove(adjacentCountryID);
					count++;
				} else if (borderObject.getCountryId().equalsIgnoreCase(adjacentCountryID)) {
					tempBorderList.get(tempBorderList.indexOf(borderObject)).getAdjacentCountries().remove(countryID);
					count++;
				}
				if (count == 2)
					break;
			}
			if (count == 2) {
				this.bordersList = tempBorderList;
				log.info("MapEditor", "editNeighbor " + editNeighborCommand, "borders removed successfully");
			} else {
				log.info("MapEditor", "editNeighbor " + editNeighborCommand,
						"The countryId or Neighboring countryId is invalid");
				System.out.println("The countryId or Neighboring countryId is invalid");
			}
		} else {
			log.info("MapEditor", "editNeighbor " + editNeighborCommand,
					"The countryId or Neighboring countryId is invalid");
			System.out.println("The countryId or Neighboring countryId is invalid");
		}
	}

	/**
	 * removeAllCountry method is used to remove all countries in warzone map based
	 * on countryID
	 * 
	 * @param warZoneMap map of warzone.
	 * @param countryID  country id.
	 */
	public void removeAllCountry(String warZoneMap, String countryID) {
		List<InputBorders> toRemove = new ArrayList<InputBorders>();
		Iterator<InputBorders> borderLineObject = this.bordersList.iterator();

		while (borderLineObject.hasNext()) {
			InputBorders borderObject = borderLineObject.next();
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

		Iterator<InputCountry> countryObject = this.countryList.iterator();
		InputCountry countryRemoveObject = null;
		while (countryObject.hasNext()) {
			InputCountry couObj = countryObject.next();
			if (couObj.getCountryId().equals(countryID)) {
				countryRemoveObject = couObj;
				break;
			}
		}
		if (countryRemoveObject != null)
			this.countryList.remove(countryRemoveObject);

	}

	/**
	 * writeContinentFile is used to update the list of continents in continent file
	 * 
	 * @param warZoneMap      map of warzone.
	 * @param updateContinent list of update continent.
	 * @param p_typeOfMap     type of map
	 */
	public void writeContinentFile(String p_typeOfMap, String warZoneMap, List<InputContinent> updateContinent) {

		if (p_typeOfMap.equalsIgnoreCase("domination")) {
			String FILE_NAME = "src/main/resources/" + p_typeOfMap + "/" + warZoneMap + "/" + warZoneMap
					+ "-continents.txt";
			StreamFactory factory = StreamFactory.newInstance();
			StreamBuilder builderCSV = new StreamBuilder("continentWrite").format("delimited")
					.parser(new DelimitedParserBuilder(' ')).addRecord(InputContinent.class);
			factory.define(builderCSV);

			File deleteFile = new File(FILE_NAME);
			deleteFile.delete();

			BeanWriter out = factory.createWriter("continentWrite", new File(FILE_NAME));

			for (InputContinent continent : updateContinent) {
				out.write(continent);
			}
			out.flush();
			out.close();
		}
		if (p_typeOfMap.equalsIgnoreCase("conquest")) {
			String FILE_NAME = "src/main/resources/" + p_typeOfMap + "/" + warZoneMap + "/" + warZoneMap
					+ "-continents.txt";
			StreamFactory factory = StreamFactory.newInstance();
			StreamBuilder builderCSV = new StreamBuilder("continentWrite").format("delimited")
					.parser(new DelimitedParserBuilder('=')).addRecord(ConquestContinent.class);
			factory.define(builderCSV);

			File deleteFile = new File(FILE_NAME);
			deleteFile.delete();

			BeanWriter out = factory.createWriter("continentWrite", new File(FILE_NAME));

			List<ConquestContinent> continentList = new ArrayList<>();
			for (InputContinent continent : updateContinent) {
				ConquestContinent convertConquest = new ConquestContinent(continent);
				continentList.add(convertConquest);
			}

			for (ConquestContinent continent : continentList) {
				out.write(continent);
			}
			out.flush();
			out.close();
		}

	}

	/**
	 * writeCountryFile is used to update the country details in warzone map
	 * 
	 * @param warZoneMap    map of warzone.
	 * @param updateCountry list of update country.
	 * @param p_typeOfMap   type of map
	 */
	public void writeCountryFile(String p_typeOfMap, String warZoneMap, List<InputCountry> updateCountry) {

		if (p_typeOfMap.equalsIgnoreCase("domination")) {
			String FILE_NAME = "src/main/resources/" + p_typeOfMap + "/" + warZoneMap + "/" + warZoneMap
					+ "-countries.txt";
			StreamFactory factory = StreamFactory.newInstance();
			StreamBuilder builderCSV = new StreamBuilder("countryWrite").format("delimited")
					.parser(new DelimitedParserBuilder(' ')).addRecord(InputCountry.class);
			factory.define(builderCSV);

			File deleteFile = new File(FILE_NAME);
			deleteFile.delete();

			BeanWriter out = factory.createWriter("countryWrite", new File(FILE_NAME));

			for (InputCountry country : updateCountry) {
				out.write(country);
			}
			out.flush();
			out.close();
		}
		if (p_typeOfMap.equalsIgnoreCase("conquest")) {
			String FILE_NAME = "src/main/resources/" + p_typeOfMap + "/" + warZoneMap + "/" + warZoneMap
					+ "-countries.txt";
			StreamFactory factory = StreamFactory.newInstance();
			StreamBuilder builderCSV = new StreamBuilder("countryWrite").format("delimited")
					.parser(new DelimitedParserBuilder(',')).addRecord(ConquestCountry.class);
			factory.define(builderCSV);

			File deleteFile = new File(FILE_NAME);
			deleteFile.delete();

			BeanWriter out = factory.createWriter("countryWrite", new File(FILE_NAME));

			List<InputContinent> matchingInputContinentId = new MapReader().readContinentFile(p_typeOfMap, warZoneMap);

			List<ConquestCountry> countryList = new ArrayList<>();
			for (InputCountry country : updateCountry) {
				ConquestCountry convertConquest = new ConquestCountry(country, matchingInputContinentId);
				countryList.add(convertConquest);

			}
			for (ConquestCountry country : countryList) {
				out.write(country);
			}

			out.flush();
			out.close();
		}

	}

	/**
	 * writeBordersFile method is used to write the updated borders text file
	 * 
	 * @param warZoneMap   map of warzone.
	 * @param updateBorder list of update border.
	 * @param p_typeOfMap  type of map
	 */
	public void writeBordersFile(String p_typeOfMap, String warZoneMap, List<InputBorders> updateBorder) {

		if (p_typeOfMap.equalsIgnoreCase("domination")) {
			String FILE_NAME = "src/main/resources/" + p_typeOfMap + "/" + warZoneMap + "/" + warZoneMap
					+ "-borders1.txt";
			StreamFactory factory = StreamFactory.newInstance();
			StreamBuilder builderCSV = new StreamBuilder("borderWrite").format("delimited")
					.parser(new DelimitedParserBuilder(' ')).addRecord(InputBorders.class);
			factory.define(builderCSV);

			File deleteFile = new File(FILE_NAME);
			deleteFile.delete();

			BeanWriter out = factory.createWriter("borderWrite", new File(FILE_NAME));

			for (InputBorders borders : updateBorder) {
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
		if (p_typeOfMap.equalsIgnoreCase("conquest")) {

			String FILE_NAME = "src/main/resources/" + p_typeOfMap + "/" + warZoneMap + "/" + warZoneMap
					+ "-countries.txt";
			StreamFactory factory = StreamFactory.newInstance();
			StreamBuilder builderCSV = new StreamBuilder("borderWrite").format("delimited")
					.parser(new DelimitedParserBuilder(',')).addRecord(ConquestBorders.class);
			factory.define(builderCSV);

			File deleteFile = new File(FILE_NAME);
			deleteFile.delete();

			BeanWriter out = factory.createWriter("borderWrite", new File(FILE_NAME));

			List<InputContinent> matchingInputContinentId = new MapReader().readContinentFile(p_typeOfMap, warZoneMap);
			List<InputCountry> matchingInputCountryId = new MapReader().readCountryMap(p_typeOfMap, warZoneMap);
			List<ConquestBorders> countryBorderList = new ArrayList<>();

			for (InputBorders borders : updateBorder) {
				Set<String> ignoreNull = borders.getAdjacentCountries();
				for (Iterator<String> iterator = ignoreNull.iterator(); iterator.hasNext();) {
					if (iterator.next().toString().isEmpty()) {
						iterator.remove();
					}
				}
				borders.setAdjacentCountries(ignoreNull);
				ConquestBorders convertConquest = new ConquestBorders(borders, matchingInputContinentId,
						matchingInputCountryId);
				countryBorderList.add(convertConquest);
			}

			for (ConquestBorders border : countryBorderList) {
				out.write(border);
			}

			out.flush();
			out.close();
		}
	}

	/**
	 * writeMasterFile is used to update the list of continents in continent file
	 * 
	 * @param p_warZoneMap war zone map
	 * @param getMasterMap master map
	 * @param p_typeOfMap  type of map
	 */
	public void writeMasterFile(String p_typeOfMap, String p_warZoneMap, Map<String, Continent> getMasterMap) {

		if (p_typeOfMap.equalsIgnoreCase("domination")) {
			String FILE_NAME = "src/main/resources/" + p_typeOfMap + "-" + p_warZoneMap + ".map";
			StreamFactory factory = StreamFactory.newInstance();
			StreamBuilder builderCSV = new StreamBuilder("continentWrite").format("delimited")
					.parser(new DelimitedParserBuilder(' ')).addRecord(HeaderContinent.class)
					.addRecord(HeaderCountry.class).addRecord(HeaderBorder.class).addRecord(InputContinent.class)
					.addRecord(InputCountry.class).addRecord(InputBorders.class);
			factory.define(builderCSV);

			File deleteFile = new File(FILE_NAME);
			deleteFile.delete();

			BeanWriter out = factory.createWriter("continentWrite", new File(FILE_NAME));
			List<InputContinent> updateContinent = new ArrayList<>();
			List<InputCountry> updateCountry = new ArrayList<>();
			List<InputBorders> updateBorder = new ArrayList<>();
			for (Entry<String, Continent> continentEntry : getMasterMap.entrySet()) {

				InputContinent inputContinent = new InputContinent(continentEntry.getValue());
				updateContinent.add(inputContinent);

				if (continentEntry.getValue().getContinentOwnedCountries() != null) {
					for (Countries countryEntry : continentEntry.getValue().getContinentOwnedCountries()) {
						InputCountry inputCountry = new InputCountry(countryEntry);
						InputBorders inputBorder = new InputBorders(countryEntry);
						updateBorder.add(inputBorder);
						updateCountry.add(inputCountry);

					}
				}
			}
			HeaderContinent headerContinent = new HeaderContinent();
			out.write(headerContinent);

			for (InputContinent continent : updateContinent) {
				out.write(continent);
			}

			HeaderCountry headerCountry = new HeaderCountry();
			out.write(headerCountry);

			Collections.sort(updateCountry, new InputCountry());
			for (InputCountry country : updateCountry) {
				out.write(country);
			}

			HeaderBorder headerBorder = new HeaderBorder();
			out.write(headerBorder);

			Collections.sort(updateBorder, new InputBorders());
			for (InputBorders border : updateBorder) {
				out.write(border);
			}

			out.flush();
			out.close();
		}
		if (p_typeOfMap.equalsIgnoreCase("conquest")) {

			String FILE_NAME = "src/main/resources/" + p_typeOfMap + "-" + p_warZoneMap + ".map";
			StreamFactory factory = StreamFactory.newInstance();
			StreamBuilder builderCSV = new StreamBuilder("continentWrite").format("delimited")
					.parser(new DelimitedParserBuilder(' ')).addRecord(ConquestHeaderContinent.class)
					.addRecord(ConquestHeaderCountry.class).addRecord(FormatConquestContinent.class)
					.addRecord(FormatConquestBorders.class).addRecord(NewLine.class);
			factory.define(builderCSV);

			File deleteFile = new File(FILE_NAME);
			deleteFile.delete();

			BeanWriter out = factory.createWriter("continentWrite", new File(FILE_NAME));

			List<InputContinent> updateContinent = new ArrayList<>();
			List<InputCountry> updateCountry = new ArrayList<>();
			List<InputBorders> updateBorder = new ArrayList<>();
			for (Entry<String, Continent> continentEntry : getMasterMap.entrySet()) {

				InputContinent inputContinent = new InputContinent(continentEntry.getValue());
				updateContinent.add(inputContinent);

				if (continentEntry.getValue().getContinentOwnedCountries() != null) {
					for (Countries countryEntry : continentEntry.getValue().getContinentOwnedCountries()) {
						InputCountry inputCountry = new InputCountry(countryEntry);
						InputBorders inputBorder = new InputBorders(countryEntry.getCountryId(), countryEntry.getCountryOwnedBorders(), 
								countryEntry.getContinentId(), countryEntry.getCountryName());
						updateBorder.add(inputBorder);
						updateCountry.add(inputCountry);

					}
				}
			}
			ConquestHeaderContinent headerContinent = new ConquestHeaderContinent();
			out.write(headerContinent);

			List<ConquestContinent> continentList = new ArrayList<>();
			for (InputContinent continent : updateContinent) {
				ConquestContinent convertConquest = new ConquestContinent(continent);
				continentList.add(convertConquest);
			}

			List<FormatConquestContinent> formatContinentList = new ArrayList<>();
			for (ConquestContinent continent : continentList) {
				FormatConquestContinent editedContinent = new FormatConquestContinent(continent);
				formatContinentList.add(editedContinent);

			}
			for (FormatConquestContinent continent : formatContinentList) {
				out.write(continent);
			}

			ConquestHeaderCountry headerCountry = new ConquestHeaderCountry();
			out.write(headerCountry);

			Collections.sort(updateBorder, new InputBorders());
			List<InputContinent> matchingInputContinentId = new MapReader().readContinentFile(p_typeOfMap,
					p_warZoneMap);
			List<InputCountry> matchingInputCountryId = new MapReader().readCountryMap(p_typeOfMap, p_warZoneMap);
			List<ConquestBorders> countryBorderList = new ArrayList<>();

			for (InputBorders borders : updateBorder) {
				Set<String> ignoreNull = borders.getAdjacentCountries();
				for (Iterator<String> iterator = ignoreNull.iterator(); iterator.hasNext();) {
					if (iterator.next().toString().isEmpty()) {
						iterator.remove();
					}
				}
				borders.setAdjacentCountries(ignoreNull);
				ConquestBorders convertConquest = new ConquestBorders(borders, matchingInputContinentId,
						matchingInputCountryId);
				countryBorderList.add(convertConquest);
			}
			NewLine line = new NewLine();
			for (ConquestContinent newLineContinent : continentList) {

				for (ConquestBorders borders : countryBorderList) {
					if (newLineContinent.getContinentName().equalsIgnoreCase(borders.getContinentName())) {
						FormatConquestBorders editedBorder = new FormatConquestBorders(borders);
						out.write(editedBorder);
					}
				}
				out.write(line);
			}
			out.flush();
			out.close();

		}
	}
}