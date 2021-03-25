package aatral.warzone.gameplay;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AirliftCard extends Order{
	public String sourceCountryID;
	public String targetCountryID;
	public String numArmies;
	public GamePlayer gamePlayerObject;
	public AirliftCard(String sourceCountryID, String targetCountryID, String numArmies) {
		this.sourceCountryID = sourceCountryID;
		this.targetCountryID = targetCountryID;
		this.numArmies = numArmies;
	}
	
	public void execute() {
		
	}
	
}
