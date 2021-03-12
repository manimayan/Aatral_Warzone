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
	public Continent getAddContinentInput(String continentCommand) 
	{
	 
	 String arrSpaceSplit[] = continentCommand.split(" ");
	 
	 Continent inputContinent = new Continent(arrSpaceSplit[1], arrSpaceSplit[2]);
	 
	 return inputContinent;
	 
	}	

//	/**
//	 * getDeleteContinentInput method is used to delete the continent based on
//	 * user's input
//	 * 
//	 * @param editContinentCommand
//	 * @return deleteContinentList
//	 */
//	public List<String> getDeleteContinentInput(String editContinentCommand) {
//		String[] splitBySpace = editContinentCommand.split(" ");
//		String[] yourArray = Arrays.copyOfRange(splitBySpace, 1, splitBySpace.length);
//		StringBuilder sb = new StringBuilder();
//		for (String s : yourArray) {
//			sb.append(s);
//		}
//		String result = sb.toString();
//		List<String> deleteContinentList = new ArrayList<>(Arrays.asList(result.split(",")));
//		return deleteContinentList;
//	}

	/**
	 * getAddCountryInput method is used to add user's input country to country list
	 * 
	 * @param editCountryCommand
	 * @return inputCountryList
	 */
	public Country getAddCountryInput(String editCountryCommand) {
		
		 String arrSpaceSplit[] = editCountryCommand.split(" ");
		 
		 Country inputCountry = new Country(arrSpaceSplit[1], arrSpaceSplit[2]);
		 
		 return inputCountry;
		
	}

//	/**
//	 * 
//	 * @param getRemoveCountryInput method is used to remove user's input country to
//	 *                              country list
//	 * @return null
//	 */
//	public List<String> getremoveCountryInput(String editCountryCommand) {
//		String[] splitBySpace = editCountryCommand.split(" ");
//		String[] yourArray = Arrays.copyOfRange(splitBySpace, 1, splitBySpace.length);
//		StringBuilder sb = new StringBuilder();
//		for (String s : yourArray) {
//			sb.append(s);
//			sb.append(" ");
//		}
//		String result = sb.toString();
//		List<String> processedInput = new ArrayList<>(Arrays.asList(result.split(",")));
//		
//		return processedInput;
//	}

//	/**
//	 * getAddNeighbourInput method is used to parse the input command and creates
//	 * the input to add the neighbour
//	 * 
//	 * @param warZoneMap
//	 * @param editNeighborCommand
//	 */
//	public void getAddNeighborInput(String warZoneMap, String editNeighborCommand) {
//		String[] splitBySpace = editNeighborCommand.split(" ");
//		String[] yourArray = Arrays.copyOfRange(splitBySpace, 1, splitBySpace.length);
//		StringBuilder sb = new StringBuilder();
//		for (String s : yourArray) {
//			sb.append(s);
//			sb.append(" ");
//		}
//		String result = sb.toString();
//		List<String> processedInput = new ArrayList<>(Arrays.asList(result.split(",")));
//
//		for (String processedInputIterate : processedInput) {
//
//			String[] splitProcessedInput = processedInputIterate.split(" ");
//			EditMap ed = new EditMap(warZoneMap);
//			ed.addNeighbours(warZoneMap, splitProcessedInput[0], splitProcessedInput[1]);
//		}
//
//	}

//	/**
//	 * getRemoveNeighbourInput method is used to parse the input command and creates
//	 * the input to remove the neighbor
//	 * 
//	 * @param warZoneMap
//	 * @param editNeighborCommand
//	 */
//	public List<String> getremoveNeighborInput(String warZoneMap, String editNeighborCommand) {
//		String[] splitBySpace = editNeighborCommand.split(" ");
//		String[] yourArray = Arrays.copyOfRange(splitBySpace, 1, splitBySpace.length);
//		StringBuilder sb = new StringBuilder();
//		for (String s : yourArray) {
//			sb.append(s);
//			sb.append(" ");
//		}
//		String result = sb.toString();
//		List<String> processedInput = new ArrayList<>(Arrays.asList(result.split(",")));
//		return processedInput;
//	}

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
