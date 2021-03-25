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

public class NegotiateCard extends Order{
	public String playerID;
	public GamePlayer gamePlayerObject;
	public NegotiateCard(String playerID) {
		this.playerID = playerID;
	}
	
	public void execute() {
		
	}
	
}
