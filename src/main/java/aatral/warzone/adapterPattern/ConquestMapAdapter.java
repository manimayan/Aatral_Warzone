package aatral.warzone.adapterPattern;

import java.util.Map;

import aatral.warzone.model.Continent;
import aatral.warzone.observerPattern.LogEntryBuffer;

/**
 * <h1>Class to implement Adapter Pattern</h1>
 * 
 * @author Manimaran Palani
 * @version 1.0
 * @since 2021-04-10
 */

public class ConquestMapAdapter extends DominationMapReader {

	private ConquestMapReader conquestMap;

	/**
	 * Instantiates a new conquest map adapter.
	 * 
	 * @param conquestMap: the conquestMap
	 */
	public ConquestMapAdapter(ConquestMapReader conquestMap) {
		super();
		this.conquestMap = conquestMap;
	}

	/**
	 * LoadMap method is used to Load the map and convert into continent,countries
	 * and borders
	 * 
	 * @param typeOfMap  : type of map
	 * @param warZoneMap has war zone map
	 * @return masterMap
	 */
	@Override
	public Map<String, Continent> loadMap(String p_typeOfMap, String warZoneMap) {
		if (p_typeOfMap.equalsIgnoreCase("conquest")) {
			return conquestMap.loadMap(p_typeOfMap, warZoneMap);
		} if (p_typeOfMap.equalsIgnoreCase("domination")) {
			return super.loadMap(p_typeOfMap, warZoneMap);
		}
		return null;
	}

	/**
	 * saveMap is used to update the list of continents,countries,borders in map
	 * file
	 * 
	 * @param p_typeOfMap  : type of map
	 * @param p_mapEditorCommand has war map editor command
	 * @param log has value of log
	 */
	@Override
	public void saveMap(String p_typeOfMap, String p_mapEditorCommand, LogEntryBuffer log) {
		if (p_typeOfMap.equalsIgnoreCase("conquest")) {
			conquestMap.saveMap(p_typeOfMap, p_mapEditorCommand, log);
		} if (p_typeOfMap.equalsIgnoreCase("domination")) {
			super.saveMap(p_typeOfMap, p_mapEditorCommand, log);
		}
	}

}
