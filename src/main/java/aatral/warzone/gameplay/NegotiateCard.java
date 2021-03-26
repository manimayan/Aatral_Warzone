package aatral.warzone.gameplay;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * <h1>NegotiateCard</h1> The Class is used to prevents allied players from attacking each other 
 *
 * @author Manimaran
 * @version 1.0
 * @since 24-02-2021
 */
public class NegotiateCard extends Order{
	public String playerID;
	public GamePlayer gamePlayerObject;
	public HashMap<String, GamePlayer> gamePlayerObjectList;
	public NegotiateCard(String playerID) {
		this.playerID = playerID;
	}
/**
 * execute method is used to execute the negotiate card function
 */
	public void execute() {
		
		gamePlayerObject.diplomacyCountries.add(this.playerID);
		for(Map.Entry gamePlayerObj : gamePlayerObjectList.entrySet())
		{
			if(gamePlayerObj.getKey().equals(this.playerID))
			{
				((GamePlayer)gamePlayerObj.getValue()).getDiplomacyCountries().add(gamePlayerObject.getPlayerName());
				break;
			}
		}
	}
	
}
