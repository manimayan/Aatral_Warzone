package aatral.warzone.implementation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.springframework.util.StringUtils;

import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;
import aatral.warzone.model.InputBorders;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
import aatral.warzone.observerPattern.LogEntryBuffer;
import aatral.warzone.observerPattern.LogWriter;
import aatral.warzone.utilities.ContinentMapReader;
import aatral.warzone.utilities.CountryBorderReader;
import aatral.warzone.utilities.CountryMapreader;
import aatral.warzone.utilities.InputProcessor;

/**
 * <h1>MapEditor has template methods to edit the selected map</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-23
 */
public class MapEditor {

	LogEntryBuffer log = new LogEntryBuffer();
	LogWriter logWriter = new LogWriter(log);


	/**
	 * showMap method is used to print the countries and borders
	 * 
	 * @param warZoneMap map of warzone.
	 */
	public void showMap(String warZoneMap) {
		log.info("MapEditor", "\"showmap "+warZoneMap+"\"", warZoneMap+" map has loaded and displayed");
		Map<String, Continent> getMasterMap = loadMap(warZoneMap);
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
	 * @param p_warZoneMap
	 * @return masterMap 
	 */
	public Map<String, Continent> loadMap(String p_warZoneMap) {

		List<InputContinent> l_inputContinentList = new ContinentMapReader().readContinentFile(p_warZoneMap);
		List<InputCountry> l_inputCountryList = new CountryMapreader().readCountryMap(p_warZoneMap);
		List<InputBorders> l_inputBordersList = new CountryBorderReader().mapCountryBorderReader(p_warZoneMap);

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
			Continent addToMaster = new Continent(l_continent, continentOwnedCountries);
			masterMap.put(l_continent.getContinentId(), addToMaster);
		}
		return masterMap;
	}


	/**
	 * saveMap method is called once the country given in editMap is not in the list
	 * 
	 * @param mapEditorCommand command for map editor.

	 */
	public void saveMap(String mapEditorCommand) {
		String getmapSaveCommand[] = mapEditorCommand.split(" ");
		if (mapEditorCommand.startsWith("savemap")) {
			String saveWarZoneMap = getmapSaveCommand[1];
			InputProcessor saveIp = new InputProcessor();
			List<String> saveFolder = saveIp.getstartupPhase();

			if (!saveFolder.contains(saveWarZoneMap)) {
				//Externalize the property later by converting into spring application
				String mapUrl = "C:\\Users\\manimaran.palani\\git\\Aatral-Warzone\\src\\main\\resources\\source\\"
						+ saveWarZoneMap;
				String resourceFolder = "C:\\Users\\manimaran.palani\\git\\Aatral-Warzone\\src\\main\\resources\\";
				Path path = Paths.get(mapUrl);
				try {
					Files.createDirectory(path);
					List<String> newMapFiles = new ArrayList<>();
					newMapFiles.add(mapUrl+"\\"+saveWarZoneMap+"-continents.txt");
					newMapFiles.add(mapUrl+"\\"+saveWarZoneMap+"-countries.txt");
					newMapFiles.add(mapUrl+"\\"+saveWarZoneMap+"-borders1.txt");
					newMapFiles.add(resourceFolder + saveWarZoneMap + ".map");
					for (String newFiles : newMapFiles) {
						Path newFilePath = Paths.get(newFiles);
						Files.createFile(newFilePath);
					}
					log.info("MapEditor", "\"savemap "+saveWarZoneMap+"\"", saveWarZoneMap+" map saved successfully");
				} catch (IOException e) {
				}
				System.out.println("Map created");
			} else {
				log.info("MapEditor", "\"savemap "+saveWarZoneMap+"\"", saveWarZoneMap+ " map already exists");
				System.out.println("map already exists");
			}
		}
	}

	/**
	 * editMap method is used to edit continent, neighbour, country based on user's
	 * input If entered country is not present, then it will add the country in the
	 * list and then it will edit
	 * 
	 * @param mapEditorCommand command for map edit.
	 */
	public void editMap(String mapEditorCommand) {
		String getEditmapName[] = mapEditorCommand.split(" ");
		String editWarZoneMap = getEditmapName[1];
		log.info("MapEditor", "\"editmap "+editWarZoneMap+"\"", editWarZoneMap+" map editing has started");
		InputProcessor editIp = new InputProcessor();
		List<String> editfolder = editIp.getstartupPhase();

		if (editfolder.contains(editWarZoneMap)) {
			EditMap editMap = new EditMap(editWarZoneMap);
			editMap.startEditMap(editWarZoneMap);
		} else {
			log.info("MapEditor", "\"editmap "+editWarZoneMap+"\"", "No "+editWarZoneMap+" map exists, Please create one");
			System.out.println("No such map exists, Please create one");
			System.out.println("Enter the below command to save a map\n Format: \n savemap filename");
			Scanner map = new Scanner(System.in);
			String mapSaveCommand = map.nextLine();
			saveMap(mapSaveCommand);
		}
	}

	/**
	 * validateMap method is used to validate the whole map
	 * 
	 * @param warZoneMap map of warzone.
	 */
	public void validateMap(String warZoneMap) {
		///invalid map
		ValidateMap validate = new ValidateMap();
		if(validate.validateFullMap(warZoneMap)){
			System.out.println("\n"+warZoneMap+ " map is valid");
			log.info("MapEditor", "\"loadMap"+warZoneMap+"\"", warZoneMap+" map is valid");
		}
	}
}
