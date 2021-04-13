package aatral.warzone.strategyPattern;

import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;

public abstract class PlayerStrategy {
	public GamePlayer gamePlayerObject; 
	public abstract Order createOrder();
	
	public PlayerStrategy(GamePlayer p_player) {
		this.gamePlayerObject = p_player;
	}
}
