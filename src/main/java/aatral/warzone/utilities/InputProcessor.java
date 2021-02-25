package aatral.warzone.utilities;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aatral.warzone.implementation.EditMap;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Country;

/**
 * <h1>Utility Class to process command line inputs</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-02-23
 */
public class InputProcessor {

	/**
	 * getAddContinentInput method is used to add the continent given by user's
	 * input into continent list
	 * 
	 * @param continentCommand
	 * @return inputContinentList
	 */
	public List<Continent> getAddContinentInput(String continentCommand) {
		String[] splitBySpace = continentCommand.split(" ");
		String[] yourArray = Arrays.copyOfRange(splitBySpace, 1, splitBySpace.length);
		StringBuilder sb = new StringBuilder();
		for (String s : yourArray) {
			sb.append(s);
			sb.append(" ");
		}
		String result = sb.toString();
		List<String> processedInput = new ArrayList<>(Arrays.asList(result.split(",")));
		System.out.println(processedInput);
		List<Continent> inputContinentList = new ArrayList<>();
		for (String processedInputIterate : processedInput) {

			String[] splitProcessedInput = processedInputIterate.split(" ");
			Continent inputContinent = new Continent(splitProcessedInput[0], splitProcessedInput[1]);
			inputContinentList.add(inputContinent);

		}
		return inputContinentList;
	}

	/**
	 * getDeleteContinentInput method is used to delete the continent based on
	 * user's input
	 * 
	 * @param editContinentCommand
	 * @return deleteContinentList
	 */
	public List<String> getDeleteContinentInput(String editContinentCommand) {
		String[] splitBySpace = editContinentCommand.split(" ");
		String[] yourArray = Arrays.copyOfRange(splitBySpace, 1, splitBySpace.length);
		StringBuilder sb = new StringBuilder();
		for (String s : yourArray) {
			sb.append(s);
		}
		String result = sb.toString();
		List<String> deleteContinentList = new ArrayList<>(Arrays.asList(result.split(",")));
		return deleteContinentList;
	}

	/**
	 * getAddCountryInput method is used to add user's input country to country list
	 * 
	 * @param editCountryCommand
	 * @return inputCountryList
	 */
	public List<Country> getAddCountryInput(String editCountryCommand) {
		String[] splitBySpace = editCountryCommand.split(" ");
		String[] yourArray = Arrays.copyOfRange(splitBySpace, 1, splitBySpace.length);
		StringBuilder sb = new StringBuilder();
		for (String s : yourArray) {
			sb.append(s);
			sb.append(" ");
		}
		String result = sb.toString();
		List<String> processedInput = new ArrayList<>(Arrays.asList(result.split(",")));
		System.out.println(processedInput);
		List<Country> inputCountryList = new ArrayList<>();
		for (String processedInputIterate : processedInput) {

			String[] splitProcessedInput = processedInputIterate.split(" ");
			Country inputCountry = new Country(splitProcessedInput[0], splitProcessedInput[1]);
			inputCountryList.add(inputCountry);

		}
		return inputCountryList;
	}

	/**
	 * 
	 * @param getRemoveCountryInput method is used to remove user's input country to
	 *                              country list
	 * @return null
	 */
	public List<Country> getremoveCountryInput(String editCountryCommand) {
		// yet to code
		return null;
	}

	/**
	 * getAddNeighbourInput method is used to parse the input command and creates
	 * the input to add the neighbour
	 * 
	 * @param warZoneMap
	 * @param editNeighborCommand
	 */
	public void getAddNeighborInput(String warZoneMap, String editNeighborCommand) {
		String[] splitBySpace = editNeighborCommand.split(" ");
		String[] yourArray = Arrays.copyOfRange(splitBySpace, 1, splitBySpace.length);
		StringBuilder sb = new StringBuilder();
		for (String s : yourArray) {
			sb.append(s);
			sb.append(" ");
		}
		String result = sb.toString();
		List<String> processedInput = new ArrayList<>(Arrays.asList(result.split(",")));
		System.out.println(processedInput);
		for (String processedInputIterate : processedInput) {

			String[] splitProcessedInput = processedInputIterate.split(" ");
			EditMap ed = new EditMap();
			ed.addNeighbours(warZoneMap, splitProcessedInput[0], splitProcessedInput[1]);
		}

	}

	/**
	 * getRemoveNeighbourInput method is used to parse the input command and creates
	 * the input to remove the neighbour
	 * 
	 * @param warZoneMap
	 * @param editNeighborCommand
	 */
	public void getremoveNeighborInput(String warZoneMap, String editNeighborCommand) {
		// yet to code
		new EditMap().removeNeighbours(warZoneMap);

	}

	/**
	 * getString method is used to parse the input command
	 * 
	 * @param editCommand
	 * @return result
	 */
	public String getString(String editCommand) {
		String[] splitBySpace = editCommand.split(" ");
		String[] yourArray = Arrays.copyOfRange(splitBySpace, 1, splitBySpace.length);
		StringBuilder sb = new StringBuilder();
		for (String s : yourArray) {
			sb.append(s);
			sb.append(" ");
		}
		String result = sb.toString();
		return result;
	}

	/**
	 * getstartupPhase method is used to identify the correct map which is loaded in
	 * the application
	 * 
	 * @return folder
	 */
	public List<String> getstartupPhase() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource("map");
		String path = url.getPath();
		File[] file = new File(path).listFiles();

		List<String> folder = new ArrayList<>();
		for (File directory : file) {
			String parentPath = directory.getAbsoluteFile().getName();
			folder.add(parentPath);
		}
		return folder;
	}
}
