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

	public List<Country> getremoveCountryInput(String editCountryCommand) {
		// yet to code
		return null;
	}

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

	public void getremoveNeighborInput(String warZoneMap, String editNeighborCommand) {
		// yet to code
		new EditMap().removeNeighbours(warZoneMap);

	}

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


