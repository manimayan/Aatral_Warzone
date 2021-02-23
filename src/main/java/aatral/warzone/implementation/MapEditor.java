package aatral.warzone.implementation;

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
	 * @param warZoneMap
	 */
	public void showMap(String warZoneMap) {
		ComposeGraph getGraph = new ComposeGraph();
		getGraph.printCountries(warZoneMap);
		getGraph.printBorders(warZoneMap);
	}

	/**
	 * saveMap method is called once the country given in editMap is not in the list
	 * 
	 * @param mapEditorCommand
	 * @param warZoneMap
	 */
	public void saveMap(String mapEditorCommand, String warZoneMap) {
		String getmapSaveCommand[] = mapEditorCommand.split(" ");
		if (mapEditorCommand.startsWith("savemap")) {
			String saveWarZoneMap = getmapSaveCommand[1];
			InputProcessor saveIp = new InputProcessor();
			List<String> saveFolder = saveIp.getstartupPhase();

			if (!saveFolder.contains(saveWarZoneMap)) {

			} else {
				System.out.println("The map already exists");
			}
		} else {
			System.out.println("Savemap command is invalid");
		}

	}

	/**
	 * editMap method is used to edit continent, neighbour, country based on user's
	 * input If entered country is not present, then it will add the country in the
	 * list and then it will edit
	 * 
	 * @param mapEditorCommand
	 */
	public void editMap(String mapEditorCommand) {
		String getEditmapName[] = mapEditorCommand.split(" ");
		String editWarZoneMap = getEditmapName[1];

		InputProcessor editIp = new InputProcessor();
		List<String> editfolder = editIp.getstartupPhase();

		if (editfolder.contains(editWarZoneMap)) {
			EditMap editMap = new EditMap();
			editMap.startEditMap(editWarZoneMap);
		} else {
			System.out.println("No such map exists, Please create one");
			System.out.println("Enter the below command to save a map\n Format: \n savemap filename");
			Scanner map = new Scanner(System.in);
			String mapSaveCommand = map.nextLine();
			saveMap(mapSaveCommand, editWarZoneMap);
		}
	}

	/**
	 * validateMap method is used to validate the whole map
	 * 
	 * @param warZoneMap
	 */
	public void validateMap(String warZoneMap) {
		ValidateMap validate = new ValidateMap();
		System.out.println("Validate - " + validate.validateFullMap(warZoneMap));
	}
}
