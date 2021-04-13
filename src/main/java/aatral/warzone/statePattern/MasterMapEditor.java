package aatral.warzone.statePattern;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.springframework.util.StringUtils;

import aatral.warzone.adapterPattern.ConquestMapAdapter;
import aatral.warzone.adapterPattern.ConquestMapReader;
import aatral.warzone.adapterPattern.DominationMapReader;
import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.mapeditor.EditCommandsImpl;
import aatral.warzone.mapeditor.ValidateMapImpl;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;
import aatral.warzone.model.InputBorders;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
import aatral.warzone.observerPattern.LogEntryBuffer;
import aatral.warzone.observerPattern.LogWriter;
import aatral.warzone.utilities.InputProcessor;
import aatral.warzone.utilities.MapReader;
import lombok.NoArgsConstructor;

/**
 * <h1>MapEditor has template methods to edit the selected map</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-23
 */
@NoArgsConstructor
public class MasterMapEditor extends MapEditor {

	LogEntryBuffer log = new LogEntryBuffer();
	LogWriter logWriter = new LogWriter(log);

	private DominationMapReader mapAdapter=new ConquestMapAdapter(new ConquestMapReader());
	
	public MasterMapEditor(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	/**
	 * showMap method is used to print the countries and borders
	 * 
	 * @param typeOfMap  : type of map
	 * @param p_warZoneMap map of warzone.
	 */
	@Override
	public void showMap(String typeOfMap, String p_warZoneMap) {
		log.info("MapEditor", "\"showmap " + p_warZoneMap + "\"", p_warZoneMap + " map has loaded and displayed");
		Map<String, Continent> getMasterMap = loadMap(typeOfMap, p_warZoneMap);
		System.out.println("\nContinent and its Countries\n");
		for (Entry<String, Continent> ContinentEntry : getMasterMap.entrySet()) {
			System.out.println("ContinentId: " + ContinentEntry.getValue().getContinentId() + ", ContinentName: "
					+ ContinentEntry.getValue().getContinentName() + "-->");
			for (Countries printCountries : ContinentEntry.getValue().getContinentOwnedCountries()) {
				System.out.println("CountryId: " + printCountries.getCountryId() + ", CountryName: "
						+ printCountries.getCountryName());

			}
			System.out.println("\n");
		}

		System.out.println("\nCountries and its Borders\n");
		for (Entry<String, Continent> ContinentEntry : getMasterMap.entrySet()) {
			for (Countries printCountries : ContinentEntry.getValue().getContinentOwnedCountries()) {
				System.out.println("\nCountryId: " + printCountries.getCountryId() + ", CountryName: "
						+ printCountries.getCountryName() + "--->");
				System.out.println(
						StringUtils.collectionToDelimitedString(printCountries.getCountryOwnedBorders(), ", "));
			}
		}
	}

	/**
	 * LoadMap method is used to Load the map and convert into continent,countries
	 * and borders
	 * 
	 * @param p_typeOfMap  : type of map
	 * @param p_warZoneMap has war zone map
	 * @return masterMap
	 */
	@Override
	public Map<String, Continent> loadMap(String p_typeOfMap, String p_warZoneMap) {
		MapReader mapReader = new MapReader();
		List<InputContinent> l_inputContinentList = mapReader.readContinentFile(p_typeOfMap, p_warZoneMap);
		List<InputCountry> l_inputCountryList = mapReader.readCountryMap(p_typeOfMap, p_warZoneMap);
		List<InputBorders> l_inputBordersList = mapReader.mapCountryBorderReader(p_typeOfMap, p_warZoneMap);

		Map<String, Continent> masterMap = new HashMap<>();
		for (InputContinent l_continent : l_inputContinentList) {

			Set<Countries> continentOwnedCountries = new HashSet<>();
			for (InputCountry l_Country : l_inputCountryList) {
				if (l_continent.getContinentId().equals(l_Country.getContinentId())) {

					Set<String> l_countryOwnedBorders = new HashSet<>();
					for (InputBorders l_Borders : l_inputBordersList) {
						if (l_Borders.getCountryId().equals(l_Country.getCountryId())) {
							l_countryOwnedBorders.addAll(l_Borders.getAdjacentCountries());
						}

					}
					Countries addtToCountrySet = new Countries(l_Country, l_countryOwnedBorders);
					continentOwnedCountries.add(addtToCountrySet);

				}

			}
			List<Countries> sort = new ArrayList<>(continentOwnedCountries);
			Continent addToMaster = new Continent(l_continent, sort);
			masterMap.put(l_continent.getContinentId(), addToMaster);
		}
		return masterMap;
	}

	/**
	 * saveMap method is called once the country given in editMap is not in the list
	 * 
	 * @param p_typeOfMap        : type of map
	 * @param p_mapEditorCommand command for map editor.
	 * 
	 */
	@Override
	public void saveMap(String p_typeOfMap, String p_mapEditorCommand) {
		String getmapSaveCommand[] = p_mapEditorCommand.split(" ");
		if (p_mapEditorCommand.startsWith("savemap")) {
			String saveWarZoneMap = getmapSaveCommand[1];
			InputProcessor saveIp = new InputProcessor();
			List<String> saveFolder = saveIp.getstartupPhase(p_typeOfMap);

			if (!saveFolder.contains(saveWarZoneMap)) {
				// Externalize the property later by converting into spring application
				String mapUrl = "C:\\Users\\manimaran.palani\\git\\Aatral-Warzone\\src\\main\\resources\\" + p_typeOfMap
						+ "\\" + saveWarZoneMap;
				String resourceFolder = "C:\\Users\\manimaran.palani\\git\\Aatral-Warzone\\src\\main\\resources\\";
				Path path = Paths.get(mapUrl);
				try {
					Files.createDirectory(path);
					List<String> newMapFiles = new ArrayList<>();
					newMapFiles.add(mapUrl + "\\" + saveWarZoneMap + "-continents.txt");
					newMapFiles.add(mapUrl + "\\" + saveWarZoneMap + "-countries.txt");
					newMapFiles.add(mapUrl + "\\" + saveWarZoneMap + "-borders1.txt");
					newMapFiles.add(resourceFolder + p_typeOfMap + "-" + saveWarZoneMap + ".map");
					for (String newFiles : newMapFiles) {
						Path newFilePath = Paths.get(newFiles);
						Files.createFile(newFilePath);
					}
					log.info("MapEditor", "\"savemap " + saveWarZoneMap + "\"",
							saveWarZoneMap + " map saved successfully");
				} catch (IOException e) {
				}
				System.out.println("Map created");
			} else {
				log.info("MapEditor", "\"savemap " + saveWarZoneMap + "\"", saveWarZoneMap + " map already exists");
				System.out.println("map already exists");
			}
		}
	}

	/**
	 * editMap method is used to edit continent, neighbor, country based on user's
	 * input If entered country is not present, then it will add the country in the
	 * list and then it will edit
	 * 
	 * @param p_typeOfMap        : type of map
	 * @param p_mapEditorCommand command for map edit.
	 */
	@Override
	public void editMap(String p_typeOfMap, String p_mapEditorCommand) {
		String getEditmapName[] = p_mapEditorCommand.split(" ");
		String editWarZoneMap = getEditmapName[1];
		log.info("MapEditor", "\"editmap " + editWarZoneMap + "\"", editWarZoneMap + " map editing has started");
		InputProcessor editIp = new InputProcessor();
		List<String> editfolder = editIp.getstartupPhase(p_typeOfMap);

		if (editfolder.contains(editWarZoneMap)) {
			EditCommandsImpl editMap = new EditCommandsImpl(p_typeOfMap, editWarZoneMap);
			editMap.startEditMap(p_typeOfMap, editWarZoneMap);
		} else {
			log.info("MapEditor", "\"editmap " + editWarZoneMap + "\"",
					"No " + editWarZoneMap + " map exists, Please create one");
			System.out.println("No such map exists, Please create one");
			System.out.println("Enter the below command to save a map\n Format: \n savemap filename");
			Scanner map = new Scanner(System.in);
			String mapSaveCommand = map.nextLine();
			System.out.println("\nPlease type the variant of map to save");
			System.out.println("Type any of the below Variants : "+ "\""+"conquest"+"\""+" or "+"\""+"domination"+"\"");
			Scanner l_type = new Scanner(System.in);
			String l_typeOfMap = l_type.nextLine().toString();
			mapAdapter.saveMap(l_typeOfMap, mapSaveCommand, log);
		}
	}

	/**
	 * validateMap method is used to validate the whole map
	 * 
	 * @param p_typeOfMap  : type of map
	 * @param p_warZoneMap map of warzone.
	 */
	@Override
	public void validateMap(String p_typeOfMap, String p_warZoneMap) {
		/// invalid map
		ValidateMapImpl validate = new ValidateMapImpl();
		if (validate.validateFullMap(p_typeOfMap, p_warZoneMap)) {
			System.out.println("\n" + p_warZoneMap + " map is valid");
			log.info("MapEditor", "\"loadMap" + p_warZoneMap + "\"", p_warZoneMap + " map is valid");
		}
	}

	@Override
	public void next() {
		gameEngine.setPhase(new GamePlayStartUp(gameEngine));
	}

	@Override
	public void gamePlayShowMap() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addGamePlayer(String p_playerName, ArrayList<String> p_playerObListTempAdd, List<String> p_playerList) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeGamePlayer(boolean p_flag, String p_playerName, List<String> l_playerObListTempRem,
			List<String> p_playerList) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HashMap<String, GamePlayer> assignCountries(List<String> p_playerList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignReinforcements(int p_armies) {
		// TODO Auto-generated method stub

	}

	@Override
	public void issueOrders() {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeOrders() {
		// TODO Auto-generated method stub

	}
}
