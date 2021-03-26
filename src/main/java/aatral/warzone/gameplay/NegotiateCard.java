package aatral.warzone.gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aatral.warzone.model.Countries;
import aatral.warzone.observerPattern.LogEntryBuffer;
import aatral.warzone.observerPattern.LogWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * <h1>NegotiateCard</h1> The Class is used to prevents allied players from
 * attacking each other
 *
 * @author Manimaran
 * @version 1.0
 * @since 24-02-2021
 */
public class NegotiateCard extends Order {
	public String playerID;
	public GamePlayer gamePlayerObject;
	public HashMap<String, GamePlayer> gamePlayerObjectList;
	
	LogEntryBuffer log = new LogEntryBuffer();
	LogWriter logWriter = new LogWriter(log);

	public NegotiateCard(String playerID) {
		this.playerID = playerID;
	}

	/**
	 * execute method is used to execute the negotiate card function
	 */
	public void execute() {
		ArrayList<Order> removableObjs = new ArrayList<>();
		for (int i = 0; i < gamePlayerObject.getOrderObjects().size(); i++) {
			if (gamePlayerObject.getOrderObjects().get(i) instanceof AdvanceOrder) {
				String l_countryToName = ((AdvanceOrder) (gamePlayerObject.getOrderObjects().get(i)))
						.getCountryToName();
				for (Map.Entry gamePlayerObj : gamePlayerObjectList.entrySet()) {
					if (gamePlayerObj.getKey().equals(playerID)) {
						List<Countries> countriesUnderOpponent = (((GamePlayer) (gamePlayerObj.getValue()))
								.getListOfCountries());
						for (Countries country : countriesUnderOpponent) {
							if (country.getCountryName().equals(l_countryToName)) {
								removableObjs.add(gamePlayerObject.getOrderObjects().get(i));
							}
						}
					}
				}
			}
			if (gamePlayerObject.getOrderObjects().get(i) instanceof BombCard) {
				String l_countryId = ((BombCard) (gamePlayerObject.getOrderObjects().get(i))).getCountryID();
				for (Map.Entry gamePlayerObj : gamePlayerObjectList.entrySet()) {
					if (gamePlayerObj.getKey().equals(playerID)) {
						List<Countries> countriesUnderOpponent = (((GamePlayer) (gamePlayerObj.getValue()))
								.getListOfCountries());
						for (Countries country : countriesUnderOpponent) {
							if (country.getCountryId().equals(l_countryId)) {
								removableObjs.add(gamePlayerObject.getOrderObjects().get(i));
							}
						}
					}
				}
			}
		}
		gamePlayerObject.getOrderObjects().removeAll(removableObjs);
		removableObjs = new ArrayList<Order>();
		for (Map.Entry gamePlayerObj : gamePlayerObjectList.entrySet()) {
			if (gamePlayerObj.getKey().equals(playerID)) {
				for (int i = 0; i < ((GamePlayer) gamePlayerObj.getValue()).getOrderObjects().size(); i++) {
					if (((GamePlayer) gamePlayerObj.getValue()).getOrderObjects().get(i) instanceof AdvanceOrder) {
						String l_countryToName = ((AdvanceOrder) (((GamePlayer) gamePlayerObj.getValue())
								.getOrderObjects().get(i))).getCountryToName();
						List<Countries> countriesUnderOpponent = gamePlayerObject.getListOfCountries();
						for (Countries country : countriesUnderOpponent) {
							if (country.getCountryName().equals(l_countryToName)) {
								removableObjs.add(((GamePlayer) gamePlayerObj.getValue()).getOrderObjects().get(i));
							}
						}
					}
					if (((GamePlayer) gamePlayerObj.getValue()).getOrderObjects().get(i) instanceof BombCard) {
						String l_countryId = ((BombCard) (((GamePlayer) gamePlayerObj.getValue()).getOrderObjects()
								.get(i))).getCountryID();
						List<Countries> countriesUnderOpponent = gamePlayerObject.getListOfCountries();
						for (Countries country : countriesUnderOpponent) {
							if (country.getCountryId().equals(l_countryId)) {
								removableObjs.add(((GamePlayer) gamePlayerObj.getValue()).getOrderObjects().get(i));
							}
						}
					}
				}
				((GamePlayer) gamePlayerObj.getValue()).getOrderObjects().removeAll(removableObjs);
				break;
			}
		}
		
		log.info("specialOrderExecution", gamePlayerObject.getPlayerName(),"negotiate "+this.playerID, "executed");	

		System.out.println(gamePlayerObject.getPlayerName()+ " has executed negotiate for the player ID "+this.playerID);
		
	}
}
