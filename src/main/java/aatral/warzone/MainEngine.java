package aatral.warzone;

import java.util.List;
import java.util.Scanner;

import aatral.warzone.implementation.EditMap;
import aatral.warzone.implementation.MapEditor;
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
	 * @param args Unused.
	 * @return Nothing.
	 */
	public static void main(String[] args) {
		System.out.println("Welome to Warzone");
		boolean proceed = true;

		// Main running block of game
		while (proceed) {

			MapEditor mapEditor = new MapEditor();
			// showing the available maps
			System.out.println("\nThe below are the available maps\n");
			InputProcessor l_ip = new InputProcessor();
			List<String> l_folder = l_ip.getstartupPhase();
			for (String l_folderName : l_folder) {
				System.out.println(l_folderName);
			}

			// type the map name to load
			System.out.println("\nPlease type in the map name to load the map");
			Scanner map = new Scanner(System.in);
			String warZoneMap = map.nextLine().toString();

			if (l_folder.contains(warZoneMap)) {
				// type the below commands to run map editor
				System.out.println("\nType the below command to edit the loaded map"
						+ "\n showmap \n savemap filename \n editmap filename \n validatemap \n loadmap filename");
				Scanner l_input = new Scanner(System.in);
				String mapEditorCommand = l_input.nextLine().trim();

				if (mapEditorCommand.startsWith("showmap")) {
					mapEditor.showMap(warZoneMap);

				} else if (mapEditorCommand.startsWith("savemap")) {
					mapEditor.saveMap(mapEditorCommand, warZoneMap);

				} else if (mapEditorCommand.startsWith("editmap")) {
					mapEditor.editMap(mapEditorCommand);

				} else if (mapEditorCommand.startsWith("validatemap")) {
					mapEditor.validateMap(warZoneMap);
				} else if (mapEditorCommand.startsWith("loadmap")) {
					String l_warZoneMap = mapEditorCommand.split(" ")[1];
					if (l_folder.contains(l_warZoneMap)) {
						GameEngine gameEngine = new GameEngine(l_warZoneMap);
						gameEngine.gameUserMenu();
					} else {
						System.out.println("No such map exists, Please create a new one");
					}
				} else {
					System.out.println("Invalid command");
					proceed = false;
					System.out.println("Editor closed");
				}
			} else {
				System.out.println("No such map exists");
				proceed = false;
				System.out.println("Editor closed");
			}

		}
	}
}