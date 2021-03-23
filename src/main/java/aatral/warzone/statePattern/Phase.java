package aatral.warzone.statePattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.model.Continent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Phase {

	protected GameEngine gameEngine;

	// go to next phase
	public abstract void next();

	// map editor behavior
	public abstract void showMap(String p_warZoneMap);

	public abstract Map<String, Continent> loadMap(String p_warZoneMap);

	public abstract void saveMap(String p_mapEditorCommand);

	public abstract void editMap(String p_mapEditorCommand);

	public abstract void validateMap(String p_warZoneMap);

	// game play behavior
	public abstract void gamePlayShowMap();

	public abstract void addGamePlayer(String p_playerName, String p_option, ArrayList<String> p_playerObListTempAdd,
			List<String> p_playerList);

	public abstract void removeGamePlayer(boolean p_flag, String p_playerName, String p_option, List<String> l_playerObListTempRem,
			List<String> p_playerList);

	public abstract HashMap<String, GamePlayer> assignCountries(HashMap<String, GamePlayer> p_playerObjectList,
			List<String> p_playerList);

	public abstract void assignReinforcements(int p_armies, GamePlayer p_gamePlayerObject);

}
