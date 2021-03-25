package aatral.warzone.statePattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import aatral.warzone.gameplay.AdvanceOrder;
import aatral.warzone.gameplay.DeployOrder;
import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;
import aatral.warzone.model.Continent;

public class GamePlayOrderExecution extends GamePlay {

	public GamePlayOrderExecution(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

	@Override
	public void next() {
		gameEngine.setPhase(new GamePlayStartUp(gameEngine));
		System.out.println(gameEngine.getGamePhase());
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
	public void addGamePlayer(String p_playerName, ArrayList<String> p_playerObListTempAdd,
			List<String> p_playerList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeGamePlayer(boolean p_flag, String p_playerName, 
			List<String> l_playerObListTempRem, List<String> p_playerList) {
		// TODO Auto-generated method stub
		return false;
		
	}

	@Override
	public HashMap<String, GamePlayer> assignCountries(
			List<String> p_playerList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignReinforcements(int p_armiest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void issueOrders() {
		// TODO Auto-generated method stub
		
	}
	
	public void executeOrders() {
		boolean ordersExist = true;
		while(ordersExist) {
			ordersExist = false;
			for (Map.Entry l_gameplayObject : gameEngine.l_playerObjectList.entrySet()) {
				gameEngine.l_gamePlayerObject = (GamePlayer) l_gameplayObject.getValue();
				if(gameEngine.l_gamePlayerObject.orderObjects.size() >0) {
					Order orderObj = gameEngine.l_gamePlayerObject.NextOrder();
					if(orderObj instanceof DeployOrder)
					{
						System.out.println("\n\nExecueting Deployment Order for the player " + gameEngine.l_gamePlayerObject.getPlayerName());
						DeployOrder deployOrderObj = (DeployOrder)orderObj;
						deployOrderObj.gamePlayerObject =  gameEngine.l_gamePlayerObject;
						deployOrderObj.execute();
						ordersExist = true;
						System.out.println();
					} else {
						gameEngine.l_gamePlayerObject.pushBackOrder(orderObj);
						continue;
					}
				}		
			}
		}
		ordersExist = true;
		while(ordersExist) {
			ordersExist = false;
			for (Map.Entry l_gameplayObject : gameEngine.l_playerObjectList.entrySet()) {
				gameEngine.l_gamePlayerObject = (GamePlayer) l_gameplayObject.getValue();
				if(gameEngine.l_gamePlayerObject.orderObjects.size() >0) {
					Order orderObj = gameEngine.l_gamePlayerObject.NextOrder();
					if(orderObj instanceof AdvanceOrder)
					{
						System.out.println("\n\nExecueting Advance Order for the player " + gameEngine.l_gamePlayerObject.getPlayerName());
						AdvanceOrder advanceOrderObj = (AdvanceOrder)orderObj;
						advanceOrderObj.gamePlayerObject =  gameEngine.l_gamePlayerObject;
						advanceOrderObj.playerObjectList =  gameEngine.l_playerObjectList;
						advanceOrderObj.execute();
						ordersExist = true;
						System.out.println();
					} else {
						gameEngine.l_gamePlayerObject.pushBackOrder(orderObj);
					}
				}		
			}
			if(gameEngine.l_gamePlayerObject.hasConqueredInTurn) {
				int randomCardValue = new Random().nextInt(4);
				GamePlayer object = gameEngine.l_gamePlayerObject;
				List<String> stringCardNames = (List<String>) object.getSpecialCards().keySet();
				object.getSpecialCards().replace(stringCardNames.get(randomCardValue), 
						object.getSpecialCards().get(stringCardNames.get(randomCardValue))+1);
			}
		}
	}

}
