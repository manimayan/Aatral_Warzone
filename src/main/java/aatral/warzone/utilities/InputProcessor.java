package aatral.warzone.utilities;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aatral.warzone.mapeditor.EditCommandsImpl;
import aatral.warzone.model.Countries;
import aatral.warzone.model.InputContinent;
import aatral.warzone.model.InputCountry;

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
	 * @param continentCommand command for continent.
	 * @return continent object.
	 */
	public InputContinent getAddContinentInput(String continentCommand) 
	{
	 
	 String arrSpaceSplit[] = continentCommand.split(" ");
	 
	 InputContinent inputContinent = new InputContinent(arrSpaceSplit[1], arrSpaceSplit[2]);
	 
	 return inputContinent;
	 
	}	


	/**
	 * getAddCountryInput method is used to add user's input country to country list
	 * 
	 * @param editCountryCommand command for edit country.
	 * @return country input list.
	 */
	public Countries getAddCountryInput(String editCountryCommand) {
		
		 String arrSpaceSplit[] = editCountryCommand.split(" ");
		 
		 Countries inputCountry = new Countries(arrSpaceSplit[1], arrSpaceSplit[2]);
		 
		 return inputCountry;
		
	}


	/**
	 * getString method is used to parse the input command
	 * 
	 * @param editCommand command for edit.
	 * @return get string result.
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
	 * @param l_typeOfMap: type of map
	 * 
	 * @return folder
	 */
	public List<String> getstartupPhase(String l_typeOfMap) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource(l_typeOfMap);
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
