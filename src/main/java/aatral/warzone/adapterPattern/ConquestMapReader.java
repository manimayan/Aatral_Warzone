package aatral.warzone.adapterPattern;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;
import aatral.warzone.model.InputBorders;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;
import aatral.warzone.observerPattern.LogEntryBuffer;
import aatral.warzone.utilities.InputProcessor;
import aatral.warzone.utilities.MapReader;
/**
 * This class is to implement the conquest map reader
 * @author William Moses
 * @version 1.0
 * @since 2021-04-10
 * 
 */
public class ConquestMapReader {
/**
 * loadMap method is used to load the war zone map
 * @param p_typeOfMap : type of the map
 * @param p_warZoneMap : war zone map
 * @return master map 
 * 
 */
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
 * saveMap method is used to save the map using map editor command
 * @param p_typeOfMap : type of map
 * @param p_mapEditorCommand : Map editor command
 * @param log has a value of log
 */
		public void saveMap(String p_typeOfMap, String p_mapEditorCommand, LogEntryBuffer log) {
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

}
