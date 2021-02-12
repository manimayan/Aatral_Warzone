package aatral.warzone;

import java.util.InputMismatchException;
import java.util.Scanner;

import aatral.warzone.utilities.MapReader;

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
					MapReader mp = new MapReader();
					mp.readContinentFile();
					break;

				case 2:
					System.out.println("Save Map");
					break;

				case 3:
					boolean flag = true;
					while (flag) {
						System.out.println("\nChoose which one to edit \n1.Continent\n2.Country\n3.Neighbour\n4.None");
						int editOption = input.nextInt();
						switch (editOption) {

						case 1:
							System.out.println("Edit continent");
							flag = false;
							break;
						case 2:
							System.out.println("Edit Country");
							flag = false;
							break;
						case 3:
							System.out.println("Edit Neighbour");
							flag = false;
							break;
						case 4:
							System.out.println("None is selected");
							flag = false;
							break;
						default:
							System.out.println("Invalid option entered.Try again!!");
							break;
						}
					}
					break;

				case 4:
					System.out.println("Validate Map");
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
		input.close();
	}
}
