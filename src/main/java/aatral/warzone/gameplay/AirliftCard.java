package aatral.warzone.gameplay;

import java.util.HashMap;
import java.util.Map.Entry;

import aatral.warzone.model.Continent;
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
 * <h1>AirliftCard</h1> The Class is the transfer the armies under the same
 * player without having the restriction of adjacency
 *
 * @author Tejeswini
 * @version 1.0
 * @since 24-02-2021
 */
public class AirliftCard extends Order {
	public String sourceCountryID;
	public String targetCountryID;
	public String numArmies;
	public GamePlayer gamePlayerObject;

	LogEntryBuffer log = new LogEntryBuffer();
	LogWriter logWriter = new LogWriter(log);

	public AirliftCard(String sourceCountryID, String targetCountryID, String numArmies) {
		this.sourceCountryID = sourceCountryID;
		this.targetCountryID = targetCountryID;
		this.numArmies = numArmies;
	}

	/**
	 * execute method is used to execute the game play
	 */
	public void execute() {
		if (validateFromCountryName(this.gamePlayerObject, this.sourceCountryID, this.numArmies)) {
			for (Entry<String, Continent> l_mapEntry : GameEngine.l_masterMap.entrySet()) {
				for (Countries l_countryObject : ((Continent) l_mapEntry.getValue()).getContinentOwnedCountries()) {
					if (l_countryObject.getCountryId().equals(sourceCountryID)) {
						l_countryObject.setArmies(l_countryObject.getArmies() - Integer.parseInt(numArmies));
					}
					if (l_countryObject.getCountryId().equals(targetCountryID)) {
						l_countryObject.setArmies(l_countryObject.getArmies() + Integer.parseInt(numArmies));
					}
				}
			}
			log.info("specialOrderExecution", gamePlayerObject.getPlayerName(),
					"airlift " + this.sourceCountryID + " " + this.targetCountryID + " " + this.numArmies, "executed");
			System.out.println(gamePlayerObject.getPlayerName() + " has executed airlift for the country ID"
					+ this.sourceCountryID + " to" + this.targetCountryID + " with armies " + this.numArmies);
		} else {
			log.info("specialOrderExecution", gamePlayerObject.getPlayerName(),
					"advance " + this.sourceCountryID + " " + this.targetCountryID + " " + this.numArmies,
					"not executed- as either fromCountry or toCountry is already captured by other player"
							+ "or fromCountry does not have enough armies as mentioned in the issueOrder");
			System.out
					.println(this.gamePlayerObject.getPlayerName() + " has not executed advance order for the country "
							+ this.sourceCountryID + " to " + this.targetCountryID
							+ "not executed- as either fromCountry or toCountry is already captured by other player"
							+ "or fromCountry does not have enough armies as mentioned in the issueOrder");
		}
	}

	/**
	 * validateFromCountryName will validate if country belongs to the player
	 * 
	 * @param l_gamePlayerObject has the game player object
	 * @param countryFromName    has the country from name
	 * @return boolean true if Valid, else false
	 */
	public boolean validateFromCountryName(GamePlayer l_gamePlayerObject, String countryFromID, String numArmies) {
		for (Countries l_countryObject : l_gamePlayerObject.getListOfCountries()) {
			if (l_countryObject.getCountryId().equals(countryFromID)
					&& (l_countryObject.getArmies() >= Integer.parseInt(numArmies)
							&& Integer.parseInt(numArmies) > 0)) {
				return true;
			}
		}
		return false;
	}

}
