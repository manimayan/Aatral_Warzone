package aatral.warzone.implementation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aatral.warzone.utilities.InputProcessor;

/**
 * <h1>MapEditor has template methods to edit the selected map</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-23
 */
public class MapEditor {

	/**
	 * showMap method is used to print the countries and borders
	 * 
	 * @param warZoneMap map of warzone.
	 */
	public void showMap(String warZoneMap) {
		ComposeGraph getGraph = new ComposeGraph();
		getGraph.printCountries(warZoneMap);
		getGraph.printBorders(warZoneMap);
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
				String mapUrl = "C:\\Users\\manimaran.palani\\git\\Aatral-Warzone\\src\\main\\resources\\map\\"
						+ saveWarZoneMap;
				Path path = Paths.get(mapUrl);
				try {
					Files.createDirectory(path);
					List<String> newMapFiles = new ArrayList<>();
					newMapFiles.add(mapUrl+"\\"+saveWarZoneMap+"-continents.txt");
					newMapFiles.add(mapUrl+"\\"+saveWarZoneMap+"-countries.txt");
					newMapFiles.add(mapUrl+"\\"+saveWarZoneMap+"-borders1.txt");
					for (String newFiles : newMapFiles) {
					    Path newFilePath = Paths.get(newFiles);
					    Files.createFile(newFilePath);
					}
				
				} catch (IOException e) {
				}
				System.out.println("Map created");
			} else {
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

		InputProcessor editIp = new InputProcessor();
		List<String> editfolder = editIp.getstartupPhase();

		if (editfolder.contains(editWarZoneMap)) {
			EditMap editMap = new EditMap(editWarZoneMap);
			editMap.startEditMap(editWarZoneMap);
		} else {
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
		ValidateMap validate = new ValidateMap();
		if(validate.validateFullMap(warZoneMap)){
			System.out.println("\n"+warZoneMap+ " map is valid");
		}
	}
}
