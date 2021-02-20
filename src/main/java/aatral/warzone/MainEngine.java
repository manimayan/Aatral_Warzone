package aatral.warzone;

import java.util.InputMismatchException;
import java.util.Scanner;

import aatral.warzone.implementation.ComposeGraph;
import aatral.warzone.utilities.EditMap;
import aatral.warzone.utilities.ValidateMap;

/**
 * <h1>Game Input Data </h1>
 * The Class get the user input to choose options to add game data and to validate the correctness of game data
 * @author  Tejaswini Reddy
 * @version 1.0
 * @since   2021-02-12
 */
public class MainEngine {

	/**
	 * Main method to get GameMap Data
	 * @param args Unused.
	 * @return Nothing.
	 */
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		boolean proceed = true;
		while (proceed) {

			System.out.println("\nChoose an option\n1.Show Map\n2.Save Map\n3.Edit Map\n4.Validate Map\n5.Exit");
			int option;
			try{
				option = input.nextInt();
				switch (option) {
				case 1:
					System.out.println("Show Map");
					ComposeGraph getGraph =  new ComposeGraph();
					getGraph.printCountries();
					getGraph.printBorders();
					break;
					
				case 2:
					System.out.println("Save Map");
					break;

				case 3:
					EditMap emEditMap = new EditMap();
					break;
				case 4:
					System.out.println("Validate Map");
					ValidateMap validate = new ValidateMap();
					System.out.println("Validate - "+validate.validateFullMap());
					break;

				case 5:
					proceed = false;
					break;

				default:
					System.out.println("Invalid option entered. Try again!!");
				}
			}
			catch(InputMismatchException e)
			{
				System.out.println("Ïnput must be an integer");
				input.nextLine();
			}
		}
		System.out.println("System terminated!");
		input.close();
	}
}
