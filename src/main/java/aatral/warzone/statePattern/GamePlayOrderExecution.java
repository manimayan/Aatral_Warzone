package aatral.warzone.statePattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import aatral.warzone.gameplay.AdvanceOrder;
import aatral.warzone.gameplay.AirliftCard;
import aatral.warzone.gameplay.BlockadeCard;
import aatral.warzone.gameplay.BombCard;
import aatral.warzone.gameplay.DeployOrder;
import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.NegotiateCard;
import aatral.warzone.gameplay.Order;
import aatral.warzone.model.Continent;



/**
 * <h1>GamePlayOrderExecution</h1> This abstract class implements the state pattern for game play order execution
 *
 * @author Tejeswini
 * @version 1.0
 * @since 24-02-2021
 */
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
	public void showMap(String p_typeOfMap, String p_warZoneMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Continent> loadMap(String p_typeOfMap, String p_warZoneMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveMap(String p_typeOfMap, String p_mapEditorCommand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editMap(String typeOfMap, String p_mapEditorCommand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateMap(String typeOfMap, String p_warZoneMap) {
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
	public HashMap<String, GamePlayer> assignCountries(HashMap<String, GamePlayer> p_playerObjectList, 
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
/**
 * executeOrders method is used to execute the game play
 */
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
					if(orderObj instanceof AdvanceOrder){
						System.out.println("\n\nExecueting Advance Order for the player " + gameEngine.l_gamePlayerObject.getPlayerName());
						AdvanceOrder advanceOrderObj = (AdvanceOrder)orderObj;
						advanceOrderObj.gamePlayerObject =  gameEngine.l_gamePlayerObject;
						advanceOrderObj.playerObjectList =  gameEngine.l_playerObjectList;
						advanceOrderObj.execute();
						ordersExist = true;
						System.out.println();
					}else if(orderObj instanceof BombCard){
						System.out.println("\n\nExecueting Special Order for the player " + gameEngine.l_gamePlayerObject.getPlayerName());
						BombCard bombObj = (BombCard)orderObj;
						bombObj.gamePlayerObject =  gameEngine.l_gamePlayerObject;
						bombObj.playerObjectList =  gameEngine.l_playerObjectList;
						bombObj.execute();
						ordersExist = true;
						System.out.println();
					} else if(orderObj instanceof BlockadeCard) {
						System.out.println("\n\nExecueting Special Order for the player " + gameEngine.l_gamePlayerObject.getPlayerName());
						BlockadeCard blockObj = (BlockadeCard)orderObj;
						blockObj.gamePlayerObject =  gameEngine.l_gamePlayerObject;
						blockObj.playerObjectList =  gameEngine.l_playerObjectList;
						blockObj.execute();
						ordersExist = true;
						System.out.println();
					} else if(orderObj instanceof AirliftCard) {
						System.out.println("\n\nExecueting Special Order for the player " + gameEngine.l_gamePlayerObject.getPlayerName());
						AirliftCard airliftObj = (AirliftCard)orderObj;
						airliftObj.gamePlayerObject =  gameEngine.l_gamePlayerObject;
						airliftObj.execute();
						ordersExist = true;
						System.out.println();
					} else if(orderObj instanceof NegotiateCard) {
						System.out.println("\n\nExecueting Special Order for the player " + gameEngine.l_gamePlayerObject.getPlayerName());
						NegotiateCard negotiateObj = (NegotiateCard)orderObj;
						negotiateObj.gamePlayerObject =  gameEngine.l_gamePlayerObject;
						negotiateObj.gamePlayerObjectList =  gameEngine.l_playerObjectList;
						negotiateObj.execute();
						ordersExist = true;
						System.out.println();
					} else{
						gameEngine.l_gamePlayerObject.pushBackOrder(orderObj);
					}
				}	
				if(gameEngine.checkIfPlayerHasWon()) {
					System.out.println("Hurray!!! PLAYER "+gameEngine.l_gamePlayerObject.getPlayerName()+" has won the game.");
					gameEngine.showMapPlayer(gameEngine.l_gamePlayerObject);
					System.out.println("Game is Closing !");
					return;
				}
			}
		}
		for (Map.Entry l_gameplayObject : gameEngine.l_playerObjectList.entrySet()) {
			gameEngine.l_gamePlayerObject = (GamePlayer) l_gameplayObject.getValue();
			if(gameEngine.l_gamePlayerObject.hasConqueredInTurn) {
				int randomCardValue = new Random().nextInt(4);
				GamePlayer object = gameEngine.l_gamePlayerObject;
				List<String> stringCardNames = new ArrayList<>();
				for(String keyString : object.getSpecialCards().keySet())
				{
					stringCardNames.add(keyString);
				}
				object.getSpecialCards().replace(stringCardNames.get(randomCardValue), object.getSpecialCards().get(stringCardNames.get(randomCardValue))+1);
			}
		}
	}
}
