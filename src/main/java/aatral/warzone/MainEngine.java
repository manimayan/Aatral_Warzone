package aatral.warzone;

import java.util.List;
import java.util.Scanner;

import aatral.warzone.implementation.MapEditor;
import aatral.warzone.observerPattern.LogEntryBuffer;
import aatral.warzone.observerPattern.LogWriter;
import aatral.warzone.utilities.InputProcessor;

/**
 * <h1>Game Input Data</h1> The Class get the user input to choose options to
 * add game data and to validate the correctness of game data
 * 
 * @author Tejaswini Reddy
 * @version 1.0
 * @since 2021-02-12
 */
public class MainEngine {

	/**
	 * Main method to get GameMap Data
	 * 
	 * @param args Unused. .
	 */
	public static void main(String[] args) {
		System.out.println("Welome to Warzone");
		boolean proceed = true;

		LogEntryBuffer log = new LogEntryBuffer();
		LogWriter logWriter = new LogWriter(log);

		// Main running block of game
		while (proceed) {

			MapEditor l_mapEditor = new MapEditor();
			// showing the available maps
			System.out.println("\nThe below are the available maps\n");
			InputProcessor l_ip = new InputProcessor();
			List<String> l_folder = l_ip.getstartupPhase();
			for (String l_folderName : l_folder) {
				System.out.println(l_folderName);
			}

			// type the map name to load
			System.out.println("\nPlease type in the map name to load the map or type " + "\"" + "newmap" + "\""
					+ " to create a Map");
			Scanner map = new Scanner(System.in);
			String l_warZoneMap = map.nextLine().toString();

			if (l_folder.contains(l_warZoneMap)) {
				l_mapEditor.loadMap(l_warZoneMap);
				// type the below commands to run map editor
				System.out.println("\nType the below command to edit the loaded map"
						+ "\n showmap \n savemap filename \n editmap filename \n validatemap \n loadmap filename");
				Scanner l_input = new Scanner(System.in);
				String mapEditorCommand = l_input.nextLine().trim();

				if (mapEditorCommand.startsWith("showmap")) {
					l_mapEditor.showMap(l_warZoneMap);
				} else if (mapEditorCommand.startsWith("savemap")) {
					l_mapEditor.saveMap(mapEditorCommand);
					proceed = false;
				} else if (mapEditorCommand.startsWith("editmap")) {
					l_mapEditor.editMap(mapEditorCommand);
				} else if (mapEditorCommand.startsWith("validatemap")) {
					l_mapEditor.validateMap(l_warZoneMap);
				} else if (mapEditorCommand.startsWith("loadmap")) {
					String l_warZoneMaps = mapEditorCommand.split(" ")[1];
					if (l_folder.contains(l_warZoneMaps)) {
						log.info("MapEditor", "\"loadmap " + l_warZoneMaps + "\"", l_warZoneMaps + " map loaded");
						GameEngine gameEngine = new GameEngine(l_warZoneMaps);
						gameEngine.gameUserMenu();

					} else {
						log.info("MapEditor", "\"loadmap " + l_warZoneMaps + "\"",
								"No " + l_warZoneMaps + " map exists");
						System.out.println("No such map exists, Please create a new one");
					}
				} else {
					log.info("MapEditor", mapEditorCommand, "Invalid Command");
					System.out.println("Invalid command");
					System.out.println("Editor closed");
				}
			} else if (l_warZoneMap.startsWith("newmap")) {
				System.out.println("Enter the below command to save a map\n Format: \n savemap filename");
				Scanner saveMap = new Scanner(System.in);
				String mapSaveCommand = saveMap.nextLine();
				if (mapSaveCommand.startsWith("savemap")) {
					l_mapEditor.saveMap(mapSaveCommand);
					proceed = false;
				} else {
					log.info("MapEditor", l_warZoneMap, "Invalid Command");
					System.out.println("Invalid command");
					proceed = false;
					System.out.println("Editor closed");
				}
			} else {
				log.info("MapEditor", l_warZoneMap, "No " + l_warZoneMap + " map exists");
				System.out.println("No such map exists");
			}

		}
	}
}