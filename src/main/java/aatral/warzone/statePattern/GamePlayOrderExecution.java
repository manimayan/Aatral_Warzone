package aatral.warzone.statePattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.model.Continent;

public class GamePlayOrderExecution extends GamePlay {

	public GamePlayOrderExecution(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMap(String p_warZoneMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Continent> loadMap(String p_warZoneMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveMap(String p_mapEditorCommand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editMap(String p_mapEditorCommand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateMap(String p_warZoneMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gamePlayShowMap() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGamePlayer(String p_playerName, String p_option, ArrayList<String> p_playerObListTempAdd,
			List<String> p_playerList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeGamePlayer(boolean p_flag, String p_playerName, String p_option,
			List<String> l_playerObListTempRem, List<String> p_playerList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, GamePlayer> assignCountries(HashMap<String, GamePlayer> p_playerObjectList,
			List<String> p_playerList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignReinforcements(int p_armies, GamePlayer p_gamePlayerObject) {
		// TODO Auto-generated method stub
		
	}

}
