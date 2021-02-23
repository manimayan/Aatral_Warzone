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
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * 
	 * @param map
	 * 
	 * @return
	 */
	public void showMap(String warZoneMap) {
		ComposeGraph getGraph = new ComposeGraph();
		getGraph.printCountries(warZoneMap);
		getGraph.printBorders(warZoneMap);
	}

	/**
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * 
	 * @param map
	 * 
	 * @return
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
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * 
	 * @param map
	 * 
	 * @return
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
			System.out.println("No such map exists, PLease create one");
			System.out.println("Enter the below command to save a map\n Format: \n savemap filename");
			Scanner map = new Scanner(System.in);
			String mapSaveCommand = map.nextLine();
			saveMap(mapSaveCommand, editWarZoneMap);
		}
	}

	/**
	 * printBorders method is used to print list of countries and its borders in the
	 * console
	 * 
	 * @param map
	 * 
	 * @return
	 */
	public void validateMap(String warZoneMap) {
		ValidateMap validate = new ValidateMap();
		System.out.println("Validate - " + validate.validateFullMap(warZoneMap));
	}
}
